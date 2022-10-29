package code.frfole.frfoletweaks;

import code.frfole.frfoletweaks.config.TweaksConfig;
import code.frfole.frfoletweaks.mixin.TypeAccessor;
import code.frfole.frfoletweaks.util.ReadOnlyMap;
import code.frfole.frfoletweaks.util.StructureBlockPos;
import code.frfole.frfoletweaks.we.FSchematicFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.entity.StructureBlockBlockEntity;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class FrfoleTweaks implements ModInitializer {
    public static final TweaksConfig CONFIG = new TweaksConfig(true, false);
    public static final Set<StructureBlockPos> STRUCTURE_BLOCKS = new CopyOnWriteArraySet<>();

    @Override
    public void onInitialize() {
        if (CONFIG.enableFSchematic()) {
            ClipboardFormats.registerClipboardFormat(new FSchematicFormat());
        }
        if (CONFIG.disableDFU()) {
            TypeAccessor.setREWRITE_CACHE(new ReadOnlyMap<>());
            TypeAccessor.setPENDING_REWRITE_CACHE(new ReadOnlyMap<>());
        }

        ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
            if (blockEntity instanceof StructureBlockBlockEntity) {
                STRUCTURE_BLOCKS.add(new StructureBlockPos(world.getRegistryKey().getValue(), blockEntity.getPos()));
            }
        });
        ServerBlockEntityEvents.BLOCK_ENTITY_UNLOAD.register((blockEntity, world) -> {
            if (blockEntity instanceof StructureBlockBlockEntity) {
                STRUCTURE_BLOCKS.remove(new StructureBlockPos(world.getRegistryKey().getValue(), blockEntity.getPos()));
            }
        });
        ServerLifecycleEvents.SERVER_STARTING.register(server -> STRUCTURE_BLOCKS.clear());
    }
}
