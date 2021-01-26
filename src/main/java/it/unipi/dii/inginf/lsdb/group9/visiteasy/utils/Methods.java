package it.unipi.dii.inginf.lsdb.group9.visiteasy.utils;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Methods {

    public static boolean printAllDocList(String city, String spec,ArrayList<Doctor> doclist)
    {
        if (doclist.size() == 0)
        {
            return false;
        }

        System.out.println("----List of all "+spec+" of "+city+"----");

        for (Doctor doctor : doclist) {
            System.out.println(doctor.getName() + " us:" + doctor.getUsername());
        }
        return true;
    }

    public static boolean printCheapestDocList(String city, String spec, ArrayList<Doctor> doclist)
    {
        if (doclist.size() == 0)
        {
            return false;
        }

        System.out.println("----List of top 3 cheapest "+spec+" of "+city+"----");

        for (Doctor doctor : doclist) {
            System.out.println("us: " + doctor.getUsername() + "  " + doctor.getName() + "   Price of the medical examination: " + doctor.getPrice() + "€");
        }
        return true;
    }


    public static void printAllDocList2(ArrayList<Doctor> doclist)
    {
        System.out.println("----List of all doctors----");

        for (Doctor doctor : doclist) {
            System.out.println(doctor.getName() + " us:" + doctor.getUsername());
        }
    }



    public static void printCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("-----Welcome to VisitEasy----- "+
                "\n Today is: ");
        System.out.println(dtf.format(now));
    }

    public static void printSlots(ArrayList<Reservation> list)
    {

        System.out.println("---ALL AVAIABLE SLOTS---");
        for (Reservation reservation : list) {
            System.out.println(reservation.getDate());
        }
    }



    public static boolean printUserRes(ArrayList<Reservation> list)
    {
        if (list.size() == 0){
            return false;
        }

        System.out.println("---MY RESERVATIONS---");
        for (Reservation reservation : list) {
            System.out.println("Date: " + reservation.getDate() + " Doctor us: " + reservation.getDocusername() + " Doctor name: " + reservation.getDocname());
        }
        return true;
    }

    public static boolean printreviews(ArrayList<Review> reviews)
    {
        if (reviews.size() > 0) {
            for (Review review : reviews) {
                System.out.println("===================================================================================\n" +
                        "id_review:" + review.getId() + "\n" + review.getDateTime() + "\n" + review.getUsername() + "\nRating:" + review.getRating() + "\n" + review.getText() + "\nNumber of likes: " + review.getNumLikes());
            }
        }else {
            System.out.println("The doctor doesn't have any reviews yet");
            return false;
        }
        return true;
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


    public static boolean printResDoc(ArrayList<Reservation> list){
        if (list.size() == 0)
        {
            return false;
        }

        System.out.println("---RESERVATIONS---");
        for (Reservation reservation : list) {
            System.out.println(reservation.getDate() + "      patient: " + reservation.getUsername());
        }
        return true;
    }



}