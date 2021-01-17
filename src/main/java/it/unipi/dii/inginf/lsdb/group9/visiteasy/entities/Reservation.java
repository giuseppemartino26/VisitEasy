package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class Reservation {

    private String docname;
    private String username;
    private String date;

    public Reservation(String docname, String date)
    {
        this.docname = docname;
        this.date = date;
    }

    public Reservation(String docname, String date, String username)
    {
        this(docname,date);
        this.username = username;
    }

    public String getDocname(){return docname;}
    public String getUsername(){return username;}
    public String getDate(){return date;}

}
