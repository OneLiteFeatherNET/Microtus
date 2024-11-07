package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

/**
 * A builder which represents a fluent Object to built teams.
 */
public final class TeamBuilder implements Team.Builder {

    private final String teamName;

    private Component displayName;
    private TeamsPacket.NameTagVisibility nameTagVisibility;
    private TeamsPacket.NameTagVisibility deathMessageVisibility;
    private TeamsPacket.CollisionRule collisionRule;
    private NamedTextColor teamColor;
    private Component prefix;
    private Component suffix;
    private boolean updateTeam;
    private boolean allowFriendlyFire;
    private boolean seeInvisiblePlayers;

    TeamBuilder(@NotNull String teamName) {
        Check.argCondition(teamName.trim().isEmpty(), "The team name cannot be empty");
        this.teamName = teamName;
        this.displayName = Component.empty();
        this.nameTagVisibility = TeamsPacket.NameTagVisibility.ALWAYS;
        this.deathMessageVisibility = TeamsPacket.NameTagVisibility.ALWAYS;
        this.collisionRule = TeamsPacket.CollisionRule.ALWAYS;

        this.teamColor = NamedTextColor.WHITE;
        this.prefix = Component.empty();
        this.suffix = Component.empty();
        this.updateTeam = false;
    }

    /**
     * Changes the prefix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param prefix The new prefix
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder prefix(@NotNull Component prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Changes the suffix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param suffix The new suffix
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder suffix(@NotNull Component suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * Changes the color of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param color The new team color
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder color(@NotNull NamedTextColor color) {
        this.teamColor = color;
        return this;
    }

    /**
     * Changes the display name of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param displayName The new display name
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder displayName(@NotNull Component displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Changes the {@link CollisionRule} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param rule The new rule
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder collisionRule(@NotNull CollisionRule rule) {
        this.collisionRule = rule;
        return this;
    }

    /**
     * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
     * @param visibility the new death message visibility
     * @return this builder, for chaining
     */
    @Override
    public Team.@NotNull Builder deathMessageVisibility(@NotNull NameTagVisibility visibility) {
        this.deathMessageVisibility = visibility;
        return this;
    }

    /**
     * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder nameTagVisibility(@NotNull NameTagVisibility visibility) {
        this.nameTagVisibility = visibility;
        return this;
    }

    /**
     * Changes the friendly flags for allow friendly fire without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder allowFriendlyFire() {
        this.allowFriendlyFire = true;
        return this;
    }

    /**
     * Changes the friendly flags to sees invisible players of own team without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder seeInvisiblePlayers() {
        this.seeInvisiblePlayers = true;
        return this;
    }

    /**
     * Allows to send an update packet when the team is built.
     *
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder updateTeamPacket() {
        this.updateTeam = true;
        return this;
    }

    /**
     * Built a team.
     *
     * @return the built team
     */
    public @NotNull Team build(boolean autoRegister) {
        Team team = new ScoreboardTeam(
                this.teamName,
                this.displayName,
                this.nameTagVisibility,
                this.deathMessageVisibility,
                this.collisionRule,
                this.teamColor,
                this.prefix,
                this.suffix,
                this.allowFriendlyFire,
                this.seeInvisiblePlayers
        );
        if (!autoRegister) return team;

        TeamManager teamManager = MinecraftServer.getTeamManager();

        if (!teamManager.exists(team)) teamManager.registerNewTeam(team);
        if (this.updateTeam) {
            team.sendUpdatePacket();
        }
        return team;
    }
}
