package com.sengul.biddingapinew.application.controller;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.autobid.UpdateAutoBidDefinitionsRequest;
import com.sengul.biddingapinew.application.request.user.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.AutoBidDefinition;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.model.User;
import com.sengul.biddingapinew.domain.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@NotNull @NotBlank @PathVariable String id) throws UserNotFoundException {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User update(@NotNull @NotBlank @PathVariable String id, @Valid @RequestBody UpdateUserRequest request) throws UserNotFoundException, BadRequestException {
        return userService.update(id, request);
    }

    @GetMapping("/{id}/bids")
    @ResponseStatus(HttpStatus.OK)
    public List<Bid> getBids(@NotNull @NotBlank @PathVariable String id) throws UserNotFoundException {
        return userService.getBids(id);
    }

    @PutMapping("/{id}/auto-bid-definitions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AutoBidDefinition> updateAutoBidDefinitions(@NotNull @NotBlank @PathVariable String id, @Valid @RequestBody UpdateAutoBidDefinitionsRequest request) throws UserNotFoundException, BadRequestException {
        return userService.updateAutoBidDefinitions(id, request);
    }

    @GetMapping("/{id}/auto-bid-definitions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AutoBidDefinition> getAutoBidDefinitions(@NotNull @NotBlank @PathVariable String id) {
        return userService.getAutoBidDefinitions(id);
    }
}