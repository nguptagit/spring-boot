package userapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil  {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	
	/**
	 * @param to
	 * @param subject
	 * @param msg
	 */
	@Async
	public void sendEmail (final String to, final String subject, final String msg) {
		
		try {
			SimpleMailMessage message = new SimpleMailMessage(); 
			message.setTo(to);
			message.setSubject(subject);
			message.setText(msg);
			javaMailSender.send(message);
		} catch (Exception ex) {
			log.error("Error in Sending Email : ",ex);
		}
	}

}
