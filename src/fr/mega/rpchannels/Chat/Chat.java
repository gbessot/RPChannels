package fr.mega.rpchannels.Chat;

import fr.mega.rpchannels.Main;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Chat {

    protected Main main;

    public Chat(Main main) {
        this.main = main;
    }

    public void addRule(String prefix, int range, String format, List<String> perm, List<String> isOnlyFor, Boolean target, Boolean everyworld) {
        JSONObject c = main.chatConfig;
        JSONObject o = new JSONObject();
        JSONArray channels = (JSONArray) c.get("channels");

        JSONObject rule = new JSONObject();
        rule.put("prefix", prefix);
        rule.put("range", range);
        rule.put("format", format);
        rule.put("target", target);
        rule.put("everyworld", everyworld);

        JSONArray permission = new JSONArray();
        for(String p : perm) {
            permission.add(p);
        }
        rule.put("sources", permission);

        JSONArray viewer = new JSONArray();
        for(String v : isOnlyFor) {
            viewer.add(v);
        }
        rule.put("destinations", viewer);

        channels.add(rule);
        o.put("channels",channels);

        FileWriter fChat = null;
        try {
            fChat = new FileWriter(main.getDataFolder()+ File.separator+"chat.json");
            fChat.write(o.toJSONString());
            fChat.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Player getTarget(String msg) {
        Player p;
        String target;
        int end = msg.indexOf(' ');
        target = msg.substring(0, end);
        p = main.getServer().getPlayer(target);
        return p;
    }

    protected String getPrefix(String rawmsg) {
        String prefix = "";
        int i = 0;
        while(!Character.isLetterOrDigit(rawmsg.charAt(i)) && rawmsg.charAt(i) != ' ' && rawmsg.charAt(i) != '.') {
            prefix += rawmsg.charAt(i);
            i++;
        }
        return prefix;
    }

    protected String chatFormat(String format, Boolean target, String rawmsg, Player player) {
        String msg;
        Player t;
        msg = format.replace("%a", player.getDisplayName());
        msg = msg.replace("&", "§");
        if(target) {
            t = getTarget(rawmsg);
            rawmsg = rawmsg.substring(t.getName().length()).trim();
            msg = msg.replace("%m", rawmsg);
            msg = msg.replace("%t", t.getDisplayName());
        } else {
            msg = msg.replace("%m", rawmsg);
        }
        return msg;
    }
}
