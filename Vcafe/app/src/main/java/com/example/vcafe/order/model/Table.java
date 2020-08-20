package com.example.vcafe.order.model;

public class Table {
    public final static int STATUS_AVAILABLE=1;
    public final static int STATUS_PROCESSING=2;
    public final static int STATUS_UNFINISHED=3;

    private String name;
    private int status;
    private   String lastChange;

    public Table() {
    }

    public Table(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public Table(String name, int status, String lastChange) {
        this.name = name;
        this.status = status;
        this.lastChange = lastChange;
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
