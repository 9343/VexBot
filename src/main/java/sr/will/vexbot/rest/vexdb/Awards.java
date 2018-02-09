package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class Awards extends APIV1 {
    public ArrayList<Award> result;

    public class Award {
        public String sku;
        public String name;
        public String team;
        public ArrayList<String> qualifies;
        public int order;
    }
}
