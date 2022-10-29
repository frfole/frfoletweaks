package code.frfole.frfoletweaks.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record StructureBlockPos(Identifier world, BlockPos pos) {
    public StructureBlockPos {
        pos = pos.toImmutable();
    }
}
