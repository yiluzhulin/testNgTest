package com.lw.httpclienttest.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
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
    private String host; //主机名+端口
    private ResourceBundle bundle; //获取资源池的工具
    private CookieStore cookieStore; //存放cookie信息

    //预置域名和ResourceBundle
    @BeforeTest
    public void beforeTest() {
        //指定对应的配置文件名，自动在resource文件夹中，寻找后缀为.properties的文件
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        //从配置文件中，获取主机信息
        host = bundle.getString("test.url");
    }

    //获取cookies的get方法
    @Test
    public void getCookiesTest() throws IOException {
        //从配置文件中，拼接请求的URL
        String uri = bundle.getString("test.getCookies.uri");
        String testGetCookiessUrl = this.host + uri;

        //执行get请求
        String result;//用于存放请求返回的数据
        HttpGet get = new HttpGet(testGetCookiessUrl);//创建一个get请求对象
        DefaultHttpClient client = new DefaultHttpClient();//创建一个client对象
        HttpResponse response = client.execute(get);//获取client对象执行后的结果
        //将执行结果转换成utf-8格式的字符串
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);//打印执行结果

        //获取cookies信息
        this.cookieStore = client.getCookieStore();
        List<Cookie> cookieList = this.cookieStore.getCookies();
        //打印cookies信息
        for(Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name:" + name + "; value:" + value);
        }
    }

    //带cookie信息的get请求，需要依赖获取cookie信息的方法
    @Test(dependsOnMethods = "getCookiesTest")
    public void setCookieTest() throws IOException {
        String result;
        String uri = bundle.getString("test.getWithCookies.uri");
        String testGetWithCookiesUrl = host+uri;
        HttpGet get = new HttpGet(testGetWithCookiesUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(this.cookieStore);

        HttpResponse response = client.execute(get);

        //获取请求结果的状态值
        int status = response.getStatusLine().getStatusCode();
        System.out.println("status:" + status);
        //如果状态为200，打印执行结果
        if(status == 200) {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result:" + result);
        }
    }
}
