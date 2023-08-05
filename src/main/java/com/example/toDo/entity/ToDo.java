package com.example.toDo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean open;

    @PrePersist
    public void prePersist() {
        this.open = true;
    }

}
