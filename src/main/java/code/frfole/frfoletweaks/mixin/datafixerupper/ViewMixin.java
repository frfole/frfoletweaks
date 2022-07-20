package code.frfole.frfoletweaks.mixin.datafixerupper;

import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(View.class)
public class ViewMixin {

    @Inject(method = "create(Lcom/mojang/datafixers/types/Type;Lcom/mojang/datafixers/types/Type;Lcom/mojang/datafixers/functions/PointFree;)Lcom/mojang/datafixers/View;", at = @At("HEAD"), cancellable = true, remap = false)
    private static <A, B> void create(Type<A> type, Type<B> newType, PointFree<Function<A, B>> function, CallbackInfoReturnable<View<A, B>> cir) {
        cir.setReturnValue(null);
    }

    @Inject(method = "create(Ljava/lang/String;Lcom/mojang/datafixers/types/Type;Lcom/mojang/datafixers/types/Type;Ljava/util/function/Function;)Lcom/mojang/datafixers/View;", at = @At("HEAD"), cancellable = true, remap = false)
    private static <A, B> void create(String name, Type<A> type, Type<B> newType, Function<DynamicOps<?>, Function<A, B>> function, CallbackInfoReturnable<View<A, B>> cir) {
        cir.setReturnValue(null);
    }
}
