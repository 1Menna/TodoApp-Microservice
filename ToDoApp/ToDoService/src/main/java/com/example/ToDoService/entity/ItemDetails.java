package com.example.ToDoService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="ItemDetails")
public class ItemDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="description")
    String description;

    @Column(name="created_at")
    Date created_at;

    @Column(name="priority")
    int priority;

    @Column(name="status")
    String status;

    @OneToOne(mappedBy = "itemDetails", fetch = FetchType.EAGER)
    Item item;

    @PrePersist
    private void onCreation(){
        this.created_at = new Date(System.currentTimeMillis());
    }
}

