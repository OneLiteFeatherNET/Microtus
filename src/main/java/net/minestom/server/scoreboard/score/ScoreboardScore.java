package net.minestom.server.scoreboard.score;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.Sidebar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ScoreboardScore implements Score {

    private int value;
    private Component display;
    private Sidebar.NumberFormat numberFormat;

    ScoreboardScore(int value, @Nullable Component display, @Nullable Sidebar.NumberFormat numberFormat) {
        this.value = value;
        this.display = display;
        this.numberFormat = numberFormat;
    }

    @Override
    public void updateValue(int value) {
        this.value = value;
    }

    @Override
    public void updateDisplay(@Nullable Component component) {
        this.display = component;
    }

    @Override
    public void updateNumberFormat(Sidebar.@Nullable NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public @Nullable Component displayName() {
        return this.display;
    }

    @Override
    public Sidebar.@Nullable NumberFormat numberFormat() {
        return this.numberFormat;
    }
}
