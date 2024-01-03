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
@Entity(name = "Grass")
@Table(name = "Grass")
public class GrassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grassId;
    private Long userId;
    @Column(name = "post_count")
    private int postCount;
    @Column(name = "grass_date")
    private String grassDate;

    public GrassEntity(Long userId, String localDate) {
        this.userId = userId;
        this.postCount = 1;
        this.grassDate = localDate;
    }

    public void updateGrass(GrassEntity grassEntity) {
        this.postCount = grassEntity.getPostCount() + 1;
    }
}
