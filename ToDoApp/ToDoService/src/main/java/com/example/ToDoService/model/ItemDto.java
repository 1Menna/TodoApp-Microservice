package com.example.ToDoService.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDto {
    String title;

    String description;

    int priority;

    String status;
}
