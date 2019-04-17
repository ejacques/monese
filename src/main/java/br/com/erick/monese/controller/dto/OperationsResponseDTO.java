package br.com.erick.monese.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public class OperationsResponseDTO {

    private List<OperationDTO> operations;
    private BigDecimal balance;

    public List<OperationDTO> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationDTO> operations) {
        this.operations = operations;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
