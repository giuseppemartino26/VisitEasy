package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class MongoManager {

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase db = mongoClient.getDatabase("progetto");
    MongoCollection<Document> users = db.getCollection("users");


    boolean signup_user(User user) {
        if (users.countDocuments(new Document("username", user.getUsername())) == 0) {
            Document doc = new Document("username", user.getUsername()).append("password", user.getPassword()).append("age", user.getAge());
            users.insertOne(doc);
            return true;
        } else return false;
    }


    boolean login_user(User user)  {

        Document result = users.find(eq("username", user.getUsername())).first(); //salvo in "result" il documento il cui campo username è uguale a quello passato come parametro
        try {
            result.getString("username");
       }catch (NullPointerException exception){
           System.out.println("The username does not exist");
           return false;
       }

        String psw = result.getString("password");
        if (psw.equals(user.getPassword())) {
            int age = result.getInteger("age");
            user.setAge(age);
            System.out.println("Correct credentials");
            return true;
        } else{
            System.out.println("Incorrect password");
            return false;
        }

    }
}
