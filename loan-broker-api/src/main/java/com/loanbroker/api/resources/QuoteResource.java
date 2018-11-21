package com.loanbroker.api.resources;

import com.loanbroker.api.LoanBrokerApiApplication;
import com.loanbroker.commons.db.Result;
import com.loanbroker.commons.db.ResultRepository;
import com.loanbroker.commons.model.QuoteRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("quote")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {

    @Autowired
    ResultRepository repository;

    @Autowired
    RabbitTemplate template;

    @GetMapping("/{ssn}")
    public Result getQuote(@PathVariable("ssn") String ssn) {
        return repository.findById(ssn).get();
    }

    @PostMapping
    public QuoteResponse postQuote(@RequestBody @Valid QuoteRequest request) {
        template.convertAndSend(LoanBrokerApiApplication.creditScoreQueue, request);
        return new QuoteResponse(request.getSsn());
    }
}
