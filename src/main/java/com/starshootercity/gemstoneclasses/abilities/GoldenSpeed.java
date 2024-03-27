package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.abilities.AttributeModifierAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class GoldenSpeed implements AttributeModifierAbility {
    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:golden_speed");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public double getAmount() {
        return 0.05;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
