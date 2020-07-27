package com.is.jishelpdesk;

public class EmpModel {

    String name, number, pan, address, aadhaar;
    long empid;

    public EmpModel(String name, String number, String pan, String address, long empid, long cActive, long ctotal) {
        this.name = name;
        this.number = number;
        this.pan = pan;
        this.address = address;
        this.empid = empid;
        this.aadhaar=aadhaar;


    }

    public EmpModel() {
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPan() {
        return pan;
    }

    public String getAddress() {
        return address;
    }

    public long getEmpid() {
        return empid;
    }

    public String getAadhaar() {
        return aadhaar;
    }


}
