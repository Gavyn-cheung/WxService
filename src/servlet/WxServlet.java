package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WxService;

/**
 * Servlet implementation class WxServlet
 */
@WebServlet("/WxServlet")
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		   timestamp	时间戳
		   nonce		随机数
		   echostr		随机字符串
		 */
		String signature	= request.getParameter("signature");
		String timestamp 	= request.getParameter("timestamp");
		String nonce 		= request.getParameter("nonce");
		String echostr 		= request.getParameter("echostr");
		
		//验证签名
		if ( WxService.check( timestamp, nonce, signature ) ) {
			System.out.println("connect success");
			//接入成功原样返回echostr
			PrintWriter out = response.getWriter();
			out.print(echostr);
			out.flush();
			out.close();
		}else {
			System.out.println("connect fail");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		
		//处理消息和时间推送
		Map<String, String> requestMap = WxService.parseRequest(request.getInputStream());
		
		//准备回复的数据包
		String responseXml = WxService.getRequest(requestMap);

		PrintWriter out = response.getWriter();
		out.print(responseXml);
		out.flush();
		out.close();
	}

}
