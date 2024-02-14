package com.starshootercity.gemstoneclasses.abilities.incomplete;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.gemstoneclasses.GemstoneClasses;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class OrbAbility implements Ability, Listener {
    private NamespacedKey getOrbKey() {
        return new NamespacedKey(GemstoneClasses.getInstance(), "orb-%s".formatted(getNamespace()));
    }

    public abstract @NotNull String getNamespace();

    public abstract int getID();

    public abstract int onOrbUseEvent(PlayerInteractEvent event);

    public ItemStack getOrb() {
        ItemStack orb = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta meta = orb.getItemMeta();
        meta.setCustomModelData(getID());
        meta.getPersistentDataContainer().set(getOrbKey(), PersistentDataType.BOOLEAN, true);
        meta.displayName(getName());
        orb.setItemMeta(meta);
        return orb;
    }

    public abstract @NotNull Component getName();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.COMMAND_BLOCK) {
            ItemStack main = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offhand = event.getPlayer().getInventory().getItemInOffHand();
            if (main.getType() == Material.COMMAND_BLOCK) {
                if (main.getItemMeta().getPersistentDataContainer().getOrDefault(getOrbKey(), PersistentDataType.BOOLEAN, false)) {
                    event.setCancelled(true);
                }
            } else if (offhand.getType() == Material.COMMAND_BLOCK) {
                if (offhand.getItemMeta().getPersistentDataContainer().getOrDefault(getOrbKey(), PersistentDataType.BOOLEAN, false)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
            if (event.getHotbarButton() != -1) {
                ItemStack item = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                if (item != null) {
                    if (item.getItemMeta() != null) {
                        if (item.getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
                    }
                }
            }
        }
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getOffHandItem().getItemMeta() == null) return;
        if (event.getOffHandItem().getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (ItemStack item : new ArrayList<>(event.getDrops())) {
            if (item.getItemMeta() == null) continue;
            if (item.getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
                event.getItemsToKeep().add(item);
                event.getDrops().remove(item);
            }
        }
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                ItemStack item = player.getInventory().getItem(0);
                boolean go = false;
                if (item == null) go = true;
                else if (item.getItemMeta() == null) go = true;
                else if (!item.getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
                    go = true;
                }
                if (go) {
                    if (item != null) {
                        for (ItemStack i : player.getInventory().addItem(item).values()) {
                            player.getWorld().dropItemNaturally(player.getLocation(), i);
                        }
                    }
                    player.getInventory().setItem(0, getOrb());
                }
            });
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) return;
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        if (event.getPlayer().getCooldown(Material.COMMAND_BLOCK) > 0) return;
        if (event.getItem().getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
            event.getPlayer().setCooldown(Material.COMMAND_BLOCK, onOrbUseEvent(event));
        }
    }
}
