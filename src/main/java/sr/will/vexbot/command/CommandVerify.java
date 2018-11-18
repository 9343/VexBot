package sr.will.vexbot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import sr.will.jarvis.command.Command;
import sr.will.vexbot.GuildVerificationData;
import sr.will.vexbot.QueryBuilder;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Teams;

import java.util.regex.Matcher;

public class CommandVerify extends Command {
    private VexBot module;

    public CommandVerify(VexBot module) {
        super("verify", "!verify <name> <team>", "Verify name and Vex team", module);
        this.module = module;
    }

    @Override
    public void execute(Message message, String... args) {
        checkModuleEnabled(message, module);

        if (args.length < 2) {
            sendUsage(message);
            return;
        }

        GuildVerificationData verificationData = module.getVerificationData(message.getGuild().getIdLong());

        if (verificationData.channelId != message.getChannel().getIdLong()) {
            return;
        }

        Matcher matcher = VexBot.validationPattern.matcher(message.getContentDisplay());

        if (!matcher.matches()) {
            return;
        }

        String name = matcher.group(1);
        String team = matcher.group(2);

        Teams teams = new QueryBuilder().filter("team", team).get(Teams.class);
        if (teams.size == 0) {
            sendFailureMessage(message, "Invalid team");
            return;
        }

        team = teams.result.get(0).number;

        String nickname = name + " | " + team;
        Role role = message.getGuild().getRoleById(verificationData.roleId);

        message.getGuild().getController().setNickname(message.getMember(), nickname).queue();
        message.getGuild().getController().addSingleRoleToMember(message.getMember(), role).queue();
        sendSuccessEmote(message);
    }
}
