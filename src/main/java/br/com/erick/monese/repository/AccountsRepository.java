package br.com.erick.monese.repository;

import br.com.erick.monese.repository.entity.AccountEntity;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class AccountsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AccountsRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<AccountEntity> findAll() {
        return entityManager
                .createQuery("select a from AccountEntity as a", AccountEntity.class)
                .getResultList();
    }

    @Transactional
    public Optional<AccountEntity> findByAccountNumber(final String accountNumber) {
        try {
            return Optional.of(entityManager
                    .createQuery("select a from AccountEntity as a where a.number = :accountNumber", AccountEntity.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void insert(AccountEntity entity) {
        entityManager.merge(entity);
    }
}
