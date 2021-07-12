package com.wxx.conference.model.HR;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by thinkpad on 2020-8-17.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="zgfh_bi_psndoc_work")
public class zgfh_bi_psndoc_work {
    @Id
    @Column(name = "PSNCODE")
    private String PSNCODE;

    @Column(name = "BEGINDATE")
    private String BEGINDATE;

    @Column(name = "ENDDATE")
    private String ENDDATE;

    @Column(name = "WORKCORP")
    private String WORKCORP;

    @Column(name = "WORKPOST")
    private String WORKPOST;
    @Column(name = "WORKDEPT")
    private String WORKDEPT;

    public String getPSNCODE() {
        return PSNCODE;
    }

    public void setPSNCODE(String PSNCODE) {
        this.PSNCODE = PSNCODE;
    }

    public String getBEGINDATE() {
        return BEGINDATE;
    }

    public void setBEGINDATE(String BEGINDATE) {
        this.BEGINDATE = BEGINDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public String getWORKCORP() {
        return WORKCORP;
    }

    public void setWORKCORP(String WORKCORP) {
        this.WORKCORP = WORKCORP;
    }

    public String getWORKPOST() {
        return WORKPOST;
    }

    public void setWORKPOST(String WORKPOST) {
        this.WORKPOST = WORKPOST;
    }

    public String getWORKDEPT() {
        return WORKDEPT;
    }

    public void setWORKDEPT(String WORKDEPT) {
        this.WORKDEPT = WORKDEPT;
    }
}
