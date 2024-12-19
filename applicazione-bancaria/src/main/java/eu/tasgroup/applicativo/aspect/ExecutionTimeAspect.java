package eu.tasgroup.applicativo.aspect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

	private Logger logger = Logger.getLogger("timelog");
	private FileHandler fileHandler;
	
	//Log tempo di esecuzione delle query
	@Around("execution( * eu.tasgroup.applicativo.repository..*(..))")
	public Object logTime(ProceedingJoinPoint jp) throws Throwable {
			
		long start = System.currentTimeMillis();
		Object pr =jp.proceed();
		long end = System.currentTimeMillis();
		if(end-start >= 2) {	
		  try {
				Path path = Paths.get("C:\\logAspectAppBancariaTime");	 
				if(Files.notExists(path)) {
					Files.createDirectory(path);
			
				}
					fileHandler=new FileHandler("C:\\logAspectAppBancariaTime\\logTime.log", true);
					logger.addHandler(fileHandler);
					logger.setLevel(Level.ALL);
					SimpleFormatter formato = new SimpleFormatter();
					fileHandler.setFormatter(formato);
			
				logger.log(Level.INFO, "Query "+jp.getSignature());
				logger.log(Level.INFO, "Tempo "+(end - start)+"ms");
				
		  }catch (SecurityException es) {
				logger.log(Level.SEVERE, es.getMessage());
			} catch (IOException eio) {
				logger.log(Level.SEVERE, eio.getMessage());
			} catch(Exception e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
		fileHandler.close();
		return pr;
	}
}
