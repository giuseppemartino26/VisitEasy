package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.*;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.MongoManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance.Neo4jManager;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods;
import org.joda.time.DateTime;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Reservation;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods;
import java.io.*;
import java.text.ParseException;
import  java.util.*;
import java.util.concurrent.*;




public class Main {

    private static MongoManager mdb = new MongoManager();
    private static Scanner keyboard = new Scanner(System.in);
    private static Neo4jManager ndb = new Neo4jManager("neo4j://localhost:11003", "neo4j", "root");


    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader tastiera = new BufferedReader(input);

    static Methods methods = new Methods();





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

                                                    "\n\n0. [Shut down the application]");


                                                    String option_doctor2 = tastiera.readLine();
                                                    switch (option_doctor2) {
                                                        case "1":
                                                            //see doctor's reservations
                                                            mdb.showreservations(usernameD);
                                                            break;
                                                        case "2":
                                                            // a new doctor create the reservations
                                                            mdb.aggiungi_cal4(usernameD);
                                                            System.out.println("New dates added successfully!");
                                                            break;

                                                        case "3":
                                                                //stampa le sue informazioni
                                                                Methods.printMyProfile(usernameD);
                                                                break;
                                                        case"4":
                                                            Doctor doctor2 = new Doctor(usernameD, "");
                                                            ArrayList<Review> reviews = ndb.showReviews3(doctor);
                                                            for (int i = 0; i < reviews.size(); i++) {
                                                                System.out.println("===================================================================================\n" +
                                                                        "id_review:" + reviews.get(i).getId() + "\n" + reviews.get(i).getDateTime() + "\n" + reviews.get(i).getUsername() + "\nRating:" + reviews.get(i).getRating() + "\n" + reviews.get(i).getText());
                                                            }

                                                        case "0":
                                                            mdb.closeconnection();
                                                            ndb.close();
                                                            System.exit(0);



                                                        default:
                                                            System.out.println("Please, select a correct command.");
                                                            continue;
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
                                        Doctor doctor = new Doctor(username, password, price, name, city, bio, specialization, address);

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


                                                        switch (sort)
                                                        {
                                                            case "1":
                                                                 Methods.printAllDocList(city, specialization);
                                                                break;

                                                            case "2":
                                                                Methods.printCheapestDocList(city, specialization);
                                                                break;

                                                            case "3":
                                                                ndb.recommendedDoctors(city, specialization);
                                                                break;

                                                            default:
                                                                System.out.println("Please, select a correct command");
                                                                continue;
                                                        }
                                                        break;
                                                    }
                                                    System.out.println("Enter the username (us) of the doctor you want to see:");

                                                    String usernamedoc = tastiera.readLine();

                                                    Methods.printMyProfile(usernamedoc);


                                                    while (true)
                                                    {
                                                        System.out.println("Select: \n1.  [BOOK A MEDICAL EXAMINATION WITH THE DOCTOR]" +
                                                                                   "\n2.  [SHOW DOCTORS REVIEWS]" +
                                                                                    "\n3.  [ADD A REVIEW TO THE DOCTOR]" +
                                                                                    "\n\n0.  [Go back to the USER homepage]" +
                                                                "                   \ne.   [Shut down the application]");


                                                        String book = tastiera.readLine();

                                                        switch (book)
                                                        {
                                                            case "1": //book

                                                            Methods.printSlots(usernamedoc);

                                                            System.out.println("Insert the datetime of the reservation you want to book");

                                                            String date1 = tastiera.readLine();
                                                            String docname = mdb.getDocName(usernamedoc);
                                                            Reservation reservation = new Reservation(usernamedoc, date1, user.getUsername(), docname);

                                                            mdb.book(reservation);
                                                            break;

                                                            case "2": //view all reviews
                                                                Doctor doctor = new Doctor(usernamedoc,"");
                                                                Methods.printreviews(doctor);

                                                                System.out.println("Type 1 if you want to add a like to a review or press another key to come back");
                                                                String like = tastiera.readLine();

                                                                if (like.equals("1")){
                                                                    System.out.println("Insert the review_id:");
                                                                    String rid = tastiera.readLine();
                                                                    User user1 = new User(user.getUsername());
                                                                    Review review = new Review(rid);
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

                                                    Methods.printUserRes(username);

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
                                       /* System.out.println("Insert your date of birth nel formato dd-MM-yyyy");
                                        String d = tastiera.readLine();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                        sdf.setLenient(false); // strict parsing

                                        Date dateofBirth = null;

                                            dateofBirth = sdf.parse(d);


                                            GregorianCalendar cal = new GregorianCalendar();
                                            cal.setTime(dateofBirth);

                                            int giorno = cal.get(Calendar.DAY_OF_MONTH);
                                            int mese = cal.get(Calendar.MONTH) + 1;
                                            int anno = cal.get(Calendar.YEAR);

                                            if(giorno>31|| mese>13|| anno>2002){
                                                System.out.println("ERROR: Insert right date");
                                                break;
                                            }


*/

                                        User user = new User(username, password, city);
                                        if (!mdb.add_user(user)) {
                                            System.out.println("The username is already used. Please choose another one.");
                                        } else {
                                            System.out.println("Registered successfully!");
                                            break;
                                        }


                                    }


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
                                                        "\n4. [DELETE A DOCTOR]" +
                                                        "\n5. [DELETE A USER]" +
                                                        "\n6. [SHOW ANALYTICS]" +

                                                        "\n\n0. [Go back]");


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

                                           /* Date dateofBirth = null;

                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                            sdf.setLenient(false); // strict parsing
                                            try {
                                                dateofBirth = sdf.parse(d);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            GregorianCalendar cal = new GregorianCalendar();
                                            cal.setTime(dateofBirth);

                                            int giorno = cal.get(Calendar.DAY_OF_MONTH);
                                            int mese = cal.get(Calendar.MONTH) + 1;
                                            int anno = cal.get(Calendar.YEAR);
                                            if(giorno>31|| mese>13|| anno>2002){
                                                System.out.println("ERROR: Insert right date");
                                                break;
                                            }
*/

                                                            User user = new User(usernameN, passwordN, cityN);

                                                            if (!mdb.add_user(user)) {
                                                                System.out.println("The username is already used. Please choose another one.");
                                                            } else {
                                                                System.out.println("add successful!");
                                                                break;
                                                            }break;

                                                    case "4":
                                                        //delete a doctor
                                                        System.out.println("Insert the username of the doctor you want to delete");
                                                        String usernameDeleteDoc = tastiera.readLine();
                                                        String passwordDeleteDoc = "ok";

                                                        Doctor doctorDelete = new Doctor(usernameDeleteDoc, passwordDeleteDoc);
                                                        if (!mdb.delete_doctor_by_the_administrator(doctorDelete)) {
                                                            System.out.println("Username not found. Please choose another one.");
                                                        } else {
                                                            System.out.println("DOCTOR deleted successfully!");
                                                            break;
                                                        }
                                                        break;

                                                    case "5":
                                                        //delete a user
                                                        System.out.println("Insert the username of the user you want to delete");
                                                        String usernameDelete = tastiera.readLine();


                                                        User userDelete = new User(usernameDelete);
                                                        if (!mdb.delete_user_by_the_administrator(userDelete)) {
                                                            System.out.println("Username not found. Please choose another one.");
                                                        } else {
                                                            System.out.println("user deleted successful!");
                                                            break;
                                                        }
                                                        break;

                                                    case "6":
                                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                                "\n1. [SHOW CITIES WITH MORE USERS]" +
                                                                "\n2. [SHOW MOST EXPENSIVE SPECIALIZATIONS]" +
                                                                "\n3. [SHOW ACTIVE REVIEWERS]" +
                                                                "\n4. [SHOW BEST REVIEWERS]" +
                                                                "\n\n0. Go back");
                                                        String analytics_adm = tastiera.readLine();

                                                        switch (analytics_adm) {
                                                            case "1":
                                                                mdb.printmostcity();
                                                                // mdb.printAvgUsers();
                                                                break;
                                                            case "2":
                                                                mdb.printMostExpSpec();
                                                                break;
                                                            case "3":
                                                                ndb.printActiveReviewers();
                                                                break;
                                                            case "4":
                                                                ndb.printBestReviewers();
                                                                break;
                                                            case "0":
                                                                System.out.println("return to the menu");
                                                                break;
                                                            default:
                                                                System.out.println("INCORRECT COMMAND. Please retry");
                                                        }


                                                    case "0":
                                                        //go back

                                                        break;

                                                    default:
                                                        System.out.println("INCORRECT COMMAND. Please retry");
                                                }


                                            }
                                        }

                                        }
                                        case "0":
                                            // go back to doctor, user, admin

                                            continue;
                                    }


                            break;


                            default:
                                System.out.println("Please, insert a number from 1 to 3");


                        }


        }



    }
}
