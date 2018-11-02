package com.messio.lineage.transfer;

public class SaveValue {
    private Long extractId;
    private int relation;

    public SaveValue(Long extractId, int relation) {
        this.extractId = extractId;
        this.relation = relation;
    }

    public SaveValue() {
    }

    public Long getExtractId() {
        return extractId;
    }

    public void setExtractId(Long extractId) {
        this.extractId = extractId;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
