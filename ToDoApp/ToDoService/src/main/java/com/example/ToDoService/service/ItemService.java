package com.example.ToDoService.service;

import com.example.ToDoService.entity.Item;
import com.example.ToDoService.entity.ItemDetails;
import com.example.ToDoService.model.ItemDto;
import com.example.ToDoService.repository.ItemDetailsRepository;
import com.example.ToDoService.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    public void add(ItemDto itemDto){
        ItemDetails itemDetails = ItemDetails
                .builder()
                .priority(itemDto.getPriority())
                .description(itemDto.getDescription())
                .build();
        Item item = Item
                .builder()
                .title(itemDto.getTitle())
                .itemDetails(itemDetails)
                .build();

        itemRepository.save(item);
    }

//====================================================================================
    public void update(long id, ItemDto itemDto){
        Item item = itemRepository.getById(id);
        ItemDetails itemDetails = item.getItemDetails();

        item.setTitle(itemDto.getTitle());
        itemDetails.setDescription(itemDto.getDescription());
        itemDetails.setPriority(itemDto.getPriority());
        itemDetails.setStatus(itemDto.getStatus());

        item.setItemDetails(itemDetails);
        itemRepository.save(item);

    }
//====================================================================================

    public ItemDto search(long id){
        Item item = itemRepository.getById(id);
        ItemDto itemDto = new ItemDto();
        itemDto.setTitle(item.getTitle());
        itemDto.setDescription(item.getItemDetails().getDescription());
        itemDto.setPriority(item.getItemDetails().getPriority());
        itemDto.setStatus(item.getItemDetails().getStatus());

        return itemDto;
    }
//====================================================================================

    public void delete(long id){
        itemRepository.deleteById(id);
    }
//====================================================================================

}
