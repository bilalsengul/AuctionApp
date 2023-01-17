package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.User;
import com.sengul.biddingapinew.domain.service.UserService;
import com.sengul.biddingapinew.infrastructure.repository.UserRepository;
import com.sengul.biddingapinew.infrastructure.utils.enums.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

}