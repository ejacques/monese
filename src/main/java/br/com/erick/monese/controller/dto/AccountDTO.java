package br.com.erick.monese.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties
public class AccountDTO {

    private String number;
    private PersonDTO owner;
    private BigDecimal balance;

    public AccountDTO() {
    }

    public AccountDTO(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonDTO getOwner() {
        return owner;
    }

    public void setOwner(PersonDTO owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO dto = (AccountDTO) o;
        return Objects.equals(number, dto.number) &&
                Objects.equals(owner, dto.owner) &&
                Objects.equals(balance, dto.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, owner, balance);
    }
}
