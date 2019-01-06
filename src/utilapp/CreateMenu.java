package utilapp;

import entity.Button;
import entity.ClickButton;
import entity.PhotoOrAlbumButton;
import entity.SubButtton;
import entity.ViewButton;
import net.sf.json.JSONObject;
import service.WxService;

public class CreateMenu {
	
	public static void main(String[] args) {
		
		Button btn = new Button();
		btn.getButton().add( new ClickButton("一级点击","1") );
		btn.getButton().add( new ViewButton("一级链接","http://www.baidu.com") );
		
		SubButtton sb = new SubButtton("SubButton");
		sb.getSub_button().add( new PhotoOrAlbumButton("PhotoOrAlbumButton", "31") );
		sb.getSub_button().add( new ClickButton("点击", "31" ) );
		sb.getSub_button().add( new ViewButton("linker", "http://mail.timelink.cn") );
		
		btn.getButton().add(sb);
		
		JSONObject jsonObj = JSONObject.fromObject(btn);
		System.out.println(jsonObj.toString());
		
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", WxService.getAccessToken());
		String result = UtilApp.post(url, jsonObj.toString());
		System.out.println(result);
	}

}
