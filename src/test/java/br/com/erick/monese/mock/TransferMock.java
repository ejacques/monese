package br.com.erick.monese.mock;

import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.repository.entity.TransferEntity;

public class TransferMock {

    public static TransferEntity mockEntity() {
        return TransferBuilder.getInstance()
                .withInstant()
                .withAmount()
                .withDestinationAccount()
                .withSourceAccount()
                .withTransactionId()
                .buildEntity();
    }

    public static TransferDTO mockDTO() {
        return TransferBuilder.getInstance()
                .withInstant()
                .withAmount()
                .withDestinationAccount()
                .withSourceAccount()
                .withTransactionId()
                .buildDTO();
    }

    public static TransferDTO mockDTOWithoutDestinationAccount() {
        return TransferBuilder.getInstance()
                .withInstant()
                .withAmount()
                .withSourceAccount()
                .withTransactionId()
                .buildDTO();
    }
}
