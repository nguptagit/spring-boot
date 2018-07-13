package userapi.configs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {


	  @Value("${mail.host}")
	  private String HOST;
	  
	  @Value("${mail.port}")
	  private Integer PORT;
	  
	  @Value("${mail.fromAddress}")
	  private String FROM_ADDRESS;
	  
	  @Value("${mail.fromPassword}")
	  private String FROM_PASSWORD;
	  
	  @Value("${mail.tarnsportPortocol}")
	  private String TRANSPORT_PROTOCOL;
	  
	  @Value("${mail.auth}")
	  private String AUTH;
	  
	  @Value("${mail.debug}")
	  private String DEBUG;
	  
	  @Value("${mail.starttlsEenable}")
	  private String STARTTLSENABLE;
	
	  
	  @Bean
	  public JavaMailSender getJavaMailSender() {
	      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	      mailSender.setHost(HOST);
	      mailSender.setPort(PORT);
	      mailSender.setUsername(FROM_ADDRESS);
	      mailSender.setPassword(FROM_PASSWORD);
	      Properties props = mailSender.getJavaMailProperties();
	      props.put("mail.transport.protocol", TRANSPORT_PROTOCOL);
	      props.put("mail.smtp.auth", AUTH);
	      props.put("mail.smtp.starttls.enable", STARTTLSENABLE);
	      props.put("mail.debug", DEBUG);
	      props.put("mail.smtp.starttls.required", STARTTLSENABLE);
	       
	      return mailSender;
	  }
	
}
