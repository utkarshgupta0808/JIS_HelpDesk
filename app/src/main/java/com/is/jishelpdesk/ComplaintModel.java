package com.is.jishelpdesk;

public class ComplaintModel {

    long tokenId;
    String name;
    String date;
    String status;
    String address;
    String complaint;
    String mobile;

    public ComplaintModel(){

    }


    public ComplaintModel(long tokenId, String name, String date, String status, String address, String complaint, String mobile) {
        this.tokenId = tokenId;
        this.name = name;
        this.date = date;
        this.status = status;
        this.address = address;
        this.complaint = complaint;
        this.mobile = mobile;
    }



    public long getTokenId() {
        return tokenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getMobile() {
        return mobile;
    }


}
