package it.unipi.dii.inginf.lsdb.group9.visiteasy.persistance;

import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Doctor;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.Review;
import it.unipi.dii.inginf.lsdb.group9.visiteasy.entities.User;
import org.joda.time.DateTime;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import java.util.ArrayList;
import java.util.List;
import static org.neo4j.driver.Values.parameters;

public class Neo4jManager implements AutoCloseable {

    private final Driver driver;
    public Neo4jManager( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }


    public void addDoctor(Doctor doctor)
    {
        try ( Session session = driver.session() )
        {
            String name = doctor.getName();
            String specialization = doctor.getSpecialization();
            String city = doctor.getCity();
            int price = doctor.getPrice();

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MERGE (d:Doctors {docname: $name, specialization: $specialization, city: $city, price: $price})",
            parameters( "name", name, "specialization", specialization, "city", city, "price",price ) );
            return null;
        });
         }
    }


    public void addUser(User user)
    {
        try ( Session session = driver.session() )
        {
            String username = user.getUsername();


            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MERGE (u:User {username: $username})",
                        parameters( "username", username ) );
                return null;
            });
        }
    }

    public void deleteDoctor(Doctor doctor)
    {
        try ( Session session = driver.session() )
        {
            String docname = doctor.getName();

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (n { docname: $docname })\n" +
                                "DETACH DELETE n",
                        parameters( "docname", docname ) );
                return null;
            });
        }
    }


    public void deleteUser(User user)
    {
        try ( Session session = driver.session() )
        {
            String username = user.getUsername();

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (n { username: $username })\n" +
                                "DETACH DELETE n",
                        parameters( "username", username ) );
                return null;
            });
        }
    }

    public void addReview(Review review)
    {
        try ( Session session = driver.session() )
        {
            String id = review.getId();
            String docname = review.getDocname();
            String username = review.getUsername();
            int rating = review.getRating();
            String text = review.getText();
            String dateTime = review.getDateTime();

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MERGE (u:User{username: $username} )" +
                                "MERGE (d:Doctors{docname: $docname})" +
                                "CREATE (r:Review{rating: $rating,text: $text, review: $id, data: $date})" +
                                "CREATE (u)-[:WRITES]->(r)-[:BELONGSTO]->(d)",
                        parameters( "username", username,"docname",docname,"rating",rating,"text",text,"id",id,"date",dateTime ) );
                return null;
            });

        }

    }


    public ArrayList<Review> showReviews(Doctor doctor)
    {
        ArrayList<Review> reviewList = new ArrayList<>();

        try ( Session session = driver.session() )
        {
            String doc_id = doctor.getUsername();
            ArrayList<Review> reviews = session.readTransaction((TransactionWork<ArrayList<Review>>) tx -> {
                        Result result = tx.run( "MATCH (u:User)-[:WRITES]->(r:Review)-[:BELONGSTO]->(d:Doctors)" +
                                        " WHERE d.doc_id = $doc_id" +
                                        " RETURN r.review_id as id, u.username as username,  r.text as text, r.rating as rating, r.data as date",
                                parameters( "doc_id", doc_id) );
                        while(result.hasNext())
                        {
                            Record r = result.next();
                            String id = r.get("id").asString();
                           String username= r.get("username").asString();
                           String text = r.get("text").asString();
                           int rating = r.get("rating").asInt();
                           String date = r.get("date").asString();

                           Review review = new Review(id,date,username,rating,text);
                           reviewList.add(review);
                        }

                return null;
            });
        }
        return reviewList;
    }


    public void like(User user,Review review)
    {
        try ( Session session = driver.session() )
        {
            String username = user.getUsername();
            String review_id = review.getId();

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (r:Review) WHERE r.review_id = $review_id " +
                                "MERGE (u:User{username: $username})" +
                                " MERGE (u)-[:LIKES]->(r)",
                        parameters("review_id",review_id, "username", username ) );
                return null;
            });

        }

    }

    public void printBestReviewers()
    {
            try ( Session session = driver.session() )
            {
                session.readTransaction((TransactionWork<Void>) tx -> {

                            String query = "MATCH (u:User)-[:WRITES]->(r:Review)<-[:LIKES]-(u2:User) WITH u.username AS Username, count(u2) AS NumLikesReceived\n" +
                                    "MATCH (u3:User)-[:WRITES]->(r3:Review) WHERE u3.username = Username\n" +
                                    "RETURN u3.username AS Username, toFloat(NumLikesReceived)/count(r3) AS Ratio ORDER BY Ratio DESC LIMIT 3";
                    Result result = tx.run(query);
                    while(result.hasNext())
                    {
                        Record r = result.next();
                        String user = r.get("Username").asString();
                        double ratio = r.get("Ratio").asDouble();
                        System.out.println("\"" + user + "\" with a ratio of " + ratio);
                    }
                    return null;
                });
            }

    }


    public void printActiveReviewers()
    {
        try ( Session session = driver.session() )
        {
            session.readTransaction((TransactionWork<Void>) tx -> {

                String query = "MATCH (u:User)-[:WRITES]->(r:Review)" +
                        " WHERE (apoc.date.parse(toString(datetime()), 'd',\"yyyy-MM-dd'T'HH:mm:ss\") - apoc.date.parse(r.data, 'd',\"yyyy-MM-dd'T'HH:mm:ss\") < 30 )" +
                        " WITH u.username AS username, count(r) AS NumWrittenReviews" +
                        " RETURN username, NumWrittenReviews " +
                        "ORDER BY NumWrittenReviews DESC LIMIT 10";
                Result result = tx.run(query);
                while(result.hasNext())
                {
                    Record r = result.next();
                    String user = r.get("username").asString();
                    int numWrittenReviews = r.get("NumWrittenReviews").asInt();
                    System.out.println("\"" + user + "\" with " + numWrittenReviews+" written reviews in the last 2 months");
                }
                return null;
            });
        }

    }

    public void recommendedDoctors(String city, String specialization)
    {
        try ( Session session = driver.session() )
        {
            session.readTransaction((TransactionWork<Void>) tx -> {

                String query = "MATCH (r:Review)-[:BELONGSTO]->(d:Doctors)" +
                        " WHERE d.city = $city AND d.specialization= $specialization" +
                        " WITH d.docname AS docname,avg(r.rating) AS AverageRating, count(r) AS NumReviews\n" +
                        "RETURN docname, 5*AverageRating/10 + 5*(1-exp(-NumReviews/10)) AS score ORDER BY score DESC LIMIT 10";
                Result result = tx.run(query,parameters("city",city,"specialization",specialization));
                while(result.hasNext())
                {
                    Record r = result.next();
                    String docname = r.get("docname").asString();
                    double score = r.get("score").asDouble();
                    System.out.println("\"" + docname + "\" with the score: " + score);
                }
                return null;
            });
        }

    }







}
