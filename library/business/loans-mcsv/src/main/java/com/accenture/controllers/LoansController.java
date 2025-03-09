package com.accenture.controllers;

import com.accenture.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoansController {

    private final Logger logger = LoggerFactory.getLogger(LoansController.class);

    @Autowired
    private LoanService loanService;

    @GetMapping("loan-book/{isbn}")
    public ResponseEntity<String> isAvailable(@PathVariable String isbn){
        boolean isAvailable=loanService.checkIfBookIsInInventory(isbn);
        String response;
        if(isAvailable){
            response = "Book is available for loan";
        }else {
            response = "Book is no available";
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
