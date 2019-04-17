package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.OperationsResponseDTO;
import br.com.erick.monese.mock.DepositBuilder;
import br.com.erick.monese.mock.DepositMock;
import br.com.erick.monese.mock.TransferMock;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class OperationServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private DepositRepository depositRepository;

    @InjectMocks
    private OperationService operationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllByAccountNumberOk() {
        //Given
        String accountNumber = DepositBuilder.ACCOUNT_NUMBER;
        Mockito.when(depositRepository.findByAccountNumber(accountNumber)).thenReturn(mockDeposits());
        Mockito.when(transferRepository.findByAccountNumber(accountNumber)).thenReturn(mockTransfers());

        //When
        OperationsResponseDTO response = operationService.findAllByAccountNumber(accountNumber);

        //Then
        Assert.assertEquals(BigDecimal.valueOf(625), response.getBalance());
        Assert.assertEquals(2, response.getOperations().size());
    }

    @Test
    public void testFindAllByAccountNumberNotFound() {
        //Given
        String accountNumber = DepositBuilder.ACCOUNT_NUMBER;
        Mockito.when(depositRepository.findByAccountNumber(accountNumber)).thenReturn(Collections.emptyList());
        Mockito.when(transferRepository.findByAccountNumber(accountNumber)).thenReturn(Collections.emptyList());

        //When
        OperationsResponseDTO response = operationService.findAllByAccountNumber(accountNumber);

        //Then
        Assert.assertEquals(BigDecimal.ZERO, response.getBalance());
        Assert.assertEquals(0, response.getOperations().size());
    }

    private List<DepositEntity> mockDeposits() {
        return Arrays.asList(DepositMock.mockEntity());
    }

    private List<TransferEntity> mockTransfers() {
        return Arrays.asList(TransferMock.mockEntity());
    }

}
