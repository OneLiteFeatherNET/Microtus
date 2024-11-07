package net.minestom.server.scoreboard.team;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.utils.PacketUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ScoreboardTeam implements Team {

    private final Set<String> members;
    private final Set<Player> playerMembers = ConcurrentHashMap.newKeySet();
    private final String teamName;
    // Adventure
    private final Pointers pointers;

    private boolean isPlayerMembersUpToDate;
    private TeamsPacket.NameTagVisibility nameTagVisibility;
    private TeamsPacket.NameTagVisibility deathMessageVisibility;
    private TeamsPacket.CollisionRule collisionRule;
    private NamedTextColor teamColor;
    private Component teamDisplayName;
    private Component prefix;
    private Component suffix;

    private  boolean allowFriendlyFire;
    private boolean seeInvisiblePlayers;

    //TODO: Data Objects to reduce parameters?
    /**
     * Creates a new {@link Team} with the given parameters.
     *
     * @param teamName          the registry name of the team
     * @param teamDisplayName   the display name of the team
     * @param nameTagVisibility the visibility of the team
     * @param collisionRule     the collision rule of the team
     * @param teamColor         the color of the team
     * @param prefix            the prefix of the team
     * @param suffix            the suffix of the team
     */
    ScoreboardTeam(
            @NotNull String teamName,
            @NotNull Component teamDisplayName,
            @NotNull TeamsPacket.NameTagVisibility nameTagVisibility,
            @NotNull TeamsPacket.NameTagVisibility deathMessageVisibility,
            @NotNull TeamsPacket.CollisionRule collisionRule,
            @NotNull NamedTextColor teamColor,
            @NotNull Component prefix,
            @NotNull Component suffix,
            boolean allowFriendlyFire,
            boolean seeInvisiblePlayers
    ) {
        this.teamName = teamName;
        this.teamDisplayName = teamDisplayName;
        this.nameTagVisibility = nameTagVisibility;
        this.deathMessageVisibility = deathMessageVisibility;
        this.collisionRule = collisionRule;
        this.teamColor = teamColor;
        this.prefix = prefix;
        this.suffix = suffix;
        this.members = new CopyOnWriteArraySet<>();
        this.allowFriendlyFire = allowFriendlyFire;
        this.seeInvisiblePlayers = seeInvisiblePlayers;

        this.pointers = Pointers.builder()
                .withDynamic(Identity.NAME, this::getName)
                .withDynamic(Identity.DISPLAY_NAME, this::getTeamDisplayName)
                .build();
    }

    /**
     * Adds a member to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be added
     */
    @Override
    public void addMember(@NotNull String member) {
        addMembers(List.of(member));
    }

    /**
     * Adds a members to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toAdd The members to be added
     */
    @Override
    public void addMembers(@NotNull Collection<@NotNull String> toAdd) {
        // Adds a new member to the team
        this.members.addAll(toAdd);

        // Initializes add player packet
        final TeamsPacket addPlayerPacket = new TeamsPacket(teamName,
                new TeamsPacket.AddEntitiesToTeamAction(toAdd));
        // Sends to all online players the add player packet
        PacketUtils.broadcastPlayPacket(addPlayerPacket);

        // invalidate player members
        this.isPlayerMembersUpToDate = false;
    }

    /**
     * Removes a member from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be removed
     */
    @Override
    public void removeMember(@NotNull String member) {
        removeMembers(List.of(member));
    }

    /**
     * Removes members from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toRemove The members to be removed
     */
    @Override
    public void removeMembers(@NotNull Collection<@NotNull String> toRemove) {
        // Initializes remove player packet
        final TeamsPacket removePlayerPacket = new TeamsPacket(teamName,
                new TeamsPacket.RemoveEntitiesToTeamAction(toRemove));
        // Sends to all online player the remove player packet
        PacketUtils.broadcastPlayPacket(removePlayerPacket);

        // Removes the member from the team
        this.members.removeAll(toRemove);

        // invalidate player members
        this.isPlayerMembersUpToDate = false;
    }

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName The new display name
     */
    public void updateTeamDisplayName(@NotNull Component teamDisplayName) {
        this.teamDisplayName = teamDisplayName;
        sendUpdatePacket();
    }

    /**
     * Changes the {@link TeamsPacket.NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility The new tag visibility
     */
    @Override
    public void updateNameTagVisibility(@NotNull TeamsPacket.NameTagVisibility nameTagVisibility) {
        this.nameTagVisibility = nameTagVisibility;
        sendUpdatePacket();
    }

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule The new collision rule
     */
    @Override
    public void updateCollisionRule(@NotNull TeamsPacket.CollisionRule collisionRule) {
        this.collisionRule = collisionRule;
        sendUpdatePacket();
    }

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color The new team color
     */
    @Override
    public void updateTeamColor(@NotNull NamedTextColor color) {
        this.teamColor = color;
        sendUpdatePacket();
    }

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    @Override
    public void updatePrefix(@NotNull Component prefix) {
        this.prefix = prefix;
        sendUpdatePacket();
    }

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    @Override
    public void updateSuffix(@NotNull Component suffix) {
        this.suffix = suffix;
        sendUpdatePacket();
    }

    @Override
    public void updateFriendlyFireFlag() {
        this.allowFriendlyFire = !this.allowFriendlyFire;
        sendUpdatePacket();
    }

    @Override
    public void updateSeeInvisiblePlayersFlag() {
        this.seeInvisiblePlayers = !this.seeInvisiblePlayers;
        sendUpdatePacket();
    }

    @Override
    public boolean allowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    @Override
    public boolean canSeeInvisiblePlayers() {
        return this.seeInvisiblePlayers;
    }


    private byte packOptions() {
        byte optionBit = 0;

        if (this.allowFriendlyFire()) {
            optionBit |= ALLOW_FRIENDLY_FIRE_BIT;
        }

        if (this.canSeeInvisiblePlayers()) {
            optionBit |= SEE_INVISIBLE_PLAYERS_BIT;
        }

        return optionBit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreboardTeam that = (ScoreboardTeam) o;
        return Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(teamName);
    }

    /**
     * Gets the registry name of the team.
     *
     * @return the registry name
     */
    public @NotNull String getName() {
        return teamName;
    }

    /**
     * Creates the creation packet to add a team.
     *
     * @return the packet to add the team
     */
    public @NotNull TeamsPacket createTeamsCreationPacket() {
        final var info = new TeamsPacket.CreateTeamAction(teamDisplayName, this.packOptions(),
                nameTagVisibility, collisionRule, teamColor, prefix, suffix, members);
        return new TeamsPacket(teamName, info);
    }

    /**
     * Creates an destruction packet to remove the team.
     *
     * @return the packet to remove the team
     */
    public @NotNull TeamsPacket createTeamDestructionPacket() {
        return new TeamsPacket(teamName, new TeamsPacket.RemoveTeamAction());
    }

    /**
     * Obtains an unmodifiable {@link Set} of registered players who are on the team.
     *
     * @return an unmodifiable {@link Set} of registered players
     */
    public @NotNull Set<String> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Gets the display name of the team.
     *
     * @return the display name
     */
    public @NotNull Component getTeamDisplayName() {
        return teamDisplayName;
    }

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    public @NotNull TeamsPacket.NameTagVisibility getNameTagVisibility() {
        return nameTagVisibility;
    }

    /**
     * Gets the death message visibility of the team.
     *
     * @return the death message visibility
     */
    public @NotNull TeamsPacket.NameTagVisibility getDeathMessageVisibility() {
        return deathMessageVisibility;
    }

    /**
     * Gets the collision rule of the team.
     *
     * @return the collision rule
     */
    public @NotNull TeamsPacket.CollisionRule getCollisionRule() {
        return collisionRule;
    }

    /**
     * Gets the color of the team.
     *
     * @return the team color
     */
    public @NotNull NamedTextColor getTeamColor() {
        return teamColor;
    }

    /**
     * Gets the prefix of the team.
     *
     * @return the team prefix
     */
    public @NotNull Component getPrefix() {
        return prefix;
    }

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix team
     */
    public @NotNull Component getSuffix() {
        return suffix;
    }

    /**
     * Sends an {@link TeamsPacket.UpdateTeamAction} action packet.
     */
    public void sendUpdatePacket() {
        final var info = new TeamsPacket.UpdateTeamAction(teamDisplayName, this.packOptions(),
                nameTagVisibility, collisionRule, teamColor, prefix, suffix);
        PacketUtils.broadcastPlayPacket(new TeamsPacket(teamName, info));
    }

    @Override
    public @NotNull Collection<Player> getPlayers() {
        if (!this.isPlayerMembersUpToDate) {
            this.playerMembers.clear();

            for (String member : this.members) {
                Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(member);

                if (player != null) {
                    this.playerMembers.add(player);
                }
            }

            this.isPlayerMembersUpToDate = true;
        }

        return this.playerMembers;
    }

    @Override
    public @NotNull Pointers pointers() {
        return this.pointers;
    }
}
