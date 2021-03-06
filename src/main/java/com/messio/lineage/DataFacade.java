package com.messio.lineage;

import com.messio.lineage.domain.Company;
import com.messio.lineage.domain.Extract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public <T, ID extends Serializable> Optional<T> findOne(Class<T> clazz, ID pk){
        Objects.requireNonNull(pk);
        return Optional.ofNullable(em.find(clazz, pk));
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
        return em
                .createNamedQuery(Company.COMPANY_ALL, Company.class)
                .getResultList();
    }

    public Optional<Company> findCompanyByName(String name){
        return em
                .createNamedQuery(Company.COMPANY_BY_NAME, Company.class)
                .setParameter(1, name)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Extract createExtract(Extract extract, String oneName, String twoName){
        extract.setOne(loadCompany(oneName));
        extract.setTwo(loadCompany(twoName));
        return create(extract);
    }

    public Company loadCompany(String name){
        return findCompanyByName(name).orElseGet(() -> {
            Company company = new Company();
            company.setName(name);
            return create(company);
        });
    }

    public List<Long> findExtractsContainingText(String text){
        return em
                .createNamedQuery(Extract.EXTRACT_IDS_LIKE, Long.class)
                .setParameter(1, String.format("%%%s%%", text))
                .getResultList();
    }
}
