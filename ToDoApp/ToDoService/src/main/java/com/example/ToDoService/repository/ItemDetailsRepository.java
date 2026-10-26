package com.example.ToDoService.repository;

import com.example.ToDoService.entity.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailsRepository extends JpaRepository<ItemDetails,Long> {
}
