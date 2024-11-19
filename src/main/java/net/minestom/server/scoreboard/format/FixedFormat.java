package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

public sealed interface FixedFormat extends NumberFormat, ComponentLike permits FixedFormatImpl {

    @NotNull Component component();
}
