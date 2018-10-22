package com.messio.lineage.domain;

import javax.persistence.*;

@Table(name = "companies")
@NamedQueries({
        @NamedQuery(name = Company.COMPANY_ALL, query = "select c from Company c")
})
@Entity
public class Company {
    public static final String COMPANY_ALL = "company.all";
    @Id
    @Column(name = "id", nullable = false)
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
