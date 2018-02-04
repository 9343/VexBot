package sr.will.vexbot;

public class GuildVerificationData {
    public long channelId;
    public long roleId;

    public GuildVerificationData(long channelId, long roleId) {
        this.channelId = channelId;
        this.roleId = roleId;
    }
}
