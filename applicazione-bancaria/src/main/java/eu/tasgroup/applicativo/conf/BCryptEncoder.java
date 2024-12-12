package eu.tasgroup.applicativo.conf;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoder {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String pass) {
        return bCryptPasswordEncoder.encode(pass);
    }
    
    /**
     * Confronta una password non criptata con una password criptata.
     * 
     * @param password password non criptata
     * @param passwordDB password criptata, generalmente presa dal DB
     * @return True se le password corrispondono, altrimenti false
     */
    public static boolean passwordMatch(String password, String passwordDB) {
        return bCryptPasswordEncoder.matches(password, passwordDB);
    }
}
