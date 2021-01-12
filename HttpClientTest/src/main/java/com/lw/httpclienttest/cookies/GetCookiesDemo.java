package com.lw.httpclienttest.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetCookiesDemo {
    private String url;
    private ResourceBundle bundle;
    private CookieStore cookieStore;
    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void getCookiesTest() throws IOException {
        //从配置文件中，拼接请求的URL
        String uri = bundle.getString("test.getCookies.uri");
        String testGetCookiessUrl = this.url + uri;

        //执行get请求
        String result;
        HttpGet get = new HttpGet(testGetCookiessUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        this.cookieStore = client.getCookieStore();
        List<Cookie> cookieList = this.cookieStore.getCookies();
        for(Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name:" + name + "; value:" + value);
        }
    }

    @Test(dependsOnMethods = "getCookiesTest")
    public void setCookieTest() throws IOException {
        String result;
        String uri = bundle.getString("test.getWithCookies.uri");
        String testGetWithCookiesUrl = url+uri;
        HttpGet get = new HttpGet(testGetWithCookiesUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(this.cookieStore);

        HttpResponse response = client.execute(get);

        int status = response.getStatusLine().getStatusCode();
        System.out.println("status:" + status);
        if(status == 200) {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result:" + result);
        }
    }
}
