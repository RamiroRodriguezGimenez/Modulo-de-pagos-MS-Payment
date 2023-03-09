package com.payment.entity;

import com.payment.dto.response.InternalResponse;
import com.payment.feignclient.AccountFeignClient;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @NotNull
    private String accountOrigin;
    @NotNull
    private String accountDestination;
    @NotNull
    @Positive
    @Digits(integer = 99, fraction = 2)
    private BigDecimal amount;

    private LocalDate date;

    private Status status;

    public Transaction() {
    }

    public Transaction(String accountOrigin, String accountDestination, BigDecimal amount) {
        this.accountOrigin = accountOrigin;
        this.accountDestination = accountDestination;
        this.amount = amount;
        this.date = LocalDate.now();
        this.status = Status.OUTSTANDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(String accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public InternalResponse validate(AccountFeignClient accountFeignClient, String accessToken){
        return null;}

    public InternalResponse execute(AccountFeignClient accountFeignClient, String accessToken){
    return null;}

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountOrigin='" + accountOrigin + '\'' +
                ", accountDestination='" + accountDestination + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
