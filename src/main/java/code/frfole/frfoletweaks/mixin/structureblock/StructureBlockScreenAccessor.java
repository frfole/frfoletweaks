package code.frfole.frfoletweaks.mixin.structureblock;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureBlockScreen.class)
public interface StructureBlockScreenAccessor {
    @Accessor
    @Mutable
    static void setMODES_EXCEPT_DATA(ImmutableList<StructureBlockMode> MODES_EXCEPT_DATA) {
        throw new UnsupportedOperationException();
    }
}
