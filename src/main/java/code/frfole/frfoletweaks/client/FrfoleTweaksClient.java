package code.frfole.frfoletweaks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
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
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientAuthCommand.register(ClientCommandManager.literal("clientauth")));
        });
    }
}
