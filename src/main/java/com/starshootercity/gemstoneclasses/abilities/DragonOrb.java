package com.starshootercity.gemstoneclasses.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.FlightAllowingAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonOrb extends OrbAbility implements VisibleAbility, FlightAllowingAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the Dragon Orb, giving flight and Resistance 10 for 10 seconds, with a 2 minute and 30 second cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Dragon Orb", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull String getNamespace() {
        return "dragon";
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public int onOrbUseEvent(PlayerInteractEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 9));
        flightTimes.put(event.getPlayer(), Bukkit.getCurrentTick() + 200);
        return 3000;
    }

    private final Map<Player, Integer> flightTimes = new HashMap<>();

    @Override
    public @NotNull Component getName() {
        return Component.text("Dragon Orb").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("gemstoneclasses:dragon_orb");
    }

    @Override
    public boolean canFly(Player player) {
        return Bukkit.getCurrentTick() - flightTimes.getOrDefault(player, Bukkit.getCurrentTick()) < 0;
    }

    @Override
    public float getFlightSpeed(Player player) {
        return 0.1f;
    }
}
