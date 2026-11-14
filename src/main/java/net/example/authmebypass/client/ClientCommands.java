package net.example.authmebypass.client;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.example.authmebypass.client.config.AuthConfig;
import net.example.authmebypass.client.config.AuthMode;
import net.example.authmebypass.client.config.ConfigIO;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
public final class ClientCommands {
 private ClientCommands(){}
 public static void register(RegisterClientCommandsEvent e){e.getDispatcher().register(Commands.literal("authbypass")
  .then(Commands.literal("status").executes(ctx->{AuthConfig c=ConfigIO.get();StatusReporter.info("mode="+c.modeEnum()+", password="+(c.hasPassword()?"set":"unset")+", attempts="+c.maxAttempts+", armed="+AuthController.isArmed());return 1;}))
  .then(Commands.literal("password").then(Commands.argument("value",StringArgumentType.greedyString()).executes(ctx->{ConfigIO.setPassword(StringArgumentType.getString(ctx,"value"));StatusReporter.good("Password saved in local config.");return 1;})))
  .then(Commands.literal("clear").executes(ctx->{ConfigIO.clearPassword();StatusReporter.info("Saved password cleared.");return 1;}))
  .then(Commands.literal("mode").then(Commands.argument("value",StringArgumentType.word()).executes(ctx->{AuthMode m=AuthMode.parse(StringArgumentType.getString(ctx,"value"));ConfigIO.get().mode=m.name();ConfigIO.save();StatusReporter.info("Mode set to "+m);return 1;})))
  .then(Commands.literal("retry").executes(ctx->{AuthController.arm();return 1;})));
 }
}
