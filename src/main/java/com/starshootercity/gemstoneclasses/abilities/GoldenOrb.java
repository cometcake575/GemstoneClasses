package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Fireball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldenOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull String getNamespace() {
        return "golden";
    }

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        event.getPlayer().launchProjectile(Fireball.class).setYield(30);
        return 3000;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Golden Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:golden_orb");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Golden Orb, launching a powerful fireball with a 2 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Golden Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
