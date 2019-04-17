package br.com.erick.monese.mock;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.DepositDTO;
import br.com.erick.monese.repository.entity.DepositEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositBuilder {

    public static final LocalDateTime TRANSFER_TIME = LocalDateTime.of(2019, 2, 14, 14, 20);
    private final String TRANSACTION_ID = "dce7c73d-52b7-47c0-a5cd-c5cd84196e40";
    public static final String ACCOUNT_NUMBER = "2654987415";
    private final BigDecimal AMOUNT = BigDecimal.valueOf(125);

    private String destinationAccountNumber;
    private LocalDateTime instant;
    private String transactionId;
    private BigDecimal amount;

    public static DepositBuilder getInstance() {
        return new DepositBuilder();
    }

    public DepositBuilder withTransactionId() {
        transactionId = TRANSACTION_ID;
        return this;
    }

    public DepositBuilder withInstant() {
        instant = TRANSFER_TIME;
        return this;
    }

    public DepositBuilder withDestinationAccount() {
        destinationAccountNumber = ACCOUNT_NUMBER;
        return this;
    }

    public DepositBuilder withAmount() {
        amount = AMOUNT;
        return this;
    }

    public DepositDTO buildDTO() {
        DepositDTO dto = new DepositDTO();
        dto.setAmount(amount);
        dto.setDestination(new AccountDTO(destinationAccountNumber));
        dto.setInstant(instant);
        dto.setTransactionId(transactionId);
        return dto;
    }

    public DepositEntity buildEntity() {
        DepositEntity entity = new DepositEntity();
        entity.setAmount(amount);
        entity.setAccountNumber(destinationAccountNumber);
        entity.setInstant(instant);
        entity.setTransactionId(transactionId);
        return entity;
    }
}
