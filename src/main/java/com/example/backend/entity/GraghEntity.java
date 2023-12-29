package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Gragh")
@Table(name="Gragh")
public class GraghEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graghId;
    private Long userId;
    private int postCount;
    private String writeDatetime;

}
