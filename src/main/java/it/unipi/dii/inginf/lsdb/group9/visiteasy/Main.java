package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.*;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.MongoManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.Neo4jManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods;
import org.bson.Document;
import org.joda.time.DateTime;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Reservation;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import java.io.*;
import  java.util.*;
import java.util.concurrent.*;




public class Main {

    private static final MongoManager mdb = new MongoManager();
    private static Scanner keyboard = new Scanner(System.in);
    private static Neo4jManager ndb = new Neo4jManager("bolt://172.16.3.110:7687", "neo4j", "root2");
    //private static Neo4jManager ndb = new Neo4jManager("neo4j://localhost:11003", "neo4j", "root");



    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader tastiera = new BufferedReader(input);



    public static void main(String[] args) throws Exception {

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
                    "\n1. DOCTOR" +
                    "\n2. USER" +
                    "\n3. ADMINISTRATOR ");


            String option = tastiera.readLine();


            switch (option) {

                case "1":

                    System.out.println("--DOCTOR--\nWhat do you want to do? " +
                            "\n1. [Login]" +
                            "\n2. [Sign up]" +
                            "\n\n0. [Go back]");


                    String option_doctor = tastiera.readLine();

                    switch (option_doctor) {
                        case "1": //LOGIN

                            while (true) {
                                System.out.println("--LOGIN DOCTOR--\nEnter your username: ");
                                String usernameD = tastiera.readLine();
                                System.out.println("Enter password: ");
                                String passwordD = tastiera.readLine();

                                Doctor doctor = new Doctor(usernameD, passwordD);
                                if (!mdb.login_doctor(doctor)) {
                                    System.out.println("Please retry");
                                } else {

                                    while (true) {
                                        System.out.println("--DOCTOR--\nSelect a command: " +
                                                "\n1. [SHOW RESERVATIONS]" +
                                                "\n2. [ADD DATES TO CALENDAR]" +
                                                "\n3. [SHOW MY PROFILE]" +
                                                "\n4. [SHOW REVIEWS]" +
                                                "\n5. [UPDATE MY PROFILE]"+
                                                "\n\n0. [Shut down the application]");


                                        String option_doctor2 = tastiera.readLine();
                                        switch (option_doctor2) {
                                            case "1":

                                                ArrayList<Reservation> list = mdb.showEntirereservationsDoc(usernameD);
                                                if (!Methods.printResDoc(list))
                                                {
                                                    System.out.println("You don't have any reservations yet");
                                                    continue;
                                                }
                                                break;
                                            case "2":
                                                // a new doctor create the reservations
                                                if (!mdb.aggiungi_cal4(usernameD))
                                                {
                                                    continue;
                                                }
                                                System.out.println("New dates added successfully!");
                                                break;

                                            case "3":
                                                //stampa le sue informazioni
                                                Doctor doctor2 = mdb.getMyProfile(usernameD);
                                                System.out.println(doctor2.getName()+"\nName: "+doctor2.getName()+"\nAddress: "+doctor2.getAddress()+"\nprice : "+doctor2.getPrice()+"€"+"\nBiography: "+doctor2.getBio());

                                                break;
                                            case"4":
                                                Doctor doctor3 = new Doctor(usernameD,"");
                                                ArrayList<Review> reviews = ndb.showReviews3(doctor3);

                                                Methods.printreviews(reviews);
                                                break;

                                            case "5":
                                                while(true) {
                                                    System.out.print("\n1. [MODIFY BIO]" +
                                                            "\n2. [UPDATE ADDRESS]" +
                                                            "\n3. [UPDATE PRICE]\n");

                                                    String op = tastiera.readLine();
                                                    switch (op){

                                                        case "1":
                                                            System.out.println("\nWrite something new about your bio: ");
                                                            String bio = tastiera.readLine();

                                                            mdb.updateBio(doctor, bio);
                                                            System.out.println("Bio updated correctly!");
                                                            break;



                                                        case "2":
                                                            System.out.println("Update your address: ");
                                                            String address = tastiera.readLine();

                                                            mdb.updateAddress(doctor, address);
                                                            System.out.println("Address updated correctly!");
                                                            break;

                                                        case "3":
                                                            System.out.println("Insert the new price:");
                                                            int newprice = Integer.parseInt(tastiera.readLine());

                                                            mdb.updatePrice(doctor,newprice);
                                                            System.out.println("Price updated correctly!");
                                                            break;

                                                        default:
                                                            System.out.println("Please, select a correct command.");
                                                            continue;


                                                    }break;
                                                }break;



                                            case "0":
                                                mdb.closeconnection();
                                                ndb.close();
                                                System.exit(0);



                                            default:
                                                System.out.println("Please, select a correct command.");

                                        }
                                    }



                                }

                            }

                        case "2": //SignUp
                            while (true) {

                                Scanner scan = new Scanner(System.in);
                                System.out.println("Insert the username: ");
                                String username = tastiera.readLine();
                                System.out.println("Insert the password: ");
                                String password = tastiera.readLine();
                                System.out.println("Insert your name: ");
                                String name = tastiera.readLine();
                                System.out.println("Insert your city: ");
                                String city = tastiera.readLine();
                                System.out.println("Insert a short bio: ");
                                String bio = tastiera.readLine();
                                System.out.println("Insert your specialization: ");
                                String specialization = tastiera.readLine();
                                System.out.println("Insert your address: ");
                                String address = tastiera.readLine();
                                System.out.println("Insert price for your visit: ");
                                while (!scan.hasNextInt()) {
                                    System.out.println("Please enter a number: ");
                                    scan.nextLine();
                                }
                                int price = scan.nextInt();
                                Doctor doctor = new Doctor(username, password, price, name, city, specialization, bio, address);

                                if (!mdb.add_doctor(doctor)) {
                                    System.out.println("The username is already used. Please choose another one.");
                                } else {
                                    System.out.println("Registration successful!");
                                    break;
                                }
                            }


                        case "0":
                            continue; //torna indietro alla pagina principale

                        default:
                            System.out.println("Insert a correct number: ");
                            continue;
                    }



                case "2":

                    while (true)
                    {
                        System.out.println("--USER--\nSelect a command: " +
                                "\n1. [LOGIN]" +
                                "\n2. [SIGN UP]" +
                                "\n\n0. [Shut down the application]");


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
                                                    "\n1. [FIND A DOCTOR]" +
                                                    "\n2. [SHOW YOUR RESERVATIONS]" +
                                                    "\ne. [SHUT DOWN THE APPLICATION]");
                                            String com = tastiera.readLine();
                                            out:
                                            switch (com) {

                                                case "1":

                                                    System.out.println("Cities and specializations are displayed here, select:");

                                                    System.out.println("CITIES CURRENTLY AVAILABLE:");
                                                    mdb.display_cities();

                                                    System.out.println("\nSPECIALIZATIONS CURRENTLY AVAILABLE:");
                                                    mdb.display_spec();

                                                    System.out.println("Insert the city:");
                                                    String city = tastiera.readLine();

                                                    System.out.println("Insert the specialization:");
                                                    String specialization = tastiera.readLine();

                                                    while (true) {
                                                        System.out.println("Choose the doctors by: (select a command)" +
                                                                "\n1. [SHOW THE ENTIRE LIST OF DOCTORS]" +
                                                                "\n2. [SHOW TOP 3 CHEAPEST DOCTORS]" +
                                                                "\n3. [SHOW BEST DOCTORS RECOMMENDED BY THE SYSTEM]");

                                                        String sort = tastiera.readLine();
                                                        ArrayList<Doctor> doclist = new ArrayList<>();


                                                        switch (sort)
                                                        {
                                                            case "1":

                                                                doclist = mdb.getDocByCitySpec(city, specialization);
                                                                if (!Methods.printAllDocList(city, specialization,doclist))
                                                                {
                                                                    System.out.println("There are no doctor for this city and specialization");
                                                                    break out;
                                                                }
                                                                break;

                                                            case "2":
                                                                doclist = mdb.cheapestDoc(city,specialization);
                                                                if (!Methods.printCheapestDocList(city, specialization,doclist))
                                                                {
                                                                    System.out.println("There are no doctor for this city and specialization");
                                                                    break out;
                                                                }
                                                                break;

                                                            case "3":
                                                                if (ndb.recommendedDoctors(city, specialization) == 0)
                                                                {
                                                                    System.out.println("The system has not found any \"best doctor\" for this city and specialization, please try to search in the entire list if you want to find one");
                                                                    break out;
                                                                }
                                                                break;

                                                            default:
                                                                System.out.println("Please, select a correct command");
                                                                continue;
                                                        }
                                                        break;
                                                    }
                                                    System.out.println("Enter the username (us) of the doctor you want to see:");

                                                    String usernamedoc = tastiera.readLine();


                                                    Doctor doctor3 = mdb.getMyProfile(usernamedoc);
                                                    System.out.println(doctor3.getName()+"\nName: "+doctor3.getName()+"\nAddress: "+doctor3.getAddress()+"\nprice : "+doctor3.getPrice()+"€"+"\nBiography: "+doctor3.getBio());




                                                    while (true)
                                                    {
                                                        System.out.println("Select: \n1.  [BOOK A MEDICAL EXAMINATION WITH THE DOCTOR]" +
                                                                "\n2.  [SHOW DOCTORS REVIEWS]" +
                                                                "\n3.  [ADD A REVIEW TO THE DOCTOR]" +
                                                                "\n\n0.  [Go back to the USER homepage]" +
                                                                "                   \ne.   [Shut down the application]");


                                                        String book = tastiera.readLine();
                                                        ArrayList<Reservation> list = new ArrayList<>();


                                                        switch (book)
                                                        {
                                                            case "1": //book

                                                                list = mdb.showEntirereservations(usernamedoc);
                                                                Methods.printSlots(list);

                                                                System.out.println("Insert the datetime of the reservation you want to book");

                                                                String date1 = tastiera.readLine();
                                                                String docname = mdb.getDocName(usernamedoc);
                                                                Reservation reservation = new Reservation(usernamedoc, date1, user.getUsername(), docname);

                                                                mdb.book(reservation);
                                                                break;

                                                            case "2": //view all reviews
                                                                Doctor doctor = new Doctor(usernamedoc,"");
                                                                ArrayList<Review> reviews = ndb.showReviews3(doctor);
                                                                if (!Methods.printreviews(reviews)){
                                                                    continue;
                                                                }

                                                                System.out.println("Type 1 if you want to add a like to a review or press another key to come back");
                                                                String like = tastiera.readLine();

                                                                if (like.equals("1")){
                                                                    System.out.println("Insert the review_id:");
                                                                    String rid = tastiera.readLine();

                                                                    User user1 = new User(user.getUsername());
                                                                    Review review = new Review(rid);
                                                                    if (ndb.findReview(review) == 0)
                                                                    {
                                                                        System.out.println("Does not exist a review with this id");
                                                                        continue;
                                                                    }
                                                                    ndb.like(user1,review);
                                                                    System.out.println("A like was added to this review");
                                                                    continue;
                                                                }else {
                                                                    continue;
                                                                }

                                                            case "3": //add a review
                                                                System.out.println("Insert a rating from 1 to 5");
                                                                int rating = keyboard.nextInt();
                                                                System.out.println("Insert the text of the review");
                                                                String text = tastiera.readLine();

                                                                Review newreview = new Review(Methods.getAlphaNumericString(20),DateTime.now().toString(),user.getUsername(),rating,text,usernamedoc);
                                                                ndb.addReview(newreview);
                                                                System.out.println("New review added!");
                                                                continue;

                                                            case "0":
                                                                break;

                                                            case "e":
                                                                mdb.closeconnection();
                                                                ndb.close();
                                                                System.exit(0);





                                                            default:
                                                                System.out.println("ERROR:Insert a correct number");
                                                                continue;


                                                        }

                                                        break;
                                                    }
                                                    break;

                                                case "2":
                                                    ArrayList<Reservation> list = new ArrayList<>();
                                                    list = mdb.showUserReservations(username);

                                                    if (!Methods.printUserRes(list))
                                                    {
                                                        System.out.println("You don't have any reservations");
                                                        continue;
                                                    }

                                                    System.out.println("Type 1 if you want to delete a reservation or press any key to return to the user page");
                                                    String d = tastiera.readLine();
                                                    if (d.equals("1")) {
                                                        System.out.println("Select the examination's datetime");
                                                        InputStreamReader dat = new InputStreamReader(System.in);
                                                        BufferedReader tastdat = new BufferedReader(dat);

                                                        String date = null;
                                                        try {
                                                            date = tastdat.readLine();
                                                        } catch (Exception e) {
                                                        }

                                                        System.out.println("Write the doctor's username (us):");
                                                        InputStreamReader inp = new InputStreamReader(System.in);
                                                        BufferedReader tast = new BufferedReader(inp);

                                                        String docname = null;
                                                        try {
                                                            docname = tast.readLine();
                                                        } catch (Exception e) {
                                                        }

                                                        Reservation reservation = new Reservation(docname, date, username);

                                                        mdb.freeSlot(reservation);

                                                    }else {
                                                        continue;
                                                    }
                                                    break;


                                                case "e":
                                                    mdb.closeconnection();
                                                    ndb.close();
                                                    System.exit(0);


                                                default:
                                                    System.out.println("INCORRECT COMMAND. Please retry");

                                            }

                                        }



                                    }

                                }


                            case "2": //SignUp
                                while (true) {

                                    System.out.println("Insert the username");
                                    String username = tastiera.readLine();
                                    System.out.println("Insert the password");
                                    String password = tastiera.readLine();
                                    System.out.println("Insert city");
                                    String city = tastiera.readLine();





                                    User user = new User(username, password, city);
                                    if (!mdb.add_user(user)) {
                                        System.out.println("The username is already used. Please choose another one.");
                                    } else {
                                        System.out.println("Registered successfully!");
                                        break;
                                    }


                                }
                                break;


                            case "0":
                                mdb.closeconnection();
                                ndb.close();
                                System.exit(0);


                            default:
                                System.out.println("insert a correct number");
                        }

                    }


                case "3": //ADMINISTRATOR
                    System.out.println("--ADMINISTRATOR--\nWhat do you want to do? " +
                            "\n1. [LOGIN]" +
                            "\n\n0. [Go back]");

                    String option_adm = tastiera.readLine();

                    switch (option_adm) {


                        case "1":
                            while (true) {
                                System.out.println("--LOGIN ADMINISTRATOR--\nInsert the username");
                                String username = tastiera.readLine();
                                System.out.println("Insert the password");
                                String password = tastiera.readLine();
                                Administrator administrator = new Administrator(username, password);
                                if (!mdb.login_administrator(administrator)) {
                                    System.out.println("Please retry");


                                } else {

                                    System.out.println("Select what do you want do:");

                                    while (true) {
                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                "\n1. [ADD AN ADMINISTRATOR]" +
                                                "\n2. [ADD A DOCTOR]" +
                                                "\n3. [ADD A USER]" +
                                                "\n4. [FIND A DOCTOR]"+
                                                "\n5. [SHOW ANALYTICS]" +


                                                "\n\n0. [Go back]" +
                                                "\ne  [Shut down the app]");


                                        String option_administrator = tastiera.readLine();

                                        switch (option_administrator) {
                                            case "1":
                                                //add an administrator
                                                System.out.println("Insert the username");
                                                String administratorUsername = tastiera.readLine();
                                                System.out.println("Insert the password");
                                                String administratorPassword = tastiera.readLine();
                                                Administrator administratorAdd = new Administrator(administratorUsername, administratorPassword);
                                                if (!mdb.add_administrator(administratorAdd)) {
                                                    System.out.println("The username is already used. Please choose another one.");
                                                } else {
                                                    System.out.println("ADDED SUCCESSFULLY!");
                                                    break;
                                                }
                                            case "2":
                                                //add a doctor

                                                System.out.println("Insert the username");
                                                String usernameD = tastiera.readLine();
                                                System.out.println("Insert the password");
                                                String passwordD = tastiera.readLine();
                                                System.out.println("Insert the name");
                                                String name = tastiera.readLine();
                                                System.out.println("Insert the city");
                                                String city = tastiera.readLine();
                                                System.out.println("Insert a short bio");
                                                String bio = tastiera.readLine();
                                                System.out.println("Insert the specialization");
                                                String specialization = tastiera.readLine();
                                                System.out.println("Insert the address");
                                                String address = tastiera.readLine();
                                                System.out.println("Insert the price for the visit");
                                                int price = keyboard.nextInt();

                                                Doctor doctor = new Doctor(usernameD, passwordD, price, name, city, specialization, bio, address);

                                                if (!mdb.add_doctor(doctor)) {
                                                    System.out.println("The username is already used. Please choose another one.");
                                                } else {
                                                    System.out.println("ADDED SUCCESSFULLY");
                                                    break;
                                                }

                                            case "3":
                                                //add a user


                                                System.out.println("Insert the username");
                                                String usernameN = tastiera.readLine();
                                                System.out.println("Insert the password");
                                                String passwordN = tastiera.readLine();
                                                System.out.println("Insert city");
                                                String cityN = tastiera.readLine();



                                                User user = new User(usernameN, passwordN, cityN);

                                                if (!mdb.add_user(user)) {
                                                    System.out.println("The username is already used. Please choose another one.");
                                                } else {
                                                    System.out.println("add successful!");
                                                    break;
                                                }break;




                                            case "5":
                                                System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                        "\n1. [SHOW CITIES WITH MORE USERS]" +
                                                        "\n2. [SHOW MOST EXPENSIVE SPECIALIZATIONS]" +
                                                        // "\n3. [SHOW ACTIVE REVIEWERS]" +  FUNZIONA SOLO IN LOCALE: NON SIAMO RIUSCITI A INSTALLARE IL PLUGIN APOC SULLA VM
                                                        "\n4. [SHOW BEST REVIEWERS]" +
                                                        "\n\n0. Go back" );
                                                String analytics_adm = tastiera.readLine();

                                                switch (analytics_adm) {
                                                    case "1":
                                                        mdb.printmostcity();

                                                        break;
                                                    case "2":
                                                        mdb.printMostExpSpec();
                                                        break;
                                                  /*  case "3":
                                                        ndb.printActiveReviewers();
                                                        break; */
                                                    case "4":
                                                        ndb.printBestReviewers();
                                                        break;
                                                    case "0":
                                                        System.out.println("return to the menu");
                                                        break;
                                                    default:
                                                        System.out.println("INCORRECT COMMAND. Please retry");
                                                }
                                                break;

                                            case "4":
                                                ArrayList<Doctor> doclist = new ArrayList<>();
                                                doclist = mdb.getAllDoc();

                                                Methods.printAllDocList2(doclist);

                                                System.out.println("Insert the doctors \"us\" that you want to see");

                                                String usernamedoc = tastiera.readLine();


                                                try {
                                                    Doctor doctorD = mdb.getMyProfile(usernamedoc);
                                                    System.out.println(doctorD.getName()+"\nName: "+doctorD.getName()+"\nAddress: "+doctorD.getAddress()+"\nprice : "+doctorD.getPrice()+"€"+"\nBiography: "+doctorD.getBio());

                                                }catch (NullPointerException e){
                                                    System.out.println("ERROR: Not found a doctor with the selected us");
                                                    continue;
                                                }

                                                System.out.println("------REVIEWS-------");

                                                Doctor doctor2 = new Doctor(usernamedoc,"");
                                                ArrayList<Review> reviews = new ArrayList<>();
                                                reviews = ndb.showReviews3(doctor2);

                                                Methods.printreviews(reviews);

                                                System.out.println("Select :" +
                                                        "\n1. [DELETE THE DOCTOR]" +
                                                        "\n2. [DELETE A USER]" +
                                                        "\n3. [DELETE A REVIEW]" +
                                                        "\n\n0. [GO BACK]");

                                                String optionad = tastiera.readLine();

                                                while (true)
                                                {
                                                    switch (optionad)
                                                    {
                                                        case "1": //DELETE DOCTOR

                                                            //salvo il documento
                                                            Document store =  mdb.store(doctor2);

                                                            if (!mdb.delete_doctor_by_the_administrator(doctor2)) {
                                                                System.out.println("Username not found. Please choose another one.");
                                                                continue;
                                                            } else
                                                            {
                                                                if (mdb.find(doctor2)){
                                                                    //doctor not removed from mongo
                                                                    System.out.println("ERROR: Please retry");
                                                                    continue;
                                                                }
                                                                ndb.deleteDoctor(doctor2); //elimino da neo4j
                                                                if (ndb.findNode(doctor2) == 1) //delete fallita su neo4j
                                                                {
                                                                    mdb.addDocument(store); //riaggiungo su mongo
                                                                }

                                                                System.out.println("DOCTOR deleted successfully!");
                                                                // break;
                                                            }
                                                            break;

                                                        case "2":
                                                            //delete a user
                                                            System.out.println("Insert the username of the user you want to delete");
                                                            String usernameDelete = tastiera.readLine();


                                                            User userDelete = new User(usernameDelete);
                                                            if (!mdb.delete_user_by_the_administrator(userDelete)) {
                                                                System.out.println("Username not found. Please choose another one.");
                                                            } else {

                                                                ndb.deleteUser(userDelete);
                                                                System.out.println("user deleted successfully!");
                                                                break;
                                                            }

                                                            break;

                                                        case "3":
                                                            System.out.println("Insert the review ID that you want to remove");
                                                            String idr = tastiera.readLine();
                                                            Review reviewr = new Review(idr);
                                                            ndb.deleteReview(reviewr);
                                                            System.out.println("Review deleted");
                                                            break;

                                                        case "0":
                                                            break;

                                                        default:
                                                            System.out.println("Please select a number from 1 to 3");

                                                    }
                                                    break;
                                                }





                                            case "0":
                                                //go back
                                                break;

                                            case "e":
                                                mdb.closeconnection();
                                                ndb.close();
                                                System.exit(0);



                                            default:
                                                System.out.println("INCORRECT COMMAND. Please retry");
                                        }


                                    }
                                }

                            }
                        case "0":


                            continue;
                    }


                    break;


                default:
                    System.out.println("Please, insert a number from 1 to 3");


            }


        }



    }
}