package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CelestialBlessing extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Right click with the Celestial Orb to get Regeneration 3, Absorption 3 and Resistance 3 for 30 seconds, putting the orb on cooldown for 5 minutes.", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Celestial Blessing", OriginSwapper.LineData.LineComponent.LineType.TITLE);
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
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 2));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 2));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 2));
        return 6000;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Celestial Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:celestial_blessing");
    }
}
