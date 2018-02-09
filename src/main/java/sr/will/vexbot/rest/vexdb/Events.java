package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class Events extends APIV1 {
    public ArrayList<Event> result;

    public class Event {
        public String sku;
        public String key;
        public String program;
        public String name;
        public String loc_venue;
        public String loc_address1;
        public String loc_address2;
        public String loc_city;
        public String loc_region;
        public String loc_postcode;
        public String loc_country;
        public String season;
        public String start;
        public String end;
        public ArrayList<String> divisions;
    }
}
