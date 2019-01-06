package test;

import org.junit.jupiter.api.Test;

import service.WxService;

class WxTest {

	@Test
	void testToken() {
		System.out.println(WxService.getAccessToken());
		System.out.println(WxService.getAccessToken());
	}

}
