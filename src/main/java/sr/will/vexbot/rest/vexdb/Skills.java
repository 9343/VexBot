package sr.will.vexbot.rest.vexdb;

import java.util.ArrayList;

public class Skills extends APIV1 {
    public ArrayList<Skill> result;

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
