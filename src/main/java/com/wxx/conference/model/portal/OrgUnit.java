package com.wxx.conference.model.portal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by thinkpad on 2019-11-7.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="org_unit")
public class OrgUnit {
    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "NAME")
    private String NAME;

    @Column(name = "CODE")
    private String CODE;

    @Column(name = "TYPE")
    private String TYPE;

    @Column(name = "IS_GROUP")
    private Integer IS_GROUP;

    @Column(name = "PATH")
    private String PATH;

    @Column(name = "IS_INTERNAL")
    private Integer IS_INTERNAL;

    @Column(name = "SORT_ID")
    private Integer SORT_ID;

    @Column(name = "IS_ENABLE")
    private Integer IS_ENABLE;

    @Column(name = "IS_DELETED")
    private Integer IS_DELETED;

    @Column(name = "STATUS")
    private Integer STATUS;

    @Column(name = "LEVEL_SCOPE")
    private String LEVEL_SCOPE;

    @Column(name = "ORG_ACCOUNT_ID")
    private String ORG_ACCOUNT_ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Integer getIS_GROUP() {
        return IS_GROUP;
    }

    public void setIS_GROUP(Integer IS_GROUP) {
        this.IS_GROUP = IS_GROUP;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public Integer getIS_INTERNAL() {
        return IS_INTERNAL;
    }

    public void setIS_INTERNAL(Integer IS_INTERNAL) {
        this.IS_INTERNAL = IS_INTERNAL;
    }

    public Integer getSORT_ID() {
        return SORT_ID;
    }

    public void setSORT_ID(Integer SORT_ID) {
        this.SORT_ID = SORT_ID;
    }

    public Integer getIS_ENABLE() {
        return IS_ENABLE;
    }

    public void setIS_ENABLE(Integer IS_ENABLE) {
        this.IS_ENABLE = IS_ENABLE;
    }

    public Integer getIS_DELETED() {
        return IS_DELETED;
    }

    public void setIS_DELETED(Integer IS_DELETED) {
        this.IS_DELETED = IS_DELETED;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }

    public String getLEVEL_SCOPE() {
        return LEVEL_SCOPE;
    }

    public void setLEVEL_SCOPE(String LEVEL_SCOPE) {
        this.LEVEL_SCOPE = LEVEL_SCOPE;
    }

    public String getORG_ACCOUNT_ID() {
        return ORG_ACCOUNT_ID;
    }

    public void setORG_ACCOUNT_ID(String ORG_ACCOUNT_ID) {
        this.ORG_ACCOUNT_ID = ORG_ACCOUNT_ID;
    }
}
