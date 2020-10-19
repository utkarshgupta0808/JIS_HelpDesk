package com.is.jishelpdesk;

public class EmpModel {

    String name, number, pan, address, aadhaar, photo;
    long empid;

    public EmpModel(String name, String number,String aadhaar, String pan, String address, long empid, long cActive, long ctotal, String photo) {
        this.name = name;
        this.number = number;
        this.pan = pan;
        this.address = address;
        this.empid = empid;
        this.aadhaar=aadhaar;
        this.photo=photo;


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

    public String getPhoto() {
        return photo;
    }
}
