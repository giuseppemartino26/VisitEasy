package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import  java.util.*;




public class Main {

   private static MongoManager mdb = new MongoManager();
   private static Scanner keyboard = new Scanner(System.in);



    public static void printAllDocList(String city, String spec)
    {
        ArrayList<Doctor> doclist = new ArrayList<>();
        doclist = mdb.getDocByCitySpec(city, spec);

        System.out.println("----List of all "+spec+" of "+city+"----");

        for (int i = 0; i < doclist.size(); i++)
        {
            System.out.println(doclist.get(i).getName());
        }
    }


    public static void main(String[] args) {


        while (true)
        {
            System.out.println("-----Welcome to VisitEasy----- \nSelect a command: \nI'm a:" +
                    "\n1. Doctor" +
                    "\n2. User" +
                    "\n3. Administrator ");

            try {
                int option = keyboard.nextInt();


                switch (option)
                {

                    case 1:

                        System.out.println("--DOCTOR--\nSelect a command: " +
                                "\n1. Login" +
                                "\n2. Sign up" +
                                "\n\n0. Go back");

                        try{
                            int option_doctor = keyboard.nextInt();

                            switch (option_doctor)
                            {
                                case 1: //LOGIN

                                    while (true)
                                    {
                                        System.out.println("--LOGIN DOCTOR--\nInsert the username");
                                        String usernameD = keyboard.next();
                                        System.out.println("Insert the password");
                                        String passwordD = keyboard.next();

                                        Doctor doctor = new Doctor(usernameD,passwordD);
                                        if (!mdb.login_doctor(doctor)){
                                            System.out.println("Please retry");
                                        }else {
                                            //TODO
                                        }

                                    }

                                case 2: //SignUp
                                    while (true)
                                    {

                                        System.out.println("Insert the username");
                                        String username = keyboard.next();
                                        System.out.println("Insert the password");
                                        String password = keyboard.next();
                                        System.out.println("Insert your name");
                                        String name = keyboard.next();
                                        System.out.println("Insert your city");
                                        String city = keyboard.next();
                                        System.out.println("Insert a short bio");
                                        String bio = keyboard.next();
                                        System.out.println("Insert your specialization");
                                        String specialization = keyboard.next();
                                        System.out.println("Insert your address");
                                        String address = keyboard.next();
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
                        }catch (Exception e){
                            System.out.println("ERROR: insert a correct number.");
                            continue;
                        }




                    case 2:
                        System.out.println("--USER--\nSelect a command: " +
                                "\n1. Login" +
                                "\n2. Sign up" +
                                "\n\n0. Go back");
                        try{
                            int option_user = keyboard.nextInt();

                            switch (option_user)
                            {
                                case 1: //LOGIN
                                    while (true)
                                    {
                                        System.out.println("--LOGIN USER--\nInsert the username");
                                        String username = keyboard.next();
                                        System.out.println("Insert the password");
                                        String password = keyboard.next();

                                        User user = new User(username,password);
                                        if (!mdb.login_user(user)){
                                            System.out.println("Please retry");
                                        }else {

                                            System.out.println("Select the city and the specialization you are interested to");

                                            System.out.println("CITIES CURRENTLY AVAIABLE:");
                                            mdb.display_cities();

                                            System.out.println("\nSPECIALIZATIONS CURRENTLY AVAIABLE:");
                                            mdb.display_spec();

                                            System.out.println("Insert the city");
                                            String city = keyboard.next();

                                            System.out.println("Insert the specialization");
                                            String specialization = keyboard.next();

                                            System.out.println("Choose the doctors by: (select a command)" +
                                                    "\n1. Show the entire list" +
                                                    "\n2. Sort by ascendent price (most 3 cheapest doctors)" +
                                                    "\n3. Doctors most reviewed" +
                                                    "\n4. Doctors recommended by the system");

                                            int sort = keyboard.nextInt();

                                            switch (sort)
                                            {
                                                case 1:
                                                    printAllDocList(city,specialization);
                                                    break;


                                            }
                                            System.out.println("Enter the name of the doctor you want to see:");
                                            String namedoc = keyboard.next();



                                            //TODO
                                        }

                                    }

                                case 2: //SignUp
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


                                case 0:
                                    continue; //torna indietro alla pagina principale

                                default:
                                    System.out.println("insert a correct number");
                                    continue;
                            }
                        }catch (Exception e){
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
                                        "\n6. populate db" +
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
                                        String name = keyboard.next();
                                        System.out.println("Insert the city");
                                        String city = keyboard.next();
                                        System.out.println("Insert a short bio");
                                        String bio = keyboard.next();
                                        System.out.println("Insert the specialization");
                                        String specialization = keyboard.next();
                                        System.out.println("Insert the address");
                                        String address = keyboard.next();
                                        System.out.println("Insert the price for the visit");
                                        int price = keyboard.nextInt();

                                        Doctor doctor = new Doctor(usernameD, passwordD, price, city, bio, specialization, bio, address);

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


                                    case 8:
                                        //populate db
                                        //System.out.println("filling the database with doctors");
                                        //mdb.populate_doctors_from_file();
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