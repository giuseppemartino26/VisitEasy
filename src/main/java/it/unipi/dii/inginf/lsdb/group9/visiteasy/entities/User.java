package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

import java.util.Date;

public class User {

    private String username;
    private String password;
    private String city;

    //COSTRUTTORI

    public User(String username)
    {
        this.username = username;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User(String username, int age)
    {
        this.username = username;
        this.age = age;
    }

    public User(String username, String password, String city)
    {
        this(username, password);
        this.city = city;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getCity(){return city;}

    //public void setAge(int age) {
        //this.age = age;
    //}
}
