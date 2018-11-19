package com.loanbroker.commons.repository;

import com.loanbroker.commons.model.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, Integer> {
}
