package com.wxx.conference.model.booking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by thinkpad on 2017-8-17.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name="bookingregion")
public class bookingRegion {

    @Id
    @Column(name = "bookingRegionId")
    private String bookingRegionId;

    @Column(name="bookingId")
    private String bookingId;

    @Column(name="regionId")
    private String regionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @Column(name="bookingDate")
    private Date bookingDate;

    @Column(name="bookingDual")
    private String bookingDual;

    @Column(name="bookingTitle")
    private String bookingTitle;

    @Column(name="isManager")
    private boolean isManager;

    @Column(name="flag")
    private String flag;

    @Column(name="userId")
    private String userId;

    public String getBookingRegionId() {
        return bookingRegionId;
    }

    public void setBookingRegionId(String bookingRegionId) {
        this.bookingRegionId = bookingRegionId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingDual() {
        return bookingDual;
    }

    public void setBookingDual(String bookingDual) {
        this.bookingDual = bookingDual;
    }

    public String getBookingTitle() {
        return bookingTitle;
    }

    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
