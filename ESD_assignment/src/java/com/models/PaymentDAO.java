/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Prithpal Sooriya
 *
 * This will handle the Payment bean/POJO and its table relations in the database
 * implements GenericDAO, and uses generics PaymentBean (the data we are accessing) and
 * Integer (the ID for the PaymentBean)
 *
 */
public class PaymentDAO implements GenericDAO<PaymentBean, Integer> {

    //variable used to access the database/JDBC
    private Database db;

    //constructor to initialise the database
    public PaymentDAO() {
        db = new Database();
    }

    //Adds a new Payment to the database (via the JDBC db)
    @Override
    public boolean create(PaymentBean newPayment) {
        boolean inserted = db.insertData(Database.PAYMENTS_TABLE, newPayment);
        return inserted;
    }

    //gets all payments from the Payments table
    @Override
    public List<PaymentBean> getAll() {
        ArrayList<PaymentBean> payments = new ArrayList<>();

        //for each payment in payments table
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE)) {
            PaymentBean payment = (PaymentBean) object;
            payments.add(payment);
        }

        return payments;
    }

    //gets all payments that have the same memberID
    public List<PaymentBean> getAllByMemberId(String memberId) {
        ArrayList<PaymentBean> payments = new ArrayList<>();

        //for each payment in payments table
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE, Database.PAYMENTS_TABLE_MEMBER_ID, memberId)) {
            PaymentBean payment = (PaymentBean) object;
            payments.add(payment);
        }

        return payments;
    }

    //get all payments that have same type of payment
    public List<PaymentBean> getAllByTypeOfPayment(String typeOfPayment) {
        ArrayList<PaymentBean> payments = new ArrayList<>();

        //for each payment in payments table
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE, Database.PAYMENTS_TABLE_TYPE_OF_PAYMENT, typeOfPayment)) {
            PaymentBean payment = (PaymentBean) object;
            payments.add(payment);
        }

        return payments;
    }

    //get all payments that are used for the annual turnover
    public List<PaymentBean> annualTurnoverList() {
        ArrayList<PaymentBean> payments = new ArrayList<>();
        double total = 0;
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE)) {
            PaymentBean payment = (PaymentBean) object;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            if (cal.getTimeInMillis() - payment.getDate().getTime() <= 0) {

                payments.add(payment);
                total += payment.getAmount();

            }
        }

        return payments;
    }

    //get the amount from payments that are used for the annual turnover
    public double annualTurnoverDouble() {
        ArrayList<PaymentBean> payments = new ArrayList<>();
        double total = 0;
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE)) {
            PaymentBean payment = (PaymentBean) object;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            if (cal.getTimeInMillis() - payment.getDate().getTime() <= 0) {

                payments.add(payment);
                total += payment.getAmount();

            }
        }

        return total;
    }

    //get the payment from the given ID (as a parameter)
    @Override
    public PaymentBean getByID(Integer id) {
        PaymentBean payment = (PaymentBean) db.searchData(Database.PAYMENTS_TABLE, Database.PAYMENTS_TABLE_ID, id);
        if (payment == null) {
            System.out.println("PaymentDAO.getByID(): Payment not found");
        }
        //other classes need to careful to see if this is null or not.
        return payment;
    }
}
