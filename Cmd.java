package Party;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Cmd extends Command
{
  public static String prefix = "§6[Party] ";

  public Cmd()
  {
    super("party");
  }
  public void execute(CommandSender cs, String[] args)
  {
    if ((cs instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)cs;
      if (args.length < 1)
      {
    	  p.sendMessage(new ComponentBuilder("#################").color(ChatColor.GOLD).append("Party").color(ChatColor.DARK_GRAY).append("#################").color(ChatColor.GOLD).create());
          p.sendMessage(new ComponentBuilder("/party list").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Zeigt die Spieler in deiner Party an.").color(ChatColor.AQUA).create());
          p.sendMessage(new ComponentBuilder("/party leave").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Verlassen/Löschen der Party.").color(ChatColor.AQUA).create());
          p.sendMessage(new ComponentBuilder("/party chat [msg]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Schreib mit den Partymitgliedern.").color(ChatColor.AQUA).create());
          p.sendMessage(new ComponentBuilder("/party invite [spielername]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Lade ein Spieler ein.").color(ChatColor.AQUA).create());
          p.sendMessage(new ComponentBuilder("/party accept").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Einladung annehmen.").color(ChatColor.AQUA).create());
          p.sendMessage(new ComponentBuilder("/party kick [spielername]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Kick einen Spieler aus der Party.").color(ChatColor.AQUA).create());
      }
      else if (args[0].equalsIgnoreCase("create"))
      {
        Party.neueParty(p);
      }
      else if (args[0].equalsIgnoreCase("list"))
      {
        Party.List(p);
      }
      else if (args[0].equalsIgnoreCase("leave"))
      {
        Party.leave(p);
      }
      else if (args[0].equalsIgnoreCase("chat"))
      {
        String msg = "";
        for (int i = 1; i < args.length; i++) {
          msg = msg + args[i] + " ";
        }
        Party.chat(p, msg);
      }
      else if (args[0].equalsIgnoreCase("invite"))
      {
        if (args.length > 1)
        {
          for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
            if (!x.getName().equalsIgnoreCase(args[1]))
              continue;
            ProxiedPlayer z = ProxyServer.getInstance().getPlayer(args[1]);
            Party.invite(p, z);

            return;
          }

          p.sendMessage(new ComponentBuilder(prefix).append(ChatColor.AQUA + "Der" + ChatColor.YELLOW + " Spieler ").color(ChatColor.RED).append(args[1]).color(ChatColor.GREEN).append(ChatColor.AQUA + " ist nicht "+ ChatColor.GREEN + " online"+ ChatColor.AQUA + ".").color(ChatColor.RED).create());
        }
        else
        {
          p.sendMessage(new ComponentBuilder(prefix).append("Zuviele Argumente").color(ChatColor.DARK_RED).create());
          p.sendMessage(new ComponentBuilder("Befehl:").color(ChatColor.RED).append("/party invite <spielername>").color(ChatColor.GREEN).create());
        }
      }
      else if (args[0].equalsIgnoreCase("accept"))
      {
        Party.accept(p);
      }
      else if (args[0].equalsIgnoreCase("kick"))
      {
        if (args.length > 1)
        {
          for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
            if (!x.getName().equalsIgnoreCase(args[1]))
              continue;
            Party.kick(p, x);

            return;
          }

          p.sendMessage(new ComponentBuilder(ChatColor.AQUA + "Der "+ ChatColor.YELLOW + "Spieler" + ChatColor.AQUA + " ist nicht " + ChatColor.GREEN + "online"+ ChatColor.AQUA + ".").color(ChatColor.DARK_RED).create());
        }
        else
        {
          p.sendMessage(new ComponentBuilder("Schreib /party kick <spielername>").color(ChatColor.DARK_RED).create());
        }
      }
      else if (args[0].equalsIgnoreCase("help"))
      {
        p.sendMessage(new ComponentBuilder("#################").color(ChatColor.GOLD).append("Party").color(ChatColor.DARK_GRAY).append("#################").color(ChatColor.GOLD).create());
        p.sendMessage(new ComponentBuilder("/party list").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Zeigt die Spieler in deiner Party an.").color(ChatColor.AQUA).create());
        p.sendMessage(new ComponentBuilder("/party leave").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Verlassen/Löschen der Party.").color(ChatColor.AQUA).create());
        p.sendMessage(new ComponentBuilder("/party chat [msg]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Schreib mit den Partymitgliedern.").color(ChatColor.AQUA).create());
        p.sendMessage(new ComponentBuilder("/party invite [spielername]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Lade ein Spieler ein.").color(ChatColor.AQUA).create());
        p.sendMessage(new ComponentBuilder("/party accept").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Einladung annehmen.").color(ChatColor.AQUA).create());
        p.sendMessage(new ComponentBuilder("/party kick [spielername]").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Kick einen Spieler aus der Party.").color(ChatColor.AQUA).create());
      }
    }
  }
}
