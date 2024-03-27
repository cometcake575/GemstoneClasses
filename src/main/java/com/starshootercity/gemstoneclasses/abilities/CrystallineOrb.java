package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.gemstoneclasses.GemstoneClasses;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrystallineOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Crystalline Orb, giving invincibility for 5 seconds, with a 1 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Crystalline Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "crystalline";
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public int onOrbUsePrimaryEvent(PlayerInteractEvent event) {
        event.getPlayer().swingMainHand();
        event.getPlayer().setInvulnerable(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(GemstoneClasses.getInstance(), () -> event.getPlayer().setInvulnerable(false), 100);
        return 1800;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setInvulnerable(false);
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Crystalline Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:crystalline_orb");
    }
}
