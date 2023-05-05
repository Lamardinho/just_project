package com.example.just_project.project_reports.dto;

import com.example.just_project.common.dto.IdAndName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XlsxTestDto {
    private Integer id;
    private String title;
    private boolean active;
    private IdAndName idAndName;
}
