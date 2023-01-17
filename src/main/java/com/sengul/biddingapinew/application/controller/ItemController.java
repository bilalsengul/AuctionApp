package com.sengul.biddingapinew.application.controller;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.ItemNotFoundException;
import com.sengul.biddingapinew.application.request.item.CreateItemRequest;
import com.sengul.biddingapinew.application.request.item.SearchItemRequest;
import com.sengul.biddingapinew.application.request.item.UpdateItemRequest;
import com.sengul.biddingapinew.application.response.SearchItemResponse;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@Valid @RequestBody CreateItemRequest request) {
        return itemService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Item get(@NotNull @NotBlank @PathVariable String id) throws ItemNotFoundException {
        return itemService.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Item update(@NotNull @NotBlank @PathVariable String id, @RequestBody UpdateItemRequest request) throws ItemNotFoundException, BadRequestException {
        return itemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@NotNull @NotBlank @PathVariable String id) throws ItemNotFoundException {
        itemService.delete(id);
    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchItemResponse search(@Valid @RequestBody SearchItemRequest request) {
        return itemService.search(request);
    }
}