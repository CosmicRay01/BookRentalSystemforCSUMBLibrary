package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

public class Account {
    private int id;
    private String username;
    private String password;

    Account(){
        id = -1;
        username = "";
        password = "";
    }

    Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    Account(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }

    public String toString() {

        return username;
    }
}
