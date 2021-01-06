package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class User {

    private String username;
    private String password;
    private int age;

    //COSTRUTTORE
    public User(String username, String password, int age)
    {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getAge(){
        return age;
    }



}
