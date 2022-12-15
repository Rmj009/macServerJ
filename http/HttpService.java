package com.asecl.simdc.org.simdc_project.http;

import com.asecl.simdc.org.simdc_project.exception.QLException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
public class HttpService {


    public void sendGetPOFromITToGetXML(String myname){

        //请求客户端
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();

        //请求参数 param拼接
        StringBuffer param = new StringBuffer();
        param.append("filename="+myname);


        //请求方式
        HttpGet httpGet = new HttpGet("http://localhost:8081/user?" + param);

        //配置httpGet请求的信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) //连接超时时间
                .setConnectionRequestTimeout(5000) //请求超时时间
                .setSocketTimeout(5000) //socket读写超时时间
                .setRedirectsEnabled(true) //是否允许重定向
                .build();

        httpGet.setConfig(requestConfig);

        //响应模型
        CloseableHttpResponse response = null;

        try {

            //发起请求
            response = closeableHttpClient.execute(httpGet);

            //请求内容
            HttpEntity httpEntity = response.getEntity();

            System.out.println("请求状态: " + response.getStatusLine());
            System.out.println("请求内容: " + EntityUtils.toString(httpEntity));

        }catch (Exception e){
            throw new QLException(e);
        }finally {

            try {

                if (closeableHttpClient != null){
                    closeableHttpClient.close();
                }
                if (response != null){
                    response.close();
                }
            }catch (Exception e){
                throw new QLException(e);
            }

        }
    }


    public int sendPostPOFromITToGetXML(String po , String lotcode , Integer count){

        int itfile=0;
        //客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //请求参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("PO", po));
        params.add(new BasicNameValuePair("LotCode", lotcode));
        params.add(new BasicNameValuePair("Count", count.toString()));


        //uri
        URI uri = null;

        try {

            uri = new URIBuilder().setScheme("http").setHost("localhost")
                    .setPort(8081).setPath("it")
                    .setParameters(params).build();
        }catch (Exception e){
            throw new QLException(e);
        }

        //请求方式
        HttpPost httpPost = new HttpPost(uri);

        //响应模型
        CloseableHttpResponse response = null;

        try {

            response = httpClient.execute(httpPost);

            //响应内容
            HttpEntity httpEntity = response.getEntity();

            System.out.println("响应状态: " + response.getStatusLine());
            System.out.println("响应内容: " + EntityUtils.toString(httpEntity));

            itfile = response.getStatusLine().getStatusCode();
        }catch (Exception e){
            throw new QLException(e);
        }finally {

            try {

                if (httpClient != null){
                    httpClient.close();
                }
                if (response != null){
                    response = null;
                }
            }catch (Exception e){
                throw new QLException(e);
            }
        }

        return itfile;
    }
   
}
