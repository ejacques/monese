package br.com.erick.monese.repository;

import br.com.erick.monese.repository.entity.DepositEntity;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class DepositRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public DepositRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<DepositEntity> findByAccountNumber(final String accountNumber) {
        return entityManager
                .createQuery("select d from DepositEntity as d where d.accountNumber = :accountNumber ", DepositEntity.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
    }

}
