package de.hackerspacebremen.presentation.jsps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.admin.SaveEmailCommand;
import de.hackerspacebremen.commands.admin.SavePushCommand;
import de.hackerspacebremen.presentation.OSNServlet;

@Singleton
public class AdminEmailServlet extends OSNServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 1147832045798741863L;

	@Inject
    private Provider<SaveEmailCommand> saveEmailCommand;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/adminEmail.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final SaveEmailCommand cmd = saveEmailCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
