package com.wxx.conference.model.NC;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by thinkpad on 2020-7-6.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="view_ps_customer_afterrecord")
public class view_ps_customer_afterrecord {
    @Id
    @Column(name="vpreferredtel")
    public String vpreferredtel;

    @Column(name="vcname")
    public String vcname;

    @Column(name="vcode")
    public String vcode;

    @Column(name="vname")
    public String vname;

    @Column(name="contactdate")
    public String contactdate;

    @Column(name="fcustype")
    public String fcustype;

    @Column(name="dmakedate")
    public String dmakedate;

    @Column(name="dproceedingdate")
    public String dproceedingdate;

    @Column(name="fproceedingtype")
    public String fproceedingtype;

    @Column(name="vproceedingdesc")
    public String vproceedingdesc;


}
