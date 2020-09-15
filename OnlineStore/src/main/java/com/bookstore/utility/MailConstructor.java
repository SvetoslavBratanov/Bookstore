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
			message = "\nPlease click on this link to verify your email and edit your personal information."
					+ "\nYou are going to receive your password in a separate email."
					+ "\n\nKind regards!\nSuave Bookstore";
			email.setSubject("Suave Bookstore - New User");
		} else {
			message = "\nPlease click on this link to verify your email and change your password. "
					+ "\nYou are going to receive your new password in a separate email."
					+ "\n\nIf you haven't done this request, you can simply ignore this message and your password won't be changed."
					+ "\n\nKind regards!\nSuave Bookstore";
			email.setSubject("Suave Bookstore - Forgotten password");
		}
	
		email.setText(url + message);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;

	}

	public SimpleMailMessage generateNewPassword(User user, String password, Locale locale) {
		String message = "Hello,\n\nYour password is: " + password
				+ "\n\nKind regards!\nSuave Bookstore";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Suave Bookstore - New password");
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
				email.setSubject("Order Confirmation - " + order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};

		return messagePreparator;
	}

}
