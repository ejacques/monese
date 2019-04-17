package br.com.erick.monese.repository;

import br.com.erick.monese.repository.entity.TransferEntity;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class TransferRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TransferRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<TransferEntity> findByAccountNumber(final String accountNumber) {
        return entityManager
                .createQuery("select t from TransferEntity as t " +
                        "where t.sourceAccountNumber = :accountNumber " +
                        "or t.destinationAccountNumber = :accountNumber ", TransferEntity.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
    }

    @Transactional
    public void saveTransfer(TransferEntity entity) {
        entityManager.persist(entity);
    }

}
