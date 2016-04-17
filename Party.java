package Party;

import java.util.ArrayList;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party
{
  public static ArrayList<String> partyführer = new ArrayList<String>();
  public static HashMap<String, String> inparty = new HashMap<String, String>();
  public static HashMap<String, Long> invitetime = new HashMap<String, Long>();
  public static HashMap<String, String> invite = new HashMap<String, String>();
  static String pr = Cmd.prefix;

  public static void neueParty(ProxiedPlayer p)
  {
    if (inparty.containsKey(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du bist bereits in einer" + ChatColor.GREEN + " Party" + ChatColor.AQUA + ".").color(ChatColor.DARK_RED).create());
      return;
    }
    if (partyführer.contains(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du bist bereits in einer" + ChatColor.GREEN + " Party" + ChatColor.AQUA + ".").color(ChatColor.DARK_RED).create());
      return;
    }
    partyführer.add(p.getName());
    p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du hast erfolgreich eine "+ ChatColor.GREEN + "Party " + ChatColor.AQUA + "erstellt.").create());
  }

  public static void List(ProxiedPlayer p)
  {
    if (partyführer.contains(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Partyowner: ").color(ChatColor.AQUA).append(ChatColor.RED + p.getName()+ ChatColor.GRAY + " | "+ ChatColor.GREEN + "Server: ").color(ChatColor.RED).append(p.getServer().getInfo().getName()).color(ChatColor.AQUA).create());
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.GREEN + "Player:").color(ChatColor.BLUE).create());
      for (ProxiedPlayer y : ProxyServer.getInstance().getPlayers()) {
        if ((inparty.containsKey(y.getName())) && (inparty.get(y.getName()) == p.getName())) {
          p.sendMessage(new ComponentBuilder(pr).append(y.getName() + ChatColor.GRAY + " | "+ ChatColor.GREEN + "Server: ").color(ChatColor.AQUA).append(y.getServer().getInfo().getName()).color(ChatColor.YELLOW).create());
        }
      }
    }
    if (inparty.containsKey(p.getName())) 
    {
    	try
        {
          p.sendMessage(new ComponentBuilder(pr).append("Partyowner: ").color(ChatColor.AQUA).append(ChatColor.RED + "").append((String)inparty.get(p.getName())).append(ChatColor.GRAY + " | " + ChatColor.GREEN + " Server: ").append(ProxyServer.getInstance().getPlayer((String)inparty.get(p.getName())).getServer().getInfo().getName()).color(ChatColor.YELLOW).create());
        } catch (NullPointerException localNullPointerException) {
        }
        for (ProxiedPlayer y : ProxyServer.getInstance().getPlayers()) {
          if ((inparty.containsKey(y.getName())) && (inparty.get(y.getName()) == inparty.get(p.getName()))) {
            p.sendMessage(new ComponentBuilder(pr).append(y.getName()).color(ChatColor.AQUA).append(ChatColor.GRAY + " | "+ ChatColor.GREEN + "Server: ").append(y.getServer().getInfo().getName()).color(ChatColor.YELLOW).create());
          }
        }
    }
    if ((!inparty.containsKey(p.getName())) && (!partyführer.contains(p.getName())))
      p.sendMessage(new ComponentBuilder(pr).append("Du bist in keiner"+ ChatColor.GREEN + " Party" + ChatColor.RED +"!").color(ChatColor.DARK_RED).create());
  }

  public static void leave(ProxiedPlayer p)
  {
    if (inparty.containsKey(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append("Du hast die "+ ChatColor.GREEN + "Party "+ ChatColor.AQUA + "verlassen.").color(ChatColor.AQUA).create());
      for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
        if (inparty.get(x.getName()) == inparty.get(p.getName())) {
          x.sendMessage(new ComponentBuilder(pr).append("Der Spieler ").color(ChatColor.RED).append(p.getName()).color(ChatColor.AQUA).append(" hat die "+ChatColor.GREEN + "Party " + ChatColor.AQUA + "verlassen.").color(ChatColor.RED).create());
        }
      }
      inparty.remove(p.getName());
      return;
    }
    if (partyführer.contains(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.GREEN + "Party "+ChatColor.AQUA + "aufgelöst!").create());
      partyführer.remove(p.getName());
      for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
        if ((!inparty.containsKey(x.getName())) || 
          (inparty.get(x.getName()) != p.getName()))
          continue;
        x.sendMessage(new ComponentBuilder(pr).append("Der " + ChatColor.RED + "PartyOwner " + ChatColor.AQUA + "hat die "+ChatColor
        		.GREEN + "Party"+ ChatColor.AQUA + " aufgelöst.").create());
        inparty.remove(x.getName());
      }

      return;
    }
    if ((!inparty.containsKey(p.getName())) && (!partyführer.contains(p.getName())))
      p.sendMessage(new ComponentBuilder(pr).append("Du bist in keiner "+ ChatColor.GREEN + "Party" + ChatColor.RED + ".").color(ChatColor.RED).create());
  }

  public static void chat(ProxiedPlayer p, String text)
  {
    text = ChatColor.translateAlternateColorCodes('&', text);
    if (partyführer.contains(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.AQUA).append(" : ").color(ChatColor.WHITE).append(text).create());
      for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
        if ((!inparty.containsKey(x.getName())) || 
          (inparty.get(x.getName()) != p.getName())) continue;
        x.sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.AQUA).append(" : ").color(ChatColor.WHITE).append(text).create());
      }
    }

    if (inparty.containsKey(p.getName()))
    {
      ProxyServer.getInstance().getPlayer((String)inparty.get(p.getName())).sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.AQUA).append(" : ").color(ChatColor.WHITE).append(text).create());
      for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
        if ((!inparty.containsKey(x.getName())) || 
          (inparty.get(x.getName()) != inparty.get(p.getName()))) continue;
        x.sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.AQUA).append(" : ").color(ChatColor.WHITE).append(text).create());
      }
    }

    if ((!inparty.containsKey(p.getName())) && (!partyführer.contains(p.getName())))
      p.sendMessage(new ComponentBuilder(pr).append("Du bist in keiner "+ ChatColor.GREEN + "Party" + ChatColor.RED + ".").color(ChatColor.RED).create());
  }

  public static void invite(ProxiedPlayer p, ProxiedPlayer z)
  {
    long aktuell = System.currentTimeMillis();
    if (partyführer.contains(p.getName()))
    {
      Integer iparty = Integer.valueOf(0);
      for (ProxiedPlayer i : ProxyServer.getInstance().getPlayers()) {
        if ((!inparty.containsKey(i.getName())) || 
          (inparty.get(i.getName()) != p.getName())) continue;
        iparty = Integer.valueOf(iparty.intValue() + 1);
      }

      if (iparty.intValue() > 10)
      {
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Deine " + ChatColor.GREEN + "Party " + ChatColor.AQUA + "ist voll!").color(ChatColor.RED).create());
        return;
      }
      if (inparty.containsKey(z.getName()))
      {
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Der "+ ChatColor.YELLOW + "Spieler"+ChatColor.AQUA + " ist bereits in deiner " + ChatColor.GREEN + "Party" + ChatColor.AQUA + ".").color(ChatColor.RED).create());
        return;
      }
      if (partyführer.contains(z.getName()))
      {
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Der " + ChatColor.YELLOW + "Spieler"+ ChatColor.AQUA + " hat bereits eine" + ChatColor.GREEN + " Party" + ChatColor.AQUA + ".").color(ChatColor.RED).create());
        return;
      }
      if ((!inparty.containsKey(z.getName())) && (!partyführer.contains(z.getName())))
      {
        invite.put(z.getName(), p.getName());
        invitetime.put(z.getName(), Long.valueOf(aktuell));
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du hast den" + ChatColor.GREEN + " Spieler" + ChatColor.YELLOW + "´").color(ChatColor.GREEN).append(z.getName()+ "´"  + ChatColor.AQUA + " in deine "+ChatColor.GREEN + "Party "+ ChatColor.AQUA + "eingeladen.").color(ChatColor.YELLOW).create());
        z.sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.RED).append(" hat dich in seine "+ ChatColor.GREEN + "Party "+ ChatColor.AQUA + "eingeladen.").color(ChatColor.AQUA).create());
        z.sendMessage(new ComponentBuilder(pr).append(ChatColor.RED + "/party accept" + ChatColor.AQUA + " um die "+ ChatColor.GREEN + "Partyeinladung"+ChatColor.AQUA + " anzunehmen!").color(ChatColor.RED).create());     
      }
    }
    else if (inparty.containsKey(p.getName()))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du bist nicht der "+ ChatColor.RED + "PartyOwner"+ ChatColor.AQUA + ".").color(ChatColor.RED).create());
    }
    else if ((!inparty.containsKey(p.getName())) && (!partyführer.contains(p.getName())))
    {
      neueParty(p);
      invite(p, z);
    }
  }

  public static void accept(ProxiedPlayer p)
  {
    if ((partyführer.contains(p.getName()) | inparty.containsKey(p.getName())))
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du bist bereits in einer "+ ChatColor.GREEN + "Party"+ChatColor.AQUA + ".").color(ChatColor.RED).create());
    }
    else if (invite.containsKey(p.getName()))
    {
      Long aktuell = Long.valueOf(System.currentTimeMillis());
      Long diff = Long.valueOf(aktuell.longValue() / 1000L - ((Long)invitetime.get(p.getName())).longValue() / 1000L);
      if (diff.longValue() > 60L)
      {
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.GREEN + "Partyeinladung "+ChatColor.AQUA + "ist ausgelaufen.").color(ChatColor.RED).create());
//        p.sendMessage("diff:" + diff);
//        p.sendMessage("current" + aktuell.longValue() / 1000L);
      }
      else
      {
        ProxiedPlayer Leiter = ProxyServer.getInstance().getPlayer((String)invite.get(p.getName()));
        Leiter.sendMessage(new ComponentBuilder(pr).append(p.getName()).color(ChatColor.AQUA).append(ChatColor.AQUA + " ist der "+ChatColor.GREEN + "Party "+ChatColor.AQUA + "beigetreten.").color(ChatColor.YELLOW).create());
        invite.remove(p.getName());
        invitetime.remove(p.getName());
        inparty.put(p.getName(), Leiter.getName());
        p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du bist ").color(ChatColor.YELLOW).append(Leiter.getName()).append(ChatColor.AQUA + "'s "+ChatColor.GREEN + "Party " + ChatColor.AQUA + "beigetreten.").create());
      }
    }
    else
    {
      p.sendMessage(new ComponentBuilder(pr).append(ChatColor.AQUA + "Du hast keine offene "+ ChatColor.GREEN + "Partyeinladung" + ChatColor.AQUA +".").color(ChatColor.RED).create());
    }
  }

  public static void kick(ProxiedPlayer pl, ProxiedPlayer p)
  {
    if (partyführer.contains(pl.getName()))
    {
      if ((inparty.containsKey(p.getName())) && (inparty.get(p.getName()) == pl.getName()))
      {
        inparty.remove(p.getName());
        p.sendMessage(new ComponentBuilder(ChatColor.AQUA + "Du wurdest aus der " + ChatColor.GREEN + "Party " + ChatColor.AQUA  + "gekickt von ").color(ChatColor.GOLD).append(pl.getName()).color(ChatColor.RED).create());
        pl.sendMessage(new ComponentBuilder(pr).append(ChatColor.RED + "Du hast ").color(ChatColor.GOLD).append(p.getName()).color(ChatColor.AQUA).append(ChatColor.RED + " aus der " + ChatColor.GREEN + "Party" + ChatColor.RED + "gekickt.").color(ChatColor.GOLD).create());
        for (ProxiedPlayer ip : ProxyServer.getInstance().getPlayers()) {
          if ((inparty.containsKey(ip.getName())) && (inparty.get(ip.getName()) == pl.getName())) {
            ip.sendMessage(new ComponentBuilder(p.getName()).color(ChatColor.AQUA).append(ChatColor.AQUA + "wurde aus der " + ChatColor.GREEN + "Party " + ChatColor.AQUA + "gekickt von ").color(ChatColor.GOLD).append(pl.getName() + ChatColor.AQUA +".").color(ChatColor.RED).create());
          }
        }
      }
      else
      {
        pl.sendMessage(new ComponentBuilder(ChatColor.AQUA + "Dieser " + ChatColor.YELLOW + "Spieler "+ ChatColor.AQUA + "befindet sich nicht in deiner " + ChatColor.GREEN + "Party" + ChatColor.AQUA + ".").color(ChatColor.RED).create());
      }
    }
    else
      pl.sendMessage(new ComponentBuilder(ChatColor.AQUA + "Du bist in keiner " + ChatColor.GREEN + "Party "+ ChatColor.AQUA + ".").color(ChatColor.RED).create());
  }
}
