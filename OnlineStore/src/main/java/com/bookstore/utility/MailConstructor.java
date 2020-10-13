package com.bookstore.utility;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bookstore.domain.Order;
import com.bookstore.domain.User;

@Component
public class MailConstructor {
	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	public SimpleMailMessage generateTokenEmail(String contextPath, Locale locale, String token, User user,
			boolean newUser) {

		String url = contextPath + "/newUser?token=" + token;
		SimpleMailMessage email = new SimpleMailMessage();
		String message = "";
		if (newUser) {
			message = "\nМоля кликнете на този линк, за да потвърдите вашия имейл и да редактирате информацията за вашия профил."
					+ "\nВие ще получите паролата си в отделен имейл."
					+ "\n\nПоздрави!\nМагазин за книги Suave";
			email.setSubject("Магазин за книги Suave - Нов потребител");
		} else {
			message = "\nМоля кликнете на този линк, за да потвърдите вашия имейл и да смените паролата си.Please click on this link to verify your email and change your password. "
					+ "\nВие ще получите новата си парола в отделен имейл."
					+ "\n\nАко не сте направили тази заявка, може просто да игнорирате това съобщение и паролата ви няма да бъде сменена."
					+ "\n\nПоздрави!\nМазазин за книги Suave";
			email.setSubject("Магазин за книги Suave - Забравена парола");
		}
	
		email.setText(url + message);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;

	}

	public SimpleMailMessage generateNewPassword(User user, String password, Locale locale) {
		String message = "Здравейте,\n\nВашата парола е: " + password
				+ "\n\nПоздрави!\nМагазин за книги Suave";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Магазин за книги Suave - Нова парола");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order, boolean isAdmin,
			Locale locale) {
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("order", order);
		context.setVariable("isAdmin", isAdmin);
		context.setVariable("cartItemList", order.getCartItemList());
		String text = templateEngine.process("orderConfirmationEmailTemplate", context);

		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				if (isAdmin) {
					email.setTo(new InternetAddress(env.getProperty("support.email")));
				} else {
					email.setTo(user.getEmail());
				}
				email.setSubject("Потвърждение на поръчката - " + order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};

		return messagePreparator;
	}

}
