package com.example.just_project.project_exchangerate.services;

import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "cbrrates", url = "plug_because_the_dynamic_address")
public interface CbrRubleRatesClient {

    @GetMapping
    ValCurs getRubleRatesFromCbrXmlUrl(URI uri);
}
