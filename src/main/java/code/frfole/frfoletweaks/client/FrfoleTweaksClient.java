package code.frfole.frfoletweaks.client;

import code.frfole.frfoletweaks.FrfoleTweaks;
import code.frfole.frfoletweaks.mixin.TypeAccessor;
import code.frfole.frfoletweaks.util.ReadOnlyMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import org.freedesktop.secret.simple.SimpleCollection;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class FrfoleTweaksClient implements ClientModInitializer {
    public static SimpleCollection simpleCollection = null;

    @Override
    public void onInitializeClient() {
        try {
            simpleCollection = new SimpleCollection("minecraft", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientCommandManager.DISPATCHER.register(ClientAuthCommand.register(ClientCommandManager.literal("clientauth")));
    }
}
