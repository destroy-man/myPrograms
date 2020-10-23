import java.io.*;
import javax.servlet.*; //нерабочая библиотека
import javax.servlet.http.*;
public class KathyServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response){
		String title="PhraseMatic has generated the following phrase.";
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<HTML><HEAD><TITLE>");
		out.println("PhraseOMatic");
		out.println("</TITLE></HEAD><BODY>");
		out.println("<H1>"+title+"</H1>");
		out.println("<P>"+PhraseOMatic.makePhrase());
		out.println("<P><a href=\"KathyServlet\">Создать другую фразу</a></p>");
		out.println("</BODY></HTML>");
		out.close();
	}
}