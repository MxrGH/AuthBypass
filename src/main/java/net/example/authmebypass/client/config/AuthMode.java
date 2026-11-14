package net.example.authmebypass.client.config;
import java.util.Locale;
public enum AuthMode {
 AUTO, LOGIN;
 public static AuthMode parse(String raw) {
  if (raw == null) return AUTO;
  try { String v = raw.trim().toUpperCase(Locale.ROOT); if (v.equals("FORCELOGIN")) return AUTO; return valueOf(v); }
  catch (IllegalArgumentException e) { return AUTO; }
 }
}
