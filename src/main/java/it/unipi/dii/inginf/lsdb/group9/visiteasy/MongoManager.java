package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import com.mongodb.client.*;

import  java.util.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.result.UpdateResult;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class MongoManager {

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase db = mongoClient.getDatabase("doctors");
    MongoCollection<Document> users = db.getCollection("users");
    MongoCollection<Document> doctors = db.getCollection("doctors");
    MongoCollection<Document> administrators = db.getCollection("administrator");

    Consumer<Document> printDocuments = document -> {System.out.println(document.toJson());};
    Consumer<Document> printvalue = document -> {System.out.println(document.getString("_id"));};

    Scanner keyboard = new Scanner(System.in);
    boolean add_user(User user)
    {
        if (users.countDocuments(new Document("username", user.getUsername())) == 0) {
            Document doc = new Document("username", user.getUsername()).append("password", user.getPassword()).append("age", user.getAge());
            users.insertOne(doc);
            return true;
        } else return false;
    }


    boolean login_user(User user)
    {
        Document result = users.find(eq("username", user.getUsername())).first(); //salvo in "result" il documento il cui campo username è uguale a quello passato come parametro
        try {
            result.getString("username");
       }catch (NullPointerException exception){
           System.out.println("The username does not exist");
           return false;
       }

        String psw = result.getString("password");
        if (psw.equals(user.getPassword())) {
            int age = result.getInteger("age");
            user.setAge(age);
            System.out.println("Correct credentials");
            return true;
        } else{
            System.out.println("Incorrect password");
            return false;
        }

    }


    boolean login_doctor(Doctor doctor)
    {
        Document result = users.find(eq("username", doctor.getUsername())).first();
        try {
            result.getString("username");
        }catch (NullPointerException exception){
            System.out.println("The username does not exist");
            return false;
        }

        String psw = result.getString("password");
        if (psw.equals(doctor.getPassword())) {
            System.out.println("Correct credentials");
            return true;
        } else{
            System.out.println("Incorrect password");
            return false;
        }

    }


    boolean login_administrator(Administrator administrator){
        Document result = administrators.find(eq("username", administrator.getUsername())).first(); //salvo in "result" il documento il cui campo username è uguale a quello passato come parametro
        try {
            result.getString("username");
        }catch (NullPointerException exception){
            System.out.println("The username does not exist");
            return false;
        }
        String psw = result.getString("password");
        if (psw.equals(administrator.getPassword())) {
            System.out.println("Correct credentials");
            return true;}
        else{
            System.out.println("Incorrect password");

        return false;}
    }
    // ADD ADMINISTRATOR BY ADMINISTRATOR

boolean add_administrator(Administrator administrator) {

    if (administrators.countDocuments(new Document("username", administrator.getUsername())) == 0) {
        Document doc = new Document("username", administrator.getUsername()).append("password", administrator.getPassword());
        administrators.insertOne(doc);
        return true;
    } else return false;

}

    //ADD NEW DOCTOR BY ADMINISTRATOR
    boolean add_doctor_by_administrator(Doctor doctor){

        if (doctors.countDocuments(new Document("username", doctor.getUsername())) == 0) {
            Document doc = new Document("username", doctor.getUsername()).append("password", doctor.getPassword());
            doctors.insertOne(doc);
            return true;
        } else return false;}



    void display_cities() //stampa tutte le città presenti nel DB
    {
        Bson myGroup = Aggregates.group("$city");
        doctors.aggregate(Arrays.asList(myGroup)).forEach(printvalue);
    }

    void display_spec() //stampa tutte le specializzazioni dei medici presenti nel DB
    {
        Bson myGroup = Aggregates.group("$specialization");
        doctors.aggregate(Arrays.asList(myGroup)).forEach(printvalue);
    }



    //UPDATE USER BY THE ADMINISTRATOR
    boolean update_user_by_the_administrator(User user) {

        Document result = users.find(eq("username", user.getUsername())).first();
        try {
            result.getString("username");
        } catch (NullPointerException exception) {
            System.out.println("The username does not exist");
            return false;}
        //tramite parametro age, decido se devo moficare password, username o age o aggiungere il campo

        if(user.getAge()==1){
            try {
                //ho passato come parametro il nuovo username nel campo password
                users.updateOne(eq("username", user.getUsername()), (Bson) set("username",user.getPassword() ));
            }catch (NullPointerException ex) {
                System.out.println("The username does not exist");
                return false;
        }
        }else if (user.getAge()==2){
            try {
                //ho passato come parametro il la nuova password nel campo password
                users.updateOne(eq("username", user.getUsername()), (Bson) set("password",user.getPassword() ));
            }catch (NullPointerException ex) {
                System.out.println("The username does not exist");
                return false;
            }


        }else {
            try {
                //chiedo la nuova età
                System.out.println("Insert new age");
                int age = keyboard.nextInt();
                users.updateOne(eq("username", user.getUsername()), (Bson) set("age",age ));
            }catch (NullPointerException ex) {
                System.out.println("The username does not exist");
                return false;
            }

        }
        return true;
    }
//DELETE USER
    boolean delete_user_by_the_administrator(User user){
        Document result = users.find(eq("username", user.getUsername())).first();
        try {
            result.getString("username");
        } catch (NullPointerException exception) {
            System.out.println("The username does not exist");
            return false;}
        users.deleteOne(eq("username",user.getUsername()));
        return true;
    }


    //ADD NEW DOCTOR BY ADMINISTRATOR
   /* boolean add_new_doctor_by_administrator(Doctor doctor)
    {
        if (users.countDocuments(new Document("username", user.getUsername())) == 0) {
            Document doc = new Document("username", user.getUsername()).append("password", user.getPassword()).append("age", user.getAge());
            users.insertOne(doc);
            return true;
        } else return false;
    }

    /*void populate_doctors_from_file(String path)
    {

        List<Document> observationDocuments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path));) {
            String line;
            while ((line = br.readLine()) != null) {
                observationDocuments.add(Document.parse(line));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        doctors.insertMany(observationDocuments);
    }


    void populate_users_from_file(String path)
    {

        List<Document> observationDocuments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path));) {
            String line;
            while ((line = br.readLine()) != null) {
                ((ArrayList<?>) observationDocuments).add(Document.parse(line));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        users.insertMany(observationDocuments);
    }

    public void populate_doctors_from_file() {
    }
    public void populate_users_from_file() {
    }*/
    }
