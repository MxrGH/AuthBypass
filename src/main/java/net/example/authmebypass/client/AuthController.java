package net.example.authmebypass.client;
import net.example.authmebypass.client.config.AuthConfig;
import net.example.authmebypass.client.config.ConfigIO;
import net.example.authmebypass.client.config.SecretBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
/** Sends only ordinary AuthMe commands using the password configured by the player. */
public final class AuthController {
 private enum State{IDLE,ARMED,SETTLED}
 private static final Minecraft MC=Minecraft.getInstance();
 private static State state=State.IDLE;
 private static int attempts,countdown=-1;
 private static boolean registerRequested;
 private static String password="";
 private AuthController(){}
 public static void arm(){reset();AuthConfig c=ConfigIO.get();password=SecretBox.open(c.password);state=State.ARMED;if(c.fireOnJoin)countdown=Math.max(1,c.initialDelayTicks);StatusReporter.info(c.hasPassword()?"Ready to respond to AuthMe prompts.":"No password set; authenticate manually or use /authbypass password <pw>.");}
 public static void disarm(){reset();}
 private static void reset(){state=State.IDLE;attempts=0;countdown=-1;registerRequested=false;password="";}
 public static boolean isArmed(){return state==State.ARMED;}
 public static void onChatText(String raw){if(state!=State.ARMED)return;String t=PromptDetector.lower(raw);
  if(PromptDetector.success(t)){state=State.SETTLED;countdown=-1;password="";StatusReporter.good("Server confirmed authentication.");return;}
  if(PromptDetector.failure(t)){AuthConfig c=ConfigIO.get();if(!c.retryOnWrongPassword||attempts>=c.maxAttempts){state=State.SETTLED;countdown=-1;password="";StatusReporter.error("Authentication failed; check your password.");}else{countdown=Math.max(20,c.attemptDelayTicks);StatusReporter.warn("Password rejected; retry scheduled.");}return;}
  if(PromptDetector.register(t)){registerRequested=true;if(countdown<0)countdown=4;return;}
  if(PromptDetector.prompt(t)&&countdown<0)countdown=4;
 }
 public static void tick(){if(state!=State.ARMED||countdown<0||MC.player==null||MC.getConnection()==null)return;if(--countdown>0)return;countdown=-1;AuthConfig c=ConfigIO.get();
  if(password.isEmpty()){StatusReporter.warn("No password configured; please authenticate manually.");state=State.SETTLED;return;}
  if(attempts>=c.maxAttempts){state=State.SETTLED;StatusReporter.error("Maximum attempts reached.");password="";return;}
  String cmd=registerRequested&&c.autoRegister?"register "+password+" "+password:"login "+password;dispatch(cmd,c);registerRequested=false;
 }
 private static void dispatch(String cmd,AuthConfig c){ClientPacketListener conn=MC.getConnection();if(conn==null||MC.player==null){countdown=5;return;}conn.sendCommand(cmd);attempts++;StatusReporter.info("Submitted /"+cmd.split(" ")[0]+" (attempt "+attempts+"/"+c.maxAttempts+").");if(attempts<c.maxAttempts)countdown=Math.max(20,c.attemptDelayTicks);else{state=State.SETTLED;password="";}}
}
