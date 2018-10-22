package com.messio.lineage;

import com.messio.lineage.domain.Company;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class DataFacade {
    @PersistenceContext
    private EntityManager em;

    public <T> T create(T t){
        Objects.requireNonNull(t);
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID pk){
        Objects.requireNonNull(pk);
        return em.find(clazz, pk);
    }

    public <T> T update(T t){
        Objects.requireNonNull(t);
        return em.merge(t);
    }

    public <T, ID extends Serializable> void delete(Class<T> clazz, ID pk){
        Objects.requireNonNull(pk);
        em.remove(em.getReference(clazz, pk));
    }

    public List<Company> findCompanies(){
        return em.createNamedQuery(Company.COMPANY_ALL, Company.class).getResultList();
    }
}
