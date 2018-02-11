package sr.will.vexbot.rest.vexdb.v1;

import java.util.List;

public class Matches extends APIV1 {
    public List<Match> result;

    public class Match {
        public String sku;
        public String division;
        public int round;
        public int instance;
        public int matchnum;
        public String field;
        public String red1;
        public String red2;
        public String red3;
        public String redsit;
        public String blue1;
        public String blue2;
        public String blue3;
        public String bluesit;
        public int redscore;
        public int bluescore;
        public int scored;
        public String scheduled;
    }
}
