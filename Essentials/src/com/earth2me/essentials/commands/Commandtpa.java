package com.earth2me.essentials.commands;

import com.earth2me.essentials.User;
import org.bukkit.Server;

import java.util.Collections;
import java.util.List;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import static com.earth2me.essentials.I18n.tl;


public class Commandtpa extends EssentialsCommand {
    public Commandtpa() {
        super("tpa");
    }

    @Override
    public void run(Server server, User user, String commandLabel, String[] args) throws Exception {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException();
        }

        User player = getPlayer(server, user, args, 0);
        if (user.getName().equalsIgnoreCase(player.getName())) {
            throw new NotEnoughArgumentsException();
        }
        if (!player.isTeleportEnabled()) {
            throw new Exception(tl("teleportDisabled", player.getDisplayName()));
        }
        if (user.getWorld() != player.getWorld() && ess.getSettings().isWorldTeleportPermissions() && !user.isAuthorized("essentials.worlds." + player.getWorld().getName())) {
            throw new Exception(tl("noPerm", "essentials.worlds." + player.getWorld().getName()));
        }
        // Don't let sender request teleport twice to the same player.
        if (user.getConfigUUID().equals(player.getTeleportRequest()) && player.hasOutstandingTeleportRequest() // Check timeout
            && !player.isTpRequestHere()) { // Make sure the last teleport request was actually tpa and not tpahere
            throw new Exception(tl("requestSentAlready", player.getDisplayName()));
        }

        if (!player.isIgnoredPlayer(user)) {
            player.requestTeleport(user, false);
            player.sendMessage(tl("teleportRequest", user.getDisplayName()));
            player.sendMessage(" ");
            TextComponent message0 = new TextComponent("    ");
            TextComponent message1 = new TextComponent(tl("typeTpaccept"));
            message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpyes" ) );
            message1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tl("typeTpaccept1")).create() ) );
            TextComponent message = new TextComponent(tl("typeTpdeny"));
            message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpno" ) );
            message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tl("typeTpdeny1")).create() ) );
            message0.addExtra(message1);
            message0.addExtra("       ");
            message0.addExtra(message);
            player.sendMessage(message0);
            player.sendMessage(" ");
            if (ess.getSettings().getTpaAcceptCancellation() != 0) {
                player.sendMessage(tl("teleportRequestTimeoutInfo", ess.getSettings().getTpaAcceptCancellation()));
            }
        }
        user.sendMessage(tl("requestSent", player.getDisplayName()));
        TextComponent msg = new TextComponent(tl("typeTpacancel"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpacancel"));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tl("typeTpacancel1")).create()));
        user.sendMessage(msg);
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, User user, String commandLabel, String[] args) {
        if (args.length == 1) {
            return getPlayers(server, user);
        } else {
            return Collections.emptyList();
        }
    }
}
