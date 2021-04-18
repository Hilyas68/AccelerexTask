package com.globalaccelerex.openinghours.controller;

import com.globalaccelerex.openinghours.service.OpenHourService;
import com.globalaccelerex.openinghours.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/open-hour")
public class OpenHourController {

    @Autowired
    OpenHourService service;

    @GetMapping(
            value = "/convert/{jsonString}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> recentTransactions(@PathVariable("jsonString") String jsonString) {
        return CompletableFuture.completedFuture(service.getReadableOpenHours(jsonString));
    }
}
