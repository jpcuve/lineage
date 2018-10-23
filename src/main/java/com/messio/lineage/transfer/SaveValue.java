package com.messio.lineage.transfer;

public class SaveValue {
    private Long extractId;
    private Long parentId;
    private Long childId;

    public SaveValue(Long extractId, Long parentId, Long childId) {
        this.extractId = extractId;
        this.parentId = parentId;
        this.childId = childId;
    }

    public SaveValue() {
    }

    public Long getExtractId() {
        return extractId;
    }

    public void setExtractId(Long extractId) {
        this.extractId = extractId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
}
