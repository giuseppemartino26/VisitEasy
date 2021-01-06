package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import  java.util.*;


public class Main {

    public static void main(String[] args) {

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
                        System.out.println("Select a command: " +
                                "\n1. Login" +
                                "\n2. Sign up" +
                                "\n\n0. Go back");
                        try{
                            int option_user = keyboard.nextInt();

                            switch (option_user)
                            {
                                case 1:
                                    //Login();
                                    break;

                                case 0:
                                    //SignUp();
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
