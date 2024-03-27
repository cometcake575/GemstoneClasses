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

public class SapphireOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Sapphire Orb, giving Regeneration 5 and Health Boost 5 for 10 seconds with a 1 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Sapphire Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "sapphire";
    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        event.getPlayer().swingMainHand();
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2));
        return 1800;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Sapphire Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:sapphire_orb");
    }
}
