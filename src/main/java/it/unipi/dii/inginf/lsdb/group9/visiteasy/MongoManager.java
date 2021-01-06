package it.unipi.dii.inginf.lsdb.group9.visiteasy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import org.bson.Document;

public class MongoManager {

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase db = mongoClient.getDatabase("progetto");
    MongoCollection<Document> users = db.getCollection("users");


boolean signup (String username, String password, int age )
{
    User user = new User(username,password,age);
    if (users.countDocuments(new Document("username",user.getUsername())) == 0) {
        Document doc = new Document("username", user.getUsername()).append("password", user.getPassword()).append("age", user.getAge());
        users.insertOne(doc);
        return true;
    }else return false;
}


}
