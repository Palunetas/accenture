package com.accenture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoanService {

    @Autowired
    private RestTemplate restTemplate;

    public Boolean checkIfBookIsInInventory(String isbn){
        String url = "http://localhost:8090/api/book/"+isbn;
        return restTemplate.getForObject(url,Boolean.class);
    }
}
