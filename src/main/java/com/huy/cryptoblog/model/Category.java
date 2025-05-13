package com.huy.cryptoblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String slug;
}
