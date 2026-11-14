package net.example.authmebypass.client;
import net.example.authmebypass.client.config.AuthConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
public final class PromptDetector {
 private static List<Pattern> success=List.of(),prompt=List.of(),register=List.of(),failure=List.of();
 private PromptDetector(){}
 public static void rebuild(AuthConfig c){success=compile(c.successPatterns);prompt=compile(c.promptPatterns);register=compile(c.registerPatterns);failure=compile(c.failurePatterns);}
 private static List<Pattern> compile(List<String> src){List<Pattern> out=new ArrayList<>();if(src!=null)for(String s:src){if(s==null||s.isBlank())continue;try{out.add(Pattern.compile(s,Pattern.CASE_INSENSITIVE));}catch(RuntimeException ignored){}}return out;}
 public static String lower(String s){return s==null?"":s.toLowerCase(Locale.ROOT);}
 public static boolean success(String s){return any(success,s);} public static boolean prompt(String s){return any(prompt,s);} public static boolean register(String s){return any(register,s);} public static boolean failure(String s){return any(failure,s);}
 private static boolean any(List<Pattern> ps,String s){for(Pattern p:ps)if(p.matcher(s).find())return true;return false;}
}
