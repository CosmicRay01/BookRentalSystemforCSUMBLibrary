package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import java.text.DecimalFormat;

public class Book {
    private int id;
    private String title;
    private String author;
    private String ISBN;
    private Double fee;
    DecimalFormat dec = new DecimalFormat("#.00");

    Book() {
        this.id = -1;
        this.title = "";
        this.author = "";
        this.ISBN = "";
        this.fee = -1.0;
    }

    Book(String title, String author, String ISBN, double fee) {
        this.id = -1;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.fee = fee;
    }

    Book(int id, String title, String author, String ISBN, double fee) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public double getFee() {
        return fee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Title: " + title
                + "\n         Author: " + author
                + "\n         ISBN: " + ISBN
                + "\n         Fee: $" + dec.format(fee) + "/hour";
    }
}



