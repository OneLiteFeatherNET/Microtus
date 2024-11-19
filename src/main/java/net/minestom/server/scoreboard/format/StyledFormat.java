package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import org.jetbrains.annotations.NotNull;

public sealed interface StyledFormat extends NumberFormat, StyleBuilderApplicable permits StyledFormatImpl {

    @NotNull Style style();
}
