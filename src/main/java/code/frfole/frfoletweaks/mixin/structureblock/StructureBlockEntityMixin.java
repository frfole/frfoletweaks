package code.frfole.frfoletweaks.mixin.structureblock;

import code.frfole.frfoletweaks.FrfoleTweaks;
import code.frfole.frfoletweaks.util.StructureBlockPos;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.Stream;

@Mixin(StructureBlockBlockEntity.class)
public abstract class StructureBlockEntityMixin {
    @Redirect(method = "streamCornerPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;stream(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/util/stream/Stream;"))
    public Stream<BlockPos> onStreamCornerPos(BlockPos start, BlockPos end) {
        return Stream.concat(
                BlockPos.stream(start.add(60, 0, 60), end.add(-60, 0, -60)),
                FrfoleTweaks.STRUCTURE_BLOCKS.stream().map(StructureBlockPos::pos)
        );
    }

    @Redirect(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    public int unclamp(int value, int min, int max) {
        return value;
    }
}
