package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AmberOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull String getNamespace() {
        return "amber";
    }

    @Override
    public int getID() {
        return 8;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        Location currentLoc = event.getPlayer().getEyeLocation();
        List<Entity> hitEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            currentLoc.add(currentLoc.getDirection().clone().divide(new Vector(0.5, 0.5, 0.5)));
            currentLoc.getWorld().spawnParticle(Particle.SONIC_BOOM, currentLoc, 1);
            for (Entity entity : currentLoc.getNearbyEntities(1, 1, 1)) {
                if (entity == event.getPlayer()) continue;
                if (!hitEntities.contains(entity)) {
                    if (!(entity instanceof LivingEntity)) continue;
                    hitEntities.add(entity);
                    net.minecraft.world.entity.Entity NMSEntity = ((CraftEntity) entity).getHandle();
                    NMSEntity.hurt(NMSEntity.damageSources().onFire(), 10);
                    entity.setFireTicks(80);
                }
            }
        }
        return 1800;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Amber Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:amber_orb");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Amber Orb, which shoots a beam of fire dealing 5 hearts of damage with a 1 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Amber Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
