package code.frfole.frfoletweaks.mixin.datafixerupper;

import code.frfole.frfoletweaks.util.FakeDataFixer;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.Schemas;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(Schemas.class)
public class SchemasMixin {

    @Mutable
    @Shadow @Final private static BiFunction<Integer, Schema, Schema> EMPTY;

    @Mutable
    @Shadow @Final private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void overcinit(CallbackInfo ci) {
        EMPTY = (integer, schema) -> FakeDataFixer.schema;
        EMPTY_IDENTIFIER_NORMALIZE = (integer, schema) -> FakeDataFixer.schema;
    }

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void createFixer(CallbackInfoReturnable<DataFixer> info) {
        info.setReturnValue(new FakeDataFixer());
    }
}
