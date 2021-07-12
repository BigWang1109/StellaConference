package com.wxx.conference.model.HR;

import java.sql.Blob;
import java.sql.Clob;

/**
 * Created by thinkpad on 2020-9-25.
 */
public class zgfh_bi_ncfile {

    private String PK_ATTACHMENT;

    private String PSNCODE;

    private String ATTACHMENT_NAME;

    private Blob ATTACHMENT;

    private String UPLOAD_DATE;

    private Clob CONTENT;

    public String getPSNCODE() {
        return PSNCODE;
    }

    public void setPSNCODE(String PSNCODE) {
        this.PSNCODE = PSNCODE;
    }

    public String getATTACHMENT_NAME() {
        return ATTACHMENT_NAME;
    }

    public void setATTACHMENT_NAME(String ATTACHMENT_NAME) {
        this.ATTACHMENT_NAME = ATTACHMENT_NAME;
    }

    public Blob getATTACHMENT() {
        return ATTACHMENT;
    }

    public void setATTACHMENT(Blob ATTACHMENT) {
        this.ATTACHMENT = ATTACHMENT;
    }

    public String getUPLOAD_DATE() {
        return UPLOAD_DATE;
    }

    public void setUPLOAD_DATE(String UPLOAD_DATE) {
        this.UPLOAD_DATE = UPLOAD_DATE;
    }

    public String getPK_ATTACHMENT() {
        return PK_ATTACHMENT;
    }

    public void setPK_ATTACHMENT(String PK_ATTACHMENT) {
        this.PK_ATTACHMENT = PK_ATTACHMENT;
    }

    public Clob getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(Clob CONTENT) {
        this.CONTENT = CONTENT;
    }
}
