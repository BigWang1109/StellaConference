package com.wxx.conference.model.boardRoomManagement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by thinkpad on 2017-9-8.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="boardRoomDevice")
public class boardRoomDevice {

    @Id
    @Column(name="deviceId")
    private String deviceId;

    @Column(name="deviceName")
    private String deviceName;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss" )
    @Column(name = "date")
    private Date date;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
