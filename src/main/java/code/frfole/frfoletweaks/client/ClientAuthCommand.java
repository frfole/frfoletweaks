package code.frfole.frfoletweaks.client;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;

import java.util.Random;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class ClientAuthCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(literal("man_full")
                .then(argument("name", StringArgumentType.string()).executes(context -> {
                    String name = StringArgumentType.getString(context, "name");
                    return tryLogin(context, name);
                })));
        builder.then(literal("man")
                .then(argument("name", StringArgumentType.string()).executes(context -> {
                    String name = MinecraftClient.getInstance().getSession().getUsername() + " - " + StringArgumentType.getString(context, "name");
                    return tryLogin(context, name);
                })));
        builder.then(literal("auto").executes(context -> {
            ServerInfo currentServerEntry = MinecraftClient.getInstance().getCurrentServerEntry();
            if (currentServerEntry == null) {
                context.getSource().sendError(Text.of("Not connected to any server"));
            } else {
                String label = MinecraftClient.getInstance().getSession().getUsername() + " - "
                        + currentServerEntry.address;
                return tryLogin(context, label);
            }
            return 0;
        }));
        builder.then(literal("info").executes(context -> {
            ServerInfo currentServerEntry = MinecraftClient.getInstance().getCurrentServerEntry();
            if (currentServerEntry == null) {
                context.getSource().sendError(Text.of("Not connected to any server"));
            } else {
                String label = MinecraftClient.getInstance().getSession().getUsername() + " - "
                        + currentServerEntry.address;
                context.getSource().sendFeedback(Text.of("Current label: " + label));
                return 0;
            }
            return 0;
        }));
        builder.then(literal("reg_man")
                .then(argument("pass", StringArgumentType.word()).executes(context -> {
                    ServerInfo currentServerEntry = MinecraftClient.getInstance().getCurrentServerEntry();
                    if (currentServerEntry == null) {
                        context.getSource().sendError(Text.of("Not connected to any server"));
                    } else {
                        String label = MinecraftClient.getInstance().getSession().getUsername() + " - "
                                + currentServerEntry.address;
                        String pass = StringArgumentType.getString(context, "pass");
                        FrfoleTweaksClient.simpleCollection.createItem(label, pass);
                        context.getSource().getPlayer().sendCommand("register " + pass + " " + pass);
                        context.getSource().sendFeedback(Text.of("You should be registered"));
                        return 0;
                    }
                    return 0;
                })));
        builder.then(literal("reg_rng")
                .then(argument("len", IntegerArgumentType.integer(0, 32)).executes(context -> {
                    ServerInfo currentServerEntry = MinecraftClient.getInstance().getCurrentServerEntry();
                    if (currentServerEntry == null) {
                        context.getSource().sendError(Text.of("Not connected to any server"));
                    } else {
                        String label = MinecraftClient.getInstance().getSession().getUsername() + " - "
                                + currentServerEntry.address;
                        String pass = genRandomString(IntegerArgumentType.getInteger(context, "len"));
                        FrfoleTweaksClient.simpleCollection.createItem(label, pass);
                        context.getSource().getPlayer().sendCommand("register " + pass + " " + pass);
                        context.getSource().sendFeedback(Text.of("You should be registered"));
                        return 0;
                    }
                    return 0;
                })));
        return builder;
    }

    private static int tryLogin(CommandContext<FabricClientCommandSource> context, String name) {
        FrfoleTweaksClient.simpleCollection.getSecrets().entrySet().stream()
                .filter(entry -> FrfoleTweaksClient.simpleCollection.getLabel(entry.getKey()).equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(entry -> {
                    context.getSource().sendFeedback(Text.of("Password found, using"));
                    context.getSource().getPlayer().sendCommand("login " + new String(entry.getValue()));
                }, () -> context.getSource().sendError(Text.of("Unable to find password matching " + name)));
        return 0;
    }

    private static final String RANDOM_STR_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String genRandomString(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(RANDOM_STR_CHARS.charAt(random.nextInt(RANDOM_STR_CHARS.length())));
        }
        return sb.toString();
    }
}
