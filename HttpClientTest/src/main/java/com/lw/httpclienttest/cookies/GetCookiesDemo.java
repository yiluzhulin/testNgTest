package com.lw.httpclienttest.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetCookiesDemo {
    private String host; //主机名+端口
    private ResourceBundle bundle; //获取资源池的工具
    private CookieStore store; //存放cookie信息

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
    public void getCookies() throws IOException {
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
        this.store = client.getCookieStore();
        List<Cookie> cookieList = this.store.getCookies();
        //打印cookies信息
        for(Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name:" + name + "; value:" + value);
        }
    }

    //带cookie信息的get请求，需要依赖获取cookie信息的方法
    @Test(dependsOnMethods = "getCookies")
    public void getWithCookies() throws IOException {
        String result;
        String uri = bundle.getString("test.getWithCookies.uri");
        String testGetWithCookiesUrl = host+uri;
        HttpGet get = new HttpGet(testGetWithCookiesUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(this.store);

        HttpResponse response = client.execute(get);

        //获取请求结果的状态值
        int status = response.getStatusLine().getStatusCode();
        System.out.println("status:" + status);
        //如果状态为200，打印执行结果
        if(status == 200) {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("result:" + result);
        }

        Assert.assertEquals(status,200);
    }

    //带cookies的post方法，需要依赖cookies信息
    @Test(dependsOnMethods = "getCookies")
    public void postWithCookies() throws IOException {
        //拼接URL
        String uri = bundle.getString("test.postWtihCookies.uri");
        String url = this.host + uri;
        //声明一个Client方法，用例执行测试
        DefaultHttpClient client = new DefaultHttpClient();
        //添加一个post请求
        HttpPost post = new HttpPost(url);
        //定义请求头，设置headers
        post.setHeader("content-type","application/json");
        //设置cookie信息
        client.setCookieStore(this.store);
        //设置参数
        JSONObject paras = new JSONObject();
        paras.put("name","daisy");
        paras.put("age",12);
        StringEntity entity = new StringEntity(paras.toString(),"utf-8");
        post.setEntity(entity);
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取执行结果
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result: " + result);
        //处理结果，判断返回结果是否符合预期
        JSONObject resultJson = new JSONObject(result);
        String msg = resultJson.getString("msg");
        int status = resultJson.getInt("status");
        Assert.assertEquals(msg,"success");
        Assert.assertEquals(status,1000);
    }
}
