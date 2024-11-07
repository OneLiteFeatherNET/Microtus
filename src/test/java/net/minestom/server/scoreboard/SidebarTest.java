package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SidebarTest {

    private static final Sidebar.ScoreboardLine TEST_LINE = new Sidebar.ScoreboardLine("testLine", Component.text("Line 1"), 1);
    private static final Component TITLE = Component.text("Minestom SideBar");

    private Sidebar sidebar;

    @BeforeEach
    void setUp() {
        sidebar = new Sidebar(TITLE);
    }

    @AfterEach
    void tearDown() {
        sidebar = null;
    }

    @Test
    void testGetObjectiveNameFromSideBar() {
        assertNotEquals("sb-100000", sidebar.getObjectiveName());
    }

    @Test
    void testAddLineOverMaximum() {
        int maxCount = 15;
        for (int i = 0; i < maxCount; i++) {
            sidebar.createLine(new Sidebar.ScoreboardLine("line" + i, Component.text("Line " + i), i));
        }
        assertThrowsExactly(
                IllegalStateException.class,
                () -> sidebar.createLine(new Sidebar.ScoreboardLine("line", Component.text("Line "), 16)),
                "You cannot add more than " + maxCount + " lines"
        );
    }

    @Test
    void testDuplicationLineSetViaReference() {
        sidebar.createLine(TEST_LINE);
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> sidebar.createLine(TEST_LINE),
                "You cannot add two times the same ScoreboardLine"
        );
    }

    @Test
    void testSideBarTitleChange() {
        assertEquals(TITLE, sidebar.getTitle());

        Component newTitle = Component.text("New Title");
        sidebar.setTitle(newTitle);

        assertEquals(newTitle, sidebar.getTitle());
    }

    @Test
    void testScoreboardLineRemoveWithoutAChange() {
        assertTrue(sidebar.getLines().isEmpty());

        sidebar.createLine(TEST_LINE);

        assertFalse(sidebar.getLines().isEmpty());

        sidebar.removeLine("secondLine");
        assertFalse(sidebar.getLines().isEmpty());
    }

    @Test
    void testScoreboardLineRemove() {
        assertTrue(sidebar.getLines().isEmpty());

        sidebar.createLine(TEST_LINE);
        assertFalse(sidebar.getLines().isEmpty());

        sidebar.removeLine(TEST_LINE.getId());
        assertTrue(sidebar.getLines().isEmpty());
    }

    @Test
    void testScoreboardLineGet() {
        assertTrue(sidebar.getLines().isEmpty());
        sidebar.createLine(TEST_LINE);

        Sidebar.ScoreboardLine line = sidebar.getLine(TEST_LINE.getId());
        assertNotNull(line);
        assertEquals(TEST_LINE, line);

        assertNull(sidebar.getLine("anotherLine"));
    }

    @Test
    void testUpdateLineContentWithoutAResult() {
        String lineId = "line";
        Sidebar.ScoreboardLine line = new Sidebar.ScoreboardLine(lineId, Component.text("Line 1"), 1);
        assertComponentContent("Line 1", line.getContent());
        sidebar.createLine(line);
        assertFalse(sidebar.getLines().isEmpty());

        sidebar.updateLineContent("anotherLine", Component.text("New content"));

        Sidebar.ScoreboardLine updatedLine = sidebar.getLine(lineId);
        assertNotNull(updatedLine);
        assertComponentContent("Line 1", updatedLine.getContent());
    }

    @Test
    void testUpdateLineContent() {
        String lineId = "line";
        Sidebar.ScoreboardLine line = new Sidebar.ScoreboardLine(lineId, Component.text("Line 1"), 1);
        assertComponentContent("Line 1", line.getContent());
        sidebar.createLine(line);
        assertFalse(sidebar.getLines().isEmpty());

        sidebar.updateLineContent(lineId, Component.text("New content"));

        Sidebar.ScoreboardLine updatedLine = sidebar.getLine(lineId);
        assertNotNull(updatedLine);
        assertComponentContent("New content", updatedLine.getContent());
    }

    @Test
    void testLineScoreUpdateWithoutAResult() {
        String lineId = "line";
        Sidebar.ScoreboardLine line = new Sidebar.ScoreboardLine(lineId, Component.text("Line 1"), 1);
        sidebar.createLine(line);
        assertFalse(sidebar.getLines().isEmpty());

        sidebar.updateLineScore("anotherLine", 2);

        Sidebar.ScoreboardLine updatedLine = sidebar.getLine(lineId);
        assertNotNull(updatedLine);
        assertEquals(1, updatedLine.getLine());
    }

    @Test
    void testLineScoreUpdate() {
        String lineId = "line";
        Sidebar.ScoreboardLine line = new Sidebar.ScoreboardLine(lineId, Component.text("Line 1"), 1);
        sidebar.createLine(line);
        assertFalse(sidebar.getLines().isEmpty());

        sidebar.updateLineScore(lineId, 300);

        Sidebar.ScoreboardLine updatedLine = sidebar.getLine(lineId);
        assertNotNull(updatedLine);
        assertEquals(300, updatedLine.getLine());
    }

    /**
     * Asserts that the component content is equal to the expected string
     *
     * @param expected  the expected string
     * @param component the component to check
     */
    private void assertComponentContent(@NotNull String expected, @NotNull Component component) {
        assertEquals(expected, PlainTextComponentSerializer.plainText().serialize(component));
    }
}
