package sr.will.vexbot.command;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.core.entities.Message;
import sr.will.jarvis.command.Command;
import sr.will.vexbot.VexBot;

public class CommandAnnounce extends Command {
    private VexBot module;

    public CommandAnnounce(VexBot module) {
        super("announce", "announce <webhook> <message>", "Send the specified message to the specified webhook", module);
        this.module = module;
    }

    @Override
    public void execute(Message message, String... args) {
        checkModuleEnabled(message, module);

        if (args.length < 2) {
            sendUsage(message);
            return;
        }

        String content = message.getContentRaw();
        content = content.substring(content.indexOf(args[0]) + args[0].length(), content.length());
        content = "{\"content\":\"" + content + "\"}";

        try {
            String string = Unirest.post(args[0]).header("Content-Type", "application/json").body(content).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            sendFailureMessage(message, "Something went wrong");
            return;
        }

        sendSuccessEmote(message);
    }
}
