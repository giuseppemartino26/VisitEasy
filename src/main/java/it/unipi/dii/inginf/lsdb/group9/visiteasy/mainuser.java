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


public class mainuser {

    private static MongoManager mdb = new MongoManager();
    private static Scanner keyboard = new Scanner(System.in);
    private static Neo4jManager ndb = new Neo4jManager("neo4j://localhost:11003", "neo4j", "root");


    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader tastiera = new BufferedReader(input);

    static Methods methods = new Methods();


    public static void main(String[] args) throws Exception {




        while (true)
        {
            System.out.println("--USER--\nSelect a command: " +
                    "\n1. Login" +
                    "\n2. Sign up" +
                    "\n\n0. Shut down the app");


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
                                        "\ne to shut down the app");
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
                                                    "\n3. Doctors recommended by the system");

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
                                        System.out.println("Enter the username of the doctor you want to see:");

                                        String usernamedoc = tastiera.readLine();

                                        Methods.printMyProfile(usernamedoc);


                                        while (true)
                                        {
                                            System.out.println("Select: \n1  if you want to book a medical examination with the doctor" +
                                                    "\n2  See all the doctor's reviews" +
                                                    "\n3  Add a review to the doctor" +
                                                    "\n0  Come back to the user page" +
                                                    "                   \ne   to shut down the app");


                                            String book = tastiera.readLine();

                                            switch (book)
                                            {
                                                case "1": //book

                                                    Methods.printSlots(usernamedoc);

                                                    System.out.println("Insert the data of the medical examination you want to book");

                                                    String date1 = tastiera.readLine();
                                                    String docname = mdb.getDocName(usernamedoc);
                                                    Reservation reservation = new Reservation(usernamedoc, date1, user.getUsername(), docname);

                                                    mdb.book(reservation);
                                                    break;

                                                case "2": //view all reviews
                                                    Doctor doctor = new Doctor(usernamedoc,"");
                                                    Methods.printreviews(doctor);

                                                    System.out.println("Select 1 if you want to add a like to a review or press another key to come back");
                                                    String like = tastiera.readLine();

                                                    if (like.equals("1")){
                                                        System.out.println("Insert the review_id:");
                                                        String rid = tastiera.readLine();
                                                        User user1 = new User(user.getUsername());
                                                        Review review = new Review(rid);
                                                        ndb.like(user1,review);
                                                        System.out.println("A like was added to the review");
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
                                                    System.out.println("New review added");
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

                                        System.out.println("Select 1 if you want to delete a reservation or press any key to return to the user page");
                                        String d = tastiera.readLine();
                                        if (d.equals("1")) {
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
                            System.out.println("Registration successful!");
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
    }


}
