package it.unipi.dii.inginf.lsdb.group9.visiteasy.entities;

public class Reservation {

    private String docname;
    private String username;
    private String date;
    private String docusername;

    public Reservation(String docusername, String date)
    {
        this.docusername = docusername;
        this.date = date;
    }

    public Reservation(String docusername, String date, String username)
    {
        this(docusername,date);
        this.username = username;
    }

    public Reservation(String docusername, String date, String username,String docname)
    {
        this(docusername, date, username);
        this.docname = docname;
    }

    public String getDocname(){return docname;}
    public String getUsername(){return username;}
    public String getDate(){return date;}
    public String getDocusername(){return docusername;}

}
