//
код нерабочий из-за библиотек
//
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MyServletA extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContextType("text/html");
		PrintWriter out=response.getWriter();
		String message="Если вы это читаете, сервлет работает!";
		out.println("<HTML><BODY>");
		out.println("<H1>"+message+"</H1>");
		out.println("</BODY></HTML>");
		out.close();
	}
}