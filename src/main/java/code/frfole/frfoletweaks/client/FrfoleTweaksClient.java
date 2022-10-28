package code.frfole.frfoletweaks.client;

import code.frfole.frfoletweaks.mixin.structureblock.StructureBlockScreenAccessor;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.block.enums.StructureBlockMode;
import org.freedesktop.secret.simple.SimpleCollection;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class FrfoleTweaksClient implements ClientModInitializer {
    public static SimpleCollection simpleCollection = null;

    @Override
    public void onInitializeClient() {
        try {
            simpleCollection = new SimpleCollection("minecraft", null);
            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientAuthCommand.register(ClientCommandManager.literal("clientauth"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StructureBlockScreenAccessor.setMODES_EXCEPT_DATA(ImmutableList.copyOf(StructureBlockMode.values()));
    }
}
