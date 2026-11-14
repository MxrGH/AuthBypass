package net.example.authmebypass.client;
import net.example.authmebypass.client.config.ConfigIO;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
public final class ClientSetup {
 private ClientSetup(){}
 public static void init(){ConfigIO.load();PromptDetector.rebuild(ConfigIO.get());NeoForge.EVENT_BUS.addListener(ClientSetup::onLoggingIn);NeoForge.EVENT_BUS.addListener(ClientSetup::onLoggingOut);NeoForge.EVENT_BUS.addListener(ClientSetup::onChat);NeoForge.EVENT_BUS.addListener(ClientSetup::onTick);NeoForge.EVENT_BUS.addListener(ClientCommands::register);}
 private static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn e){if(net.minecraft.client.Minecraft.getInstance().getCurrentServer()!=null)AuthController.arm();}
 private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut e){AuthController.disarm();}
 private static void onChat(ClientChatReceivedEvent.System e){if(AuthController.isArmed())AuthController.onChatText(e.getMessage().getString());}
 private static void onTick(ClientTickEvent.Post e){AuthController.tick();}
}
