package com.chucheka.orderservice.services;

import com.chucheka.orderservice.dto.GenericResponse;
import com.chucheka.orderservice.dto.ItemDto;
import com.chucheka.orderservice.entities.Item;
import com.chucheka.orderservice.repositories.ItemRepository;
import com.chucheka.orderservice.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> getItems(Integer page, Integer size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt", sort).ascending());

        return itemRepository.findAll(pageable);

    }

    public GenericResponse<Item> createItem(ItemDto itemDto) {


        Item item = new Item();

        item.setName(itemDto.name());
        item.setPrice(itemDto.price());
        item.setImageUrl(itemDto.imageUrl());
        item.setRestaurantCode(itemDto.restaurantCode());
        item.setItemCode(AppUtils.generateUniqueCode());

        itemRepository.save(item);

        return new GenericResponse<>(true, "Item created successfully", item);

    }
}
