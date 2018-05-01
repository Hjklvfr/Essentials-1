package com.earth2me.essentials.commands;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.User;
import net.ess3.api.events.GlowedStatusChangeEvent;

import static com.earth2me.essentials.I18n.tl;

import net.ess3.api.events.VanishStatusChangeEvent;


public class Commandglow extends EssentialsToggleCommand {
    public Commandglow() {
        super("glow", "essentials.glow.others");
    }

    @Override
    protected void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        toggleOtherPlayers(server, sender, args);
    }

    @Override
    protected void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        handleToggleWithArgs(server, user, args);
    }

    @Override
    void togglePlayer(CommandSource sender, User user, Boolean enabled) throws NotEnoughArgumentsException {
        if (enabled == null) {
            enabled = !user.isGlowed();
        }

        final User controller = sender.isPlayer() ? ess.getUser(sender.getPlayer()) : null;
        GlowedStatusChangeEvent glowEvent = new GlowedStatusChangeEvent(controller, user, enabled);
        ess.getServer().getPluginManager().callEvent(glowEvent);
        if (glowEvent.isCancelled()) {
            return;
        }

        user.setGlowed(enabled);
        user.sendMessage(tl("glow", user.getDisplayName(), enabled ? tl("enabled") : tl("disabled")));

        if (enabled) {
            user.sendMessage(tl("glowed"));
        }
        if (!sender.isPlayer() || !sender.getPlayer().equals(user.getBase())) {
            sender.sendMessage(tl("glow", user.getDisplayName(), enabled ? tl("enabled") : tl("disabled")));
        }
    }
}