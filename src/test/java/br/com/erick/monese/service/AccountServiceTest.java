package br.com.erick.monese.service;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.repository.AccountsRepository;
import br.com.erick.monese.repository.entity.AccountEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private OperationService operationService;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testFindAllOk(){
        //Given
        Mockito.when(accountsRepository.findAll()).thenReturn(Arrays.asList(mockAccountEntity()));

        //When
        List<AccountDTO> response = accountService.findAll(false);

        //Then
        Assert.assertEquals(1, response.size());
        Assert.assertEquals("Jon Doe", response.get(0).getOwner().getName());
    }

    @Test
    public void testFindAllOkWithBalance(){
        //Given
        Mockito.when(accountsRepository.findAll()).thenReturn(Arrays.asList(mockAccountEntity()));
        Mockito.when(operationService.calculateBalance(Mockito.any())).thenReturn(BigDecimal.TEN);

        //When
        List<AccountDTO> response = accountService.findAll(true);

        //Then
        Assert.assertEquals(1, response.size());
        Assert.assertEquals("Jon Doe", response.get(0).getOwner().getName());
        Assert.assertEquals(BigDecimal.TEN, response.get(0).getBalance());
    }

    @Test
    public void testFindAllNotFound(){
        //Given
        Mockito.when(accountsRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<AccountDTO> response = accountService.findAll(false);

        //Then
        Assert.assertEquals(0, response.size());
    }

    private AccountEntity mockAccountEntity() {
        AccountEntity entity = new AccountEntity();
        entity.setName("Jon Doe");
        entity.setNumber("123456");
        entity.setSuid("9876543210");
        return entity;
    }
}
