package sr.will.vexbot.rest.vexdb.v1;

import java.util.List;

public class SeasonRankings extends APIV1 {
    public List<SeasonRanking> result;

    public class SeasonRanking {
        public String team;
        public String season;
        public String program;
        public int vrating_rank;
        public double vrating;
    }
}
