package code.frfole.frfoletweaks;

import code.frfole.frfoletweaks.config.TweaksConfig;
import code.frfole.frfoletweaks.mixin.TypeAccessor;
import code.frfole.frfoletweaks.util.ReadOnlyMap;
import code.frfole.frfoletweaks.we.FSchematicFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.fabricmc.api.ModInitializer;

public class FrfoleTweaks implements ModInitializer {
    public static TweaksConfig CONFIG = new TweaksConfig(true, false);

    @Override
    public void onInitialize() {
        if (CONFIG.enableFSchematic()) {
            ClipboardFormats.registerClipboardFormat(new FSchematicFormat());
        }
        if (CONFIG.disableDFU()) {
            TypeAccessor.setREWRITE_CACHE(new ReadOnlyMap<>());
            TypeAccessor.setPENDING_REWRITE_CACHE(new ReadOnlyMap<>());
        }
    }
}
