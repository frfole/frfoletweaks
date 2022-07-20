package code.frfole.frfoletweaks;

import code.frfole.frfoletweaks.we.FSchematicFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import net.fabricmc.api.ModInitializer;

public class FrfoleTweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        ClipboardFormats.registerClipboardFormat(new FSchematicFormat());
    }
}
