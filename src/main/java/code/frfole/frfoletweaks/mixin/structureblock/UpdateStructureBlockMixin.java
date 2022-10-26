package code.frfole.frfoletweaks.mixin.structureblock;

import net.minecraft.network.packet.c2s.play.UpdateStructureBlockC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(UpdateStructureBlockC2SPacket.class)
public class UpdateStructureBlockMixin {

    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    public int unclamp(int value, int min, int max) {
        return value;
    }
}
