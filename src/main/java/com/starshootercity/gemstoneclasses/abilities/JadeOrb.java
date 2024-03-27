package com.starshootercity.gemstoneclasses.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JadeOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Jade Orb, giving complete invisibility for 10 seconds, with a 1 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Jade Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "jade";
    }

    @Override
    public int getID() {
        return 4;
    }

    private final Map<Player, Integer> invisMap = new HashMap<>();

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        event.getPlayer().swingMainHand();
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0));
        invisMap.put(event.getPlayer(), Bukkit.getCurrentTick() + 200);
        return 1800;
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        super.onServerTickEnd(event);
        for (Player invis : invisMap.keySet()) {
            if (invisMap.get(invis) - Bukkit.getCurrentTick() < 0) {
                invis.sendEquipmentChange(invis, Map.of(EquipmentSlot.HEAD, invis.getInventory().getItem(EquipmentSlot.HEAD), EquipmentSlot.CHEST, invis.getInventory().getItem(EquipmentSlot.CHEST), EquipmentSlot.LEGS, invis.getInventory().getItem(EquipmentSlot.LEGS), EquipmentSlot.FEET, invis.getInventory().getItem(EquipmentSlot.FEET)));
                invisMap.remove(invis);
                continue;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendEquipmentChange(invis, Map.of(EquipmentSlot.HEAD, new ItemStack(Material.AIR), EquipmentSlot.CHEST, new ItemStack(Material.AIR), EquipmentSlot.LEGS, new ItemStack(Material.AIR), EquipmentSlot.FEET, new ItemStack(Material.AIR)));
            }
        }
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Jade Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:jade_orb");
    }
}
