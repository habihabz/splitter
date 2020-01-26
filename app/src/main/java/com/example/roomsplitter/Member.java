package com.example.roomsplitter;

public class Member {
    public String member_id;
    public String member_name;
    public String member_phone;
    public String member_email;
    public double member_balance;

    public Member(String member_id, String member_name, String member_phone, String member_email, double member_balance) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_phone = member_phone;
        this.member_email = member_email;
        this.member_balance = member_balance;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public double getMember_balance() {
        return member_balance;
    }

    public void setMember_balance(double member_balance) {
        this.member_balance = member_balance;
    }
}
