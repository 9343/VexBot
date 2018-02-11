package sr.will.vexbot.event;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import sr.will.jarvis.Jarvis;
import sr.will.jarvis.command.Command;
import sr.will.jarvis.event.EventHandler;
import sr.will.jarvis.event.EventPriority;
import sr.will.vexbot.GuildVerificationData;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.v1.Teams;

import java.util.regex.Matcher;

public class EventHandlerVerify extends EventHandler {
    private VexBot module;

    public EventHandlerVerify(VexBot module) {
        super(module, EventPriority.MEDIUM);
        this.module = module;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MessageReceivedEvent) {
            onMessageReceived((MessageReceivedEvent) event);
        }
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() == Jarvis.getJda().getSelfUser().getIdLong()) {
            return;
        }

        if (!module.isEnabled(event.getGuild().getIdLong()) || !module.isDataSet(event.getGuild().getIdLong())) {
            return;
        }

        GuildVerificationData verificationData = module.getVerificationData(event.getGuild().getIdLong());

        if (verificationData.channelId != event.getChannel().getIdLong()) {
            return;
        }

        Matcher matcher = VexBot.validationPattern.matcher(event.getMessage().getContentDisplay());

        if (!matcher.matches()) {
            return;
        }

        String name = matcher.group(1);
        String team = matcher.group(2);

        Teams teams = module.getTeams(team);
        if (teams.size == 0) {
            Command.sendFailureMessage(event.getMessage(), "Invalid team");
            return;
        }

        team = teams.result.get(0).number;

        String nickname = name + " | " + team;
        Role role = event.getGuild().getRoleById(verificationData.roleId);

        event.getGuild().getController().setNickname(event.getMember(), nickname).queue();
        event.getGuild().getController().addSingleRoleToMember(event.getMember(), role).queue();
        Command.sendSuccessEmote(event.getMessage());
    }
}
