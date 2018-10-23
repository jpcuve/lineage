package com.messio.lineage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Table(name = "relations")
@Entity
@JsonIgnoreProperties({"extract", "parent", "child"})
public class Relation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "extract_id")
    private Extract extract;
    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    private Company parent;
    @ManyToOne
    @JoinColumn(name = "child_company_id")
    private Company child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Extract getExtract() {
        return extract;
    }

    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

    public Company getChild() {
        return child;
    }

    public void setChild(Company child) {
        this.child = child;
    }
}
