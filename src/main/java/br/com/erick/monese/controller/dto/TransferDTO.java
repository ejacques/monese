package br.com.erick.monese.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class TransferDTO extends OperationDTO {

    private static final String TYPE = "TRANSFER";

    private AccountDTO source;
    private AccountDTO destination;

    public String getType() {
        return TYPE;
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
