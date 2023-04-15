package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String flag;
    private int id;
    private String name;
    private String slug;
    private SportDTO sport;
}
