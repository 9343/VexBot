package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class SeasonRankings extends APIV1 {
    public ArrayList<SeasonRanking> result;

    public class SeasonRanking {
        public String team;
        public String season;
        public String program;
        public int vrating_rank;
        public double vrating;
    }
}
