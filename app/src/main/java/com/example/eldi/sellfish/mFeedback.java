package com.example.eldi.sellfish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class mFeedback {
    private String isi, status, pengirim;

    private ArrayList<String> feedback;

    public mFeedback() {

    }

    public mFeedback(String isi, String status,
                     ArrayList<String> feedback) {
        this.isi = isi;
        this.status = status;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public ArrayList<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(ArrayList<String> feedback) {
        this.feedback = feedback;
    }


}


