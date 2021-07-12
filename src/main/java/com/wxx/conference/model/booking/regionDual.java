package com.wxx.conference.model.booking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by thinkpad on 2017-8-17.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name = "regiondual")
public class regionDual {

    @Id
    @Column(name = "dualId")
    private String dualId;

    @Column(name = "dualName")
    private String dualName;

    public String getDualId() {
        return dualId;
    }

    public void setDualId(String dualId) {
        this.dualId = dualId;
    }

    public String getDualName() {
        return dualName;
    }

    public void setDualName(String dualName) {
        this.dualName = dualName;
    }
}
