import java.net.Socket;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.net.ServerSocket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpProxy extends HttpServlet{

	private static ServerSocket serverSide;
	private static Socket clientSide;
	private static final int SOCKET_PORT = 8080;
	private static HttpServletRequest httpRequest;
	private static HttpServletResponse httpResponse;
	private static PrintWriter out;
	
	public static void main(String[] args){
		
		try {
			HttpProxy http = new HttpProxy();
			
			serverSide = new ServerSocket(SOCKET_PORT);
			
			System.out.println("Listening from port " + SOCKET_PORT + "...");
			
			while(true) {
				clientSide = serverSide.accept();
				//System.out.println("Entrou");
				http.printHeaders();
			}
			
		}
		catch (Exception e){
			System.err.println("Server connection error: " + e.getMessage());
		}		
	}
	
	public void printHeaders() {
		try{
			clientSide.setReuseAddress(true);//To drop and reopen the port after the program is done
			httpResponse.setContentType("text/html");
			out = httpResponse.getWriter();
			
			Enumeration en = httpRequest.getHeaderNames();
			
			while(en.hasMoreElements()) {
			
				String headerName = (String) en.nextElement();
				
				String headerValue = httpRequest.getHeader(headerName);
			
				out.print("Header name = " + headerName + " Header value = " + headerValue + "<br>");
			}
			
			out.close();
			serverSide.close();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

}
