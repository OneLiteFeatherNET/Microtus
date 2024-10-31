package net.minestom.server.scoreboard;

import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.UniqueIdUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * An object which manages all the {@link Team}'s
 */
public final class TeamManager {

    /**
     * Represents all registered teams
     */
    private final Set<Team> teams;

    /**
     * Default constructor
     */
    public TeamManager() {
        this.teams = new CopyOnWriteArraySet<>();
    }

    /**
     * Registers a new {@link Team}
     *
     * @param team The team to be registered
     */
    public void registerNewTeam(@NotNull Team team) {
        this.teams.add(team);
        PacketUtils.broadcastPlayPacket(team.createTeamsCreationPacket());
    }

    /**
     * Deletes a {@link Team}
     *
     * @param registryName the registry name of team
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    public boolean deleteTeam(@NotNull String registryName) {
        Team team = this.getTeam(registryName);
        if (team == null) return false;
        return this.deleteTeam(team);
    }

    /**
     * Deletes a {@link Team}
     *
     * @param team the team to be deleted
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    public boolean deleteTeam(@NotNull Team team) {
        return this.teams.removeIf(givenTeams -> {
            if (!givenTeams.equals(team)) return false;
            // Sends to all online players a team destroy packet
            PacketUtils.broadcastPlayPacket(team.createTeamDestructionPacket());
            return true;
        });
    }

    /**
     * Gets a {@link Team} with the given name
     *
     * @param teamName The registry name of the team
     * @return a registered {@link Team} or {@code null}
     */
    public @Nullable Team getTeam(@NotNull String teamName) {
        for (Team team : this.teams) {
            if (team.getName().equals(teamName)) return team;
        }
        return null;
    }

    /**
     * Checks if the given name a registry name of a registered {@link Team}
     *
     * @param teamName The name of the team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    public boolean exists(@NotNull String teamName) {
        for (Team team : this.teams) {
            if (team.getName().equals(teamName)) return true;
        }
        return false;
    }

    /**
     * Checks if the given {@link Team} registered
     *
     * @param team The searched team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    public boolean exists(@NotNull Team team) {
        return this.exists(team.getName());
    }

    /**
     * Gets a {@link List} with all registered {@link Player} in the team
     * <br>
     * <b>Note:</b> The list exclude all entities. To get all entities of the team, you can use {@link #getEntities(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link Player}
     */
    public @NotNull List<String> getPlayers(@NotNull Team team) {
        List<String> players = new ArrayList<>();
        for (String member : team.getMembers()) {
            boolean match = UniqueIdUtils.isUniqueId(member);

            if (!match) players.add(member);
        }
        return players;
    }

    /**
     * Gets a {@link List} with all registered {@link LivingEntity} in the team
     * <br>
     * <b>Note:</b> The list exclude all players. To get all players of the team, you can use {@link #getPlayers(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link LivingEntity}
     */
    public @NotNull List<String> getEntities(@NotNull Team team) {
        List<String> entities = new ArrayList<>();
        for (String member : team.getMembers()) {
            boolean match = UniqueIdUtils.isUniqueId(member);

            if (match) entities.add(member);
        }
        return entities;
    }

    /**
     * Gets a {@link Set} with all registered {@link Team}'s
     *
     * @return a {@link Set} with all registered {@link Team}'s
     */
    public @NotNull Set<Team> getTeams() {
        return this.teams;
    }
}
