package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.repository.AccountsRepository;
import br.com.erick.monese.service.converter.AccountConverter;
import io.micronaut.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Singleton
public class AccountService {

    @Inject
    private AccountsRepository accountsRepository;

    @Inject
    private OperationService operationService;

    public List<AccountDTO> findAll(final boolean showBalance) {
        return accountsRepository.findAll().stream()
                .map(AccountConverter::toDTO)
                .map(accountDTO -> {
                    if (showBalance) {
                        accountDTO.setBalance(calculateBalance(accountDTO.getNumber()));
                    }
                    return accountDTO;
                })
                .collect(Collectors.toList());
    }

    public Optional<AccountDTO> findByAccountNumber(final String accountNumber) {
        return accountsRepository.findByAccountNumber(accountNumber)
                .map(AccountConverter::toDTO)
                .map(dto -> {
                    dto.setBalance(calculateBalance(accountNumber));
                    return dto;
                });
    }

    public BigDecimal calculateBalance(final String accountNumber) {
        return operationService.calculateBalance(accountNumber);
    }

}
