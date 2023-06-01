package com.demo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoObject {
    @JsonProperty("SBD")
    private String SBD;

    @JsonProperty("MDT")
    private String MDT;

    // Getter và Setter

    public String getSBD() {
        return SBD;
    }

    public void setSBD(String SBD) {
        this.SBD = SBD;
    }

    public String getMDT() {
        return MDT;
    }

    public void setMDT(String MDT) {
        this.MDT = MDT;
    }
}
