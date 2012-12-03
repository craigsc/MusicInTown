package com.craigsc.hacku2010.servlet;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.craigsc.hacku2010.domain.Subscription;
import com.craigsc.hacku2010.model.SubscriptionModel;


@SuppressWarnings("serial")
public class SubscriptionServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SubscriptionServlet.class.getName());

	public void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws IOException, ServletException {
		String artist = req.getParameter("artist");
		artist = artist.replace(" ", "%20");
		String number = req.getParameter("phone");
		int isUsername = Integer.parseInt(req.getParameter("isUsername"));
		double lat = Double.valueOf(req.getParameter("lat") == null ? "0.0" : req.getParameter("lat"));
		double lon = Double.valueOf(req.getParameter("long") == null ? "0.0" : req.getParameter("long"));
		if (validate(artist, number, lat, lon)) {
			number = formatPhoneNumber(number);
			Subscription newUser;
			if (isUsername == 0) {
				newUser = new Subscription(number, lat, lon, artist, null);
			} else {
				newUser = new Subscription(number, lat, lon, null, artist);
			}
			log.info("New user signed up: " + number + " " + artist + " " 
					+ lat + " " + lon);
			SubscriptionModel sm = new SubscriptionModel();
			if(sm.saveSubscription(newUser)) {
				if (sm.sendVerificationCode(newUser)) {
					req.getSession().setAttribute("phone", number);
					req.getRequestDispatcher("verify.jsp").forward(req, resp);
				} else {
					req.getSession().setAttribute("error", "Oops! Looks like we're having server issues, try again later. Sorry!");
					resp.sendRedirect("");
				}
			} else {
				req.getSession().setAttribute("error", "Oops! Looks like the phone number you entered is already in our system.");
				resp.sendRedirect("");
			}
		} else {
			req.getSession().setAttribute("error", "Oops! We couldn't understand something you submitted, please try again.");
			resp.sendRedirect("");
		}
		
	}
	
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) 
			throws IOException {
		resp.sendRedirect("");
	}
	
	public String formatPhoneNumber(String number) {
		number = number.trim();
		number = number.replace("(", "");
		number = number.replace(")", "");
		number = number.replace("-", "");
		number = number.replace(".", "");
		number = number.replace(" ", "");
		return number;
	}
	
	public boolean validate(String artist, String phone, double lat, double lon) {
		//verify artist
		if (artist == null || artist.trim().length() == 0) {
			return false;
		}
		
		//verify phone
		if (phone != null) {
			String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";  
			CharSequence inputStr = phone;  
			Pattern pattern = Pattern.compile(expression);  
			Matcher matcher = pattern.matcher(inputStr);
			if (!matcher.matches()) {
				return false;
			}
		} else {
			return false;
		}
		
		//verify geo
		if (lat == 0.0 || lon == 0.0) {
			return false;
		}
		return true;
	}
}
