package me.rismose.nojumpingallowed;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public final class NoJumpingAllowed extends JavaPlugin implements Listener {

    public boolean isJumpMessageOn;

    public NoJumpingAllowed() {
        isJumpMessageOn = this.getConfig().getBoolean("JumpMessageToggle");
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.reloadConfig();

        System.out.println("NoJumpingAllowed has been started!");

        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

    }





    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
            Player player = e.getPlayer();
            Vector velocity = player.getVelocity();
            if (velocity.getY() > 0) {
                double jumpVelocity = 0.42F;
                PotionEffect jumpPotion = player.getPotionEffect(PotionEffectType.JUMP);
                if (jumpPotion != null) {

                    jumpVelocity += (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;
                }
                if (player.getLocation().getBlock().getType() != Material.LADDER && Double.compare(velocity.getY(), jumpVelocity) == 0) {

                    if (isJumpMessageOn) {
                        String JumpMessage = getConfig().getString("JumpMessage");
                        player.sendMessage(ChatColor.YELLOW + JumpMessage);
                    } else if (!isJumpMessageOn) {
                        // Empty
                    }

                    int JumpDMG = getConfig().getInt("JumpDMG");
                    player.damage(JumpDMG * 2);
                }
            }
    }



    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("reloadhearts")) {
            this.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "NoJumpingAllowed has been reloaded.");
            return true;
        }
        return false;
    }
}
