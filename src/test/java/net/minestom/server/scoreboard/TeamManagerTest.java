package net.minestom.server.scoreboard;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class TeamManagerTest {

    private static final String TEAM_NAME = "Test";
    private TeamManager teamManager;

    @BeforeEach
    void setUp() {
        teamManager = new TeamManager();
    }

    @AfterEach
    void tearDown() {
        teamManager = null;
    }

    @Test
    void testTeamDeletionViaName(@NotNull Env env) {
        Team team = Team.builder(TEAM_NAME).build();
        assertFalse(teamManager.deleteTeam("Minestom"));
        teamManager.registerNewTeam(team);
        assertTrue(teamManager.deleteTeam(TEAM_NAME));
    }

    @Test
    void testTeamDeletionViaReference(@NotNull Env env) {
        Team team = Team.builder(TEAM_NAME).build();
        assertFalse(teamManager.deleteTeam(team));

        teamManager.registerNewTeam(team);
        assertTrue(teamManager.deleteTeam(team));
    }

    @Test
    void testNonTeamDeletionViaReference(@NotNull Env env) {
        Team team = Team.builder(TEAM_NAME).build();
        assertFalse(teamManager.deleteTeam(team));
    }

    @Test
    void testGetPlayersFromTeam(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Team team = Team.builder(TEAM_NAME).build();

        for (int i = 0; i < 2; i++) {
            team.addMember(UUID.randomUUID().toString());
        }

        assertEquals(2, team.getMembers().size());

        Set<Player> players = HashSet.newHashSet(3);
        for (int i = 0; i < 3; i++) {
            Player player = env.createPlayer(instance);
            player.setUsernameField("Player" + i);
            players.add(player);
        }

        List<String> playerNames = players.stream().map(Player::getUsername).toList();
        team.addMembers(playerNames);
        assertEquals(5, team.getMembers().size());

        List<String> teamPlayers = teamManager.getPlayers(team);

        assertEquals(3, teamPlayers.size());

        for (int i = 0; i < teamPlayers.size(); i++) {
            assertEquals(playerNames.get(i), teamPlayers.get(i));
        }

        env.destroyInstance(instance, true);
    }

    @Test
    void testGetEntitiesFromTeam(@NotNull Env env) {
        Instance instance = env.createFlatInstance();

        Team team = Team.builder(TEAM_NAME).build();
        Set<Player> players = HashSet.newHashSet(3);
        for (int i = 0; i < 3; i++) {
            Player player = env.createPlayer(instance);
            player.setUsernameField("Player" + i);
            players.add(player);
        }

        assertEquals(3, players.size());

        team.addMembers(players.stream().map(Player::getUsername).collect(Collectors.toSet()));

        UUID randomUUID = UUID.randomUUID();
        team.addMember(randomUUID.toString());

        assertEquals(4, team.getMembers().size());

        List<String> entities = teamManager.getEntities(team);
        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
        assertEquals(randomUUID.toString(), entities.getFirst());

        env.destroyInstance(instance, true);
    }
}
