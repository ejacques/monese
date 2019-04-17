package br.com.erick.monese;

import br.com.erick.monese.controller.dto.TransferRequestDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("/operations")
public interface OperationClient {

    @Post("/transfer")
    HttpResponse transfer(@Body TransferRequestDTO payload);

}
