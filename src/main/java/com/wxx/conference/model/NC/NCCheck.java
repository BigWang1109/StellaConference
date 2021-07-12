package com.wxx.conference.model.NC;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by thinkpad on 2020-5-7.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class NCCheck {

    private String checkType;

    private String checkValue;

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }
}
