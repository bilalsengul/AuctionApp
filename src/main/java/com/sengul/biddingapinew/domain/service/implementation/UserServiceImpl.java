package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.user.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.model.User;
import com.sengul.biddingapinew.domain.service.UserService;
import com.sengul.biddingapinew.infrastructure.repository.BidRepository;
import com.sengul.biddingapinew.infrastructure.repository.UserRepository;
import com.sengul.biddingapinew.infrastructure.utils.enums.HttpHeaders;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BidRepository bidRepository;

    @Override
    public User get(String id) throws UserNotFoundException {
        log.info("Started handling request= Get(id: " + id + ")");

        Optional<User> result = userRepository.findById(id);

        result.orElseThrow(() -> new UserNotFoundException(id));

        log.info("Finished handling request= Get(id: " + id + ")");
        return result.get();
    }

    @Override
    public User update(String id, UpdateUserRequest request) throws UserNotFoundException, BadRequestException {
        log.info("Started handling request= " + request);

        User user = this.get(id);

        String sessionUserId = MDC.get(HttpHeaders.X_USER_ID.key());

        User sessionUser = this.get(sessionUserId);

        if (user.getId().equals(sessionUser.getId())) {

            if (request.getName() != null) {
                user.setName(request.getName());
            }

            if (request.getSurname() != null) {
                user.setSurname(request.getSurname());
            }

            if (request.getPassword() != null) {
                user.setPassword(request.getPassword());
            }

            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
        } else {
            throw new BadRequestException("You cannot update users any info!");
        }

        user.setUpdatedDate(System.currentTimeMillis());

        userRepository.save(user);

        log.info("Finished handling request= " + request);
        return user;
    }

    @Override
    public void addNewItem(String id, Item item) throws UserNotFoundException {
        log.info("Started handling request= AddNewItem(id: " + id + ", item:" + item + ")");

        User user = this.get(id);
        user.addItem(item);
        userRepository.save(user);

        log.info("Finished handling request= AddNewItem(id: " + id + ", item:" + item + ")");
    }

    @Override
    public List<Bid> getBids(String id) {
        log.info("Started handling request= GetBids(id: " + id + ")");

        List<Bid> result = bidRepository.findByUserId(id);

        log.info("Finished handling request= GetBids(id: " + id + ")");
        return result;
    }

    @Override
    public void refundBidsOnItem(String id, @NotNull String itemId) throws UserNotFoundException {
        log.info("Started handling request= RefundBidsOnItem(id: " + id + ", itemId: " + itemId + ")");
        User refundUser = this.get(id);
        List<Bid> refundBids = this.getBids(id).stream().filter(bid -> Objects.equals(bid.getItemId(), itemId)).toList();
        if (!refundBids.isEmpty()) {
            Bid maximumBid = refundBids.stream().max(Comparator.comparingDouble(Bid::getPrice)).get();
            Double refundPrice = maximumBid.getPrice();
            Double currentBalance = refundUser.getBudget();
            refundUser.setBudget(currentBalance + refundPrice);
            userRepository.save(refundUser);
        }
        log.info("Finished handling request= RefundBidsOnItem(id: " + id + ", itemId: " + itemId + ")");
    }
}