package it.unipi.dii.inginf.lsdb.group9.visiteasy.utils;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.*;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.MongoManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.Neo4jManager;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Methods {

    private static MongoManager mdb = new MongoManager();
 //   private static Neo4jManager ndb = new Neo4jManager("neo4j://localhost:11003", "neo4j", "root");
 private static Neo4jManager ndb = new Neo4jManager("bolt://172.16.3.110:7687", "neo4j", "root2");
    private static Scanner keyboard = new Scanner(System.in);
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader tastiera = new BufferedReader(input);



    public static void printAllDocList(String city, String spec)
    {
        ArrayList<Doctor> doclist = new ArrayList<>();
        doclist = mdb.getDocByCitySpec(city, spec);

        System.out.println("----List of all "+spec+" of "+city+"----");

        for (int i = 0; i < doclist.size(); i++)
        {
            System.out.println(doclist.get(i).getName()+" us:"+doclist.get(i).getUsername());
        }
    }


    public static void printCheapestDocList(String city, String spec)
    {
        ArrayList<Doctor> doclist = new ArrayList<>();
        doclist = mdb.cheapestDoc(city,spec);

        System.out.println("----List of top 3 cheapest "+spec+" of "+city+"----");

        for (int i = 0; i < doclist.size(); i++)
        {
            System.out.println("us: "+doclist.get(i).getUsername()+"  "+doclist.get(i).getName()+"   Price of the medical examination: "+doclist.get(i).getPrice()+"€");
        }
    }


    public static void printMyProfile(String username)
    {
        Doctor doctor = mdb.getMyProfile(username);
        System.out.println(doctor.getName()+"\nName: "+doctor.getName()+"\nAddress: "+doctor.getAddress()+"\nprice : "+doctor.getPrice()+"€"+"\nBiography: "+doctor.getBio());
    }


    public static void printCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("-----Welcome to VisitEasy----- "+
                "\n Today is: ");
        System.out.println(dtf.format(now));
    }

    public static void printSlots(String name){
        ArrayList<Reservation> list = new ArrayList<>();
        list = mdb.showEntirereservations(name);

        System.out.println("---ALL AVAIABLE SLOTS---");
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).getDate());
        }
    }



    public static void printUserRes(String name){
        ArrayList<Reservation> list = new ArrayList<>();
        list = mdb.showUserReservations(name);

        System.out.println("---MY RESERVATIONS---");
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println("Date: "+list.get(i).getDate()+" Doctor us: "+list.get(i).getDocusername()+" Doctor name: "+list.get(i).getDocname());
        }
    }

    public static void printreviews(Doctor doctor)
    {
        ArrayList<Review> reviews = ndb.showReviews3(doctor);

        if (reviews.size() > 0) {
            for (int i = 0; i < reviews.size(); i++) {
                System.out.println("===================================================================================\n" +
                        "id_review:" + reviews.get(i).getId() + "\n" + reviews.get(i).getDateTime() + "\n" + reviews.get(i).getUsername() + "\nRating:" + reviews.get(i).getRating() + "\n" + reviews.get(i).getText() + "\nNumber of likes: " + reviews.get(i).getNumLikes());
            }
        }else {
            System.out.println("The doctor doesn't have any reviews yet");
        }
    }

   public static String getAlphaNumericString(int n)
    {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    public static void printResDoc(String name){
        ArrayList<Reservation> list = new ArrayList<>();
        list = mdb.showEntirereservationsDoc(name);

        System.out.println("---RESERVATIONS---");
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).getDate()+"      patient: "+list.get(i).getUsername());
        }
    }



}
