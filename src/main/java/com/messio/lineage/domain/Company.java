package com.messio.lineage.domain;

import javax.persistence.*;

@Table(name = "companies")
@NamedQueries({
        @NamedQuery(name = Company.COMPANY_ALL, query = "select c from Company c"),
        @NamedQuery(name = Company.COMPANY_BY_NAME, query = "select c from Company c where name = ?1")
})
@Entity
public class Company {
    public static final String COMPANY_ALL = "company.all";
    public static final String COMPANY_BY_NAME = "company.byName";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
