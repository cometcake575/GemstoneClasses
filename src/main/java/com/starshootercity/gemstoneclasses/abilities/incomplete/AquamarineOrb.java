package com.starshootercity.gemstoneclasses.abilities.incomplete;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AquamarineOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Aquamarine Orb, an infinite water bucket with a 5 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
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
    public int onOrbUseEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return 0;
        Block relative = event.getClickedBlock().getRelative(event.getBlockFace());
        if (relative.isSolid()) return 0;
        event.getPlayer().swingMainHand();
        relative.setType(Material.WATER);
        return 100;
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
