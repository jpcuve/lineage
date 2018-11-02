package com.messio.lineage.transfer;

import java.util.List;

public class NEREntity {
    private String name;
    private String type;
    private NERMention[] mentions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NERMention[] getMentions() {
        return mentions;
    }

    public void setMentions(NERMention[] mentions) {
        this.mentions = mentions;
    }
}
