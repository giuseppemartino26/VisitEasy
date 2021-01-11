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

    public Doctor(String name) {
        this.name = name;
    }

    public Doctor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Doctor(String username, String password, int price) {
        this(username, password);
        this.price = price;
    }

    public Doctor(String name, String address,String bio)
    {
        this.name = name;
        this.address = address;
        this.bio = bio;
    }


    public Doctor(String username, String password,int price, String name, String city, String specialization, String bio, String address) {
        this(username, password,price);
        this.bio = bio;
        this.city = city;
        this.name = name;
        this.specialization = specialization;
        this.address = address;

    }




    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getName() { return name; }

    public String getCity() { return city; }

    public String getSpecialization() {
        return specialization;
    }

    public String getBio() {
        return bio;
    }

    public String getAddress() {
        return address;
    }


    public int getPrice(){
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}