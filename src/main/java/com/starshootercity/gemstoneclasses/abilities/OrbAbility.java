package com.starshootercity.gemstoneclasses.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.gemstoneclasses.GemstoneClasses;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class OrbAbility implements Ability, Listener {
    private NamespacedKey getOrbKey() {
        return new NamespacedKey(GemstoneClasses.getInstance(), "orb-%s".formatted(getNamespace()));
    }

    public abstract @NotNull String getNamespace();

    public abstract int getID();

    public abstract int onOrbUsePrimaryEvent(PlayerInteractEvent event);
    public int onOrbUseSecondaryEvent(PlayerInteractEvent event) {
        return 0;
    }

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
            } else {
                if (event.getWhoClicked().getInventory().getItemInOffHand().getItemMeta() == null) return;
                if (event.getWhoClicked().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
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
        if (event.getMainHandItem().getItemMeta() == null) return;
        if (event.getMainHandItem().getItemMeta().getPersistentDataContainer().has(getOrbKey())) event.setCancelled(true);
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
        event.getPlayer().setCooldown(Material.COMMAND_BLOCK, 0);
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                ItemStack item = player.getInventory().getItemInOffHand();
                boolean go = false;
                if (item.getItemMeta() == null) go = true;
                else if (!item.getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
                    go = true;
                }
                if (item.getType() == Material.COMMAND_BLOCK) item.setType(Material.AIR);
                if (go) {
                    for (ItemStack i : player.getInventory().addItem(item).values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), i);
                    }
                    player.getInventory().setItemInOffHand(getOrb());
                }
            });
        }
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        for (ItemStack item : event.getPlayer().getInventory()) {
            if (item == null) continue;
            if (item.getType() == Material.COMMAND_BLOCK) {
                event.getPlayer().getInventory().remove(item);
            }
        }
        if (event.getPlayer().getInventory().getItemInOffHand().getType() == Material.COMMAND_BLOCK) {
            event.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        if (event.getItem().getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
            if (event.getAction().isRightClick()) event.setCancelled(true);
            if (!event.getPlayer().isSneaking()) return;
            if (event.getPlayer().getCooldown(Material.COMMAND_BLOCK) > 0) return;
            event.getPlayer().setCooldown(Material.COMMAND_BLOCK, event.getAction().isLeftClick() ? onOrbUseSecondaryEvent(event) : onOrbUsePrimaryEvent(event));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (!List.of(EntityType.ALLAY, EntityType.ITEM_FRAME, EntityType.GLOW_ITEM_FRAME).contains(event.getRightClicked().getType())) return;
        if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer().has(getOrbKey())) {
            event.setCancelled(true);
        }
    }
}
