package com.starshootercity.gemstoneclasses;

import com.starshootercity.OriginsAddon;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.Ability;
import com.starshootercity.gemstoneclasses.abilities.*;
import com.starshootercity.gemstoneclasses.abilities.incomplete.*;
import net.kyori.adventure.resource.ResourcePackInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GemstoneClasses extends OriginsAddon {
    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(
                new AquamarineOrb(),
                new CrystallineOrb(),
                new DragonFormOrb(),
                new ExtraHearts(),
                new FireResistance(),
                new HeroAndHaste(),
                new JadeOrb(),
                new OnyxOrb(),
                new Regeneration(),
                new ResistanceAndSpeed(),
                new SapphireOrb(),
                new WaterPowers()
        );
    }

    @Override
    public @NotNull String getNamespace() {
        return "gemstoneclasses";
    }

    @Override
    public @Nullable ResourcePackInfo getResourcePackInfo() {
        try {
            return ResourcePackInfo.resourcePackInfo()
                    .uri(URI.create(getConfig().getString("resource-pack.link", "https://github.com/cometcake575/Origins-Reborn/raw/main/src/main/Origins%20Pack.zip")))
                    .computeHashAndBuild().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}