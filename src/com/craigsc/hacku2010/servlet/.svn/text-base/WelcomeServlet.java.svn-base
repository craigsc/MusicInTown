package com.craigsc.hacku2010.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.craigsc.hacku2010.domain.Subscription;
import com.craigsc.hacku2010.model.SubscriptionModel;

public class WelcomeServlet extends HttpServlet {
	
	public void doPost(final HttpServletRequest req, final HttpServletResponse resp) 
			throws IOException, ServletException {
		String phone = req.getParameter("phone");
		String verificationCode = req.getParameter("verificationCode");
		SubscriptionModel sm = new SubscriptionModel();
		Subscription user = sm.getSubscription(phone);
		if (user == null) {
			req.getSession().setAttribute("error", "Oops! Something weird happened... just try signing up again please.");
			resp.sendRedirect("");
			return;
		}
		if (user.getVerificationCode().equalsIgnoreCase(verificationCode)) {
			if (sm.setActive(user)) {
				if (user.getArtist() == null) {
					sm.sendEventsSmsForUsername(user);
				} else {
					sm.sendEventsSms(user);
				}
				req.getRequestDispatcher("welcome.jsp").forward(req, resp);
			} else {
				req.getSession().setAttribute("error", "Oops! Errors while activating your account, try again in a few minutes.");
				resp.sendRedirect("verify.jsp");
			}
		} else {
			req.getSession().setAttribute("error", "Incorrect Verification Code");
			resp.sendRedirect("verify.jsp");
		}
	}
	
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) 
			throws IOException {
		resp.sendRedirect("");
	}
}
