package com.hrbeu.pojo;

import java.io.Serializable;

public class DocumentTag implements Serializable {
    private Long documentId;
    private Long tagId;
    public DocumentTag(){}
    public DocumentTag(Long documentId, Long tagId) {
        this.documentId = documentId;
        this.tagId = tagId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
