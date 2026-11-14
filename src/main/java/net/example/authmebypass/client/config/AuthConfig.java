package net.example.authmebypass.client.config;
import java.util.ArrayList;
import java.util.List;
public final class AuthConfig {
 public String mode=AuthMode.AUTO.name();
 public String password="";
 public boolean autoRegister=true, fireOnJoin=true, retryOnWrongPassword=true;
 public int maxAttempts=3, initialDelayTicks=20, attemptDelayTicks=60;
 public List<String> successPatterns=new ArrayList<>(List.of("successfully logged in","logged in successfully","you are now logged in","already logged in","successfully registered","you are logged in"));
 public List<String> promptPatterns=new ArrayList<>(List.of("/login","please log ?in","you must log ?in","log ?in to play","not logged in","authenticate","identification","/connexion","/anmelden"));
 public List<String> registerPatterns=new ArrayList<>(List.of("/register","please register","not registered","/inscription","/registrieren"));
 public List<String> failurePatterns=new ArrayList<>(List.of("wrong password","incorrect password","invalid password","password is incorrect"));
 public AuthMode modeEnum(){return AuthMode.parse(mode);}
 public boolean hasPassword(){return password!=null&&!password.isBlank();}
}
