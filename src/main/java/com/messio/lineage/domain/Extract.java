package com.messio.lineage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Table(name = "extracts")
@Entity
@NamedQueries({
        @NamedQuery(
                name = Extract.EXTRACT_IDS_LIKE,
                query = "select e.id from Extract e where e.relation is null and e.sentences like ?1 and e.one.irrelevant = false and e.two.irrelevant = false order by e.id"
        )
})
@JsonIgnoreProperties("companies")
public class Extract {
    public static final String EXTRACT_IDS_LIKE = "extract.idsLike";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    @Basic
    @Column(name = "decision_id")
    private long decisionId;
    @Basic
    @Column(name = "lang")
    private String lang;
    @Lob
    @Column(name = "sentences")
    private String sentences;
    @Basic
    @Column
    private Integer relation;
    @ManyToOne
    @JoinColumn(name = "one_company_id")
    private Company one;
    @ManyToOne
    @JoinColumn(name = "two_company_id")
    private Company two;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(long decisionId) {
        this.decisionId = decisionId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Company getOne() {
        return one;
    }

    public void setOne(Company one) {
        this.one = one;
    }

    public Company getTwo() {
        return two;
    }

    public void setTwo(Company two) {
        this.two = two;
    }
}
