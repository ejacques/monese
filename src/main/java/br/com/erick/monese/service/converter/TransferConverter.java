package br.com.erick.monese.service.converter;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.repository.entity.TransferEntity;

public class TransferConverter {

    public static TransferDTO toDTO(final TransferEntity entity) {
        if (entity == null) {
            return null;
        }
        TransferDTO dto = new TransferDTO();
        dto.setAmount(entity.getAmount());
        dto.setSource(new AccountDTO(entity.getSourceAccountNumber()));
        dto.setDestination(new AccountDTO(entity.getDestinationAccountNumber()));
        dto.setInstant(entity.getInstant());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }

    public static TransferEntity toEntity(final TransferDTO dto) {
        if (dto == null) {
            return null;
        }
        TransferEntity entity = new TransferEntity();
        entity.setAmount(dto.getAmount());
        entity.setSourceAccountNumber(dto.getSource() == null ? null : dto.getSource().getNumber());
        entity.setDestinationAccountNumber(dto.getDestination() == null ? null : dto.getDestination().getNumber());
        entity.setInstant(dto.getInstant());
        entity.setTransactionId(dto.getTransactionId());
        return entity;
    }

}
