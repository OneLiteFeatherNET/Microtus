package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

record FixedFormatImpl(@NotNull Component component) implements FixedFormat {
    @Override
    public @NotNull Component asComponent() {
        return this.component();
    }

    @Override
    public @NotNull NumberFormat.FormatType type() {
        return FormatType.FIXED;
    }
}
