package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.ItemNotFoundException;
import com.sengul.biddingapinew.application.request.autobid.AutoBidOnItemRequest;
import com.sengul.biddingapinew.domain.model.AutoBidDefinition;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.service.AutoBidDefinitionService;
import com.sengul.biddingapinew.infrastructure.repository.AutoBidDefinitionRepository;
import com.sengul.biddingapinew.infrastructure.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoBidDefinitionServiceImpl implements AutoBidDefinitionService {

    private final AutoBidDefinitionRepository autoBidDefinitionRepository;

    private final ItemRepository itemRepository;

    @Override
    public AutoBidDefinition create(String itemId, AutoBidOnItemRequest request) throws ItemNotFoundException, BadRequestException {
        log.info("Started handling request= Create(itemId: " + itemId + ", userId: " + request.getUserId() + ")");

        Optional<Item> itemResult = itemRepository.findById(itemId);

        Item item = itemResult.orElseThrow(() -> new ItemNotFoundException(itemId));

        if (!item.getOnAuction()) {
            throw new BadRequestException("You cannot set auto bid on item which is not actively on auction!");
        }

        AutoBidDefinition newDefinition = new AutoBidDefinition(
                request.getUserId(),
                itemId,
                request.getBidPrice()
        );

        autoBidDefinitionRepository.save(newDefinition);

        log.info("Finished handling request= Create(itemId: " + itemId + ", userId: " + request.getUserId() + ")");
        return newDefinition;
    }
}