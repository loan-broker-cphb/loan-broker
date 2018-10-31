package com.loanbroker.api.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("quote")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {

    @GetMapping
    public String getQuote() {
        return "Good one!";
    }
}
