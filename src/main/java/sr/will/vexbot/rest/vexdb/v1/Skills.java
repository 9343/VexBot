package sr.will.vexbot.rest.vexdb.v1;

import java.util.List;

public class Skills extends APIV1 {
    public List<Skill> result;

    public class Skill {
        public String sku;
        public int type;
        public int rank;
        public String team;
        public String program;
        public int attempts;
        public int score;
        public int season_rank;
        public int season_attempts;
    }
}
