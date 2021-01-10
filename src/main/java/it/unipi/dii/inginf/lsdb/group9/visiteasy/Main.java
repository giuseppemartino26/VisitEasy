package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import  java.util.*;


public class Main {

    public static void main(String[] args) {

        MongoManager mdb = new MongoManager();
        Scanner keyboard = new Scanner(System.in);

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
                                        "\n5. update a doctor" +
                                        "\n6. delete a user" +
                                        "\n7. update a user" +
                                        "\n8. populate db" +
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
                                        //update a doctor


                                        System.out.println("what do you want to modify?");
                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                "\n1. username" +
                                                "\n2. password" +
                                                "\n3. city" +
                                                "\n4. specialization" +
                                                "\n5. bio" +
                                                "\n6. address" +
                                                "\n7. name" +
                                                "\n8. price"+
                                                "\n\n0. Go back");
                                        try{
                                            int option_administrator_modify_doctor = keyboard.nextInt();

                                            switch (option_administrator_modify_doctor)
                                            {
                                                case 1:
                                                    //modify username doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameModify = keyboard.next();
                                                    System.out.println("Insert the new username");
                                                    String passwordNModify = keyboard.next();
                                                    int price1 = 1;
                                                    Doctor doctorUsername = new Doctor(usernameModify, passwordNModify,price1);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorUsername)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("username updated successful!");
                                                        break;
                                                    }
                                                case 2:
                                                    //modify password doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpdate = keyboard.next();
                                                    System.out.println("Insert the new password");
                                                    String passwordUpdate = keyboard.next();
                                                    int price2 = 2;
                                                    Doctor doctorPassword = new Doctor(usernameUpdate, passwordUpdate, price2);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorPassword)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("password updated successful!");
                                                        break;
                                                    }
                                                case 3:
                                                    //modify city doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpCity = keyboard.next();
                                                    System.out.println("Insert the new city");
                                                    String passwordUpCity = keyboard.next();
                                                    int price3 = 3;
                                                    Doctor doctorCity = new Doctor(usernameUpCity, passwordUpCity, price3);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorCity)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("city updated successful!");
                                                        break;
                                                    }

                                                case 4:
                                                    //modify specialization doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpS = keyboard.next();
                                                    System.out.println("Insert the new specialization");
                                                    String passwordUpS = keyboard.next();
                                                    int price4 = 4;
                                                    Doctor doctorS = new Doctor(usernameUpS, passwordUpS, price4);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorS)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("specialization updated successful!");
                                                        break;
                                                    }
                                                case 5:
                                                    //modify bio doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpB = keyboard.next();
                                                    System.out.println("Insert the new bio");
                                                    String passwordUpB = keyboard.next();
                                                    int price5 = 5;
                                                    Doctor doctorB = new Doctor(usernameUpB, passwordUpB, price5);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorB)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("bio updated successful!");
                                                        break;
                                                    }
                                                case 6:
                                                    //modify address doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpA = keyboard.next();
                                                    System.out.println("Insert the new address");
                                                    String passwordUpA = keyboard.next();
                                                    int price6 = 6;
                                                    Doctor doctorA = new Doctor(usernameUpA, passwordUpA, price6);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorA)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("address updated successful!");
                                                        break;
                                                    }
                                                case 7:
                                                    //modify name doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpN = keyboard.next();
                                                    System.out.println("Insert the new name");
                                                    String passwordUpN = keyboard.next();
                                                    int price7 = 7;
                                                    Doctor doctorN = new Doctor(usernameUpN, passwordUpN, price7);
                                                    if (!mdb.update_doctor_by_the_administrator(doctorN)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("name updated successful!");
                                                        break;
                                                    }
                                                break;
                                                case 8:
                                                    //modify price doctor
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpPrice = keyboard.next();
                                                    String passwordUpPrice = "password";
                                                    int price8 = 8;
                                                    Doctor DoctorPrice = new Doctor(usernameUpPrice, passwordUpPrice, price8);
                                                    if (!mdb.update_doctor_by_the_administrator(DoctorPrice)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("price updated successful!");
                                                        break;
                                                    }
                                                break;
                                                    case 0:
                                                    //go back

                                                    break;
                                            }
                                        }catch (Exception e){
                                            System.out.println("ERROR--------");
                                            continue;}
                                    case 6:
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
                                    case 7:
                                        //update a user

                                        System.out.println("what do you want to modify?");
                                        System.out.println("--ADMINISTRATOR--\nSelect a command: " +
                                                "\n1. username" +
                                                "\n2. password" +
                                                "\n3. age"+
                                                "\n\n0. Go back");
                                        try{
                                            int option_administrator_modify_user = keyboard.nextInt();

                                            switch (option_administrator_modify_user)
                                            {
                                                case 1:
                                                    //modify username user
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameModify = keyboard.next();
                                                    System.out.println("Insert the new username");
                                                    String passwordNModify = keyboard.next();
                                                    int age1 = 1;
                                                    User userUsername = new User(usernameModify, passwordNModify, age1);
                                                    if (!mdb.update_user_by_the_administrator(userUsername)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("username updated successful!");
                                                        break;
                                                    }
                                                case 2:
                                                    //modify password user
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpdate = keyboard.next();
                                                    System.out.println("Insert the new password");
                                                    String passwordUpdate = keyboard.next();
                                                    int age2 = 2;
                                                    User userPassword = new User(usernameUpdate, passwordUpdate, age2);
                                                    if (!mdb.update_user_by_the_administrator(userPassword)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("password updated successful!");
                                                        break;
                                                    }
                                                case 3:
                                                    //modify age user
                                                    System.out.println("Insert the username you want modify");
                                                    String usernameUpAge = keyboard.next();
                                                    String passwordUpAge = "password";
                                                    int age3 = 3;
                                                    User userAge = new User(usernameUpAge, passwordUpAge, age3);
                                                    if (!mdb.update_user_by_the_administrator(userAge)) {
                                                        System.out.println(" username not found. Please choose another one.");
                                                    } else {
                                                        System.out.println("age updated successful!");
                                                        break;
                                                    }
                                                case 0:
                                                    //go back

                                                    break;
                                            }
                                        }catch (Exception e){
                                            System.out.println("ERROR--------");
                                            continue;}

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
                                continue;
                            }
                            //break;

                        }


                    default:
                        System.out.println("Please, insert a number from 1 to 3");
                        continue;
                }
            }catch (Exception e){
                System.out.println("ERROR: insert a number from 1 to 3.");
                continue;
            }


        }

        // break;

    }
}