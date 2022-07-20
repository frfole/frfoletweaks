package code.frfole.frfoletweaks.client;

import code.frfole.frfoletweaks.Keyring;
import code.frfole.frfoletweaks.client.config.TweaksConfig;
import code.frfole.frfoletweaks.mixin.TypeAccessor;
import code.frfole.frfoletweaks.util.ReadOnlyMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

import java.io.IOException;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class FrfoleTweaksClient implements ClientModInitializer {
    public static final Keyring keyring;
    public static TweaksConfig config = new TweaksConfig(true); // TODO: loader

    static {
        try {
            keyring = new Keyring(Path.of("/", "tmp", "keyring.sock"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onInitializeClient() {
        ClientCommandManager.DISPATCHER.register(ClientAuthCommand.register(ClientCommandManager.literal("clientauth")));

        if (config.disableDFU()) {
            TypeAccessor.setREWRITE_CACHE(new ReadOnlyMap<>());
            TypeAccessor.setPENDING_REWRITE_CACHE(new ReadOnlyMap<>());
        }
    }
}
