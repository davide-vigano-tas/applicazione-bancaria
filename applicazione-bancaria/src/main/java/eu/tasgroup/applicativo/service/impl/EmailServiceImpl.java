package eu.tasgroup.applicativo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.service.AmministratoriService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.EmailService;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private AmministratoriService amministratoriService;
    
    @Autowired
    private ClientiService clientiService;

	@Override
	public void tooManyTries(String email) {
        Context context = new Context();
      

	        try {
	        	
	        	List<Amministratore> admins = amministratoriService.findAll().stream()
	        			.filter((a)->!a.getEmailAdmin().equals(email)).toList();
	        	
	        	for(Amministratore a : admins) {
	           	  context.setVariable("admin", a.getNomeAdmin()+"  "+a.getCognomeAdmin());
	           	  context.setVariable("email", email);

	              String processHtml = templateEngine.process("too-many-tries", context);
		      		
		      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
		      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		            helper.setText(processHtml, true); // true indicates HTML
		            helper.setTo(a.getEmailAdmin());
		            
		            helper.setFrom("samuelmastro66@gmail.com");
		        	if(clientiService.findByEmailCliente(email).isPresent()) {
		        			
		        		helper.setSubject("L'utente "+email+" è stato bloccato: troppi tentativi");
		    		} else if(amministratoriService.findByEmailAdmin(email).isPresent()) {
		    			helper.setSubject("L'admin "+email+" è stato bloccato: troppi tentativi");
		    		} else {
		    			
		    			return;
		    		}
		        		mailSender.send(mimeMessage);
		        	}
	   
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	

	}

	@Override
	public void movimentoEffettuato(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificaCredenziali(String email) {
		// TODO Auto-generated method stub

	}

}
