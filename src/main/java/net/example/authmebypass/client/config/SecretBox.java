package net.example.authmebypass.client.config;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
/** Local config encryption for casual privacy; not a secure OS keystore. */
public final class SecretBox {
 private static final Logger LOG=LogUtils.getLogger();
 private static final String APP_SALT="authmebypass/v1/at-rest";
 private static final SecureRandom RANDOM=new SecureRandom();
 private SecretBox() {}
 private static String material() { return System.getProperty("user.name","?")+'|'+System.getProperty("os.name","?")+'|'+System.getProperty("os.arch","?")+'|'+APP_SALT; }
 private static SecretKey derive(byte[] salt) throws Exception {
  PBEKeySpec spec=new PBEKeySpec(material().toCharArray(),salt,120_000,256);
  try { return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(),"AES"); }
  finally { spec.clearPassword(); }
 }
 public static String seal(String value) {
  if(value==null||value.isEmpty()) return "";
  try { byte[] salt=new byte[16],iv=new byte[12]; RANDOM.nextBytes(salt); RANDOM.nextBytes(iv);
   Cipher c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.ENCRYPT_MODE,derive(salt),new GCMParameterSpec(128,iv));
   byte[] ct=c.doFinal(value.getBytes(StandardCharsets.UTF_8)); Base64.Encoder b=Base64.getEncoder();
   return "v1:"+b.encodeToString(salt)+':'+b.encodeToString(iv)+':'+b.encodeToString(ct);
  } catch(Exception e) { LOG.error("Could not encrypt password",e); return ""; }
 }
 public static String open(String sealed) {
  if(sealed==null||sealed.isEmpty()) return ""; String[] p=sealed.split(":"); if(p.length!=4||!p[0].equals("v1")) return "";
  try { Base64.Decoder b=Base64.getDecoder(); Cipher c=Cipher.getInstance("AES/GCM/NoPadding");
   c.init(Cipher.DECRYPT_MODE,derive(b.decode(p[1])),new GCMParameterSpec(128,b.decode(p[2])));
   return new String(c.doFinal(b.decode(p[3])),StandardCharsets.UTF_8);
  } catch(Exception e) { LOG.warn("Saved password could not be decrypted; set it again with /authbypass password <pw>"); return ""; }
 }
}
