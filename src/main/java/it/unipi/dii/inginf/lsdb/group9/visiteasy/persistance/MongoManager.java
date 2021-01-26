package it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance;

import com.mongodb.*;
import com.mongodb.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import  java.util.*;
import java.lang.*;

import com.mongodb.client.model.*;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Administrator;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Reservation;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;


public class MongoManager {

    private final MongoClient mongoClient;
    private final MongoDatabase db;
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> doctors;
    private final MongoCollection<Document> administrators;



    public MongoManager()
    {
        //  mongoClient = MongoClients.create("mongodb://172.16.3.109:27020,172.16.3.110:27020,172.16.3.111:27020/" +
        //         "?retryWrites=true&w=majority&wtimeout=10000");
        ConnectionString uri = new ConnectionString("mongodb://172.16.3.109:27020,172.16.3.110:27020,172.16.3.111:27020");
        MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(uri).readPreference(ReadPreference.nearest()).retryWrites(true).writeConcern(WriteConcern.MAJORITY).readConcern(ReadConcern.MAJORITY).build();
        mongoClient = MongoClients.create(mcs);
        db = mongoClient.getDatabase("progetto");
        users = db.getCollection("users");
        doctors = db.getCollection("doctors");
        administrators = db.getCollection("administrators");

    }


    Consumer<Document> printDocuments = document -> {System.out.println(document.toJson());};
    Consumer<Document> printvalue = document -> {System.out.println(document.getString("_id"));};
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader tastiera = new BufferedReader(input);


    Scanner keyboard = new Scanner(System.in);

    public void closeconnection()
    {
        mongoClient.close();
    }

    /*Ritorna il nome di un dottore il cui username viene passato come parametro*/
    public String getDocName(String docusername)
    {
        Document result = doctors.find(eq("username", docusername)).first();
        String name = null;
        try {
            name = result.getString("name");
        }catch (NullPointerException e){
            System.out.println("ERROR: no doctor found");
        }
        return name;
    }

/* Add a new user in the user collection */
    public boolean add_user(User user)
    {
        if (users.countDocuments(new Document("username", user.getUsername())) == 0) {
            Document doc = new Document("username", user.getUsername()).append("password", user.getPassword()).append("city", user.getCity());
            users.insertOne(doc);
            return true;
        } else return false;
    }

    /* Add a new doctor in the doctor collection */
    public boolean add_doctor(Doctor doctor)
    {
        if (doctors.countDocuments(new Document("username", doctor.getUsername())) == 0) {
            Document doc = new Document("username", doctor.getUsername()).append("password", doctor.getPassword()).append("price", doctor.getPrice()).append("city", doctor.getCity()).append("name", doctor.getName()).append("specialization", doctor.getSpecialization()).append("bio", doctor.getBio()).append("address", doctor.getAddress());
            doctors.insertOne(doc);
            return true;
        } else return false;
    }




    public boolean login_user(User user)
    {
        Document result = users.find(eq("username", user.getUsername())).first(); //salvo in "result" il documento il cui campo username è uguale a quello passato come parametro
        try {
            result.getString("username");
        }catch (NullPointerException exception){
            System.out.println("The username does not exist");
            return false;
        }

        String psw = result.getString("password");
        if (psw.equals(user.getPassword())) {

            System.out.println("Correct credentials");
            return true;
        } else{
            System.out.println("Incorrect password");
            return false;
        }

    }


    public boolean login_doctor(Doctor doctor)
    {
        Document result = doctors.find(eq("username", doctor.getUsername())).first();
        try {
            result.getString("username");
        }catch (NullPointerException exception){
            System.out.println("The username does not exist");
            return false;
        }

        String psw = result.getString("password");
        if (psw.equals(doctor.getPassword())) {
            System.out.println("Correct credentials");
            return true;
        } else{
            System.out.println("Incorrect password");
            return false;
        }

    }


    public boolean login_administrator(Administrator administrator){
        Document result = administrators.find(eq("username", administrator.getUsername())).first(); //salvo in "result" il documento il cui campo username è uguale a quello passato come parametro
        try {
            result.getString("username");
        }catch (NullPointerException exception){
            System.out.println("The username does not exist");
            return false;
        }
        String psw = result.getString("password");
        if (psw.equals(administrator.getPassword())) {
            System.out.println("Correct credentials");
            return true;}
        else{
            System.out.println("Incorrect password");

            return false;
        }
    }

    /* adds a new administrator in the administrators collection*/
    public boolean add_administrator(Administrator administrator) {

        if (administrators.countDocuments(new Document("username", administrator.getUsername())) == 0) {
            Document doc = new Document("username", administrator.getUsername()).append("password", administrator.getPassword());
            administrators.insertOne(doc);
            return true;
        } else return false;

    }


   /* prints all the cities of doctors in the DB*/
    public void display_cities()
    {
        Bson myGroup = Aggregates.group("$city");
        doctors.aggregate(Arrays.asList(myGroup)).forEach(printvalue);
    }

    /* prints all the specializations of doctors in the DB*/
    public void display_spec()
    {
        Bson myGroup = Aggregates.group("$specialization");
        doctors.aggregate(Arrays.asList(myGroup)).forEach(printvalue);
    }

/* Returns the list of doctors of a given city and specialization*/
    public ArrayList<Doctor> getDocByCitySpec(String city, String specialization)
    {
        ArrayList<Doctor> doclist = new ArrayList<>();

        Consumer<Document> addtolist = document -> {
            Doctor newdoc = new Doctor(document.getString("username"),"",document.getString("name"));
            doclist.add(newdoc);
        };

        doctors.find(and(eq("city", city), eq("specialization", specialization))).forEach(addtolist);
        return doclist;
    }

    /* Returns the list of all doctors in the DB*/
    public ArrayList<Doctor> getAllDoc()
    {
        ArrayList<Doctor> doclist = new ArrayList<>();

        Consumer<Document> addtolist = document -> {
            Doctor newdoc = new Doctor(document.getString("username"),"",document.getString("name"));
            doclist.add(newdoc);
        };

        doctors.find().forEach(addtolist);
        return doclist;
    }

/* Returns the list of cheapest doctors for a given city and specialization*/
    public ArrayList<Doctor> cheapestDoc(String city, String specialization)
    {
        ArrayList<Doctor> doclist = new ArrayList<>();

        Consumer<Document> addtolist = document -> {
            Doctor newdoc = new Doctor(document.getInteger("price"),document.getString("name"),document.getString("username"));
            doclist.add(newdoc);
        };

        Bson myMatch = match(and(eq("city",city), eq("specialization", specialization)));
        Bson mySort = sort(ascending("price"));
        Bson myLimit = limit(3);

        doctors.aggregate(Arrays.asList(myMatch,mySort,myLimit)).forEach(addtolist);
        return doclist;
    }

/* returns a "Doctor" object with all completed fields, giving his username */
    public Doctor getMyProfile(String username)
    {
        Document result = doctors.find(eq("username", username)).first();
        Doctor doctor = new Doctor(result.getString("name"),result.getInteger("price"),result.getString("address"),result.getString("bio"));
        return  doctor;
    }


    //DELETE USER
    public boolean delete_user_by_the_administrator(User user)
    {
        Document result = users.find(eq("username", user.getUsername())).first();
        try {
            result.getString("username");
        } catch (NullPointerException exception) {
            System.out.println("The username does not exist");
            return false;
        }
        users.deleteOne(eq("username", user.getUsername()));
        return true;
    }

    //DELETE DOCTOR
    public boolean delete_doctor_by_the_administrator(Doctor doctor)
    {
        Document result = doctors.find(eq("username", doctor.getUsername())).first();
        try {
            result.getString("username");
        } catch (NullPointerException exception) {
            System.out.println("The username does not exist");
            return false;
        }
        doctors.deleteOne(eq("username", doctor.getUsername()));
        return true;
    }

    /* Returns the found document in the doctors collection*/
    public Document store(Doctor doctor)
    {
        Document result = new Document();
        return  result = doctors.find(eq("username", doctor.getUsername())).first();
    }

    public void addDocument(Document document)
    {
        doctors.insertOne(document);
    }




    /* Returns a list of dates from a start date to an end date*/
    public static List<DateTime> getDateRange(DateTime start, DateTime end)
    {
        List<DateTime> ret = new ArrayList<DateTime>();
        DateTime tmp = start;
        while (tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    /* Ritorna true se nelle reservations di un dottore c'è uno slot con una certa data e un certo paziente */
    boolean trovato(String docusername,String date,String patient)
    {
        Bson match = match(eq("username",docusername));
        Bson unwind = unwind("$reservations");
        Bson match2 = match(and(eq("reservations.date",date),eq("reservations.patient",patient)));
        Bson count = count();

        int b;
        try
        { b = doctors.aggregate(Arrays.asList(match, unwind, match2, count)).first().getInteger("count");
           // System.out.println(b);
        }catch (NullPointerException e){

            return false;
        }

        return true; //ha trovato quello che gli passo nei parametri

    }

    /*releases a slot/ deletes a reservation, removing it both from users and doctors collection. Used transaction for atomicity of the operation*/
    public void freeSlot(Reservation reservation)
    {
        String doctor = reservation.getDocusername();
        String date = reservation.getDate();
        String user = reservation.getUsername();

        /* Mi assicuro che lo slot che voglio eliminare sia proprio quello dell'utente che ha fatto il login*/
        if (trovato(doctor,date,user))
        {
            ClientSession clientSession = mongoClient.startSession();
            try
            {
                clientSession.startTransaction();

                doctors.updateOne(clientSession,new Document("username",doctor).append("reservations.date",date),Updates.set("reservations.$.patient",""));

                Bson match = new Document("username",user);
                Bson deleteu = Updates.pull("reservations", new Document("date", date));
                users.updateOne(clientSession,match,deleteu);
                System.out.println("Reservation deleted");

                clientSession.commitTransaction();
            }catch (Exception e){
                System.out.println("ERROR: Slot has not been released");
                clientSession.abortTransaction();
            }finally {
                clientSession.close();
            }

        }else {
            System.out.println("ERROR: There is not present any your reservation in the selected slot");
        }
    }


/* book a reservation. Used mongoDB transactions*/
    public void book(Reservation reservation)
    {
        String user = reservation.getUsername();
        String date = reservation.getDate();
        String docusername = reservation.getDocusername();
        String docname = reservation.getDocname();
        /* controllo se ho già una prenotazione nella stessa data e ora*/
        if (users.countDocuments(new Document("username",user).append("reservations.date",date)) == 0)
        {      /*se lo slot è libero inserisco l'appuntamento sia nella collection doctors che in users */

            if (trovato(docusername, date, ""))
            {
                ClientSession clientSession = mongoClient.startSession();

                try
                {
                    clientSession.startTransaction();

                    doctors.updateOne(clientSession,new Document("username", docusername).append("reservations.date", date), Updates.set("reservations.$.patient", user));

                    Document newres = new Document("date", date).append("doctor", docusername).append("namedoc",docname);
                    users.updateOne(clientSession,eq("username", user), Updates.push("reservations", newres));

                    clientSession.commitTransaction();
                    System.out.println("Reservation made! :)");


                }catch (Exception e){
                    System.out.println("ERROR: Something went wrong, reservation not made");
                    clientSession.abortTransaction();

                }finally {
                    clientSession.close();
                }

            } else {
                System.out.println("We're sorry :( , the slot is already occupied by another patient, please choose another one.");
            }
        }else {
            System.out.println("ERROR: You already have a reservation in this datetime");
        }
    }

    /* Deletes old reservations*/
    public void deleteDate() {

        DateTime start = DateTime.now().minusDays(2);
        DateTime end = start.plusDays(1);

        List<DateTime> between = getDateRange(start, end);

        ArrayList<String> orari = new ArrayList<>();
        orari.add(" 09:00");
        orari.add(" 09:30");
        orari.add(" 10:00");
        orari.add(" 10:30");
        orari.add(" 11:00");
        orari.add(" 11:30");
        orari.add(" 12:00");
        orari.add(" 12:30");
        orari.add(" 15:00");
        orari.add(" 15:30");
        orari.add(" 16:00");
        orari.add(" 16:30");
        orari.add(" 17:00");
        orari.add(" 17:30");
        orari.add(" 18:00");
        orari.add(" 18:30");

        for (DateTime d : between)
        {
            for (String o: orari)
            {
                Bson delete = Updates.pull("reservations", new Document("date", d.toString(DateTimeFormat.shortDate())+o));
                doctors.updateMany(new Document(), delete);
                users.updateMany(new Document(), delete);
            }
        }

    }

/* Inserts empty time slots in the array of document "reservation" inside the document a given doctor*/
    public boolean aggiungi_cal4(String username) throws IOException{
        ArrayList<String> ore = new ArrayList<>();

        ArrayList<String> orari = new ArrayList<>();
        orari.add("09:00");
        orari.add("09:30");
        orari.add("10:00");
        orari.add("10:30");
        orari.add("11:00");
        orari.add("11:30");
        orari.add("12:00");
        orari.add("12:30");
        orari.add("15:00");
        orari.add("15:30");
        orari.add("16:00");
        orari.add("16:30");
        orari.add("17:00");
        orari.add("17:30");
        orari.add("18:00");
        orari.add("18:30");

        System.out.println("You can add the availability of new time slots starting from a date of your choice for a certain number of consecutive days");
        System.out.println("How many time slots per day do you want to add?");
        int hour = keyboard.nextInt();
        int i = 0;

        do {
            System.out.println("Available time slots are:\n09:00 - 09:30 - 10:00 - 10:30 - 11:00 - 11:30 - 12:00 - 12:30 "+
                    "\n15:30 - 16:00 - 16:30 - 17:00 - 17:30 - 18:00 - 18:30"+
                    "\nEnter the time slot you want to add as hh:mm: ");
            String orario = tastiera.readLine();

            if (orari.contains(orario)) {
                ore.add(orario);
                i = i+1;

            } else{
                System.out.println("TIME SLOT NOT VALID!");
            }

        } while (i<hour);


        System.out.println("Add the first date as yyyy-MM-dd:   ");
        String date = tastiera.readLine();
        System.out.println("How many days do you want to add?");
        int day = keyboard.nextInt();
        day = day -1;

        DateTime start;

        try {
            start = DateTime.parse(date);

            DateTime end = start.plusDays(day);

            List<DateTime> between = getDateRange(start, end);

            for (DateTime d : between) {
                for (String o : ore) {
                    Document newres = new Document("date", d.toString(DateTimeFormat.shortDate()) + " " + o).append("patient", "");
                    doctors.updateMany(new Document("username", username), Updates.push("reservations", newres));
                }
            }
            return true;
        }catch (IllegalArgumentException e){
            System.out.println("ERROR: Date format not valid");
        }
        return false;

    }



    /* Return a list of objects Reservation of all the available slots (only reservations where the value of 'patient' field is empty) of a given doctor */
    public ArrayList<Reservation> showEntirereservations(String usernamedoc)
    {
        ArrayList<Reservation> datelibere = new ArrayList<>();
        Consumer<Document> printcale = document -> {

            Reservation reservation = new Reservation(usernamedoc,document.getEmbedded(Arrays.asList("reservations", "date"), String.class));
            datelibere.add(reservation);
        };

        Bson match = match(eq("username",usernamedoc));
        Bson unwind = unwind("$reservations");
        Bson match2 = match(eq("reservations.patient",""));
        Bson project = project(fields(excludeId(), include("reservations")));

        doctors.aggregate(Arrays.asList(match,unwind,match2,project)).forEach(printcale);
        return datelibere;
    }

/* Returns the list of objects Reservation of all the timeslots of a given doctor*/
    public ArrayList<Reservation> showEntirereservationsDoc(String usernamedoc)
    {
        ArrayList<Reservation> datelibere = new ArrayList<>();
        Consumer<Document> printcale = document -> {

            String date = document.getEmbedded(Arrays.asList("reservations", "date"), String.class);
            String patient = document.getEmbedded(Arrays.asList("reservations", "patient"), String.class);
            Reservation reservation = new Reservation(usernamedoc,date,patient);
            datelibere.add(reservation);
        };

        Bson match = match(eq("username",usernamedoc));
        Bson unwind = unwind("$reservations");
        Bson project = project(fields(excludeId(), include("reservations")));

        doctors.aggregate(Arrays.asList(match,unwind,project)).forEach(printcale);
        return datelibere;
    }



/* Returns a list of object Reservation related to all the reservations done by a specific user*/
    public ArrayList<Reservation> showUserReservations(String user)
    {
        ArrayList<Reservation> datelist = new ArrayList<>();
        Consumer<Document> printcale = document -> {


            String docusername = document.getEmbedded(Arrays.asList("reservations","doctor"), String.class);
            String date = document.getEmbedded(Arrays.asList("reservations","date"), String.class);
            String namedoc = document.getEmbedded(Arrays.asList("reservations","namedoc"), String.class);

            Reservation reservation = new Reservation(docusername,date,user,namedoc);

            datelist.add(reservation);

        };

        Bson match = match(eq("username",user));
        Bson unwind = unwind("$reservations");
        Bson project = project(fields(excludeId(), include("reservations")));

        users.aggregate(Arrays.asList(match,unwind,project)).forEach(printcale);
        return datelist;
    }





    //Analytics 3 most expensive specializations
    public void printMostExpSpec() {

        Bson group1 = new Document("$group", new Document("_id", new Document("specialization", "$specialization"))
                .append("AvgPrice", new Document("$avg", "$price")));
        Bson project1 = project(fields(excludeId(), computed("specialization", "$_id.specialization"),
                include("AvgPrice")));


        Bson sort = sort(descending("AvgPrice"));
        Bson limit = limit(3);
        System.out.println("The three most expensive specialization are: ");
        doctors.aggregate(Arrays.asList(group1, project1, sort, limit))
                .forEach(printDocuments);

    }



// Analytics: prints the cities where there are more user registred in the application
    public void printmostcity()
    {

        Bson group = group("$city", sum("count", 1L), Accumulators.push("city", "$city"));
        Bson sort = sort(descending("count"));
        Bson limit = limit(3);
        Bson project = project(fields(excludeId(), computed("city", "$_id"), include("count")));

        List<Document> results = users.aggregate(Arrays.asList(group, sort, limit, project)).into(new ArrayList<>());

        System.out.println("Cities where the app is used more: ");
        results.forEach(printDocuments);

    }

    /* returns true if a doctor is present in the collection doctors, false if not*/
    public boolean find(Doctor doctor)
    {
        return doctors.countDocuments(new Document("username", doctor.getUsername())) != 0;
    }


    /* update the field bio of a document of a specific doctor in the collection doctors*/
    public void updateBio(Doctor doctor, String bio)
    {
        String username = doctor.getUsername();

        Document result = doctors.find(eq("username", username)).first();

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("bio", bio); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        db.getCollection("doctors").updateOne(result, updateObject);


    }

    /* update the field address of a document of a specific doctor in the collection doctors*/
    public void updateAddress(Doctor doctor, String address){

        String username = doctor.getUsername();
        Document result = doctors.find(eq("username", username)).first();

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("address", address); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        db.getCollection("doctors").updateOne(result, updateObject);
    }

    /* update the field price of a document of a specific doctor in the collection doctors*/
    public void updatePrice(Doctor doctor, int price){

        String username = doctor.getUsername();
        Document result = doctors.find(eq("username", username)).first();

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("price", price); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        db.getCollection("doctors").updateOne(result, updateObject);
    }


}