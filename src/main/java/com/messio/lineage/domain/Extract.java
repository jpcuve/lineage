package com.messio.lineage.domain;

import javax.persistence.*;

@Table(name = "extracts")
@Entity
public class Extract {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "decision_id")
    private long decisionId;
    @Basic
    @Column(name = "lang")
    private String lang;
    @Basic
    @Column(name = "sentence_start")
    private int sentenceStart;
    @Basic
    @Column(name = "sentences")
    private String sentences;

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

    public int getSentenceStart() {
        return sentenceStart;
    }

    public void setSentenceStart(int sentenceStart) {
        this.sentenceStart = sentenceStart;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }
}
