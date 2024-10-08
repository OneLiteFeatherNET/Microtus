package net.minestom.server.entity.metadata.monster;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.MetadataHolder;
import org.jetbrains.annotations.NotNull;

public class VexMeta extends MonsterMeta {
    public static final byte OFFSET = MonsterMeta.MAX_OFFSET;
    public static final byte MAX_OFFSET = OFFSET + 1;
    private static final byte ATTACKING_BIT = 0x01;  //Microtus - update java keyword usage

    public VexMeta(@NotNull Entity entity, @NotNull MetadataHolder metadata) {
        super(entity, metadata);
    }

    public boolean isAttacking() {
        return getMaskBit(OFFSET, ATTACKING_BIT);
    }

    public void setAttacking(boolean value) {
        setMaskBit(OFFSET, ATTACKING_BIT, value);
    }

}
