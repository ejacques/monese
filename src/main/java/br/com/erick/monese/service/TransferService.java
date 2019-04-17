package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.exceptions.BusinessException;
import br.com.erick.monese.repository.TransferRepository;
import br.com.erick.monese.repository.entity.TransferEntity;
import br.com.erick.monese.service.converter.TransferConverter;
import io.micronaut.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Primary
@Singleton
public class TransferService {

    @Inject
    private AccountService accountService;

    @Inject
    private TransferRepository transferRepository;

    public TransferDTO transfer(AccountDTO source, AccountDTO destination, BigDecimal amount) {
        AccountDTO sourceAccount = accountService.findByAccountNumber(source.getNumber())
                .orElseThrow(() -> new BusinessException("Invalid source account number [%s]", source.getNumber()));
        accountService.findByAccountNumber(destination.getNumber())
                .orElseThrow(() -> new BusinessException("Invalid destination account number [%s]", destination.getNumber()));

        if (hasAvailableAmount(sourceAccount, amount)) {
            return TransferConverter.toDTO(makeTransfer(sourceAccount, destination, amount));
        }

        throw new BusinessException("Insufficient funds");
    }

    private TransferEntity makeTransfer(AccountDTO source, AccountDTO destination, BigDecimal amount) {
        TransferEntity entity = new TransferEntity();
        entity.setTransactionId(UUID.randomUUID().toString());
        entity.setInstant(LocalDateTime.now());
        entity.setAmount(amount);
        entity.setSourceAccountNumber(source.getNumber());
        entity.setDestinationAccountNumber(destination.getNumber());
        transferRepository.saveTransfer(entity);
        return entity;
    }

    private boolean hasAvailableAmount(AccountDTO account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

}
