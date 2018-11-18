package sr.will.vexbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import sr.will.jarvis.command.Command;
import sr.will.vexbot.QueryBuilder;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Awards;

import java.awt.*;
import java.util.regex.Matcher;

public class CommandAwards extends Command {
    private VexBot module;

    public CommandAwards(VexBot module) {
        super("awards", "awards <team>", "Get awards for a team", module);
        this.module = module;
    }

    @Override
    public void execute(Message message, String... args) {
        checkModuleEnabled(message, module);

        long startTime = System.currentTimeMillis();

        if (args.length == 0) {
            sendFailureMessage(message, "Team not specified");
            return;
        }

        Matcher matcher = VexBot.teamPattern.matcher(args[0]);
        if (!matcher.matches()) {
            sendFailureMessage(message, "Invalid team number");
            return;
        }

        Awards awards = new QueryBuilder().filter("team", matcher.group()).get(Awards.class);
        if (awards == null || awards.status != 1) {
            sendFailureMessage(message, "An error occurred");
            return;
        }

        if (awards.size == 0) {
            sendFailureMessage(message, "No awards found");
            return;
        }

        StringBuilder result = new StringBuilder();
        String event = "";
        String team = null;
        for (Awards.Award award : awards.result) {
            if (team == null) {
                team = award.team;
            }

            if (award.sku.equals(event)) {
                event = award.sku;
                result.append("\n**").append(award.sku).append("**");
            }

            result.append("\n:trophy:").append(award.name);
        }

        message.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setAuthor(team, "https://vexdb.io/teams/view/" + team)
                .setDescription(result.toString())
                .setFooter("Returned in " + (System.currentTimeMillis() - startTime) + "ms", null)
                .build()).queue();
    }
}
