package net.minestom.server.scoreboard.format;

import org.jetbrains.annotations.NotNull;

record BlankFormat() implements NumberFormat {

    @Override
    public @NotNull NumberFormat.FormatType type() {
        return FormatType.BLANK;
    }
}
