package net.ess3.api.events;

import net.ess3.api.IUser;

/**
 * Called only in Commandglowed. For other events please use classes such as PlayerJoinEvent and eventually {@link IUser#isVanished()}.
 */
public class GlowedStatusChangeEvent extends StatusChangeEvent {
    public GlowedStatusChangeEvent(IUser affected, IUser controller, boolean value) {
        super(affected, controller, value);
    }
}