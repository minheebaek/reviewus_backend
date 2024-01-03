package com.example.backend.dto.object;

import com.example.backend.entity.GrassEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrassItemDto {
    private int postCount;
    private String grassDate;

    public GrassItemDto(GrassEntity grassEntity){
        this.postCount=grassEntity.getPostCount();
        this.grassDate=grassEntity.getGrassDate();
    }
}
