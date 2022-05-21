package com.example.just_project.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContractResult<T> {

    private T data;

    private List<String> violations = new ArrayList<>();

    public ContractResult(T data) {
        this.setData(data);
    }
}
