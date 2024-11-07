package net.minestom.server.scoreboard.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.adventure.audience.PacketGroupingAudience;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

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
     * @param member the member to be added
     */
    void addMember(@NotNull String member);

    /**
     * Adds a members to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toAdd the members to be added
     */
    void addMembers(@NotNull Collection<@NotNull String> toAdd);

    /**
     * Removes a member from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member the member to be removed
     */
    void removeMember(@NotNull String member);

    /**
     * Removes members from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toRemove the members to be removed
     */
    void removeMembers(@NotNull Collection<@NotNull String> toRemove);

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName the new display name
     */
    void updateDisplayName(@NotNull Component teamDisplayName);

    /**
     * Changes the {@link NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility the new tag visibility
     */
    void updateNameTagVisibility(@NotNull NameTagVisibility nameTagVisibility);

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule the new collision rule
     */
    void updateCollisionRule(@NotNull CollisionRule collisionRule);

    /**
     * Changes the death message visibility of the team and sends an update packet.
     *
     * @param deathMessageVisibility the new death message visibility
     */
    void updateDeathMessageVisibility(@NotNull NameTagVisibility deathMessageVisibility);

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color the new team color
     */
    void updateTeamColor(@NotNull NamedTextColor color);

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix the new prefix
     */
    void updatePrefix(@NotNull Component prefix);

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix the new suffix
     */
    void updateSuffix(@NotNull Component suffix);

    /**
     * Changes the friendly flags for allow friendly fire and sends an update packet.
     */
    void updateFriendlyFireFlag();

    /**
     * Changes the friendly flags to sees invisible players of own team and sends an update packet.
     */
    void updateSeeInvisiblePlayersFlag();

    /**
     * Sends an {@link TeamsPacket.UpdateTeamAction} action packet.
     */
    void sendUpdatePacket();

    /**
     * Gets if the team can see invisible players.
     *
     * @return true if the team can see invisible players
     */
    boolean allowFriendlyFire();

    /**
     * Gets if the team can see invisible players.
     *
     * @return true if the team can see invisible players
     */
    boolean canSeeInvisiblePlayers();

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
    @NotNull Component getDIsplayName();

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    @NotNull NameTagVisibility getNameTagVisibility();

    /**
     * Gets the death message visibility of the team.
     *
     * @return the death message visibility
     */
    @NotNull NameTagVisibility getDeathMessageVisibility();

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
    @NotNull TextColor getColor();

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

    /**
     * Gets the players who are on the team.
     *
     * @return the players on the team
     */
    @NotNull @UnmodifiableView Collection<Player> getPlayers();

    /**
     * This interface represents a builder for a {@link Team}.
     */
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
         *
         * @param visibility the new death message visibility
         * @return this builder, for chaining
         */
        @NotNull Builder deathMessageVisibility(@NotNull NameTagVisibility visibility);

        /**
         * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
         * <br><br>
         * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
         *
         * @param visibility The new tag visibility
         * @return this builder, for chaining
         */
        @NotNull Builder nameTagVisibility(@NotNull NameTagVisibility visibility);

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
