package code.frfole.frfoletweaks.mixin.structureblock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.block.entity.StructureBlockBlockEntity.class)
public class StructureBlockEntityMixin {

    @Redirect(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    public int unclamp(int value, int min, int max) {
        return value;
    }
}
