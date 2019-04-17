package br.com.erick.monese.mock;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.repository.entity.TransferEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferBuilder {

    public static final LocalDateTime TRANSFER_TIME = LocalDateTime.of(2019, 2, 14, 14, 20);
    private final String TRANSACTION_ID = "7ce7c73d-52b7-47c0-a5cd-c5cd84196e40";
    public static final String SOURCE_ACCOUNT_NUMBER = "1968974456";
    public static final String DESTINATION_ACCOUNT_NUMBER = "2654987415";
    private final BigDecimal AMOUNT = BigDecimal.valueOf(500);

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private LocalDateTime instant;
    private String transactionId;
    private BigDecimal amount;

    public static TransferBuilder getInstance() {
        return new TransferBuilder();
    }

    public TransferBuilder withTransactionId() {
        transactionId = TRANSACTION_ID;
        return this;
    }

    public TransferBuilder withInstant() {
        instant = TRANSFER_TIME;
        return this;
    }

    public TransferBuilder withSourceAccount() {
        sourceAccountNumber = SOURCE_ACCOUNT_NUMBER;
        return this;
    }

    public TransferBuilder withDestinationAccount() {
        destinationAccountNumber = DESTINATION_ACCOUNT_NUMBER;
        return this;
    }

    public TransferBuilder withAmount() {
        amount = AMOUNT;
        return this;
    }

    public TransferDTO buildDTO() {
        TransferDTO dto = new TransferDTO();
        dto.setAmount(amount);
        dto.setDestination(new AccountDTO(destinationAccountNumber));
        dto.setSource(new AccountDTO(sourceAccountNumber));
        dto.setInstant(instant);
        dto.setTransactionId(transactionId);
        return dto;
    }

    public TransferEntity buildEntity() {
        TransferEntity entity = new TransferEntity();
        entity.setTransactionId(transactionId);
        entity.setInstant(instant);
        entity.setAmount(amount);
        entity.setSourceAccountNumber(sourceAccountNumber);
        entity.setDestinationAccountNumber(destinationAccountNumber);
        return entity;
    }

}
