package br.com.erick.monese.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DepositDTO extends OperationDTO {

    private static final String TYPE = "DEPOSIT";

    public AccountDTO destination;

    public String getType() {
        return TYPE;
    }

    public AccountDTO getDestination() {
        return destination;
    }

    public void setDestination(AccountDTO destination) {
        this.destination = destination;
    }
}
