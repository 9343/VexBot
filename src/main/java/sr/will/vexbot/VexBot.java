package sr.will.vexbot;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.core.Permission;
import sr.will.jarvis.Jarvis;
import sr.will.jarvis.module.Module;
import sr.will.vexbot.command.CommandAnnounce;
import sr.will.vexbot.command.CommandAwards;
import sr.will.vexbot.command.CommandTeam;
import sr.will.vexbot.command.CommandVerifySettings;
import sr.will.vexbot.event.EventHandlerVerify;
import sr.will.vexbot.rest.vexdb.v1.Awards;
import sr.will.vexbot.rest.vexdb.v1.Teams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class VexBot extends Module {
    private HashMap<Long, GuildVerificationData> guildVerificationData = new HashMap<>();
    private Gson gson = new Gson();

    public static final Pattern teamPattern = Pattern.compile("[0-9]{1,5}[a-zA-Z]");
    public static final Pattern validationPattern = Pattern.compile("([a-zA-Z]+)\\s*\\|\\s*(" + teamPattern.pattern() + ")");

    public void initialize() {
        setNeededPermissions(
                Permission.MANAGE_ROLES,
                Permission.NICKNAME_MANAGE,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_MANAGE,
                Permission.MESSAGE_ADD_REACTION
        );
        setGuildWhitelist(
                290558097246650369L,
                305772966044631040L
        );
        setDefaultEnabled(false);

        registerEventHandler(new EventHandlerVerify(this));

        registerCommand("announce", new CommandAnnounce(this));
        registerCommand("awards", new CommandAwards(this));
        registerCommand("team", new CommandTeam(this));
        registerCommand("verifysettings", new CommandVerifySettings(this));
    }

    public void finishStart() {
        Jarvis.getDatabase().execute("CREATE TABLE IF NOT EXISTS verify_data(" +
                "id int NOT NULL AUTO_INCREMENT," +
                "guild bigint(20) NOT NULL," +
                "channel bigint(20) NOT NULL," +
                "role bigint(20) NOT NULL," +
                "PRIMARY KEY (id));");

        ResultSet result = Jarvis.getDatabase().executeQuery("SELECT guild, channel, role FROM verify_data;");
        try {
            while (result.next()) {
                guildVerificationData.put(result.getLong("guild"), new GuildVerificationData(result.getLong("channel"), result.getLong("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }

    public void reload() {

    }

    public void setVerificationData(long guildId, long channelId, long roleId) {
        guildVerificationData.put(guildId, new GuildVerificationData(channelId, roleId));
        Jarvis.getDatabase().execute("INSERT INTO verify_data (guild, channel, role) VALUES (?, ?, ?);", guildId, channelId, roleId);
    }

    public boolean isDataSet(long guildId) {
        return guildVerificationData.containsKey(guildId);
    }

    public GuildVerificationData getVerificationData(long guildId) {
        return guildVerificationData.get(guildId);
    }

    public Long getVerificationChannel(long guildId) {
        return guildVerificationData.get(guildId).channelId;
    }

    public String getFromDB(String query) {
        try {
            return Unirest.get("https://api.vexdb.io/v1/" + query + "&season=current")
                    .header("User-Agent", "Jarvis github.com/9343/VexBot")
                    .asString()
                    .getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Awards getAwards(String team) {
        return gson.fromJson(getFromDB("get_awards?team=" + team), Awards.class);
    }

    public Teams getTeams(String team) {
        return gson.fromJson(getFromDB("get_teams?team=" + team), Teams.class);
    }
}
