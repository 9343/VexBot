package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class Teams extends APIV1 {
    public ArrayList<Team> result;

    public class Team {
        public String number;
        public String program;
        public String team_name;
        public String robot_name;
        public String organisation;
        public String city;
        public String region;
        public String country;
        public String grade;
        public int is_registered;
    }
}
