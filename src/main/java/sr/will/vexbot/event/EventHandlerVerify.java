package sr.will.vexbot.event;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import sr.will.jarvis.command.Command;
import sr.will.jarvis.event.EventHandler;
import sr.will.jarvis.event.EventPriority;
import sr.will.vexbot.GuildVerificationData;
import sr.will.vexbot.VexBot;
import sr.will.vexbot.rest.vexdb.Teams;

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
        if (!module.isEnabled(event.getGuild().getIdLong())) {
            return;
        }

        if (!module.isDataSet(event.getGuild().getIdLong())) {
            return;
        }

        GuildVerificationData verificationData = module.getVerificationData(event.getGuild().getIdLong());

        if (verificationData.channelId != event.getChannel().getIdLong()) {
            return;
        }

        if (!event.getMessage().getContentDisplay().contains("|")) {
            return;
        }

        String name = event.getMessage().getContentDisplay().split("\\|")[0].trim();
        String team = event.getMessage().getContentDisplay().split("\\|")[1].trim();

        Teams teams = module.getTeams(team);
        if (teams.result.size() == 0) {
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
