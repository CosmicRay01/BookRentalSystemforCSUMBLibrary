package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

public class Transaction {
    private int id;
    private String type;
    private String username;
    private String date;

    Transaction() {
        id = -1;
        type = "";
        username = "";
        date = "";
    }

    Transaction(String type, String username, String date) {
        this.type = type;
        this.username = username;
        this.date = date;
    }

    Transaction(int id, String type, String username, String date) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {

        this.type = type;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public int getId() {

        return id;
    }

    public String getType() {

        return type;
    }

    public String getUsername() {

        return username;
    }

    public String getDate() {

        return date;
    }

    @Override
    public String toString() {

        String str = "Type: " + type + "\n       User: " + username + "\n       Date: " + date + "\n";
        return str;
    }
}
