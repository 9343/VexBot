package sr.will.vexbot.rest.vexdb.v1;

import java.util.List;

public class Awards extends APIV1 {
    public List<Award> result;

    public class Award {
        public String sku;
        public String name;
        public String team;
        public List<String> qualifies;
        public int order;
    }
}
