package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldenHealth implements AttributeModifierAbility, VisibleAbility {
    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:golden_health");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MAX_HEALTH;
    }

    @Override
    public double getAmount() {
        return 10;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have 50% more speed, health and strength", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Golden Abilities", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
