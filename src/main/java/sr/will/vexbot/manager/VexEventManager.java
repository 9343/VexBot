package sr.will.vexbot.manager;

import sr.will.vexbot.QueryBuilder;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Events;

import java.time.ZonedDateTime;

public class VexEventManager {
    private VexBot module;

    public VexEventManager(VexBot module) {
        this.module = module;

        ZonedDateTime dateTime = ZonedDateTime.now();
    }

    public void checkEvents(String date) {
        Events events = new QueryBuilder().filter("region", "Maine").filter("date", date).get(Events.class);
    }
}
