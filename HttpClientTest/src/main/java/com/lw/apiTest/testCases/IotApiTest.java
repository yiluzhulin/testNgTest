package com.lw.apiTest.testCases;

import com.lw.apiTest.common.SignUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 模拟公版测试平台接口测试机
 */
public class IotApiTest {
    private String server;
    private String productId = "";
    private String productSecret = "";
    private String oem = "";
    private String models = "";
    private String platform = "";
    private String deviceType = "";
    private String version = "";
    private String sdkversion = "";
    private String appversion = "";
    private String networkType = "";
    private String mid = "";
    private String deviceId = "";
    private String deviceSecret = "";
    private String deltaID = "";
    private ResourceBundle bundle;
    private Map<String,String> map = new HashMap<String, String>();

    //预配置
    @BeforeTest
    public void beforetest() {
        this.bundle = ResourceBundle.getBundle("apiResource", Locale.CHINA);
        //配置接口地址
        server = bundle.getString("iotTest");

        //配置项目信息
        this.productId = bundle.getString("productId");
        this.productSecret = bundle.getString("productSecret");
        this.oem = bundle.getString("oem");
        this.models = bundle.getString("models");
        this.platform = bundle.getString("platform");
        this.deviceType = bundle.getString("deviceType");
        this.version = bundle.getString("version");
        this.sdkversion = bundle.getString("sdkversion");
        this.appversion = bundle.getString("appversion");
        this.networkType = bundle.getString("networkType");
        this.mid = bundle.getString("mid");
    }

    /**
     * 注册接口
     */
    @Test
    public void register() {
        //拼接URL
        String url = this.server + "/register/" + this.productId;
        //打印URL
        System.out.println("URL:\n" + url);

        //生命post方法和client
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();

        //设置headers信息
        post.setHeader("content-type","application/json");

        //获取sign
        long timestamp = System.currentTimeMillis()/1000;
        String sign = SignUtils.hexString(this.mid+this.productId+timestamp,this.productSecret);

        System.out.println("timestamp:" + timestamp);
        //添加参数
        JSONObject paras = new JSONObject();
        paras.put("mid",this.mid);
        paras.put("oem",this.oem);
        paras.put("models",this.models);
        paras.put("platform",this.platform);
        paras.put("deviceType",this.deviceType);
        paras.put("timestamp",timestamp);
        paras.put("sign",sign);
        paras.put("sdkversion",this.sdkversion);
        paras.put("appversion",this.appversion);
        paras.put("version",this.version);
        paras.put("networkType",this.networkType);

        //打印请求参数
        String request = paras.toString();
        System.out.println("Request:\n" + request);

        try {
            //配置post请求的参数
            StringEntity entity = new StringEntity(paras.toString(),"utf-8");
            post.setEntity(entity);

            //执行post请求，并获取返回结果
            try {
                HttpResponse response = client.execute(post);
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                int status = response.getStatusLine().getStatusCode();
                if(status == 200) {
                    //打印返回结果
                    System.out.println("Result:\n" + result);

                    //处理返回结果
                    JSONObject responseJson = new JSONObject(result);
                    String msg = responseJson.getString("msg");
                    Assert.assertEquals(msg,"success");
                    this.deviceId = responseJson.getJSONObject("data").getString("deviceId");
                    this.deviceSecret = responseJson.getJSONObject("data").getString("deviceSecret");

                }else {
                    System.out.println("-----注册结果请求失败------");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测结果
     */
    @Test(dependsOnMethods = "register")
    public void checkVersionTest() {
        //拼接URL
        String url = this.server + "/product/" + this.productId + "/" + this.deviceId + "/ota/checkVersion";
        //打印URL
        System.out.println("URL:\n" + url);

        //获取sign
        long timestamp = System.currentTimeMillis()/1000;
        String sign = SignUtils.hexString(this.deviceId+this.productId+timestamp,this.deviceSecret);

        //声明post、client对象
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();

        //设置headers
        post.setHeader("content-type","application/json");


        //添加参数
        JSONObject paras = new JSONObject();
        paras.put("mid",this.mid);
        paras.put("version",this.version);
        paras.put("timestamp",timestamp+"");
        paras.put("sign",sign);

        //配置参数
        try {
            StringEntity entity = new StringEntity(paras.toString(),"utf-8");
            post.setEntity(entity);

            //打印参数
            String request = paras.toString();
            System.out.println("request:\n" + request);

            //执行post请求，并获取返回结果
            try {
                HttpResponse response = client.execute(post);
                String result = EntityUtils.toString(response.getEntity(),"utf-8");

                //打印返回结果
                int status = response.getStatusLine().getStatusCode();
                if(status == 200) {
                    System.out.println("result:\n" + result);
                } else {
                    System.out.println("-----检测请求失败-----");
                }

                //处理返回结果
                JSONObject responseJson = new JSONObject(result);
                String msg = responseJson.getString("msg");
                Assert.assertEquals(msg,"success");
                this.deltaID = responseJson.getJSONObject("data").getJSONObject("version").getString("deltaID");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载结果上报
     */
    @Test(dependsOnMethods = "checkVersionTest")
    public void reportDownResultTest() {
        //拼接URL
        String url = this.server + "/product/" + this.productId + "/" + this.deviceId + "/ota/reportDownResult";
        //打印URL
        System.out.println("URL:\n" + url);

        //声明post、client请求
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();

        //获取sign
        Long timestamp = System.currentTimeMillis()/1000;
        String sign = SignUtils.hexString(this.deviceId+this.productId+timestamp,this.deviceSecret);

        //配置headers
        post.setHeader("content-type","application/json");

        //添加参数
        JSONObject paras = new JSONObject();
        paras.put("mid",this.mid);
        paras.put("deltaID",this.deltaID);
        paras.put("downloadStatus","1");
        paras.put("downStart",1555047771);
        paras.put("downEnd",1655047841);
        paras.put("timestamp",timestamp+"");
        paras.put("sign",sign);

        //配置参数
        try {
            StringEntity entity = new StringEntity(paras.toString(),"utf-8");
            post.setEntity(entity);
            //打印参数
            String request = paras.toString();
            System.out.println("request:\n" + request);

            //执行post请求，并获取返回结果
            try {
                HttpResponse response = client.execute(post);
                String result = EntityUtils.toString(response.getEntity(),"utf-8");

                //打印返回结果
                int status = response.getStatusLine().getStatusCode();
                if(status == 200) {
                    System.out.println("result：\n"+result);
                } else{
                    System.out.println("-----下载上报请求失败:"+status+"------");
                }

                //处理结果
                JSONObject responseJson = new JSONObject(result);
                String msg = responseJson.getString("msg");
                Assert.assertEquals(msg,"success");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 升级结果上报
     */
    @Test(dependsOnMethods = "checkVersionTest")
    public void reportUpgradeResultTest() {
        //拼接URL
        String url = this.server + "/product/" + this.productId + "/" + this.deviceId + "/ota/reportUpgradeResult";
        //打印URL
        System.out.println("URL：\n" + url);

        //声明post、client对象
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();

        //获取sign
        long timestamp = System.currentTimeMillis()/1000;
        String sign = SignUtils.hexString(this.deviceId + this.productId + timestamp,this.deviceSecret);

        //添加参数
        JSONObject paras = new JSONObject();
        paras.put("mid",this.mid);
        paras.put("deltaID",this.deltaID);
        paras.put("updateStatus",1);
        paras.put("timestamp",timestamp);
        paras.put("sign",sign);

        //配置参数
        try {
            StringEntity entity = new StringEntity(paras.toString(),"utf-8");
            post.setEntity(entity);

            //打印参数
            String request = paras.toString();
            System.out.println("request:\n"+request);

            //执行post，并获取测试结果
            try {
                HttpResponse response = client.execute(post);
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                //打印返回结果
                int status = response.getStatusLine().getStatusCode();
                if(status == 200) {
                    System.out.println("result:\n" + result);
                } else {
                    System.out.println("-----升级上报接口请求失败--------");
                }

                //处理返回结果
                JSONObject responseJson = new JSONObject(result);
                String msg = responseJson.getString("msg");
                Assert.assertEquals(msg,"success");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
