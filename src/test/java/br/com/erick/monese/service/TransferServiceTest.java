package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.exceptions.BusinessException;
import br.com.erick.monese.repository.DepositRepository;
import br.com.erick.monese.repository.TransferRepository;
import br.com.erick.monese.repository.entity.DepositEntity;
import br.com.erick.monese.repository.entity.TransferEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.erick.monese.mock.TransferBuilder.DESTINATION_ACCOUNT_NUMBER;
import static br.com.erick.monese.mock.TransferBuilder.SOURCE_ACCOUNT_NUMBER;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private DepositRepository depositRepository;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransferService transferService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferOk() {
        //Given
        doNothing().when(transferRepository).saveTransfer(Mockito.any(TransferEntity.class));
        when(transferRepository.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(Collections.emptyList());
        when(depositRepository.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(mockDeposits());
        when(accountService.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(mockSourceAccount());
        when(accountService.findByAccountNumber(DESTINATION_ACCOUNT_NUMBER)).thenReturn(mockDestinationAccount());

        //When
        TransferDTO response = transferService.transfer(
                new AccountDTO(SOURCE_ACCOUNT_NUMBER),
                new AccountDTO(DESTINATION_ACCOUNT_NUMBER),
                BigDecimal.valueOf(75)
        );

        //Then
        Assert.assertEquals(SOURCE_ACCOUNT_NUMBER, response.getSource().getNumber());
        Assert.assertEquals(DESTINATION_ACCOUNT_NUMBER, response.getDestination().getNumber());
    }

    @Test
    public void testTransferWithInsufficientFunds() {
        //Given
        doNothing().when(transferRepository).saveTransfer(Mockito.any(TransferEntity.class));
        when(transferRepository.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(Collections.emptyList());
        when(depositRepository.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(mockDeposits());
        when(accountService.findByAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(mockSourceAccountWithInsufficientFunds());
        when(accountService.findByAccountNumber(DESTINATION_ACCOUNT_NUMBER)).thenReturn(mockDestinationAccount());

        //When
        try {
            transferService.transfer(
                    new AccountDTO(SOURCE_ACCOUNT_NUMBER),
                    new AccountDTO(DESTINATION_ACCOUNT_NUMBER),
                    BigDecimal.valueOf(75)
            );
        } catch (BusinessException e) {
            //Then
            Assert.assertEquals("Insufficient funds", e.getMessage());
        }
    }

    private Optional<AccountDTO> mockSourceAccount() {
        AccountDTO dto = new AccountDTO();
        dto.setBalance(BigDecimal.valueOf(500));
        dto.setNumber(SOURCE_ACCOUNT_NUMBER);
        return Optional.of(dto);
    }

    private Optional<AccountDTO> mockSourceAccountWithInsufficientFunds() {
        AccountDTO dto = new AccountDTO();
        dto.setBalance(BigDecimal.ZERO);
        dto.setNumber(SOURCE_ACCOUNT_NUMBER);
        return Optional.of(dto);
    }

    private Optional<AccountDTO> mockDestinationAccount() {
        AccountDTO dto = new AccountDTO();
        dto.setBalance(BigDecimal.valueOf(500));
        dto.setNumber(DESTINATION_ACCOUNT_NUMBER);
        return Optional.of(dto);
    }

    private List<DepositEntity> mockDeposits() {
        DepositEntity entity = new DepositEntity();
        entity.setAccountNumber(SOURCE_ACCOUNT_NUMBER);
        entity.setAmount(BigDecimal.valueOf(500));
        return Arrays.asList(entity);
    }
}
