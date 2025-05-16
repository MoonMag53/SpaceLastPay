package ru.moonmag;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

public class SpaceLastPay extends JavaPlugin {

    private static Economy economy;
    private static final HashMap<UUID, Double> lastPayments = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fЗапуск плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§f§1§7Плагин запустился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
        if (!setupEconomy()) {
            getLogger().severe("Vault не найден! Отключение плагина.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new PaymentListener(), this);
        new LastPayPlaceholder().register();
        new ru.moonmag.Metrics(this, 25599);
        new UpdateChecker(this).checkForUpdates();
    }

    @Override
    public void onDisable() {
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fОтключение плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §x§f§f§0§0§0§0Плагин отключился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public static void setLastPayment(UUID player, double amount) {
        lastPayments.put(player, amount);
    }

    public static double getLastPayment(UUID player) {
        return lastPayments.getOrDefault(player, 0.0);
    }

    public static Economy getEconomy() {
        return economy;
    }

    public class LastPayPlaceholder extends PlaceholderExpansion {

        @Override
        public String getIdentifier() {
            return "spacelastpay";
        }

        @Override
        public String getAuthor() {
            return "SpaceDev";
        }

        @Override
        public String getVersion() {
            return "1.2";
        }

        @Override
        public String onRequest(OfflinePlayer player, String identifier) {
            if (identifier.equals("amount")) {
                return String.valueOf(getLastPayment(player.getUniqueId()));
            }
            return null;
        }
    }
}