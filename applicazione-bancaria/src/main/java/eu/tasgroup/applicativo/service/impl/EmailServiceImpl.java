package eu.tasgroup.applicativo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
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
	public void movimentoEffettuato(String email, MovimentoConto mc) {
        Context context = new Context();
        

        try {
        	
        	Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
        	
        	if(cliente.isPresent()) {
        		Cliente c = cliente.get();
        		context.setVariable("user", c.getNomeCliente()+"  "+c.getCognomeCliente());
             	context.setVariable("email", email);
             	context.setVariable("movimento", mc);

                String processHtml = templateEngine.process("movimento-importante-effettuato", context);
  	      		
  	      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
  	      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
  	            helper.setText(processHtml, true); // true indicates HTML
  	            helper.setTo(c.getEmailCliente());
  	            
  	            helper.setFrom("samuelmastro66@gmail.com");
  	        	if(clientiService.findByEmailCliente(email).isPresent()) {
  	        			
  	        		helper.setSubject("Movimento di "+
  	        		mc.getImporto()+" sul conto "+mc.getConto().getCodConto());
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
	public void sendResetLinkAdmin(String url, String email) {
        Context context = new Context();
        

        try {
        	
        
        	Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
        	if(admin.isPresent()) {
        		context.setVariable("admin", admin.get());
        		context.setVariable("resetUrl", url);
                

                String processHtml = templateEngine.process("admin-reset-mail", context);
  	      		
  	      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
  	      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
  	            helper.setText(processHtml, true); // true indicates HTML
  	            helper.setTo(email);
  	            
  	            helper.setFrom("samuelmastro66@gmail.com");
  	            if(amministratoriService.findByEmailAdmin(email).isPresent()) {
  	    			helper.setSubject("Email di reset password");
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
	public void sendPasswordConfirmationAdmin(String email) {
		  Context context = new Context();
	        

	        try {
	        	
	        
	        	Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
	        	if(admin.isPresent()) {
	        		context.setVariable("admin", admin.get());

	                String processHtml = templateEngine.process("admin-reset-confirmation", context);
	  	      		
	  	      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	  	      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	  	            helper.setText(processHtml, true); // true indicates HTML
	  	            helper.setTo(email);
	  	            
	  	            helper.setFrom("samuelmastro66@gmail.com");
	  	            if(amministratoriService.findByEmailAdmin(email).isPresent()) {
	  	    			helper.setSubject("Password reset");
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
	public void sendResetLinkClient(String url, String email) {
		 Context context = new Context();
	        

	        try {
	        	
	        
	        	Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
	        	if(cliente.isPresent()) {
	        		context.setVariable("user", cliente.get());
	        		context.setVariable("resetUrl", url);
	                

	                String processHtml = templateEngine.process("cliente-reset-mail", context);
	  	      		
	  	      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	  	      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	  	            helper.setText(processHtml, true); // true indicates HTML
	  	            helper.setTo(email);
	  	            
	  	            helper.setFrom("samuelmastro66@gmail.com");
	  	            if(clientiService.findByEmailCliente(email).isPresent()) {
	  	    			helper.setSubject("Email di reset password");
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
	public void sendPasswordConfirmationCliente(String emailCliente) {
		  Context context = new Context();
	        

	        try {
	        	
	        
	        	Optional<Cliente> cliente =clientiService.findByEmailCliente(emailCliente);
	        	if(cliente.isPresent()) {
	        		context.setVariable("user", cliente.get());

	                String processHtml = templateEngine.process("cliente-reset-confirmation", context);
	  	      		
	  	      	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	  	      	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	  	            helper.setText(processHtml, true); // true indicates HTMLemailCliente
	  	            helper.setTo(emailCliente);
	  	            
	  	            helper.setFrom("samuelmastro66@gmail.com");
	  	            if(clientiService.findByEmailCliente(emailCliente).isPresent()) {
	  	    			helper.setSubject("Password reset");
	  	    		} else {
	  	    			
	  	    			return;
	  	    		}
	  	        		mailSender.send(mimeMessage);
	        	}
	           	  
		        	
	   
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
