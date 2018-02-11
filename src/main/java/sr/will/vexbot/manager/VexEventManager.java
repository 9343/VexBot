package sr.will.vexbot.manager;

import com.google.gson.Gson;
import sr.will.jarvis.manager.JarvisThread;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Events;

import java.time.ZonedDateTime;

public class VexEventManager {
    private VexBot module;

    public VexEventManager(VexBot module) {
        this.module = module;

        ZonedDateTime dateTime = ZonedDateTime.now();

        new JarvisThread().module(module).start();
    }

    public void checkEvents(String date) {
        Gson gson = new Gson();
        Events events = gson.fromJson(module.getFromDB("get_events?region=Maine&date=" + date), Events.class);

    }
}
