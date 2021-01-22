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
                    "\n1. DOCTOR" +
                    "\n2. USER" +
                    "\n3. ADMINISTRATOR ");

            try {
                String option = tastiera.readLine();


                switch (option) {

                    case "1":

                        System.out.println("--DOCTOR--\nWhat do you want to do? " +
                                "\n1. [Login]" +
                                "\n2. [Sign up]" +
                                "\n\n0. [Go back]");

                        try {
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
                                            try {
                                                while (true) {
                                                    System.out.println("--DOCTOR--\nSelect a command: " +
                                                    "\n1. [see reservations]" +
                                                    "\n2. [insert dates and hour to your reservations]" +
                                                    "\n3. [view my profile]" +

                                                    "\n\n0. [Go back]");

                                                    String option_doctor2 = tastiera.readLine();
                                                    switch (option_doctor2) {
                                                        case "1":
                                                            //see doctor's reservations
                                                            mdb.showreservations(usernameD);
                                                            break;
                                                        case "2":
                                                            // a new doctor create the reservations
                                                            mdb.aggiungi_cal4(usernameD);

                                                                System.out.println("Calendar correctly added");
                                                                break;




                                                        case "3":
                                                            while (true) {
                                                                //stampa le sue informazioni
                                                                Methods.printMyProfile(usernameD);

                                                                // TODO Cypher


                                                                break;
                                                            }
                                                        case "0":
                                                            continue; //torna indietro alla pagina principale
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("ERROR: enter a correct number.");
                                                continue;
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
                                    System.out.println("insert a correct number: ");
                                    continue;
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: insert a correct number.");
                            continue;
                        }


                    case "2":

                        while (true) {
                            System.out.println("--USER--\nSelect a command: " +
                                    "\n1. Login" +
                                    "\n2. Sign up" +
                                    "\n\n0. Go back");


                            String option_user = tastiera.readLine();

                            switch (option_user) {
                                case "1": //LOGIN USER
                                    while (true) {
                                        System.out.println("--LOGIN USER--\nInsert the username");
                                        String username = tastiera.readLine();
                                        System.out.println("Insert the password");
                                        String password = tastiera.readLine();

                                        User user = new User(username, password);
                                        if (!mdb.login_user(user)) {
                                            System.out.println("Please retry");
                                        } else {

                                            while (true) {

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


                                                        while (true) {
                                                            System.out.println("Select: \n1 if you want to book a medical examination with the doctor" +
                                                                    "\n2. See all the doctor's reviews");


                                                            String book = tastiera.readLine();

                                                            switch (book) {
                                                                case "1":

                                                                    Methods.printSlots(usernamedoc);

                                                                    System.out.println("Insert the data of the medical examination you want to book");

                                                                    String date1 = tastiera.readLine();
                                                                    String docname = mdb.getDocName(usernamedoc);
                                                                    Reservation reservation = new Reservation(usernamedoc, date1, user.getUsername(), docname);

                                                                    mdb.book(reservation);
                                                                    break;

                                                                case "2":
                                                                    Doctor doctor = new Doctor(usernamedoc, "");
                                                                    ArrayList<Review> reviews = ndb.showReviews3(doctor);
                                                                    for (int i = 0; i < reviews.size(); i++) {
                                                                        System.out.println("===================================================================================\n" +
                                                                                "id_review:" + reviews.get(i).getId() + "\n" + reviews.get(i).getDateTime() + "\n" + reviews.get(i).getUsername() + "\nRating:" + reviews.get(i).getRating() + "\n" + reviews.get(i).getText());
                                                                    }

                                                                    System.out.println("Select 1 if you want to add a like to a review");
                                                                    String like = tastiera.readLine();

                                                                    if (like.equals("1")) {
                                                                        System.out.println("Insert the review_id:");
                                                                        String rid = tastiera.readLine();
                                                                        User user1 = new User(user.getUsername());
                                                                        Review review = new Review(rid);
                                                                        ndb.like(user1, review);
                                                                        break;
                                                                    } else {
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
                                            System.out.println("Registration successful!");
                                            break;
                                        }


                                    }


                                case "0":
                                    continue; //torna indietro alla pagina principale

                                default:
                                    System.out.println("insert a correct number");
                                    continue;
                            }
                        }


                    case "3": //ADMINISTRATOR
                        System.out.println("--ADMINISTRATOR--\nWhat do you want to do? " +
                                "\n1. [Login]" +
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
                                                        "\n1. add an administrator" +
                                                        "\n2. add a doctor" +
                                                        "\n3. add a user" +
                                                        "\n4. delete a doctor" +
                                                        "\n5. delete a user" +
                                                        "\n6. show analytics" +

                                                        "\n\n0. Go back");


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
                                                            System.out.println("add successful!");
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
                                                            System.out.println("add successful!");
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
                                                            System.out.println(" username not found. Please choose another one.");
                                                        } else {
                                                            System.out.println("doctor deleted successfully!");
                                                            break;
                                                        }
                                                        break;

                                                    case "5":
                                                        //delete a user
                                                        System.out.println("Insert the username of the user you want to delete");
                                                        String usernameDelete = tastiera.readLine();


                                                        User userDelete = new User(usernameDelete);
                                                        if (!mdb.delete_user_by_the_administrator(userDelete)) {
                                                            System.out.println(" username not found. Please choose another one.");
                                                        } else {
                                                            System.out.println("user deleted successful!");
                                                            break;
                                                        }
                                                        break;

                                                    case "6":
                                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                                "\n1. show cities with more registered users" +
                                                                "\n2. show the most expensive specialization" +
                                                                "\n3. most active reviewers" +
                                                                "\n4. best reviewers" +
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
                }catch(Exception e){
                    System.out.println("ERROR: insert a number from 1 to 3.");
                }

            break;


        }

        //break;

    }
}
