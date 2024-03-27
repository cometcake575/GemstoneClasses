package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.gemstoneclasses.GemstoneClasses;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CelestialShield extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Left click with the Celestial Orb to create a barried around you for 10 seconds, preventing all damage and reflecting 50% of damage, putting the orb on cooldown for 2 minutes.", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Celestial Shield", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "celestial";
    }

    @Override
    public int getID() {
        return 9;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        return 0;
    }

    @Override
    public int onOrbUseSecondaryEvent(PlayerInteractEvent event) {
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(GemstoneClasses.getInstance(), () -> event.getPlayer().getPersistentDataContainer().remove(key));
        return 2400;
    }

    @EventHandler
    @SuppressWarnings("UnstableApiUsage")
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(key)) {
            event.setCancelled(true);
            if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
                if (damageByEntityEvent.getDamager() instanceof LivingEntity entity) {
                    entity.damage(event.getDamage(), event.getDamageSource());
                }
            }
        }
    }

    private final NamespacedKey key = new NamespacedKey(GemstoneClasses.getInstance(), "celestial_shield");

    @Override
    public @NotNull Component getName() {
        return Component.text("Celestial Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:celestial_shield");
    }
}
