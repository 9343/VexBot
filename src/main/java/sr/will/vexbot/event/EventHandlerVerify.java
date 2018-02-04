package sr.will.vexbot.event;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import sr.will.jarvis.command.Command;
import sr.will.jarvis.event.EventHandler;
import sr.will.jarvis.event.EventPriority;
import sr.will.vexbot.GuildVerificationData;
import sr.will.vexbot.VexBot;

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

        // TODO check for valid team

        String nickname = name + " | " + team;
        Role role = event.getGuild().getRoleById(verificationData.roleId);

        event.getGuild().getController().setNickname(event.getMember(), nickname).queue();
        event.getGuild().getController().addSingleRoleToMember(event.getMember(), role).queue();
        Command.sendSuccessEmote(event.getMessage());
    }
}
