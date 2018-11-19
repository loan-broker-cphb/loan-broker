package com.loanbroker.api.resources;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public String postQuote(@RequestBody @Valid QuoteRequest request) {
        return request.getLoanAmount().toPlainString();
    }
}
