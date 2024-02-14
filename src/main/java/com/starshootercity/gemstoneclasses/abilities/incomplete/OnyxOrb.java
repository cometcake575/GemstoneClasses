package com.starshootercity.gemstoneclasses.abilities.incomplete;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnyxOrb extends OrbAbility implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return null;
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return null;
    }

    @Override
    public @NotNull String getNamespace() {
        return null;
    }

    @Override
    public int getID() {
        return 5;
    }

    @Override
    public int onOrbUseEvent(PlayerInteractEvent event) {
        return 0;
    }

    @Override
    public @NotNull Component getName() {
        return null;
    }

    @Override
    public @NotNull Key getKey() {
        return null;
    }
}
