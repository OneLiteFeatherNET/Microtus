package net.minestom.server.scoreboard;

import it.unimi.dsi.fastutil.bytes.ByteConsumer;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing Team functionality for Scoreboards")
@ExtendWith(MicrotusExtension.class)
class TeamTest {

    private static final String TEST_NAME = "Minestom Team";

    @Test
    void testInvalidTeamNameUsage() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> Team.builder(""),
                "The team name cannot be empty"
        );
    }

    @Test
    void testBasicTeamCreation() {
        Team.Builder teamBuilder = Team.builder(TEST_NAME);
        assertNotNull(teamBuilder);

        Team team = teamBuilder.build();
        assertInstanceOf(Team.class, team);
        assertInstanceOf(ScoreboardTeam.class, team);

        assertEquals(TEST_NAME, team.getName());
        assertEquals(Component.empty(), team.getTeamDisplayName());
        assertEquals(Component.empty(), team.getPrefix());
        assertEquals(Component.empty(), team.getSuffix());
    }

    @Test
    void testPacketDataSet() {
        Team team = Team.builder(TEST_NAME)
                .collisionRule(TeamsPacket.CollisionRule.NEVER)
                .nameTagVisibility(TeamsPacket.NameTagVisibility.NEVER)
                .build();

        // Standard values are ALWAYS
        assertNotEquals(TeamsPacket.CollisionRule.ALWAYS, team.getCollisionRule());
        assertNotEquals(TeamsPacket.NameTagVisibility.ALWAYS, team.getNameTagVisibility());
    }

    @Test
    void testDeathMessageVisibilityChange() {
        Team team = Team.builder(TEST_NAME)
                .deathMessageVisibility(TeamsPacket.NameTagVisibility.HIDE_FOR_OTHER_TEAMS)
                .build();
        assertNotNull(team);
        // Standard value is ALWAYS
        assertNotEquals(TeamsPacket.NameTagVisibility.ALWAYS, team.getDeathMessageVisibility());
    }

    @Test
    void testTeamMemberLogic(@NotNull Env env) {
        Team team = Team.builder(TEST_NAME)
                .build();

        Instance instance = env.createFlatInstance();

        Player player = env.createPlayer(instance);
        player.setUsernameField("FirstPlayer");
        Player secondPlayer = env.createPlayer(instance);
        secondPlayer.setUsernameField("SecondPlayer");

        team.addMember(player.getUsername());

        assertFalse(team.getMembers().isEmpty());
        assertTrue(team.getMembers().contains(player.getUsername()));
        assertFalse(team.getMembers().contains(secondPlayer.getUsername()));

        team.removeMember(secondPlayer.getUsername());
        assertFalse(team.getMembers().isEmpty());

        team.removeMember(player.getUsername());
        assertTrue(team.getMembers().isEmpty());

        env.destroyInstance(instance, true);

    }

    @Test
    void testTeamAutoRegistration(@NotNull Env env) {
        TeamManager teamManager = env.process().team();
        assertNotNull(teamManager);
        assertFalse(teamManager.exists(TEST_NAME));

        Team team = Team.builder(TEST_NAME)
                .build(true);

        assertTrue(teamManager.exists(team));

        Team givenTeam = teamManager.getTeam(TEST_NAME);
        assertNotNull(givenTeam);
        assertEquals(team, givenTeam);
    }

    @Test
    void testFriendlyFireFlag(@NotNull Env env) {
        Team team = Team.builder(TEST_NAME).build();

        assertNotNull(team);
        assertFalse(team.allowFriendlyFire());
        assertFalse(team.canSeeInvisiblePlayers());

        TeamsPacket packet = team.createTeamsCreationPacket();
        assertTeamCreationFlag(packet, (byte) 0, b -> assertFalse(unpackFriendlyFire(b)));

        team.updateFriendlyFireFlag();

        TeamsPacket updatedTeamPacket = team.createTeamsCreationPacket();
        assertTeamCreationFlag(updatedTeamPacket, (byte) 1, b -> assertTrue(unpackFriendlyFire(b)));
    }

    @Test
    void testInvisibleFlag(@NotNull Env env) {
        Team team = Team.builder(TEST_NAME).build();

        assertNotNull(team);
        assertFalse(team.allowFriendlyFire());
        assertFalse(team.canSeeInvisiblePlayers());

        TeamsPacket packet = team.createTeamsCreationPacket();
        assertTeamCreationFlag(packet, (byte) 0, b -> assertFalse(unpackSeeInvisible(b)));

        team.updateSeeInvisiblePlayersFlag();

        TeamsPacket updatedTeamPacket = team.createTeamsCreationPacket();
        assertTeamCreationFlag(updatedTeamPacket, (byte) 2, b -> assertTrue(unpackSeeInvisible(b)));
    }

    @Test
    void testFlagRemove(@NotNull Env env) {
        Team team = Team.builder(TEST_NAME).allowFriendlyFire().seeInvisiblePlayers().build();

        assertNotNull(team);
        assertTrue(team.allowFriendlyFire());
        assertTrue(team.canSeeInvisiblePlayers());

        TeamsPacket packet = team.createTeamsCreationPacket();
        assertTeamCreationFlag(packet, (byte) 3, id -> {
            assertTrue(unpackFriendlyFire(id));
            assertTrue(unpackSeeInvisible(id));
        });

        team.updateFriendlyFireFlag();

        TeamsPacket updatedTeamPacket = team.createTeamsCreationPacket();
        assertTeamCreationFlag(updatedTeamPacket, (byte) 2, id -> {
            assertFalse(unpackFriendlyFire(id));
            assertTrue(unpackSeeInvisible(id));
        });

        team.updateSeeInvisiblePlayersFlag();

        TeamsPacket removedTeamPacket = team.createTeamsCreationPacket();
        assertTeamCreationFlag(removedTeamPacket, (byte) 0, id -> {
            assertFalse(unpackFriendlyFire(id));
            assertFalse(unpackSeeInvisible(id));
        });
    }

    /**
     * Asserts that the given {@link TeamsPacket} is a {@link TeamsPacket.CreateTeamAction} and that the flag is set correctly.
     *
     * @param teamsPacket the packet to check
     * @param flag        the expected flag
     * @param flagChecker the flag checker
     */
    private void assertTeamCreationFlag(@NotNull TeamsPacket teamsPacket, byte flag, @NotNull ByteConsumer flagChecker) {
        assertNotNull(teamsPacket);
        assertInstanceOf(TeamsPacket.CreateTeamAction.class, teamsPacket.action());

        TeamsPacket.CreateTeamAction givenAction = (TeamsPacket.CreateTeamAction) teamsPacket.action();

        assertEquals(flag, givenAction.friendlyFlags());
        flagChecker.accept(givenAction.friendlyFlags());
    }

    /**
     * Unpacks the friendly fire flag from the given byte.
     * @param flags the flags to unpack
     * @return {@code true} if the friendly fire flag is set
     */
    private boolean unpackFriendlyFire(int flags) {
        return (flags & Team.ALLOW_FRIENDLY_FIRE_BIT) > 0;
    }

    /**
     * Unpacks the see invisible flag from the given byte.
     * @param flags the flags to unpack
     * @return {@code true} if the see invisible flag is set
     */
    private boolean unpackSeeInvisible(int flags) {
        return (flags & Team.SEE_INVISIBLE_PLAYERS_BIT) > 0;
    }
}
