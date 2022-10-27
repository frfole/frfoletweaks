package code.frfole.frfoletweaks.mixin.structureblock;

import net.minecraft.client.render.block.entity.StructureBlockBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureBlockBlockEntityRenderer.class)
public class StructureBlockEntityRenderer {
    @Inject(method = "getRenderDistance", at = @At("HEAD"), cancellable = true)
    public void getRenderDistance(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(512);
    }
}
