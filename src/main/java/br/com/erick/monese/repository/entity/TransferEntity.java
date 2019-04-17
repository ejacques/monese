package br.com.erick.monese.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "transfer")
public class TransferEntity {

    @Id
    @NotNull
    @Column(name = "transaction_id")
    private String transactionId;
    @NotNull
    private LocalDateTime instant;
    @NotNull
    @Column(name = "source_account_number")
    private String sourceAccountNumber;
    @NotNull
    @Column(name = "destination_account_number")
    private String destinationAccountNumber;
    @NotNull
    private BigDecimal amount;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getInstant() {
        return instant;
    }

    public void setInstant(LocalDateTime instant) {
        this.instant = instant;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferEntity entity = (TransferEntity) o;
        return Objects.equals(transactionId, entity.transactionId) &&
                Objects.equals(instant, entity.instant) &&
                Objects.equals(sourceAccountNumber, entity.sourceAccountNumber) &&
                Objects.equals(destinationAccountNumber, entity.destinationAccountNumber) &&
                Objects.equals(amount, entity.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, instant, sourceAccountNumber, destinationAccountNumber, amount);
    }
}
