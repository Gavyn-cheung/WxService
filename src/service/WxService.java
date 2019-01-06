package service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import entity.AccessToken;
import entity.Article;
import entity.BaseMessage;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.VideoMessage;
import entity.VoiceMessage;
import net.sf.json.JSONObject;
import utilapp.UtilApp;

public class WxService {
	private static final String TOKEN = "timelinkcia";
	private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String APPID = "wxc604f10c60d4f40f";
	private static final String APPSECRET = "24c338c2df5bcb8793a100b3e2370bbe";
	private static AccessToken atToken;
	
	//��ȡAccessToken
	private static void getToken() {
		String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		String tokenStr = UtilApp.get(url);
		JSONObject jsonObj = JSONObject.fromObject(tokenStr);
		String token = jsonObj.getString("access_token");
		String expireIn = jsonObj.getString("expires_in");
		//����Token���󲢱���
		atToken = new AccessToken(token, expireIn);
	}
	
	//���Ⱪ¶��ȡ��AccessToken
	public static String getAccessToken() {
		if( atToken == null || atToken.isExpired() ) {
			getToken();
		}
		return atToken.getAccessToken();
	}
	
	public static boolean check( String timestamp, String nonce, String signature ) {
		String[] strs = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort( strs );
		String str = strs[0] + strs[1] + strs[2];
		String sig = sha1( str );

		return sig.equalsIgnoreCase(signature);
	}
	private static String sha1(String src) {
		try {
			MessageDigest md = MessageDigest.getInstance("sha1");
			byte[] digest = md.digest(src.getBytes());
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
			StringBuilder sb = new StringBuilder();
			for( byte b:digest ) {
				sb.append( chars[(b>>4)&15] );
				sb.append( chars[b&15] );
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> parseRequest( InputStream is ){
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		try {

			Document doc = reader.read(is);

			Element root = doc.getRootElement();

			List<Element> elements = root.elements();
			for ( Element e:elements ) {
				map.put(e.getName(), e.getStringValue());
				
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	//获取不同类型的消息
	public static String getRequest(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String MsgType = requestMap.get("MsgType");
		//消息分类处理
		switch( MsgType ) {
		case "text":
			msg = dealTextMessage( requestMap );
			break;
			
		case "image":
			break;
			
		case "voice":
			break;
			
		case "video":
			break;
			
		case "shortvideo":
			break;
			
		case "location":
			break;
			
		case "link":
			break;
			
		default:
			break;
		}
		//返回数据为XML数据包
		if( msg != null )
			return beanToXml(msg);
		else {
			return null;
		}
	}
	
	private static String beanToXml(BaseMessage msg) {
		XStream stream = new XStream();
		//转换为XML包
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		String xml = stream.toXML(msg);
		return xml;
	}
	
	//文本消息处理
	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		//获取接收到的文本消息
		String recvMsg = requestMap.get("Content");
		
		if ( recvMsg.equals("ͼ��") ) {
			List<Article> articles = new ArrayList<Article>();
			articles.add( new Article("����", "����", "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=weixin&step_word=&hs=0&pn=3&spn=0&di=138782997100&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1879314925%2C1000533753&os=2924127191%2C4164144748&simid=0%2C0&adpicid=0&lpn=0&ln=1860&fr=&fmq=1546701722504_R&fm=hao123&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F59%2F01%2F3357484a1d08743.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Flafij3t_z%26e3Bv54AzdH3Ff7vwtAzdH3F8camma8m_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined", "http://www.hao123.com") );
			NewsMessage nMsg = new NewsMessage( requestMap, articles );
			return nMsg;
		}
		
		//三方数据接口接入
		String sendMsg = thirdPartyProcess( recvMsg );
		
		TextMessage tm = new TextMessage( requestMap, sendMsg );
		return tm;
	}
	
	//三方数据接口返回数据
	private static String thirdPartyProcess(String recvMsg) {
		return "感谢您热心提供资讯，祝生活愉快";
	}
}
