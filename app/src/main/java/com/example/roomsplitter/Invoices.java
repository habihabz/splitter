package com.example.roomsplitter;

import java.sql.Timestamp;
import java.util.Date;

public class Invoices {
    public long inv_id;
    public String inv_room;
    public String inv_date;
    public String inv_type;
    public double inv_amount;
    public String inv_cons_list_id;
    public String inv_paid_by;
    public String inv_doc_link;
    public String inv_remarks;
    public long inv_cre_by;
    public Date inv_cre_date;

    public long getInv_id() {
        return inv_id;
    }

    public void setInv_id(long inv_id) {
        this.inv_id = inv_id;
    }

    public String getInv_room() {
        return inv_room;
    }

    public void setInv_room(String inv_room) {
        this.inv_room = inv_room;
    }

    public String getInv_date() {
        return inv_date;
    }

    public void setInv_date(String inv_date) {
        this.inv_date = inv_date;
    }

    public String getInv_type() {
        return inv_type;
    }

    public void setInv_type(String inv_type) {
        this.inv_type = inv_type;
    }

    public double getInv_amount() {
        return inv_amount;
    }

    public void setInv_amount(double inv_amount) {
        this.inv_amount = inv_amount;
    }

    public String getInv_cons_list_id() {
        return inv_cons_list_id;
    }

    public void setInv_cons_list_id(String inv_cons_list_id) {
        this.inv_cons_list_id = inv_cons_list_id;
    }

    public String getInv_paid_by() {
        return inv_paid_by;
    }

    public void setInv_paid_by(String inv_paid_by) {
        this.inv_paid_by = inv_paid_by;
    }

    public String getInv_doc_link() {
        return inv_doc_link;
    }

    public void setInv_doc_link(String inv_doc_link) {
        this.inv_doc_link = inv_doc_link;
    }

    public String getInv_remarks() {
        return inv_remarks;
    }

    public void setInv_remarks(String inv_remarks) {
        this.inv_remarks = inv_remarks;
    }

    public long getInv_cre_by() {
        return inv_cre_by;
    }

    public void setInv_cre_by(long inv_cre_by) {
        this.inv_cre_by = inv_cre_by;
    }

    public Date getInv_cre_date() {
        return inv_cre_date;
    }

    public void setInv_cre_date(Date inv_cre_date) {
        this.inv_cre_date = inv_cre_date;
    }
}
