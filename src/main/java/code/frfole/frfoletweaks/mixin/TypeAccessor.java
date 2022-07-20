package code.frfole.frfoletweaks.mixin;

import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mixin(Type.class)
public interface TypeAccessor {
    @Mutable
    @Accessor(remap = false)
    static void setPENDING_REWRITE_CACHE(Map<Triple<Type<?>, TypeRewriteRule, PointFreeRule>, CompletableFuture<Optional<? extends RewriteResult<?, ?>>>> PENDING_REWRITE_CACHE) {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor(remap = false)
    static void setREWRITE_CACHE(Map<Triple<Type<?>, TypeRewriteRule, PointFreeRule>, Optional<? extends RewriteResult<?, ?>>> REWRITE_CACHE) {
        throw new UnsupportedOperationException();
    }
}
