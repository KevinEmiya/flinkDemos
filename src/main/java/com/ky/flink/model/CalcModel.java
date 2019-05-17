package com.ky.flink.model;

import java.io.Serializable;

public class CalcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    String group;

    int value;

    boolean switchOn;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSwitchOn() {
        return switchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        this.switchOn = switchOn;
    }
}
