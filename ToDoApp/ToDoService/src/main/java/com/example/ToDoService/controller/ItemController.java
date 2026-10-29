package com.example.ToDoService.controller;

import com.example.ToDoService.model.ItemDto;
import com.example.ToDoService.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<Object> addItem(@RequestBody ItemDto item){
        itemService.add(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto item, @PathVariable int id){
        itemService.update(id,item);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Object> searchItem(@PathVariable int id){
        return ResponseEntity.ok(itemService.search(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable int id){
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

}
