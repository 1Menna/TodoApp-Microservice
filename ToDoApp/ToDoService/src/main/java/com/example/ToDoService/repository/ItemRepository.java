package com.example.ToDoService.repository;

import com.example.ToDoService.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
