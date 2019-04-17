package br.com.erick.monese.controller;

import br.com.erick.monese.OperationClient;
import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.controller.dto.TransferRequestDTO;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static br.com.erick.monese.mock.TransferBuilder.DESTINATION_ACCOUNT_NUMBER;
import static br.com.erick.monese.mock.TransferBuilder.SOURCE_ACCOUNT_NUMBER;

@MicronautTest
public class IntegrationRestTest {

    private EmbeddedServer embeddedServer;
    private OperationClient client;

    @Before
    public void setup() {
        this.embeddedServer = ApplicationContext.run(EmbeddedServer.class);
        this.client = embeddedServer.getApplicationContext().getBean(OperationClient.class);
    }

    @Test
    public void testTransferOk() {
        //Given
        TransferRequestDTO request = mockRequest();
        AccountDTO sourceAccountBefore = client.getAccount(SOURCE_ACCOUNT_NUMBER).body();
        AccountDTO destinationAccountBefore = client.getAccount(DESTINATION_ACCOUNT_NUMBER).body();

        //When
        HttpResponse<TransferDTO> httpResponse = client.transfer(request);

        AccountDTO sourceAccountAfter = client.getAccount(SOURCE_ACCOUNT_NUMBER).body();
        AccountDTO destinationAccountAfter = client.getAccount(DESTINATION_ACCOUNT_NUMBER).body();

        //Then
        Assert.assertEquals(HttpStatus.OK, httpResponse.getStatus());
        Assert.assertEquals(request.getAmount().add(destinationAccountBefore.getBalance()),
                destinationAccountAfter.getBalance());
        Assert.assertEquals(request.getAmount().negate().add(sourceAccountBefore.getBalance()),
                sourceAccountAfter.getBalance());
    }

    @Test
    public void testTransferWithWrongSourceAccount() {
        //Given
        TransferRequestDTO request = mockRequestWithInvalidSourceAccount();

        //When
        try {
            client.transfer(request);
        } catch (HttpClientResponseException e) {
            //Then
            Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, e.getStatus());
            Assert.assertEquals("Invalid source account number [000000]", e.getMessage());
        }

    }

    @Test
    public void testTransferWithoutSourceAccount() {
        //Given
        TransferRequestDTO request = mockRequestWithoutSourceAccount();

        //When
        try {
            client.transfer(request);
        } catch (HttpClientResponseException e) {
            //Then
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            Assert.assertEquals("payload.source: must not be null", e.getMessage());
        }
    }

    private TransferRequestDTO mockRequest() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setAmount(BigDecimal.valueOf(75));
        dto.setSource(new AccountDTO(SOURCE_ACCOUNT_NUMBER));
        dto.setDestination(new AccountDTO(DESTINATION_ACCOUNT_NUMBER));
        return dto;
    }

    private TransferRequestDTO mockRequestWithInvalidSourceAccount() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setAmount(BigDecimal.valueOf(75));
        dto.setSource(new AccountDTO("000000"));
        dto.setDestination(new AccountDTO(DESTINATION_ACCOUNT_NUMBER));
        return dto;
    }

    private TransferRequestDTO mockRequestWithoutSourceAccount() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setAmount(BigDecimal.valueOf(75));
        dto.setDestination(new AccountDTO(DESTINATION_ACCOUNT_NUMBER));
        return dto;
    }

    @After
    public void cleanup() {
        embeddedServer.stop();
    }
}
