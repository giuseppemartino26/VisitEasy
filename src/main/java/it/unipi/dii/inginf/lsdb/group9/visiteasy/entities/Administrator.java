package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class Administrator {

    private String username;
    private String password;

    //inserisco costruttori
    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;

    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }


}