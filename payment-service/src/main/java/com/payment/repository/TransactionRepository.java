package com.payment.repository;

import com.payment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long>  {

    List<Transaction> findByAccountOriginAndDateBetween(String accountOrigin, LocalDate beforeDate, LocalDate afterDate);
}
