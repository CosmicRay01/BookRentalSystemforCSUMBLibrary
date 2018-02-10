package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hold {
    private int id;
    private String username;
    private String bookTitle;
    private String pickUpDate;
    private String returnDate;

    Hold() {
        id = -1;
        username = null;
        bookTitle = null;
        pickUpDate = null;
        returnDate = null;
    }

    Hold(String bookTitle, String username, String pickUpDate, String returnDate) {
        id = -1;
        this.bookTitle = bookTitle;
        this.username = username;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
    }

    Hold(int id, String bookTite, String username, String  pickUpDate, String returnDate) {
        this.id = id;
        this.bookTitle = bookTite;
        this.username = username;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public String getBookTitle() {

        return bookTitle;
    }

    public String getUsername() {
        return username;
    }

    public String getPickUpDate() {

        return pickUpDate.toString();
    }

    public String getReturnDate() {

        return returnDate.toString();
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setBookTitle(String bookTitle) {

        this.bookTitle = bookTitle;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPickUpDate(String pickUpDate) {

        this.pickUpDate = pickUpDate;
    }

    public void setReturnDate(String returnDate) {

        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Id: " + id
                + "\n       Book:" + bookTitle
                + "\n       User: " + username
                + "\n       Pick up date: " + pickUpDate
                + "\n       Return date: " + returnDate;
    }
}
