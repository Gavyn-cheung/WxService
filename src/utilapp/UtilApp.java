package utilapp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UtilApp {
	
	//��ָ����ַ����post����
	public static String post(String url, String data) {
		
		URL urlObj;
		try {
			urlObj = new URL(url);
			URLConnection openConnection = urlObj.openConnection();
			//����Ϊ�ɷ�������״̬
			openConnection.setDoOutput(true);
			//��ȡ�����
			OutputStream os = openConnection.getOutputStream();
			//д����
			os.write(data.getBytes());
			os.close();
			
			//��ȡ������������
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
	
	//��ָ����ַ����get����
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
