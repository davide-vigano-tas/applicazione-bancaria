package eu.tasgroup.applicativo.aspect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {
	
	
	private Logger logger = Logger.getLogger("log1");
	private FileHandler fileHandler;
	


	@After("execution(* eu.tasgroup.applicativo.controller..*(..) )")
	public void log(JoinPoint jp) {
		 // Recupera il contesto di sicurezza
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Entrato");
        try {
			Path path = Paths.get("C:\\logAspectAppBancaria");
			 System.out.println("Entrato");
			if(Files.notExists(path)) {
				Files.createDirectory(path);
		
			}
			if(logger.getHandlers().length == 0) {
				fileHandler=new FileHandler("C:\\logAspectAppBancaria\\logfile.log", true);
				logger.addHandler(fileHandler);
				logger.setLevel(Level.ALL);
				SimpleFormatter formato = new SimpleFormatter();
				fileHandler.setFormatter(formato);
			}
			logger.log(Level.INFO, "-------------------------------------");
			 
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Controlla se il principal è un'istanza di UserDetails
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();

                    
                    // Recupera i dettagli dell'URL
                    String method = request.getMethod();
                    String requestUrl = request.getRequestURL().toString();
                    Enumeration<String> queryString = request.getParameterNames();
                	
                        
            			logger.log(Level.INFO, "Email utente: "+userDetails.getUsername());
            			logger.log(Level.INFO, "Method: "+method);
            			logger.log(Level.INFO, "Url richiesta: "+requestUrl);
            			
            			while(queryString.hasMoreElements()) {
            				logger.log(Level.INFO, "Parametro "+queryString.nextElement());
            			}
            			
            			Object[] args = jp.getArgs();
            			for(Object object : args) {
            				logger.log(Level.INFO, "Modello :"+object.toString());
            			}
                    
                
                } else {
                	
                	logger.log(Level.WARNING, "Attributes");
                }
                
            }  else {
            	logger.log(Level.WARNING, "Error checking user details");
            }
        } else {
        	logger.log(Level.WARNING, "Not Autenticated");
        }
        
        logger.log(Level.INFO, "-------------------------------------");
        
    	} catch (SecurityException es) {
			logger.log(Level.SEVERE, es.getMessage());
		} catch (IOException eio) {
			logger.log(Level.SEVERE, eio.getMessage());
		} catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

}
