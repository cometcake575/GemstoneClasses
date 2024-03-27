package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AquamarineOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Aquamarine Orb, freezing whatever you right click for 5 seconds with a 1 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Aquamarine Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "aquamarine";
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        if (event.getPlayer().getTargetEntity(5) instanceof LivingEntity entity) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 9));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 9));
            return 1800;
        } else return 0;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        PotionEffect effect = event.getPlayer().getPotionEffect(PotionEffectType.SLOW);
        if (effect != null) {
            if (effect.getAmplifier() == 9) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        PotionEffect effect = event.getEntity().getPotionEffect(PotionEffectType.SLOW);
        if (effect != null) {
            if (effect.getAmplifier() == 9) event.setCancelled(true);
        }
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Aquamarine Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:aquamarine_orb");
    }
}
