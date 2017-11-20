package fr.mega.rpchannels.Chat;

import fr.mega.rpchannels.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatListener extends Chat implements Listener {

    public ChatListener(Main main) {
        super(main);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        String rawmsg = e.getMessage();
        String prefix = getPrefix(rawmsg);

        JSONObject config = main.chatConfig;
        JSONArray channels = (JSONArray) config.get("channels");

        List<String> chanPrefix = new ArrayList<>();
        for(int i = 0; i < channels.size(); i++) {
            JSONObject o = (JSONObject) channels.get(i);
            chanPrefix.add((String) o.get("prefix"));
        }

        int index = chanPrefix.indexOf(prefix);
        if(index == -1 || prefix.equals("")) {
            index = 0;
        }
        JSONObject data = (JSONObject) channels.get(index);

        long range = (long) data.get("range");
        String format = (String) data.get("format");
        Boolean target = (Boolean) data.get("target");
        List<String> sources = (List<String>) data.get("sources");
        List<String> destinations = (List<String>) data.get("destinations");
        Boolean everyworld = (Boolean) data.get("everyworld");
        if(index > 0)
            rawmsg = rawmsg.substring(prefix.length()).trim();

        if(sources.contains(main.getPlayerGroup(p)) || sources.contains("*")) {
            e.getRecipients().remove(p);
            if(!target) {
                String msg = chatFormat(format, target, rawmsg, p);
                p.sendMessage(msg);
                for(Player player : e.getRecipients()) {
                    if(destinations.contains(main.getPlayerGroup(player)) || destinations.contains("*")) {
                        if(player.getWorld() == p.getWorld() || everyworld) {
                                if(range < 1)
                                    player.sendMessage(msg);
                                else if(player.getLocation().distance(p.getLocation()) <= range) {
                                    player.sendMessage(msg);
                                }
                        }
                    }
                }
                e.getRecipients().clear();
            } else {
                Player t = getTarget(rawmsg);
                String msg = chatFormat(format, target, rawmsg, p);
                p.sendMessage(msg);
                t.sendMessage(msg);
            }
        }
    }
}
