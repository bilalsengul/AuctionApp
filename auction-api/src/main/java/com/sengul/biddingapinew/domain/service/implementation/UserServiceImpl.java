package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.autobid.UpdateAutoBidDefinitionsRequest;
import com.sengul.biddingapinew.application.request.user.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.AutoBidDefinition;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.model.User;
import com.sengul.biddingapinew.domain.service.UserService;
import com.sengul.biddingapinew.infrastructure.repository.AutoBidDefinitionRepository;
import com.sengul.biddingapinew.infrastructure.repository.BidRepository;
import com.sengul.biddingapinew.infrastructure.repository.UserRepository;
import com.sengul.biddingapinew.infrastructure.utils.enums.HttpHeaders;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BidRepository bidRepository;

    private final AutoBidDefinitionRepository autoBidDefinitionRepository;

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

            if (request.getBudget() != null) {
                user.setBudget(request.getBudget());
            }

            if (request.getBalance() != null) {
                user.setBalance(request.getBalance());
            }

            if (request.getItems() != null) {
                user.setItems(request.getItems());
            }

            if (request.getAutoBidBalance() != null) {
                user.setAutoBidBalance(request.getAutoBidBalance());
            }

            if (request.getAutoBidBudget() != null) {
                double autoBidBudget = user.getAutoBidBudget() != null ? user.getAutoBidBudget() : 0.0;
                double change = request.getAutoBidBudget() - autoBidBudget;
                double budget = user.getBudget() != null ? user.getBudget() : 0.0;

                if ((budget == 0.0 && autoBidBudget > 0.0) || (change > budget)) {
                    throw new BadRequestException("Insufficient budget!");
                }
                user.setAutoBidBudget(request.getAutoBidBudget());
                user.setBudget(budget + change);
            }

            if (request.getAutoBidBalanceNotificationThreshold() != null) {
                user.setAutoBidBalanceNotificationThreshold(request.getAutoBidBalanceNotificationThreshold());
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

    @Override
    public List<AutoBidDefinition> updateAutoBidDefinitions(String id, UpdateAutoBidDefinitionsRequest request) throws UserNotFoundException, BadRequestException {
        log.info("Started handling request= " + request);

        User user = this.get(id);
        List<AutoBidDefinition> currentDefinitions = autoBidDefinitionRepository.findByUserId(id);
        List<AutoBidDefinition> newDefinitions = request.getDefinitions();
        Double usersAutoBidBudget = user.getAutoBidBudget();

        validateDefinitions(user, currentDefinitions, newDefinitions);

        List<String> notConfiguredDefinitionIds = currentDefinitions.stream().filter(x -> x.getBudget() == 0.0).map(AutoBidDefinition::getId).toList();

        newDefinitions.forEach(newDefinition -> {
            Double newDefinitionBudget = usersAutoBidBudget * (newDefinition.getBudgetPercentage() / 100.0);
            newDefinition.setBudget(newDefinitionBudget);
            if (notConfiguredDefinitionIds.contains(newDefinition.getId())) {
                newDefinition.setBalance(newDefinitionBudget);
                newDefinition.setActive(true);
            }
            autoBidDefinitionRepository.save(newDefinition);
        });

        log.info("Finished handling request= " + request);
        return null;
    }

    @Override
    public List<AutoBidDefinition> getAutoBidDefinitions(String id) {
        log.info("Started handling request= GetAutoBidDefinitions(id: " + id + ")");

        List<AutoBidDefinition> result = autoBidDefinitionRepository.findByUserId(id);

        log.info("Finished handling request= GetAutoBidDefinitions(id: " + id + ")");
        return result;
    }

    private void validateDefinitions(User user, List<AutoBidDefinition> currentDefinitions, List<AutoBidDefinition> newDefinitions) throws BadRequestException {
        if (user.getAutoBidBudget() == null || user.getAutoBidBudget() <= 0) {
            throw new BadRequestException("Please allocate Auto Bid Budget first!");
        }

        if (newDefinitions.stream().mapToDouble(AutoBidDefinition::getBudgetPercentage).sum() != 100.0) {
            throw new BadRequestException("Sum of budget percentage must be 100.0!");
        }

        List<AutoBidDefinition> usedDefinitions = currentDefinitions.stream().filter(x -> !x.getBudget().equals(x.getBalance())).toList();
        Double usersAutoBidBudget = user.getAutoBidBudget();
        List<String> validationErrors = new ArrayList<>();

        if (!usedDefinitions.isEmpty()) {
            usedDefinitions.forEach(oldDefinition -> {
                Optional<AutoBidDefinition> optionalNewDefinition = newDefinitions.stream().filter(x -> Objects.equals(x.getId(), oldDefinition.getId())).findFirst();
                if (optionalNewDefinition.isPresent()) {
                    AutoBidDefinition newDefinition = optionalNewDefinition.get();
                    double oldDefinitionExpense = oldDefinition.getBudget() - oldDefinition.getBalance();
                    double newDefinitionBudget = usersAutoBidBudget * (newDefinition.getBudgetPercentage() / 100.0);

                    if (oldDefinitionExpense > newDefinitionBudget) {
                        validationErrors.add("Old auto bidding expense of item with id: " +
                                oldDefinition.getItemId() +
                                " is " +
                                oldDefinitionExpense +
                                " and new auto bidding budget is " +
                                newDefinitionBudget +
                                "! You cannot set higher budget than expense!");
                    }
                }
            });

            if (!validationErrors.isEmpty()) {
                throw new BadRequestException(String.join("/n", validationErrors));
            }
        }
    }
}