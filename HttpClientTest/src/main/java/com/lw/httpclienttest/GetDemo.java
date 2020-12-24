package com.lw.httpclienttest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetDemo {
    @Test
    public void getTest() throws IOException {
        //用来存放返回的数据
        String result;
        //创建一个get方法，并指定访问的域名
        HttpGet get = new HttpGet("http://www.baidu.com");
        //创建client去执行get方法
        HttpClient client = new DefaultHttpClient();
        //client执行get方法，其返回值是一个HttpRespinse,该方法有一个异常需要处理
        HttpResponse response = client.execute(get);
        //getEntity()获取整个响应的内容
        //EntityUtils.toString()将返回内容转换成字符串形式
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

    }
}

