package com.starshootercity.gemstoneclasses.abilities.incomplete;

import com.starshootercity.abilities.Ability;
import com.starshootercity.gemstoneclasses.GemstoneClasses;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class OrbAbility implements Ability, Listener {
    private static final NamespacedKey key = new NamespacedKey(GemstoneClasses.getInstance(), "orb");

    public abstract int getID();

    public abstract int onOrbUseEvent(PlayerInteractEvent event);

    public ItemStack getOrb() {
        ItemStack orb = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta meta = orb.getItemMeta();
        meta.setCustomModelData(getID());
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        orb.setItemMeta(meta);
        return orb;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.COMMAND_BLOCK) {
            ItemStack main = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offhand = event.getPlayer().getInventory().getItemInOffHand();
            if (main.getType() == Material.COMMAND_BLOCK) {
                if (main.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) {
                    event.setCancelled(true);
                }
            } else if (offhand.getType() == Material.COMMAND_BLOCK) {
                if (offhand.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
            ItemStack item = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            if (item != null) {
                if (item.getItemMeta() != null) {
                    if (item.getItemMeta().getPersistentDataContainer().has(key)) event.setCancelled(true);
                }
            }
        }
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

    }
}
