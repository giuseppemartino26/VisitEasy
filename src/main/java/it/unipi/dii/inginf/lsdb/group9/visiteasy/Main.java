package it.unipi.dii.inginf.lsdb.group9.visiteasy;

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
                                case 1:
                                    // TODO Login();
                                    break;

                                case 2: //SignUp
                                    System.out.println("Insert the username");
                                    String username = keyboard.next();
                                    System.out.println("Insert the password");
                                    String password = keyboard.next();
                                    System.out.println("Insert your age");
                                    int age = keyboard.nextInt();

                                    if (!mdb.signup(username, password, age)){
                                        System.out.println("The username is already used. Please choose another one.");
                                    }else {System.out.println("Registration successful!");}
                                    continue;

                                case 0:
                                    continue;

                                default:
                                    System.out.println("insert a correct number");
                                    continue;
                            }
                        }catch (Exception e){
                            System.out.println("ERROR: insert a correct number.");
                            continue;
                        }



                    case 3:
                        break;

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
