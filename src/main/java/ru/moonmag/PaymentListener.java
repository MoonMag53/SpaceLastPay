package ru.moonmag;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PaymentListener implements Listener {

    @EventHandler
    public void onPlayerPay(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length < 3) return;

        if (args[0].equalsIgnoreCase("/pay")) {
            Player sender = event.getPlayer();
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                try {
                    double amount = Double.parseDouble(args[2]);

                    Economy economy = SpaceLastPay.getEconomy();
                    if (economy != null) {
                        SpaceLastPay.setLastPayment(sender.getUniqueId(), amount);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }
}