package com.wxx.conference.model.dingding;

/**
 * Created by thinkpad on 2020-10-9.
 */
public class JsapiTicket {
    private String ticket;
    private Long expires_in;

    public JsapiTicket() {
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}
