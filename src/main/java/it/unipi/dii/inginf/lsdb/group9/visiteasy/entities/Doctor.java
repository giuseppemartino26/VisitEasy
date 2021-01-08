package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class Doctor {

    private String username;
    private String password;
    private String name;
    private int price;
    private String city;
    private String specialization;
    private String bio;
    private String address;


    //COSTRUTTORI

    public Doctor(String username, String password, String name, String city, String specialization, String bio, String address) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.city = city;
        this.name = name;
        this.specialization = specialization;
        this.address = address;
    }

    public Doctor(String username, String password, String name, String city, String specialization, String bio, String address, int price) {
        this(username, password, name, city, specialization, bio, address);
        this.price = price;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int age) {
        this.price = price;
    }
}

