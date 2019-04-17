package br.com.erick.monese.service.converter;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.DepositDTO;
import br.com.erick.monese.repository.entity.DepositEntity;

public class DepositConverter {

    public static DepositDTO toDTO(final DepositEntity entity) {
        if (entity == null) {
            return null;
        }
        DepositDTO dto = new DepositDTO();
        dto.setAmount(entity.getAmount());
        dto.setDestination(new AccountDTO(entity.getAccountNumber()));
        dto.setInstant(entity.getInstant());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }

    public static DepositEntity toEntity(final DepositDTO dto) {
        if (dto == null) {
            return null;
        }
        DepositEntity entity = new DepositEntity();
        entity.setAmount(dto.getAmount());
        entity.setAccountNumber(dto.getDestination() == null ? null : dto.getDestination().getNumber());
        entity.setInstant(dto.getInstant());
        entity.setTransactionId(dto.getTransactionId());
        return entity;
    }
}
