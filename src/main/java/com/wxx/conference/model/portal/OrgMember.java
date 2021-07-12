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
@Table(name="org_member")
public class OrgMember {

    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "NAME")
    private String NAME;

    @Column(name = "CODE")
    private String CODE;

    @Column(name = "IS_INTERNAL")
    private Integer IS_INTERNAL;

    @Column(name = "IS_LOGINABLE")
    private Integer IS_LOGINABLE;

    @Column(name = "IS_VIRTUAL")
    private Integer IS_VIRTUAL;

    @Column(name = "IS_ADMIN")
    private Integer IS_ADMIN;

    @Column(name = "IS_ASSIGNED")
    private Integer IS_ASSIGNED;

    @Column(name = "TYPE")
    private Integer TYPE;

    @Column(name = "STATE")
    private Integer STATE;

    @Column(name = "IS_ENABLE")
    private Integer IS_ENABLE;

    @Column(name = "IS_DELETED")
    private Integer IS_DELETED;

    @Column(name = "STATUS")
    private Integer STATUS;

    @Column(name = "SORT_ID")
    private String SORT_ID;

    @Column(name = "ORG_DEPARTMENT_ID")
    private String ORG_DEPARTMENT_ID;

    @Column(name = "ORG_POST_ID")
    private String ORG_POST_ID;

    @Column(name = "ORG_LEVEL_ID")
    private String ORG_LEVEL_ID;

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

    public Integer getIS_INTERNAL() {
        return IS_INTERNAL;
    }

    public void setIS_INTERNAL(Integer IS_INTERNAL) {
        this.IS_INTERNAL = IS_INTERNAL;
    }

    public Integer getIS_LOGINABLE() {
        return IS_LOGINABLE;
    }

    public void setIS_LOGINABLE(Integer IS_LOGINABLE) {
        this.IS_LOGINABLE = IS_LOGINABLE;
    }

    public Integer getIS_VIRTUAL() {
        return IS_VIRTUAL;
    }

    public void setIS_VIRTUAL(Integer IS_VIRTUAL) {
        this.IS_VIRTUAL = IS_VIRTUAL;
    }

    public Integer getIS_ADMIN() {
        return IS_ADMIN;
    }

    public void setIS_ADMIN(Integer IS_ADMIN) {
        this.IS_ADMIN = IS_ADMIN;
    }

    public Integer getIS_ASSIGNED() {
        return IS_ASSIGNED;
    }

    public void setIS_ASSIGNED(Integer IS_ASSIGNED) {
        this.IS_ASSIGNED = IS_ASSIGNED;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public Integer getSTATE() {
        return STATE;
    }

    public void setSTATE(Integer STATE) {
        this.STATE = STATE;
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

    public String getSORT_ID() {
        return SORT_ID;
    }

    public void setSORT_ID(String SORT_ID) {
        this.SORT_ID = SORT_ID;
    }

    public String getORG_ACCOUNT_ID() {
        return ORG_ACCOUNT_ID;
    }

    public void setORG_ACCOUNT_ID(String ORG_ACCOUNT_ID) {
        this.ORG_ACCOUNT_ID = ORG_ACCOUNT_ID;
    }

    public String getORG_DEPARTMENT_ID() {
        return ORG_DEPARTMENT_ID;
    }

    public void setORG_DEPARTMENT_ID(String ORG_DEPARTMENT_ID) {
        this.ORG_DEPARTMENT_ID = ORG_DEPARTMENT_ID;
    }

    public String getORG_POST_ID() {
        return ORG_POST_ID;
    }

    public void setORG_POST_ID(String ORG_POST_ID) {
        this.ORG_POST_ID = ORG_POST_ID;
    }

    public String getORG_LEVEL_ID() {
        return ORG_LEVEL_ID;
    }

    public void setORG_LEVEL_ID(String ORG_LEVEL_ID) {
        this.ORG_LEVEL_ID = ORG_LEVEL_ID;
    }
}
