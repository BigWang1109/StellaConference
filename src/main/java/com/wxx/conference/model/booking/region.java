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
@Table(name = "region")
public class region {

    @Id
    @Column(name="regionId")
    private String regionId;

    @Column(name="regionName")
    private String regionName;

    @Column(name = "regionFloor")
    private String regionFloor;

    @Column(name = "flag")
    private boolean flag;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionFloor() {
        return regionFloor;
    }

    public void setRegionFloor(String regionFloor) {
        this.regionFloor = regionFloor;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
