package net.minestom.server.scoreboard.format;

record BlankFormat() implements NumberFormat {

    @Override
    public int id() {
        return 0;
    }
}
