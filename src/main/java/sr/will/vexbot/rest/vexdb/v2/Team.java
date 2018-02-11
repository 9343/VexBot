package sr.will.vexbot.rest.vexdb.v2;

public class Team {
    public int id;
    public String number;
    public String teamName;
    public String robotName;
    public String organisation;
    public Location location;
    public boolean registered;

    public class Location {
        public String city;
        public String region;
        public String country;
        public double lat;
        public double longitude;
    }

    public class Program {
        public int id;
        public String title;
    }

    public class Grade {
        public int id;
        public String title;
    }
}
