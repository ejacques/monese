package br.com.erick.monese.mock;

import br.com.erick.monese.controller.dto.DepositDTO;
import br.com.erick.monese.repository.entity.DepositEntity;

public class DepositMock {

    public static DepositEntity mockEntity() {
        return DepositBuilder.getInstance()
                .withInstant()
                .withAmount()
                .withDestinationAccount()
                .withTransactionId()
                .buildEntity();
    }

    public static DepositDTO mockDTO() {
        return DepositBuilder.getInstance()
                .withInstant()
                .withAmount()
                .withDestinationAccount()
                .withTransactionId()
                .buildDTO();
    }
}
