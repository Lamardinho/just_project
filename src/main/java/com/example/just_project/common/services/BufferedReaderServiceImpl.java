package com.example.just_project.common.services;

import com.example.just_project.common.services.contract.BufferedReaderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

@Service
@RequiredArgsConstructor
public class BufferedReaderServiceImpl implements BufferedReaderService {

    @Override
    @SneakyThrows
    public String readAll(BufferedReader bufferedReader) {
        val stringBuilder = new StringBuilder();
        int i;
        while ((i = bufferedReader.read()) != -1) {
            stringBuilder.append((char) i);
        }
        return stringBuilder.toString();
    }
}
