package sr.will.vexbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import sr.will.jarvis.command.Command;
import sr.will.vexbot.QueryBuilder;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Teams;

import java.awt.*;
import java.util.regex.Matcher;

public class CommandTeam extends Command {
    private VexBot module;

    public CommandTeam(VexBot module) {
        super("team", "team <team>", "Get info for a team", module);
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

        Teams teams = new QueryBuilder().filter("team", matcher.group()).get(Teams.class);
        if (teams == null || teams.status != 1) {
            sendFailureMessage(message, "An error occurred");
            return;
        }

        if (teams.size == 0) {
            sendFailureMessage(message, "Team does not exist");
            return;
        }

        Teams.Team team = teams.result.get(0);

        message.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setAuthor(team.number, "https://vexdb.io/teams/view/" + team.number)
                .addField("Team Name", team.team_name, true)
                .addField("Organization", team.organisation, true)
                .addField("Location", team.city + ", " + team.region + ", " + team.country, true)
                .addField("Grade", team.grade, true)
                .addField("Program", team.program, true)
                .setFooter("Returned in " + (System.currentTimeMillis() - startTime) + "ms", null)
                .build()).queue();
    }
}
