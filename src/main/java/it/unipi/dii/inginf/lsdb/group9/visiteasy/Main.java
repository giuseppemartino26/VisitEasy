package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.*;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.MongoManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.Neo4jManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods;
import org.joda.time.DateTime;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import  java.util.*;
import java.util.concurrent.*;




public class Main {

    private static MongoManager mdb = new MongoManager();
    private static Scanner keyboard = new Scanner(System.in);
    private static Neo4jManager ndb = new Neo4jManager("neo4j://localhost:11003", "neo4j", "root");


    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader tastiera = new BufferedReader(input);

    static Methods methods = new Methods();





    public static void main(String[] args) {

        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mdb.deleteDate();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(deleteRunnable, 0, 24, TimeUnit.HOURS);



        while (true)
        {
            Methods.printCurrentDate();
            System.out.println("\nSelect a command: \nI'm a:" +
                    "\n1. Doctor" +
                    "\n2. User" +
                    "\n3. Administrator ");

            try {
                int option = keyboard.nextInt();


                switch (option) {


                    case 9:
                     /*   Review review = new Review("gm26", DateTime.now().toString(),"dottinesistente","giuseppemartino26",4,"Recensione prova");
                        ndb.addReview(review);
                        System.out.println("FATTO");*/

                     /*   Doctor doctor26 = new Doctor("Dott. Cosmo Godino");
                        ArrayList<Review> lista = ndb.showReviews(doctor26);

                        for (int i = 0; i < lista.size(); i++){
                            System.out.println("===================================================================================\n" +
                                    "id_review:"+lista.get(i).getId()+"\n"+lista.get(i).getDateTime()+"\n"+lista.get(i).getUsername()+"\nRating:"+lista.get(i).getRating()+"\n"+lista.get(i).getText());
                        }*/

                       /* User user266 = new User("giusepppe","martino");
                        Review review = new Review("gm26", DateTime.now().toString(),"dottinesistente","giuseppemartino26",4,"Recensione prova");
                        ndb.like(user266,review);*/

                        //  ndb.recommendedDoctors("Torino","Nutritionist");


                   /*     Reservation reservation26 = new Reservation("5fe8ade83a65753d7c4b4667","16/01/21 09:30","giuseppe");
                        mdb.book(reservation26);*/

                        //   System.out.println(mdb.getDocName("5fe8ade83a65753d7c4b4667"));

                        Methods.printUserRes("giuseppe");

                        System.out.println("fatto");


                        return;


                    case 1:

                        System.out.println("--DOCTOR--\nSelect a command: " +
                                "\n1. Login" +
                                "\n2. Sign up" +
                                "\n\n0. Go back");

                        try {
                            int option_doctor = keyboard.nextInt();

                            switch (option_doctor) {
                                case 1: //LOGIN

                                    while (true) {
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader tastiera = new BufferedReader(input);
                                        System.out.println("--LOGIN DOCTOR--\nInsert the username");
                                        String usernameD = tastiera.readLine();
                                        System.out.println("Insert the password");
                                        String passwordD = keyboard.next();

                                        Doctor doctor = new Doctor(usernameD, passwordD);
                                        if (!mdb.login_doctor(doctor)) {
                                            System.out.println("Please retry");
                                        } else {
                                            System.out.println("--DOCTOR--\nSelect a command: " +
                                                    "\n1. see reservations" +
                                                    "\n2. insert dates to your reservations" +
                                                    "\n3. add hour to your reservations" +
                                                    "\n4. view my profile" +

                                                    "\n\n0. Go back");
                                            try {
                                                int option_doctor2 = keyboard.nextInt();
                                                switch (option_doctor2) {
                                                    case 1:
                                                        //see doctor's reservations
                                                        mdb.showreservations(usernameD);
                                                        break;
                                                    case 2:
                                                        // a new doctor create the reservations
                                                        mdb.aggiungi_cal4(usernameD);
                                                        break;
                                                    case 3:
                                                        // a doctor can add date to his reservations
                                                        mdb.insert_hour_doctor_reservations(usernameD);
                                                        break;
                                                    case 4:
                                                        while (true) {
                                                            //stampa le sue informazioni
                                                            Methods.printMyProfile(usernameD);

                                                            // TODO Cypher


                                                            break;
                                                        }
                                                    case 0:
                                                        break;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("ERROR: insert a correct number.");
                                                continue;
                                            }


                                        }

                                    }

                                case 2: //SignUp
                                    while (true) {
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader tastiera = new BufferedReader(input);
                                        System.out.println("Insert the username");
                                        String username = tastiera.readLine();
                                        System.out.println("Insert the password");
                                        String password = keyboard.next();
                                        System.out.println("Insert your name");
                                        String name = tastiera.readLine();
                                        System.out.println("Insert your city");
                                        String city = keyboard.next();
                                        System.out.println("Insert a short bio");
                                        String bio = tastiera.readLine();
                                        System.out.println("Insert your specialization");
                                        String specialization = keyboard.next();
                                        System.out.println("Insert your address");
                                        String address = tastiera.readLine();
                                        System.out.println("Insert price for your visit");
                                        int price = keyboard.nextInt();

                                        Doctor doctor = new Doctor(username, password, price, name, city, bio, specialization, address);

                                        if (!mdb.add_doctor(doctor)) {
                                            System.out.println("The username is already used. Please choose another one.");
                                        } else {
                                            System.out.println("Registration successful!");
                                            break;
                                        }
                                    }


                                case 0:
                                    continue; //torna indietro alla pagina principale

                                default:
                                    System.out.println("insert a correct number");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: insert a correct number.");
                            continue;
                        }


                    case 2:

                    while (true)
                   {
                        System.out.println("--USER--\nSelect a command: " +
                                "\n1. Login" +
                                "\n2. Sign up" +
                                "\n\n0. Go back");


                       String option_user = tastiera.readLine();

                        switch (option_user)
                        {
                            case "1": //LOGIN USER
                                while (true)
                                {
                                    System.out.println("--LOGIN USER--\nInsert the username");
                                    String username = tastiera.readLine();
                                    System.out.println("Insert the password");
                                    String password = tastiera.readLine();

                                    User user = new User(username, password);
                                    if (!mdb.login_user(user)) {
                                        System.out.println("Please retry");
                                    } else
                                        {

                                        while (true)
                                        {

                                            System.out.println("***USER: " + username + "***" +
                                                    "\nSelect:" +
                                                    "\n1 to find a doctor" +
                                                    "\n2 to see all your reservations" +
                                                    "\n0 to come back to the login page");
                                            String com = tastiera.readLine();

                                            switch (com) {

                                                case "1":

                                                    System.out.println("Select the city and the specialization you are interested to");

                                                    System.out.println("CITIES CURRENTLY AVAIABLE:");
                                                    mdb.display_cities();

                                                    System.out.println("\nSPECIALIZATIONS CURRENTLY AVAIABLE:");
                                                    mdb.display_spec();

                                                    System.out.println("Insert the city");
                                                    String city = tastiera.readLine();

                                                    System.out.println("Insert the specialization");
                                                    String specialization = tastiera.readLine();

                                                    while (true) {
                                                        System.out.println("Choose the doctors by: (select a command)" +
                                                                "\n1. Show the entire list" +
                                                                "\n2. Most 3 cheapest doctors" +
                                                                "\n3. Doctors recommended by the system" +
                                                                "\n0. Go back");

                                                        String sort = tastiera.readLine();


                                                        switch (sort) {
                                                            case "1":
                                                                 Methods.printAllDocList(city, specialization);
                                                                break;

                                                            case "2":
                                                                Methods.printCheapestDocList(city, specialization);
                                                                break;

                                                            case "3":
                                                                ndb.recommendedDoctors(city, specialization);

                                                            case "0":
                                                                break;

                                                            default:
                                                                System.out.println("Please, select a correct command");
                                                                continue;
                                                        }
                                                        break;
                                                    }
                                                    System.out.println("Enter the username of the doctor you want to see:");

                                                    String usernamedoc = tastiera.readLine();

                                                    Methods.printMyProfile(usernamedoc);


                                                    while (true)
                                                    {
                                                        System.out.println("Select: \n1 if you want to book a medical examination with the doctor" +
                                                                                   "\n2. See all the doctor's reviews");


                                                        String book = tastiera.readLine();

                                                        switch (book)
                                                        {
                                                            case "1":

                                                            Methods.printSlots(usernamedoc);

                                                            System.out.println("Insert the data of the medical examination you want to book");

                                                            String date1 = tastiera.readLine();
                                                            String docname = mdb.getDocName(usernamedoc);
                                                            Reservation reservation = new Reservation(usernamedoc, date1, user.getUsername(), docname);

                                                            mdb.book(reservation);
                                                            break;

                                                            case "2":
                                                                Doctor doctor = new Doctor(usernamedoc,"");
                                                                ndb.showReviews(doctor);

                                                                System.out.println("Select 1 if you want to add a like to a review");
                                                                String like = tastiera.readLine();
                                                                if (like.equals("1")){
                                                                    System.out.println("Insert the review_id:");
                                                                    String rid = tastiera.readLine();
                                                                    User user1 = new User(user.getUsername());
                                                                    Review review = new Review(rid);
                                                                    ndb.like(user1,review);
                                                                    break;
                                                                }else {
                                                                    break;
                                                                }



                                                            default:
                                                                System.out.println("ERROR:Insert a correct number");


                                                        }


                                                     }

                                                case "2":
                                                    // mdb.showUserReservations(username);
                                                    Methods.printUserRes(username);

                                                    System.out.println("Select 1 if you want to delete a reservation");
                                                    int d = keyboard.nextInt();
                                                    if (d == 1) {
                                                        System.out.println("Select the examination's date");
                                                        InputStreamReader dat = new InputStreamReader(System.in);
                                                        BufferedReader tastdat = new BufferedReader(dat);

                                                        String date = null;
                                                        try {
                                                            date = tastdat.readLine();
                                                        } catch (Exception e) {
                                                        }

                                                        System.out.println("Write the doctor's name:");
                                                        InputStreamReader inp = new InputStreamReader(System.in);
                                                        BufferedReader tast = new BufferedReader(inp);

                                                        String docname = null;
                                                        try {
                                                            docname = tast.readLine();
                                                        } catch (Exception e) {
                                                        }

                                                        Reservation reservation = new Reservation(docname, date, username);

                                                        mdb.freeSlot(reservation);
                                                    }


                                                case "0":
                                                    break;

                                                default:
                                                    System.out.println("INCORRECT COMMAND. Please retry");

                                            }

                                        }


                                         }

                                }

                            case "2": //SignUp
                                while (true)
                                {

                                    System.out.println("Insert the username");
                                    String username = keyboard.next();
                                    System.out.println("Insert the password");
                                    String password = keyboard.next();
                                    System.out.println("Insert your age");
                                    int age = keyboard.nextInt();

                                    User user = new User(username, password, age);

                                    if (!mdb.add_user(user)) {
                                        System.out.println("The username is already used. Please choose another one.");
                                    } else {
                                        System.out.println("Registration successful!");
                                        break;
                                    }
                                }


                            default:
                                System.out.println("insert a correct number");
                        }
                        //ritorna e chiudi
                }



                    case 3: //ADMINISTRATOR
                        while(true) {
                            System.out.println("--LOGIN ADMINISTRATOR--\nInsert the username");
                            String username = keyboard.next();
                            System.out.println("Insert the password");
                            String password = keyboard.next();
                            Administrator administrator = new Administrator(username,password);
                            if (!mdb.login_administrator(administrator)){
                                System.out.println("Please retry");
                            }else {

                                System.out.println("Select what do you want do:");


                                System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                        "\n1. add an administrator" +
                                        "\n2. add a doctor" +
                                        "\n3. add a user" +
                                        "\n4. delete a doctor" +
                                        "\n5. delete a user" +
                                        "\n6. show analytics" +
                                        "\n7. populate db" +
                                        "\n8. update calendar " +
                                        "\n\n0. Go back");
                            }
                            try{
                                int option_administrator = keyboard.nextInt();

                                switch (option_administrator)
                                {
                                    case 1:
                                        //add an administrator
                                        System.out.println("Insert the username");
                                        String administratorUsername = keyboard.next();
                                        System.out.println("Insert the password");
                                        String administratorPassword = keyboard.next();
                                        Administrator administratorAdd= new Administrator(administratorUsername, administratorPassword);
                                        if (!mdb.add_administrator(administratorAdd)) {
                                            System.out.println("The username is already used. Please choose another one.");
                                        } else {
                                            System.out.println("add successful!");
                                            break;
                                        }
                                    case 2:
                                        //add a doctor
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader tastiera = new BufferedReader(input);
                                        System.out.println("Insert the username");
                                        String usernameD = keyboard.next();
                                        System.out.println("Insert the password");
                                        String passwordD = keyboard.next();
                                        System.out.println("Insert the name");
                                        String name = tastiera.readLine();
                                        System.out.println("Insert the city");
                                        String city = tastiera.readLine();
                                        System.out.println("Insert a short bio");
                                        String bio = tastiera.readLine();
                                        System.out.println("Insert the specialization");
                                        String specialization = keyboard.next();
                                        System.out.println("Insert the address");
                                        String address = tastiera.readLine();
                                        System.out.println("Insert the price for the visit");
                                        int price = keyboard.nextInt();

                                        Doctor doctor = new Doctor(usernameD, passwordD, price, name, city, specialization, bio, address);

                                        if (!mdb.add_doctor(doctor)) {
                                            System.out.println("The username is already used. Please choose another one.");
                                        } else {
                                            System.out.println("add successful!");
                                            break;
                                        }

                                    case 3:
                                        //add a user
                                        while (true)
                                        {

                                            System.out.println("Insert the username");
                                            String usernameNew = keyboard.next();
                                            System.out.println("Insert the password");
                                            String passwordNew = keyboard.next();
                                            System.out.println("Insert the age");
                                            int age = keyboard.nextInt();

                                            User user = new User(usernameNew, passwordNew, age);

                                            if (!mdb.add_user(user)) {
                                                System.out.println("The username is already used. Please choose another one.");
                                            } else {
                                                System.out.println("add successful!");
                                                break;
                                            }
                                        }
                                    case 4:
                                        //delete a doctor
                                        System.out.println("Insert the username you want to delete");
                                        String usernameDeleteDoc = keyboard.next();
                                        String passwordDeleteDoc = "ok";

                                        Doctor doctorDelete = new Doctor(usernameDeleteDoc, passwordDeleteDoc);
                                        if (!mdb.delete_doctor_by_the_administrator(doctorDelete)) {
                                            System.out.println(" username not found. Please choose another one.");
                                        } else {
                                            System.out.println("username deleted successful!");
                                            break;
                                        }
                                        break;

                                    case 5:
                                        //delete a user
                                        System.out.println("Insert the username you want to delete");
                                        String usernameDelete = keyboard.next();
                                        String passwordDelete = "ok";
                                        int age4 = 1;
                                        User userDelete = new User(usernameDelete, passwordDelete, age4);
                                        if (!mdb.delete_user_by_the_administrator(userDelete)) {
                                            System.out.println(" username not found. Please choose another one.");
                                        } else {
                                            System.out.println("username deleted successful!");
                                            break;
                                        }
                                        break;

                                    case 6:
                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                "\n1. show avg users" +
                                                "\n2. show the most expensive specialization" +
                                                "\n\n0. Go back");
                                        int analytics_adm = keyboard.nextInt();

                                        switch (analytics_adm){
                                            case 1:

                                                mdb.printAvgUsers();
                                            case 2:
                                                mdb.printMostExpSpec();
                                                break;
                                            case 0:
                                                System.out.println("return to the menu");
                                                break;}
                                    case 7:
                                        //populate db
                                        //System.out.println("filling the database with doctors");
                                        //mdb.populate_doctors_from_file();
                                        break;
                                    case 8:

                                        mdb.deleteReservation("1a");
                                        break;

                                    case 0:
                                        //go back
                                        System.out.println("ok 0");
                                        break;
                                }
                            }catch (Exception e){
                                System.out.println("ERROR: insert a correct number.");
                            }
                            //break;

                        }


                    default:
                        System.out.println("Please, insert a number from 1 to 3");
                }
            }catch (Exception e){
                System.out.println("ERROR: insert a number from 1 to 3.");
            }


        }

        // break;

    }
}
