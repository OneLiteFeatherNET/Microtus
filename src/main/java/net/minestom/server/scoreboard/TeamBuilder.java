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
    /**
     * The display name of the team.
     */
    private Component displayName;
    /**
     * A BitMask.
     */
    private byte friendlyFlags;
    /**
     * The visibility of the team.
     */
    private TeamsPacket.NameTagVisibility nameTagVisibility;

    private TeamsPacket.NameTagVisibility deathMessageVisibility;

    /**
     * The collision rule of the team.
     */
    private TeamsPacket.CollisionRule collisionRule;

    /**
     * Used to color the name of players on the team <br>
     * The color of a team defines how the names of the team members are visualized.
     */
    private NamedTextColor teamColor;

    /**
     * Shown before the names of the players who belong to this team.
     */
    private Component prefix;
    /**
     * Shown after the names of the player who belong to this team.
     */
    private Component suffix;
    /**
     * True, if it should send an update packet
     */
    private boolean updateTeam;

    private boolean allowFriendlyFire;
    private boolean seeInvisiblePlayers;

    TeamBuilder(@NotNull String teamName) {
        Check.argCondition(teamName.trim().isEmpty(), "The team name cannot be empty");
        this.teamName = teamName;
        this.displayName = Component.empty();
        this.friendlyFlags = 0x00;
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
     * Changes the friendly flags of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param flag The new flag
     * @return this builder, for chaining
     */
    @Override
    public @NotNull TeamBuilder friendlyFlags(byte flag) {
        this.friendlyFlags = flag;
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
                this.friendlyFlags,
                this.nameTagVisibility,
                this.deathMessageVisibility,
                this.collisionRule,
                this.teamColor,
                this.prefix,
                this.suffix
        );
        team.setAllowFriendlyFire(this.allowFriendlyFire);
        team.setSeeInvisiblePlayers(this.seeInvisiblePlayers);
        if (!autoRegister) return team;

        TeamManager teamManager = MinecraftServer.getTeamManager();

        if (!teamManager.exists(team)) teamManager.registerNewTeam(team);
        if (this.updateTeam) {
            team.sendUpdatePacket();
        }
        return team;
    }
}
