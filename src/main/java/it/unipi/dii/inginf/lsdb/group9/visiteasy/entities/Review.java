package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class Review {

    private String id;
    private String dateTime;
    private String docname;
    private String username;
    private int rating;
    private String text;

    public Review(String id, String dateTime, String username, int rating, String text)
    {
        this.id = id;
        this.dateTime = dateTime;
        this.username = username;
        this.rating = rating;
        this.text = text;
    }

    public Review(String id, String dateTime, String docname, String username, int rating, String text)
    {
        this(id,dateTime,username,rating,text);
        this.docname = docname;
    }

    public Review(String id)
    {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDocname() {
        return docname;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }
}
