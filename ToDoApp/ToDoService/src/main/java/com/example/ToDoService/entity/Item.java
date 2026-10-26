package com.example.ToDoService.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="title")
    String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemDetails_id")
    ItemDetails itemDetails;
}
