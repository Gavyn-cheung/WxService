package entity;

public class AccessToken {
	
	private String accessTokenString;
	private long expireTime;
	
	
	public String getAccessToken() {
		return accessTokenString;
	}
	
	public void setAccessToken(String accessTokenString) {
		this.accessTokenString = accessTokenString;
	}
	
	public long getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	public AccessToken(String accessTokenString, String expireIn) {
		super();
		this.accessTokenString = accessTokenString;
		expireTime = System.currentTimeMillis() + Integer.parseInt(expireIn)*1000;
		
	}

	//ÅÐ¶ÏTokenÊÇ·ñ¹ýÆÚ
	public boolean isExpired() {
		return System.currentTimeMillis()>expireTime;
	}
}
