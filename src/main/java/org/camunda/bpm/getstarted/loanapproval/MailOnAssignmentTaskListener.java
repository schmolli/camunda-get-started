package org.camunda.bpm.getstarted.loanapproval;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;

public class MailOnAssignmentTaskListener implements TaskListener {

	private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS-ASSIGNMENT");

	public void notify(DelegateTask delegateTask) {
		
		if (delegateTask.getAssignee() != null) {
			
			IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
			User user = identityService.createUserQuery().userId(delegateTask.getAssignee()).singleResult();
			LOGGER.info("Assigned task '" + delegateTask.getName() + "' to '" + delegateTask.getAssignee() + "'");
			
			//We try to send an e-mail if an address is stored for the assignee (Admin), 
			//the address is not empty (length!=0) and 
			//if the addres does not contain the string "camunda" (default mail adresses).
			if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().contains("camunda")) {
				
				// Email for the sender i.e. the process engine
				String from = "YourAlias@gmail.com";
				final String username = "";// change accordingly
				final String password = "";// change accordingly

				// Assuming you are sending email through smtp.gmail.com - otherwise use settings of your provider.
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				try {
					//Important: Update E-Mail-Addresses of Peter, Mary, John with Camunda's Admin-Web application
					//Use an E-Mail-Address that can be checked by you!
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from, "ProcessEngine Martin"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
					message.setSubject("You got assigned for: "+ delegateTask.getName());
					message.setText("Dear " +user.getFirstName()+",\n\n You got assigned for a new task: "+delegateTask.getName());

					Transport.send(message);

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				LOGGER.info("E-Mail was sent to (" + delegateTask.getAssignee() + ") " + user.getEmail());
				
			} else {
				LOGGER.info("No (valid) e-mail address available for " + delegateTask.getAssignee() + ". No e-mail could be sent!");
			}
		}else{
			LOGGER.info("Assigned task '" + delegateTask.getName() + "' to nobody.");
		}
			
	}
}
