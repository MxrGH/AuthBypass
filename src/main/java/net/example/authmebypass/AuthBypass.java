package net.example.authmebypass;
import com.mojang.logging.LogUtils;
import net.example.authmebypass.client.ClientSetup;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
@Mod(AuthBypass.MOD_ID)
public final class AuthBypass {
 public static final String MOD_ID = "authmebypass";
 public static final String PREFIX = "\u00a7b[AuthBypass]\u00a7r ";
 public static final Logger LOG = LogUtils.getLogger();
 public AuthBypass(IEventBus modBus) { if (FMLEnvironment.getDist() == Dist.CLIENT) ClientSetup.init(); }
}
