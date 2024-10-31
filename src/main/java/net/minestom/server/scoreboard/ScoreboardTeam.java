package net.minestom.server.scoreboard;

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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ScoreboardTeam implements Team {

    /**
     * A collection of all registered entities who are on the team.
     */
    private final Set<String> members;

    /**
     * The registry name of the team.
     */
    private final String teamName;
    /**
     * The display name of the team.
     */
    private Component teamDisplayName;
    /**
     * A BitMask.
     */
    private byte friendlyFlags;
    /**
     * The visibility of the team.
     */
    private TeamsPacket.NameTagVisibility nameTagVisibility;
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

    private final Set<Player> playerMembers = ConcurrentHashMap.newKeySet();
    private boolean isPlayerMembersUpToDate;

    // Adventure
    private final Pointers pointers;

    //TODO: Data Objects to reduce parameters?
    /**
     * Creates a new {@link Team} with the given parameters.
     *
     * @param teamName          the registry name of the team
     * @param teamDisplayName   the display name of the team
     * @param friendlyFlags     a bitmask
     * @param nameTagVisibility the visibility of the team
     * @param collisionRule     the collision rule of the team
     * @param teamColor         the color of the team
     * @param prefix            the prefix of the team
     * @param suffix            the suffix of the team
     */
    ScoreboardTeam(
            @NotNull String teamName,
            @NotNull Component teamDisplayName,
            byte friendlyFlags,
            @NotNull TeamsPacket.NameTagVisibility nameTagVisibility,
            @NotNull TeamsPacket.CollisionRule collisionRule,
            @NotNull NamedTextColor teamColor,
            @NotNull Component prefix,
            @NotNull Component suffix
    ) {
        this.teamName = teamName;
        this.teamDisplayName = teamDisplayName;
        this.friendlyFlags = friendlyFlags;
        this.nameTagVisibility = nameTagVisibility;
        this.collisionRule = collisionRule;
        this.teamColor = teamColor;
        this.prefix = prefix;
        this.suffix = suffix;
        this.members = new CopyOnWriteArraySet<>();

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
    public void updateNameTagVisibility(@NotNull TeamsPacket.NameTagVisibility nameTagVisibility) {
        this.nameTagVisibility = nameTagVisibility;
        sendUpdatePacket();
    }

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule The new collision rule
     */
    public void updateCollisionRule(@NotNull TeamsPacket.CollisionRule collisionRule) {
        this.collisionRule = collisionRule;
        sendUpdatePacket();
    }

    /**
     * Changes the color of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param color The new team color
     * @see #updateTeamColor(NamedTextColor)
     */
    public void setTeamColor(@NotNull NamedTextColor color) {
        this.teamColor = color;
    }

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color The new team color
     */
    public void updateTeamColor(@NotNull NamedTextColor color) {
        this.setTeamColor(color);
        sendUpdatePacket();
    }

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    public void updatePrefix(@NotNull Component prefix) {
        this.prefix = prefix;
        sendUpdatePacket();
    }

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    public void updateSuffix(@NotNull Component suffix) {
        this.suffix = suffix;
        sendUpdatePacket();
    }

    /**
     * Changes the friendly flags of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param flag The new friendly flag
     */
    public void setFriendlyFlags(byte flag) {
        this.friendlyFlags = flag;
    }

    /**
     * Changes the friendly flags of the team and sends an update packet.
     *
     * @param flag The new friendly flag
     */
    public void updateFriendlyFlags(byte flag) {
        this.setFriendlyFlags(flag);
        this.sendUpdatePacket();
    }

    private boolean getFriendlyFlagBit(byte index) {
        return (this.friendlyFlags & index) == index;
    }

    private void setFriendlyFlagBit(byte index, boolean value) {
        if (value) {
            this.friendlyFlags |= index;
        } else {
            this.friendlyFlags &= ~index;
        }
    }

    public void setAllowFriendlyFire(boolean value) {
        this.setFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT, value);
    }

    public void updateAllowFriendlyFire(boolean value) {
        this.setAllowFriendlyFire(value);
        this.sendUpdatePacket();
    }

    public boolean isAllowFriendlyFire() {
        return this.getFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT);
    }

    public void setSeeInvisiblePlayers(boolean value) {
        this.setFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT, value);
    }

    public void updateSeeInvisiblePlayers(boolean value) {
        this.setSeeInvisiblePlayers(value);
        this.sendUpdatePacket();
    }

    public boolean isSeeInvisiblePlayers() {
        return this.getFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT);
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
        final var info = new TeamsPacket.CreateTeamAction(teamDisplayName, friendlyFlags,
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
     * Gets the friendly flags of the team.
     *
     * @return the friendly flags
     */
    public byte getFriendlyFlags() {
        return friendlyFlags;
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
        final var info = new TeamsPacket.UpdateTeamAction(teamDisplayName, friendlyFlags,
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
