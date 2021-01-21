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

    public Doctor(String name, int price){
        this(name);
        this.price = price;
    }

    public Doctor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Doctor(String username, String password, String name) {
        this(username, password);
        this.name = name;
    }

    public Doctor(String username, String password, int price) {
        this(username, password);
        this.price = price;
    }

    public Doctor(int price,String name, String username)
    {
        this.price = price;
        this.name= name;
        this.username = username;
    }

    public Doctor(String name, int price, String address, String bio)
    {
        this.name = name;
        this.price = price;
        this.address = address;
        this.bio = bio;

    }

    public Doctor(String name, String specialization, String city, int price)
    {
        this.name = name;
        this.specialization = specialization;
        this.city = city;
        this.price = price;
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