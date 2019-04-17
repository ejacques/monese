package br.com.erick.monese.service.converter;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.PersonDTO;
import br.com.erick.monese.repository.entity.AccountEntity;

import java.util.Optional;

public class AccountConverter {

    //A converter implemented in the classic "if" style
    public static AccountDTO toDTO(final AccountEntity entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        dto.setNumber(entity.getNumber());
        dto.setOwner(new PersonDTO(entity.getSuid(), entity.getName()));
        return dto;
    }

    //A converter using Optional from Java 8
    public static AccountEntity toEntity(final AccountDTO dto) {
        return Optional.ofNullable(dto)
                .map(accountDTO -> {
                    AccountEntity entity = new AccountEntity();
                    entity.setNumber(dto.getNumber());
                    Optional.ofNullable(dto.getOwner())
                            .ifPresent(owner -> {
                                entity.setSuid(owner.getSuid());
                                entity.setName(owner.getName());
                            });
                    return entity;
                })
                .orElse(null);
    }

}
