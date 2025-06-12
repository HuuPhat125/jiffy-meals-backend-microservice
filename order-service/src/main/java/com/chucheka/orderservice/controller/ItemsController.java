package com.chucheka.orderservice.controller;


import com.chucheka.orderservice.dto.GenericResponse;
import com.chucheka.orderservice.dto.ItemDto;
import com.chucheka.orderservice.entities.Item;
import com.chucheka.orderservice.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemService itemService;

    @GetMapping("")
    public Page<Item> getItems(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) String sort) {

        return itemService.getItems(page, size, sort);

    }

    @PostMapping("")
    public GenericResponse<Item> createItems(@RequestBody @Valid ItemDto itemDto ){

        return itemService.createItem(itemDto);

    }
}
