package com.craigsc.hacku2010.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.craigsc.hacku2010.model.SubscriptionModel;

public class StopServlet extends HttpServlet {
	public void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
		if (req.getParameter("From").equalsIgnoreCase("stop")) {
			SubscriptionModel sm = new SubscriptionModel();
			sm.deactivateUser(req.getParameter("From"));
		}
	}
	
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
		if (req.getParameter("From").equalsIgnoreCase("stop")) {
			SubscriptionModel sm = new SubscriptionModel();
			sm.deactivateUser(req.getParameter("From"));
		}
	}
}
