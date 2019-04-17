package br.com.erick.monese.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Valid
public class TransferRequestDTO {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private AccountDTO source;
    @NotNull
    private AccountDTO destination;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountDTO getSource() {
        return source;
    }

    public void setSource(AccountDTO source) {
        this.source = source;
    }

    public AccountDTO getDestination() {
        return destination;
    }

    public void setDestination(AccountDTO destination) {
        this.destination = destination;
    }
}
