package com.starshootercity.gemstoneclasses;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.gemstoneclasses.abilities.*;
import net.kyori.adventure.resource.ResourcePackInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GemstoneClasses extends OriginsAddon implements Listener {
    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(
                new AmberOrb(),
                new AquamarineOrb(),
                new CelestialBlessing(),
                new CrystallineOrb(),
                new CitrineOrb(),
                new DragonOrb(),
                new ExtraHearts(),
                new FireResistance(),
                new GoldenHealth(),
                new GoldenOrb(),
                new GoldenSpeed(),
                new GoldenStrength(),
                new HeroAndHaste(),
                new JadeOrb(),
                new OnyxOrb(),
                new Regeneration(),
                new ResistanceAndSpeed(),
                new SapphireOrb(),
                new Saturation(),
                new Strength(),
                new CelestialShield(),
                new WaterPowers()
        );
    }

    @Override
    public @NotNull String getNamespace() {
        return "gemstoneclasses";
    }

    @Override
    public @Nullable ResourcePackInfo getResourcePackInfo() {
        if (!getConfig().getBoolean("enable-resource-pack")) {
            return null;
        }
        try {
            return ResourcePackInfo.resourcePackInfo()
                    .uri(URI.create(getConfig().getString("resource-pack-url", "https://github.com/cometcake575/GemstoneClasses/raw/main/src/main/GemstoneClasses%20Pack.zip")))
                    .computeHashAndBuild().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRegister() {
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        for (ItemStack item : event.getPlayer().getInventory()) {
            if (item == null) continue;
            if (item.getType() == Material.COMMAND_BLOCK) {
                event.getPlayer().getInventory().remove(item);
            }
        }
        event.getPlayer().setCooldown(Material.COMMAND_BLOCK, 0);
    }
}