package com.diffa.eticket;

import android.telephony.SmsManager;
import android.widget.Toast;


/**
 * Created by DF on 17/11/2017.
 */

public class ComputeMovie {
    private int noTickets;
    private double price;
    private double totalPrice;


    public void computeTotal() {
        this.setTotalPrice(this.getNoTickets() * this.getPrice());
    }

    public double getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(int noTickets) {
        this.noTickets = noTickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void classType(String classType) {
        switch(classType) {
            case "Regular":
                this.price = 30000;
                break;
            case "Deluxe":
                this.price = 45000;
                break;
            default:
                break;
        }
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
