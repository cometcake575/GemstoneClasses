package com.starshootercity.gemstoneclasses.abilities;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class CitrineOrb extends OrbAbility {
    @Override
    public @NotNull String getNamespace() {
        return "citrine";
    }

    @Override
    public int getID() {
        return 7;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        if (event.getPlayer().getTargetEntity(5) instanceof LivingEntity entity) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 300, 0));
            return 1800;
        } else return 0;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Citrine Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:citrine_orb");
    }
}
