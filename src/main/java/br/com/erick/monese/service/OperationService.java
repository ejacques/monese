package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.DepositDTO;
import br.com.erick.monese.controller.dto.OperationDTO;
import br.com.erick.monese.controller.dto.OperationsResponseDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.repository.DepositRepository;
import br.com.erick.monese.repository.TransferRepository;
import br.com.erick.monese.repository.entity.DepositEntity;
import br.com.erick.monese.repository.entity.TransferEntity;
import br.com.erick.monese.service.converter.DepositConverter;
import br.com.erick.monese.service.converter.TransferConverter;
import io.micronaut.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Singleton
public class OperationService {

    @Inject
    private TransferRepository transferRepository;

    @Inject
    private DepositRepository depositRepository;

    public OperationsResponseDTO findAllByAccountNumber(final String accountNumber) {
        OperationsResponseDTO dto = new OperationsResponseDTO();

        List<OperationDTO> operations = findTransfers(accountNumber);
        operations.addAll(findDeposits(accountNumber));

        dto.setOperations(operations.stream()
                .sorted(Comparator.comparing(OperationDTO::getInstant))
                .collect(Collectors.toList()));
        dto.setBalance(calculateBalance(operations, accountNumber));
        return dto;
    }

    private List<DepositDTO> findDeposits(String accountNumber) {
        return depositRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(DepositConverter::toDTO)
                .collect(Collectors.toList());
    }

    private List<OperationDTO> findTransfers(String accountNumber) {
        return transferRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(TransferConverter::toDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateBalance(final List<OperationDTO> operations, final String accountNumber) {
        return operations.stream()
                .map(operation -> this.getOperationBalance(operation, accountNumber))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateBalance(final String accountNumber) {
        return BigDecimal.ZERO
                .add(getTransferBalance(accountNumber))
                .add(getDepositBalance(accountNumber));
    }

    private BigDecimal getTransferBalance(final String accountNumber) {
        return getTransferBalance(accountNumber, transferRepository.findByAccountNumber(accountNumber));
    }

    private BigDecimal getTransferBalance(final String accountNumber, final List<TransferEntity> transfers) {
        return transfers
                .stream()
                .map(transferEntity -> {
                    if (accountNumber.equals(transferEntity.getSourceAccountNumber())) {
                        return transferEntity.getAmount().negate();
                    } else {
                        return transferEntity.getAmount();
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getDepositBalance(final String accountNumber) {
        return getDepositBalance(depositRepository.findByAccountNumber(accountNumber));
    }

    private BigDecimal getDepositBalance(final List<DepositEntity> deposits) {
        return deposits
                .stream()
                .map(DepositEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOperationBalance(OperationDTO operation, String accountNumber) {
        if (operation instanceof DepositDTO) {
            return operation.getAmount();
        }
        if (operation instanceof TransferDTO) {
            if (accountNumber.equals(((TransferDTO) operation).getSource().getNumber())) {
                return operation.getAmount().negate();
            } else {
                return operation.getAmount();
            }
        }
        return BigDecimal.ZERO;
    }

}
