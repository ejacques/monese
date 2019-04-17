package br.com.erick.monese.controller;


import br.com.erick.monese.controller.dto.DepositRequestDTO;
import br.com.erick.monese.controller.dto.TransferRequestDTO;
import br.com.erick.monese.exceptions.BusinessException;
import br.com.erick.monese.service.TransferService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@Controller("/operations")
public class OperationController {

    @Inject
    TransferService transferService;

    @Post("/transfer")
    public HttpResponse transfer(@Body @Valid TransferRequestDTO payload) {
        try {
            return HttpResponse.ok(transferService.transfer(payload.getSource(), payload.getDestination(), payload.getAmount()));
        } catch (BusinessException e) {
            return HttpResponse.unprocessableEntity().body(e);
        }
    }

    @Post("/deposit")
    public HttpResponse<Void> deposit(@Body @Valid DepositRequestDTO payload) {
        throw new UnsupportedOperationException();
    }
}
