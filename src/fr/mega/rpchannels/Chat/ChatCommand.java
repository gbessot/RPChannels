package fr.mega.rpchannels.Chat;

import fr.mega.rpchannels.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand extends Chat implements CommandExecutor {

    public ChatCommand(Main main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 2) {
            if(sender instanceof Player) {
                if (sender.isOp()) {
                    main.setPlayerGroup(args[0], args[1]);
                    sender.sendMessage(ChatColor.GREEN + args[0] + " have been added to " + args[1]);
                }
            } else {
                main.setPlayerGroup(args[0], args[1]);
                main.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[RPChannels] " + args[0] + " have been added to " + args[1]);
            }
        } else {
            if(sender instanceof Player) {
                if (sender.isOp())
                    sender.sendMessage(ChatColor.RED + "Syntax : /group <Player> <Group>");
            } else
                main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[RPChannels] " + "Syntax : group <Player> <Group>");
        }
        return false;
    }
}
