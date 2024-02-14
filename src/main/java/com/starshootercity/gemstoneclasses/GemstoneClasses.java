package com.starshootercity.gemstoneclasses;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.gemstoneclasses.abilities.*;
import com.starshootercity.gemstoneclasses.abilities.incomplete.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GemstoneClasses extends OriginsAddon {
    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(
                new AquamarineOrb(),
                new CrystallineOrb(),
                new DragonOrb(),
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

    /*
    @Override
    public @Nullable ResourcePackInfo getResourcePackInfo() {
        try {
            return ResourcePackInfo.resourcePackInfo()
                    .uri(URI.create(getConfig().getString("resource-pack-url", "")))
                    .computeHashAndBuild().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

     */

    @Override
    public void onRegister() {
        saveDefaultConfig();
    }
}