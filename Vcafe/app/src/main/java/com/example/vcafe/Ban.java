package com.example.vcafe;

import java.io.Serializable;

public class Ban implements Serializable {
    private String lastChange;
    private String name;
    private int status;
    public Ban()
    {
    }

    public Ban(String lastChange, String name, int status) {
        this.lastChange = lastChange;
        this.name = name;
        this.status = status;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
