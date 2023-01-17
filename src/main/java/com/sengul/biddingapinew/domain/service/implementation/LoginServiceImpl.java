package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.global.LoginRequest;
import com.sengul.biddingapinew.application.response.LoginResponse;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.model.User;
import com.sengul.biddingapinew.domain.service.LoginService;
import com.sengul.biddingapinew.infrastructure.repository.ItemRepository;
import com.sengul.biddingapinew.infrastructure.repository.UserRepository;
import com.sengul.biddingapinew.infrastructure.utils.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public LoginServiceImpl(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        prepareInitialData();
    }

    @Override
    public LoginResponse login(LoginRequest request) throws UserNotFoundException {
        log.info("Started handling request= username: " + request);

        Optional<User> result = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        result.orElseThrow(() -> new UserNotFoundException(request.getUsername()));

        User user = result.get();

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRoles(),
                user.getEmail()
        );

        log.info("Finished handling request= username: " + request);

        return response;
    }

    private void prepareInitialData() {
        User admin = new User(
                "bilal.sengul",
                "pass",
                "Bilal",
                "Şengül",
                "bilalsengul@scopic.com",
                10000.0,
                1000.0,
                List.of(UserRole.ADMIN, UserRole.REGULAR)
        );
        User admin2 = new User(
                "admin.username",
                "adminpass",
                "AdminName",
                "AdminSurname",
                "admin@scopic.com",
                10000.0,
                1000.0,
                List.of(UserRole.ADMIN, UserRole.REGULAR)
        );
        User regular = new User(
                "regular.username",
                "regularpass",
                "RegularName",
                "RegularSurname",
                "regular@scopic.com",
                10000.0,
                1000.0,
                List.of(UserRole.REGULAR)
        );
        User regular2 = new User(
                "regular2.username",
                "regular2pass",
                "Regular2Name",
                "Regular2Surname",
                "regular2@scopic.com",
                10000.0,
                1000.0,
                List.of(UserRole.REGULAR)
        );
        Item item = new Item(
                "DenemeItem1",
                "DenemeItem1Description",
                100.0
        );
        Item item2 = new Item(
                "DenemeItem2",
                "DenemeItem2Description",
                125.0
        );
        Item item3 = new Item(
                "DenemeItem3",
                "DenemeItem3Description",
                150.0
        );
        userRepository.deleteAll();
        itemRepository.deleteAll();
        List<User> users = List.of(admin, admin2, regular, regular2);
        List<Item> items = List.of(item, item2, item3);
        userRepository.saveAll(users);
        itemRepository.saveAll(items);
        users.forEach(x -> System.out.println("UserName: " + x.getName() + " ID: " + x.getId()));
        items.forEach(y -> System.out.println("ItemName: " + y.getName() + " ID: " + y.getId()));
    }
}