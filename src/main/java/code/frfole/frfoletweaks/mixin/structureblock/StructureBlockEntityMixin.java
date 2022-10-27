package code.frfole.frfoletweaks.mixin.structureblock;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

@Mixin(StructureBlockBlockEntity.class)
public abstract class StructureBlockEntityMixin {
    // TODO: Find a way to remove positions from this set when the structure block is removed
    private static final Set<BlockPos> BLOCKS = new CopyOnWriteArraySet<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(BlockPos pos, BlockState state, CallbackInfo ci) {
        BLOCKS.add(pos);
    }

    @Redirect(method = "streamCornerPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;stream(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/util/stream/Stream;"))
    public Stream<BlockPos> onStreamCornerPos(BlockPos start, BlockPos end) {
        return Stream.concat(
                BlockPos.stream(start, end),
                BLOCKS.stream()
        );
    }

    @Redirect(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    public int unclamp(int value, int min, int max) {;
        return value;
    }
}
