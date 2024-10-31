package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.adventure.audience.PacketGroupingAudience;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * This object represents a team on a scoreboard that has a common display theme and other properties.
 */
public interface Team extends PacketGroupingAudience {

    byte ALLOW_FRIENDLY_FIRE_BIT = 0x01;
    byte SEE_INVISIBLE_PLAYERS_BIT = 0x02;

    /**
     * Creates a new {@link Team} builder.
     *
     * @return a new team builder
     */
    @Contract(pure = true)
    static @NotNull Builder builder(@NotNull String name) {
        return new TeamBuilder(name);
    }

    /**
     * Adds a member to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be added
     */
    void addMember(@NotNull String member);

    /**
     * Adds a members to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toAdd The members to be added
     */
    void addMembers(@NotNull Collection<@NotNull String> toAdd);

    /**
     * Removes a member from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be removed
     */
    void removeMember(@NotNull String member);

    /**
     * Removes members from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toRemove The members to be removed
     */
    void removeMembers(@NotNull Collection<@NotNull String> toRemove);

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName The new display name
     */
    void updateTeamDisplayName(Component teamDisplayName);

    /**
     * Changes the {@link NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility The new tag visibility
     */
    void updateNameTagVisibility(@NotNull NameTagVisibility nameTagVisibility);

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule The new collision rule
     */
    void updateCollisionRule(@NotNull CollisionRule collisionRule);

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color The new team color
     */
    void updateTeamColor(@NotNull NamedTextColor color);

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    void updatePrefix(Component prefix);

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    void updateSuffix(Component suffix);

    /**
     * Changes the friendly flags of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param flag The new friendly flag
     */
    void setFriendlyFlags(byte flag);

    /**
     * Changes the friendly flags of the team and sends an update packet.
     *
     * @param flag The new friendly flag
     */
    void updateFriendlyFlags(byte flag);

    void setAllowFriendlyFire(boolean value);

    void updateAllowFriendlyFire(boolean value);

    boolean isAllowFriendlyFire();

    void setSeeInvisiblePlayers(boolean value);

    void updateSeeInvisiblePlayers(boolean value);

    boolean isSeeInvisiblePlayers();

    /**
     * Sends an {@link TeamsPacket.UpdateTeamAction} action packet.
     */
    void sendUpdatePacket();

    /**
     * Gets the registry name of the team.
     *
     * @return the registry name
     */
    @NotNull String getName();

    /**
     * Creates the creation packet to add a team.
     *
     * @return the packet to add the team
     */
    @NotNull TeamsPacket createTeamsCreationPacket();

    /**
     * Creates a destruction packet to remove the team.
     *
     * @return the packet to remove the team
     */
    @NotNull TeamsPacket createTeamDestructionPacket();

    /**
     * Obtains an unmodifiable {@link Set} of registered players who are on the team.
     *
     * @return an unmodifiable {@link Set} of registered players
     */
    @NotNull Set<String> getMembers();

    /**
     * Gets the display name of the team.
     *
     * @return the display name
     */
    @NotNull Component getTeamDisplayName();

    /**
     * Gets the friendly flags of the team.
     *
     * @return the friendly flags
     */
    byte getFriendlyFlags();

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    @NotNull NameTagVisibility getNameTagVisibility();

    /**
     * Gets the collision rule of the team.
     *
     * @return the collision rule
     */
    @NotNull CollisionRule getCollisionRule();

    /**
     * Gets the color of the team.
     *
     * @return the team color
     */
    @NotNull NamedTextColor getTeamColor();

    /**
     * Gets the prefix of the team.
     *
     * @return the team prefix
     */
    @NotNull Component getPrefix();

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix team
     */
    @NotNull Component getSuffix();

    @NotNull Collection<Player> getPlayers();

    sealed interface Builder permits TeamBuilder {

        /**
         * Updates the prefix of the {@link Team}.
         *
         * @param prefix The new prefix
         * @return this builder, for chaining
         */
        @NotNull Builder prefix(@NotNull Component prefix);

        /**
         * Changes the suffix of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param suffix The new suffix
         * @return this builder, for chaining
         */
        @NotNull Builder suffix(@NotNull Component suffix);

        /**
         * Changes the color of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param color The new team color
         * @return this builder, for chaining
         */
        @NotNull Builder color(@NotNull NamedTextColor color);

        /**
         * Changes the display name of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param displayName The new display name
         * @return this builder, for chaining
         */
        @NotNull Builder displayName(@NotNull Component displayName);

        /**
         * Changes the {@link CollisionRule} of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param rule The new rule
         * @return this builder, for chaining
         */
        @NotNull Builder collisionRule(@NotNull CollisionRule rule);

        /**
         * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param visibility The new tag visibility
         * @return this builder, for chaining
         */
        @NotNull Builder visibility(@NotNull NameTagVisibility visibility);

        /**
         * Changes the friendly flags of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param flag The new flag
         * @return this builder, for chaining
         */
        @NotNull Builder friendlyFlags(byte flag);

        /**
         * Changes the friendly flags for allow friendly fire without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @return this builder, for chaining
         */
        @NotNull Builder allowFriendlyFire();

        /**
         * Changes the friendly flags to sees invisible players of own team without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @return this builder, for chaining
         */
        @NotNull Builder seeInvisiblePlayers();

        /**
         * Allows to send an update packet when the team is built.
         *
         * @return this builder, for chaining
         */
        @NotNull Builder updateTeamPacket();

        /**
         * Builds the {@link Team}.
         *
         * @return the team
         */
        default @NotNull Team build() {
            return this.build(false);
        }

        /**
         * Builds the {@link Team}.
         *
         * @param autoRegister If the team should be automatically registered
         * @return the team
         */
        @NotNull Team build(boolean autoRegister);
    }
}
