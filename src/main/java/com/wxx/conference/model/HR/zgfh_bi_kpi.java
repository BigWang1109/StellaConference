package com.wxx.conference.model.HR;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by thinkpad on 2020-8-19.
 */
public class zgfh_bi_kpi {

    private String PSNCODE;

    private String KPI_YEAR;

    private String DEGREE;

    private String REWARD;

    private String MAJOR;

    public String getPSNCODE() {
        return PSNCODE;
    }

    public void setPSNCODE(String PSNCODE) {
        this.PSNCODE = PSNCODE;
    }

    public String getKPI_YEAR() {
        return KPI_YEAR;
    }

    public void setKPI_YEAR(String KPI_YEAR) {
        this.KPI_YEAR = KPI_YEAR;
    }

    public String getDEGREE() {
        return DEGREE;
    }

    public void setDEGREE(String DEGREE) {
        this.DEGREE = DEGREE;
    }

    public String getREWARD() {
        return REWARD;
    }

    public void setREWARD(String REWARD) {
        this.REWARD = REWARD;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public void setMAJOR(String MAJOR) {
        this.MAJOR = MAJOR;
    }
}
