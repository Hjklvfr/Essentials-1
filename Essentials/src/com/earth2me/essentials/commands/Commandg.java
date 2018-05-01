package com.earth2me.essentials.commands;

import com.earth2me.essentials.CommandSource;
import static com.earth2me.essentials.I18n.tl;
import com.earth2me.essentials.User;
import java.util.Locale;
import org.bukkit.GameMode;
import org.bukkit.Server;


public class Commandg extends EssentialsCommand
{

    public Commandg()
    {
        super("g");
    }

    @Override
    protected void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception
    {
        GameMode gameMode;
        switch (args.length)
        {
            case 0:
                throw new NotEnoughArgumentsException();
            case 1:
                gameMode = matchGameMode(commandLabel);
                break;
            case 2:
                gameMode = matchGameMode(args[0].toLowerCase(Locale.ENGLISH));
                break;
            default:
                break;
        }

    }

    @Override
    protected void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception
    {
        GameMode gameMode;
        if (args.length == 0 && user.isAuthorized("essentials.g"))
        {
            gameMode = matchGameMode(commandLabel);
        }
        else if (args.length > 1 && args[1].trim().length() > 2 && user.isAuthorized("essentials.g"))
        {
            gameMode = matchGameMode(args[0].toLowerCase(Locale.ENGLISH));
            return;
        }
        else
        {
            try
            {
                gameMode = matchGameMode(args[0].toLowerCase(Locale.ENGLISH));
            }
            catch (NotEnoughArgumentsException e)
            {
                if (user.isAuthorized("essentials.g"))
                {
                    gameMode = matchGameMode(commandLabel);
                    return;
                }
                throw new NotEnoughArgumentsException();
            }
        }
        if (gameMode == null)
        {
            gameMode = user.getBase().getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : user.getBase().getGameMode() == GameMode.CREATIVE ? GameMode.ADVENTURE : GameMode.SURVIVAL;
        }
        user.getBase().setGameMode(gameMode);
        user.sendMessage(tl("gameMode", tl(user.getBase().getGameMode().toString().toLowerCase(Locale.ENGLISH)), user.getDisplayName()));
    }

    private GameMode matchGameMode(String modeString) throws NotEnoughArgumentsException
    {
        GameMode mode;
        if (modeString.equalsIgnoreCase("gmc") || modeString.equalsIgnoreCase("creative")
                || modeString.contains("creat") || modeString.equalsIgnoreCase("1") || modeString.equalsIgnoreCase("c"))
        {
            mode = GameMode.CREATIVE;
        }
        else if (modeString.equalsIgnoreCase("gms") || modeString.equalsIgnoreCase("survival")
                || modeString.contains("survi") || modeString.equalsIgnoreCase("0") || modeString.equalsIgnoreCase("s"))
        {
            mode = GameMode.SURVIVAL;
        }
        else if (modeString.equalsIgnoreCase("gma") || modeString.equalsIgnoreCase("adventure")
                || modeString.contains("advent") || modeString.equalsIgnoreCase("2") || modeString.equalsIgnoreCase("a"))
        {
            mode = GameMode.ADVENTURE;
        }
        else if (modeString.equalsIgnoreCase("gmt") || modeString.contains("toggle") || modeString.contains("cycle")
                || modeString.equalsIgnoreCase("t"))
        {
            mode = null;
        }
        else
        {
            throw new NotEnoughArgumentsException();
        }
        return mode;
    }
}
