package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class Rankings extends APIV1 {
    public ArrayList<Ranking> result;

    public class Ranking {
        public String sku;
        public String division;
        public int rank;
        public String team;
        public int wins;
        public int losses;
        public int ties;
        public int wp;
        public int ap;
        public int sp;
        public int trsp;
        public int max_score;
        public double opr;
        public double dpr;
        public double ccwm;
    }
}
