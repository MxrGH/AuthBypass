package net.example.authmebypass.client;
import net.example.authmebypass.AuthBypass;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
public final class StatusReporter {
 private static final Minecraft MC=Minecraft.getInstance();
 private StatusReporter(){}
 private static void chat(String s,ChatFormatting c){if(MC.gui!=null)MC.showDebugChat(Component.literal(AuthBypass.PREFIX+s).withStyle(c));}
 public static void info(String s){AuthBypass.LOG.info(s);chat(s,ChatFormatting.GRAY);} public static void good(String s){AuthBypass.LOG.info(s);chat(s,ChatFormatting.GREEN);} public static void warn(String s){AuthBypass.LOG.warn(s);chat(s,ChatFormatting.YELLOW);} public static void error(String s){AuthBypass.LOG.error(s);chat(s,ChatFormatting.RED);}
}
