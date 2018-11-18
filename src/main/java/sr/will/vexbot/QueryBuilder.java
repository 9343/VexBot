package sr.will.vexbot;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;

import static sr.will.jarvis.Jarvis.getLogger;

public class QueryBuilder {
    private HashMap<String, String> filters = new HashMap<>();

    private static final String apiUrl = "https://api.vexdb.io/v1/";

    private static Gson gson = new Gson();

    public QueryBuilder filter(String key, String value) {
        filters.put(key, value);
        return this;
    }

    public <T> T get(Class<T> typeClass) {
        return gson.fromJson(getFromDB(VexBot.methods.get(typeClass), filters), typeClass);
    }

    private String getFromDB(String query, HashMap<String, String> filters) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(apiUrl).append(query).append("?");

        filters.putIfAbsent("season", "current");

        for (String filter : filters.keySet()) {
            queryString.append(filter).append("=").append(filters.get(filter)).append("&");
        }

        queryString.deleteCharAt(queryString.length() - 1);

        getLogger().info(queryString.toString());

        try {
            return Unirest.get(queryString.toString())
                    .header("User-Agent", "Jarvis github.com/9343/VexBot")
                    .asString()
                    .getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
