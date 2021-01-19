package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Reservation;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods;
import java.io.*;
import  java.util.*;
import java.util.concurrent.*;

import static it.unipi.dii.inginf.lsdb.group9.visiteasy.utils.Methods.*;


public class Main {

    private static MongoManager mdb = new MongoManager();
    private static Scanner keyboard = new Scanner(System.in);
    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader tastiera = new BufferedReader(input);



    public static void main(String[] args) {

        Runnable deleteRunnable = () -> mdb.deleteDate();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(deleteRunnable, 0, 24, TimeUnit.HOURS);




        while (true)
        {
            printCurrentDate();
            System.out.println("\nSelect a command: \nI'm a:" +
                    "\n1. DOCTOR" +
                    "\n2. USER" +
                    "\n3. ADMINISTRATOR ");

            try {
                int option = keyboard.nextInt();


                switch (option)
                {

                    case 1:

                        System.out.println("--DOCTOR--\nWhat do you want to do? " +
                                "\n1. [Login]" +
                                "\n2. [Sign up]" +
                                "\n\n0. [Go back]");

                        try{
                            int option_doctor = keyboard.nextInt();

                            switch (option_doctor)
                            {
                                case 1: //LOGIN

                                    while (true)
                                    {
                                        System.out.println("--LOGIN DOCTOR--\nEnter your username: ");
                                        String usernameD = tastiera.readLine();
                                        System.out.println("Enter password: ");
                                        String passwordD = tastiera.readLine();

                                        Doctor doctor = new Doctor(usernameD,passwordD);
                                        if (!mdb.login_doctor(doctor)){
                                            System.out.println("Please retry");
                                        }else {
                                            System.out.println("--DOCTOR--\nSelect a command: " +
                                                    "\n1. [see reservations]" +
                                                    "\n2. [insert dates and hour to your reservations]" +
                                                    "\n3. [view my profile]"+

                                                    "\n\n0. [Go back]");
                                            try{
                                                while (true){
                                                int option_doctor2 = keyboard.nextInt();
                                                switch (option_doctor2){
                                                    case 1:
                                                        //see doctor's reservations
                                                        mdb.showreservations(usernameD);
                                                        break;
                                                    case 2:
                                                        // a new doctor create the reservations
                                                        mdb.aggiungi_cal4(usernameD);
                                                        break;

                                                    case 3:
                                                        while (true) {
                                                            //stampa le sue informazioni
                                                            printMyProfile(usernameD);

                                                            // TODO Cypher




                                                            break;
                                                        }
                                                    case 0:
                                                        continue; //torna indietro alla pagina principale
                                                }}
                                            }
                                            catch (Exception e){
                                                System.out.println("ERROR: enter a correct number.");
                                                continue;
                                            }


                                        }

                                    }

                                case 2: //SignUp
                                    while (true)
                                    {

                                        Scanner scan = new Scanner(System.in);
                                        System.out.println("Insert the username: ");
                                        String username =  tastiera.readLine();
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
                                        while(!scan.hasNextInt()) {
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


                                case 0:
                                    continue; //torna indietro alla pagina principale

                                default:
                                    System.out.println("insert a correct number: ");
                                    continue;
                            }
                        }catch (Exception e){
                            System.out.println("ERROR: insert a correct number.");
                            continue;
                        }




                    case 2:
                        System.out.println("--USER--\nWhat do you want to do? " +
                                "\n1. [Login]" +
                                "\n2. [Sign up]" +
                                "\n\n0. [Go back]");
                        try{
                            int option_user = keyboard.nextInt();

                            switch (option_user)
                            {
                                case 1: //LOGIN
                                    while (true)
                                    {

                                        System.out.println("--LOGIN USER--\nInsert the username:");
                                        String username = tastiera.readLine();
                                        System.out.println("Insert the password:");
                                        String password = tastiera.readLine();

                                        User user = new User(username,password);
                                        if (!mdb.login_user(user)){
                                            System.out.println("Please retry");
                                        }else {

                                            while (true) {


                                                System.out.println("***USER: " + username + "***" +
                                                        "\nSelect:" +
                                                        "\n1. [find a doctor]" +
                                                        "\n2. [see your reservations]" +
                                                        "\n0. [come back]");
                                                int com = keyboard.nextInt();

                                                switch (com) {

                                                    case 1:

                                                        System.out.println("Select the city and the specialization you are interested to");

                                                        System.out.println("CITIES CURRENTLY AVAILABLE:");
                                                        mdb.display_cities();

                                                        System.out.println("\nSPECIALIZATIONS CURRENTLY AVAILABLE:");
                                                        mdb.display_spec();

                                                        System.out.println("Insert the city:");
                                                        String city = tastiera.readLine();

                                                        System.out.println("Insert the specialization:");
                                                        String specialization = tastiera.readLine();

                                                        System.out.println("View doctors by: " +
                                                                "\n1. [Show the entire list]" +
                                                                "\n2. [Sort by ascendent price (top 3 cheapest doctors)]" +
                                                                "\n3. [Recommended by the system]");

                                                        int sort = keyboard.nextInt();

                                                        switch (sort)
                                                        {
                                                            case 1:
                                                                Methods.printAllDocList(city,specialization);
                                                                break;

                                                            case 2:
                                                                printCheapestDocList(city, specialization);
                                                                break;

                                                            //TODO Inserire quelli con Cypher


                                                        }
                                                        System.out.println("Enter the name of the doctor you want to see:");



                                                        String nome = null;
                                                        try {
                                                            nome = tastiera.readLine();
                                                        } catch (Exception e) {
                                                        }

                                                        printDocInfo(nome);

                                                        System.out.println("Type '0' if you want to book a medical examination with the doctor");

                                                        int book = keyboard.nextInt();
                                                        if (book == 0) {

                                                            printSlots(nome);

                                                            System.out.println("Insert the data of the medical examination you want to book");



                                                            String date1 = null;
                                                            try {
                                                                date1 = tastiera.readLine();
                                                            } catch (Exception e) {
                                                            }

                                                            Reservation reservation = new Reservation(nome,date1,user.getUsername());


                                                            mdb.book(reservation);
                                                        }

                                                        continue;
                                                    case 2:
                                                        mdb.showUserReservations(username);

                                                        System.out.println("Type 0 if you want to delete a reservation:");
                                                        int d = keyboard.nextInt();
                                                        if (d == 1)
                                                        {
                                                            System.out.println("Select the examination's date:");


                                                            String date = null;
                                                            try {
                                                                date = tastiera.readLine();
                                                            } catch (Exception e) {
                                                            }

                                                            System.out.println("Write the doctor's name:");


                                                            String docname = null;
                                                            try {
                                                                docname = tastiera.readLine();
                                                            } catch (Exception e) {
                                                            }

                                                            Reservation reservation = new Reservation(docname,date,username);

                                                            mdb.freeSlot(reservation);
                                                        }


                                                    case 0:
                                                        break;

                                                    default:
                                                        System.out.println("INCORRECT COMMAND. Please retry");
                                                        continue;
                                                }
                                                break;
                                            }



                                        }

                                    }

                                case 2: //SignUp
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


                                        case 0:
                                    continue; //torna indietro alla pagina principale

                                default:
                                    System.out.println("insert a correct number");
                                    continue;
                            }
                        }catch (Exception e) {
                            System.out.println("ERROR: insert a correct number.");
                            continue;
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
                                            System.out.println("username deleted successfully!");
                                            break;
                                        }
                                        break;

                                    case 5:
                                        //delete a user
                                        System.out.println("Insert the username you want to delete");
                                        String usernameDelete = keyboard.next();
                                        String passwordDelete = "ok";

                                        User userDelete = new User(usernameDelete, passwordDelete);
                                        if (!mdb.delete_user_by_the_administrator(userDelete)) {
                                            System.out.println(" username not found. Please choose another one.");
                                        } else {
                                            System.out.println("username deleted successful!");
                                            break;
                                        }
                                        break;

                                    case 6:
                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                "\n1. show cities with more registered users" +
                                                "\n2. show the most expensive specialization" +
                                                "\n\n0. Go back");
                                        int analytics_adm = keyboard.nextInt();

                                        switch (analytics_adm){
                                            case 1:
                                                mdb.printmostcity();
                                               // mdb.printAvgUsers();
                                                break;
                                            case 2:
                                                mdb.printMostExpSpec();
                                                break;
                                            case 0:
                                                System.out.println("return to the menu");
                                                break;}




                                    case 0:
                                        //go back
                                        System.out.println("ok 0");
                                        break;
                                }
                            }catch (Exception e){
                                System.out.println("ERROR: insert a correct number.");
                            }
                            break;

                        }


                    default:
                        System.out.println("Please, insert a number from 1 to 3");

                }
            }catch (Exception e){
                System.out.println("ERROR: insert a number from 1 to 3.");
            }

            break;


        }

        //break;

    }
}
