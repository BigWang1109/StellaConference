package com.wxx.conference.model.HR;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;

/**
 * Created by thinkpad on 2020-8-14.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="zgfh_bi_psndoc2020")
public class zgfh_bi_psndoc {

    @Column(name = "CORPNAME")
    private String CORPNAME;
    @Column(name = "COPRCODE")
    private String COPRCODE;
    @Column(name = "GWCODE")
    private String GWCODE;
    @Column(name = "GW")
    private String GW;
    @Column(name = "ZWCODE")
    private String ZWCODE;
    @Column(name = "ZW")
    private String ZW;
    @Column(name = "SYCODE")
    private String SYCODE;
    @Column(name = "SY")
    private String SY;
    @Column(name = "HYCODE")
    private String HYCODE;
    @Column(name = "HY")
    private String HY;
    @Column(name = "MZCODE")
    private String MZCODE;
    @Column(name = "MZ")
    private String MZ;
    @Column(name = "SEXCODE")
    private String SEXCODE;
    @Column(name = "SEX")
    private String SEX;
    @Column(name = "XLCODE")
    private String XLCODE;
    @Column(name = "XL")
    private String XL;
    @Column(name = "SCHOOL")
    private String SCHOOL;
    @Column(name = "BIRTHDAY")
    private String BIRTHDAY;
    @Id
    @Column(name = "PSNCODE")
    private String PSNCODE;
    @Column(name = "PSNNAME")
    private String PSNNAME;
    @Column(name = "RSDATE")
    private String RSDATE;
    @Column(name = "JG")
    private String JG;
    @Column(name = "HK")
    private String HK;
    @Column(name = "RDDATE")
    private String RDDATE;
    @Column(name = "ZZMMCODE")
    private String ZZMMCODE;
    @Column(name = "ZZMM")
    private String ZZMM;
    @Column(name = "WORKDATE")
    private String WORKDATE;
    @Column(name = "ZY")
    private String ZY;
    @Column(name="AGE")
    private String AGE;
    @Column(name="PY")
    private String PY;
    @Column(name="QPY")
    private String QPY;
    @Column(name="searchVal")
    private String searchVal;
    @Column(name = "PHOTO")
    private byte[] PHOTO;
    @Column(name="DEPTNAME")
    private String DEPTNAME;
    @Column(name="CORP_SYSTEM")
    private String CORP_SYSTEM;
    @Column(name="SEARCH_INFO")
    private String SEARCH_INFO;
    @Column(name="GL")
    private String GL;
    @Column(name="percentage")
    private String percentage;
    @Column(name = "psnclassname")
    private String psnclassname;
    @Column(name = "ADDR")
    private String ADDR;
    public String getCORPNAME() {
        return CORPNAME;
    }

    public void setCORPNAME(String CORPNAME) {
        this.CORPNAME = CORPNAME;
    }

    public String getCOPRCODE() {
        return COPRCODE;
    }

    public void setCOPRCODE(String COPRCODE) {
        this.COPRCODE = COPRCODE;
    }

    public String getGWCODE() {
        return GWCODE;
    }

    public void setGWCODE(String GWCODE) {
        this.GWCODE = GWCODE;
    }

    public String getGW() {
        return GW;
    }

    public void setGW(String GW) {
        this.GW = GW;
    }

    public String getZWCODE() {
        return ZWCODE;
    }

    public void setZWCODE(String ZWCODE) {
        this.ZWCODE = ZWCODE;
    }

    public String getZW() {
        return ZW;
    }

    public void setZW(String ZW) {
        this.ZW = ZW;
    }

    public String getSYCODE() {
        return SYCODE;
    }

    public void setSYCODE(String SYCODE) {
        this.SYCODE = SYCODE;
    }

    public String getSY() {
        return SY;
    }

    public void setSY(String SY) {
        this.SY = SY;
    }

    public String getHYCODE() {
        return HYCODE;
    }

    public void setHYCODE(String HYCODE) {
        this.HYCODE = HYCODE;
    }

    public String getHY() {
        return HY;
    }

    public void setHY(String HY) {
        this.HY = HY;
    }

    public String getMZCODE() {
        return MZCODE;
    }

    public void setMZCODE(String MZCODE) {
        this.MZCODE = MZCODE;
    }

    public String getMZ() {
        return MZ;
    }

    public void setMZ(String MZ) {
        this.MZ = MZ;
    }

    public String getSEXCODE() {
        return SEXCODE;
    }

    public void setSEXCODE(String SEXCODE) {
        this.SEXCODE = SEXCODE;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getXLCODE() {
        return XLCODE;
    }

    public void setXLCODE(String XLCODE) {
        this.XLCODE = XLCODE;
    }

    public String getXL() {
        return XL;
    }

    public void setXL(String XL) {
        this.XL = XL;
    }

    public String getSCHOOL() {
        return SCHOOL;
    }

    public void setSCHOOL(String SCHOOL) {
        this.SCHOOL = SCHOOL;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getPSNCODE() {
        return PSNCODE;
    }

    public void setPSNCODE(String PSNCODE) {
        this.PSNCODE = PSNCODE;
    }

    public String getPSNNAME() {
        return PSNNAME;
    }

    public void setPSNNAME(String PSNNAME) {
        this.PSNNAME = PSNNAME;
    }

    public String getRSDATE() {
        return RSDATE;
    }

    public void setRSDATE(String RSDATE) {
        this.RSDATE = RSDATE;
    }

    public String getJG() {
        return JG;
    }

    public void setJG(String JG) {
        this.JG = JG;
    }

    public String getHK() {
        return HK;
    }

    public void setHK(String HK) {
        this.HK = HK;
    }

    public String getRDDATE() {
        return RDDATE;
    }

    public void setRDDATE(String RDDATE) {
        this.RDDATE = RDDATE;
    }

    public String getZZMMCODE() {
        return ZZMMCODE;
    }

    public void setZZMMCODE(String ZZMMCODE) {
        this.ZZMMCODE = ZZMMCODE;
    }

    public String getZZMM() {
        return ZZMM;
    }

    public void setZZMM(String ZZMM) {
        this.ZZMM = ZZMM;
    }

    public String getWORKDATE() {
        return WORKDATE;
    }

    public void setWORKDATE(String WORKDATE) {
        this.WORKDATE = WORKDATE;
    }

    public String getZY() {
        return ZY;
    }

    public void setZY(String ZY) {
        this.ZY = ZY;
    }

//    public Blob getPHOTO() {
//        return PHOTO;
//    }
//
//    public void setPHOTO(Blob PHOTO) {
//        this.PHOTO = PHOTO;
//    }


    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getPY() {
        return PY;
    }

    public void setPY(String PY) {
        this.PY = PY;
    }

    public String getQPY() {
        return QPY;
    }

    public void setQPY(String QPY) {
        this.QPY = QPY;
    }

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public byte[] getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(byte[] PHOTO) {
        this.PHOTO = PHOTO;
    }


    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public String getCORP_SYSTEM() {
        return CORP_SYSTEM;
    }

    public void setCORP_SYSTEM(String CORP_SYSTEM) {
        this.CORP_SYSTEM = CORP_SYSTEM;
    }

    public String getSEARCH_INFO() {
        return SEARCH_INFO;
    }

    public void setSEARCH_INFO(String SEARCH_INFO) {
        this.SEARCH_INFO = SEARCH_INFO;
    }

    public String getGL() {
        return GL;
    }

    public void setGL(String GL) {
        this.GL = GL;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPsnclassname() {
        return psnclassname;
    }

    public void setPsnclassname(String psnclassname) {
        this.psnclassname = psnclassname;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;

        if(obj instanceof zgfh_bi_psndoc){
            zgfh_bi_psndoc psndoc = (zgfh_bi_psndoc)obj;
            if(this.getPSNCODE().equals(psndoc.getPSNCODE())){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + ( PSNCODE== null ? 0 : PSNCODE.hashCode());
        return result;
    }
}
