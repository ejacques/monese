package br.com.erick.monese.controller;

import br.com.erick.monese.controller.dto.AccountDTO;
import br.com.erick.monese.controller.dto.OperationsResponseDTO;
import br.com.erick.monese.service.AccountService;
import br.com.erick.monese.service.OperationService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import java.util.List;

@Validated
@Controller("/accounts")
public class AccountController {

    @Inject
    private AccountService accountService;

    @Inject
    private OperationService operationService;

    @Get
    public HttpResponse<List<AccountDTO>> getAccounts(@QueryValue(value = "showBalance", defaultValue = "false") boolean showBalance) {
        return HttpResponse.ok(accountService.findAll(showBalance));
    }

    @Get("/{accountNumber}")
    public HttpResponse<AccountDTO> getAccount(String accountNumber) {
        return accountService.findByAccountNumber(accountNumber)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get("/{accountNumber}/operations")
    public HttpResponse<OperationsResponseDTO> getOperations(String accountNumber) {
        return HttpResponse.ok(operationService.findAllByAccountNumber(accountNumber));
    }

}
