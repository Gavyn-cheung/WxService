package utilapp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UtilApp {
	
	//向指定地址发送post请求
	public static String post(String url, String data) {
		
		URL urlObj;
		try {
			urlObj = new URL(url);
			URLConnection openConnection = urlObj.openConnection();
			//设置为可发送数据状态
			openConnection.setDoOutput(true);
			//获取输出流
			OutputStream os = openConnection.getOutputStream();
			//写数据
			os.write(data.getBytes());
			os.close();
			
			//获取输入流读数据
			InputStream is = openConnection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while( (len = is.read(b)) != -1 ) {
				sb.append(new String(b, 0, len) );
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//向指定地址发送get请求
	public static String get(String url) {
		try {
			URL urlObj = new URL(url);
			URLConnection openConnection = urlObj.openConnection();
			InputStream is = openConnection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while( (len = is.read(b)) != -1 ) {
				sb.append(new String(b, 0, len) );
			}
			return sb.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
