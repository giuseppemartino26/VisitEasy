package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;

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
                        
                        break;

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

                                        if (!mdb.signup_user(user)) {
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


                    case 3:
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
                                        "\n5. modify a doctor" +
                                        "\n6. delete a user" +
                                        "\n7. modify a user" +
                                        "\n8. populate db" +
                                        "\n\n0. Go back");
                        }
                            try{
                                int option_administrator = keyboard.nextInt();

                                switch (option_administrator)
                                {
                                    case 1:
                                        System.out.println("ok 1");
                                        break;
                                    case 2:
                                        System.out.println("ok 2");
                                        break;
                                    case 3:
                                        System.out.println("ok 3");
                                        break;
                                    case 4:
                                        System.out.println("ok 4");
                                        break;
                                    case 5:
                                        System.out.println("ok 5");
                                        break;
                                    case 6:
                                        System.out.println("ok 6");
                                        break;
                                    case 7:
                                        System.out.println("ok 7");
                                        break;
                                    case 8:
                                        System.out.println("filling the database with doctors");
                                         mdb.populate_doctors_from_file();

                                    case 0:
                                        System.out.println("ok 0");
                                        break;
                                }
                        }catch (Exception e){
                                    System.out.println("ERROR: insert a correct number.");
                                    continue;
                            }

                        }


                    default:
                        System.out.println("Please, insert a number from 1 to 3");
                        continue;
                }
            }catch (Exception e){
                System.out.println("ERROR: insert a number from 1 to 3.");
                continue;
            }

        break;
        }



    }
}
