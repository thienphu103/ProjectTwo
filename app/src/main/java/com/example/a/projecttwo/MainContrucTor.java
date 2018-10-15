package com.example.a.projecttwo;

import java.io.Serializable;

public class MainContrucTor implements Serializable {
    String nameCustomer;
    String NumberTable;
    String Status;




    public MainContrucTor() {
    }

    public MainContrucTor(String nameCustomer, String numberTable, String status) {
        this.nameCustomer = nameCustomer;
        NumberTable = numberTable;
        Status = status;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNumberTable() {
        return NumberTable;
    }

    public void setNumberTable(String numberTable) {
        NumberTable = numberTable;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
