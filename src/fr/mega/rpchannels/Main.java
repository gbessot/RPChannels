package fr.mega.rpchannels;

import fr.mega.rpchannels.Chat.Chat;
import fr.mega.rpchannels.Chat.ChatCommand;
import fr.mega.rpchannels.Chat.ChatListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JavaPlugin {

    public JSONObject chatConfig;
    public Chat chat;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getCommand("group").setExecutor(new ChatCommand(this));

        chat = new Chat(this);

        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        JSONParser parser = new JSONParser();

        File chatFile = new File(getDataFolder(), "chat.json");
        if(!chatFile.exists()) {
            try {
                chatFile.createNewFile();
                JSONObject o = new JSONObject();
                JSONArray channels = new JSONArray();
                o.put("channels", channels);

                FileWriter fChat = new FileWriter(getDataFolder()+File.separator+"chat.json");
                fChat.write(o.toJSONString());
                fChat.close();

                chatConfig = (JSONObject) parser.parse(new FileReader(getDataFolder()+File.separator+"chat.json"));
                chat.addRule("default", 20, "%a &f<&7says&f> &b%m", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("*", 20, "* %a &b%m *", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("#", 3, "%a &f<&7whispers&f> &2%m", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("#*", 3, "* %a &2%m *", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("!", 50, "%a &f<&7yells&f> &c%m", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("!*", 50, "* %a &c%m *", Arrays.asList("*"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("?", -1, "%a &f<&7asks&f> &5%m", Arrays.asList("*"), Arrays.asList("admin"), Boolean.FALSE, Boolean.TRUE);
                chat.addRule("=", 50, "** &b%m **", Arrays.asList("admin"), Arrays.asList("*"), Boolean.FALSE, Boolean.FALSE);
                chat.addRule("?>", -1, "%a &7-> &7%t &f<&7answers&f> &5%m", Arrays.asList("admin"), Arrays.asList("*"), Boolean.TRUE, Boolean.TRUE);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            chatConfig = (JSONObject) parser.parse(new FileReader(getDataFolder()+File.separator+"chat.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[RPChannels] Enabled.");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[RPChannels] Disabled.");
    }

    public String getPlayerGroup(Player player) {
        return getConfig().getString(player.getName());
    }

    public void setPlayerGroup(String pName, String group) {
        getConfig().set(pName, group);
        saveConfig();
    }

}
