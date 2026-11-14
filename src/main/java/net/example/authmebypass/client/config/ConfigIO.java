package net.example.authmebypass.client.config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
public final class ConfigIO {
 private static final Logger LOG=LogUtils.getLogger();
 private static final Gson GSON=new GsonBuilder().setPrettyPrinting().create();
 private static final Path FILE=FMLPaths.CONFIGDIR.get().resolve("authmebypass.json");
 private static AuthConfig instance=new AuthConfig();
 private ConfigIO(){}
 public static AuthConfig get(){return instance;}
 public static void load(){
  if(!Files.exists(FILE)){instance=new AuthConfig();save();return;}
  try{AuthConfig p=GSON.fromJson(Files.readString(FILE,StandardCharsets.UTF_8),AuthConfig.class);if(p!=null){instance=p;clamp();}}
  catch(IOException|RuntimeException e){LOG.error("Config unreadable; keeping defaults",e);}
 }
 public static void save(){try{Files.createDirectories(FILE.getParent());Files.writeString(FILE,GSON.toJson(instance),StandardCharsets.UTF_8);}catch(IOException e){LOG.error("Could not write config",e);}}
 public static void setPassword(String p){instance.password=SecretBox.seal(p);save();}
 public static void clearPassword(){instance.password="";save();}
 private static void clamp(){instance.maxAttempts=Math.max(1,Math.min(10,instance.maxAttempts));instance.initialDelayTicks=Math.max(1,instance.initialDelayTicks);instance.attemptDelayTicks=Math.max(10,instance.attemptDelayTicks);instance.mode=instance.modeEnum().name();}
}
