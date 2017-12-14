package movimentacao.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/restrito/login")
public class RegistraLoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 2484844735889974166L;
	
	@Override
	protected void doGet(HttpServletRequest req , HttpServletResponse resp)
							throws ServletException , IOException
	{
		String login = req.getParameter("login");
		//resp.getWriter().print("Login: " + login);
		System.out.println("Login: " + login);
	}
}
