package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.ItemNotFoundException;
import com.sengul.biddingapinew.application.request.item.CreateItemRequest;
import com.sengul.biddingapinew.application.request.item.SearchItemRequest;
import com.sengul.biddingapinew.application.request.item.UpdateItemRequest;
import com.sengul.biddingapinew.application.response.SearchItemResponse;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.service.ItemService;
import com.sengul.biddingapinew.infrastructure.repository.ItemRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public Item create(CreateItemRequest request) {
        log.info("Started handling request= " + request);

        Item newItem = new Item(
                request.getName(),
                request.getDescription(),
                request.getImage(),
                request.getOpeningPrice(),
                request.isOnAuction(),
                request.getAuctionEndDate()
        );

        itemRepository.save(newItem);

        log.info("Finished handling request= " + request);
        return newItem;
    }

    @Override
    public Item get(String id) throws ItemNotFoundException {
        log.info("Started handling request= " + id);

        Optional<Item> result = itemRepository.findById(id);

        result.orElseThrow(() -> new ItemNotFoundException(id));

        log.info("Finished handling request= " + id);
        return result.get();
    }

    @Override
    public Item update(String id, UpdateItemRequest request) throws ItemNotFoundException, BadRequestException {
        log.info("Started handling request= " + request);

        Item item = this.get(id);

        validateUpdate(item, request);

        if (request.getName() != null) {
            item.setName(request.getName());
        }

        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }

        if (request.getImagePath() != null) {
            item.setImage(request.getImagePath());
        }

        if (request.getOpeningPrice() != null) {
            item.setOpeningPrice(request.getOpeningPrice());
            item.setCurrentPrice(request.getOpeningPrice());
        }

        if (request.getCurrentPrice() != null) {
            item.setCurrentPrice(request.getCurrentPrice());
        }

        if (request.getAuctionEndDate() != null) {
            item.setAuctionEndDate(request.getAuctionEndDate());
        }

        if (request.getOnAuction() != null) {
            item.setOnAuction(request.getOnAuction());
        }

        item.setUpdatedDate(System.currentTimeMillis());

        itemRepository.save(item);

        log.info("Finished handling request= " + request);
        return item;
    }

    @Override
    public void delete(String id) throws ItemNotFoundException {
        log.info("Started handling request= Delete(id: " + id + ")");

        Item item = this.get(id);

        itemRepository.delete(item);

        log.info("Finished handling request= Delete(id: " + id + ")");
    }

    @Override
    public SearchItemResponse search(SearchItemRequest request) {
        log.info("Started handling request= " + request);

        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getSize());

        String searchKeyword;
        Criteria searchCriteria;
        Criteria searchNameCriteria = Criteria.where("name");
        Criteria searchDescriptionCriteria = Criteria.where("description");
        Criteria searchOnAuctionCriteria = Criteria.where("onAuction").is(true);

        if (request.getExactMatch()) {
            searchKeyword = request.getKeyword();
            searchNameCriteria.is(searchKeyword);
            searchDescriptionCriteria.is(searchKeyword);

        } else {
            searchKeyword = ".*" + request.getKeyword() + ".*";
            searchNameCriteria.regex(searchKeyword, "i");
            searchDescriptionCriteria.regex(searchKeyword, "i");

        }
        if (request.getSearchInDescription()) {
            searchCriteria = new Criteria().orOperator(searchNameCriteria, searchDescriptionCriteria);
        } else {
            searchCriteria = searchNameCriteria;
        }

        if (!request.getWithExpired()) {
            searchCriteria.andOperator(searchOnAuctionCriteria);
        }

        Query searchItemQuery = new Query().addCriteria(searchCriteria);

        Long total = mongoTemplate.count(searchItemQuery, Item.class);

        searchItemQuery = searchItemQuery.with(pageable);

        if (request.getSortByPriceDescending() != null) {
            Sort.Order sortOrder;
            if (request.getSortByPriceDescending()) {
                sortOrder = Sort.Order.desc("currentPrice");
            } else {
                sortOrder = Sort.Order.asc("currentPrice");
            }
            Sort sort = Sort.by(sortOrder);
            searchItemQuery = searchItemQuery.with(sort);
        }

        List<Item> result = mongoTemplate.find(searchItemQuery, Item.class);

        SearchItemResponse response = new SearchItemResponse(
                request.getSize(),
                request.getCurrentPage(),
                result.size(),
                total,
                result
        );

        log.info("Finished handling request= " + request);
        return response;
    }


    private void validateUpdate(@NotNull Item item, UpdateItemRequest request) throws BadRequestException {
        if (item.getOnAuction()) {
            if (request.anyFullNonUpdatableFieldWhileOnAuction()) {
                throw new BadRequestException("You cannot update item which is currently on auction!");
            }

            if (request.getAuctionEndDate() != null && item.getAuctionEndDate() != null && item.getAuctionEndDate() > request.getAuctionEndDate()) {
                throw new BadRequestException("You cannot lower auction time!");
            }
        }
    }
}