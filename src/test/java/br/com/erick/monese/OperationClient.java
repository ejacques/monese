package br.com.erick.monese;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.TransferDTO;
import br.com.erick.monese.controller.dto.TransferRequestDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("/")
public interface OperationClient {

    @Post("operations/transfer")
    HttpResponse<TransferDTO> transfer(@Body TransferRequestDTO payload);

    @Get("accounts/{accountNumber}")
    HttpResponse<AccountDTO> getAccount(@PathVariable String accountNumber);

}
