package com.wxx.conference.model.HR;

import javax.persistence.*;

/**
 * Created by thinkpad on 2020-9-28.
 */
@Entity
@Table(name="oabi.ZGFH_BI_TEST_PHOTO")
public class ZGFH_BI_TEST_PHOTO {
    @Id
    @Column(name = "CODE")
    private String CODE;
    @Column(name = "PHOTO")
    private byte[] PHOTO;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public byte[] getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(byte[] PHOTO) {
        this.PHOTO = PHOTO;
    }
}
