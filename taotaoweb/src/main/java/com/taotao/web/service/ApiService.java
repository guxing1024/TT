package com.taotao.web.service;

import com.taotao.web.httpclient.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description //TODO httpclientService$
 * @Date 18:36
 **/
@Service
public class ApiService implements BeanFactoryAware {


    @Autowired
    private RequestConfig requestConfig;

    private BeanFactory beanFactory;

    /**
     * @Description GET
     * @Param [url]
     * @return java.lang.String
     **/
    public String doGet(String url) throws ClientProtocolException, IOException {

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpclient().execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * @Description 带参数的GET 请求
     * @Param [url, params]
     * @return java.lang.String
     **/
    public String doGet(String url, Map<String, String> params) throws URISyntaxException, IOException {
        // 定义请求的参数
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(),entry.getValue());
        }
        return this.doGet(builder.build().toString());
    }

    /**
     * @Description 带参数的POST
     * @Param [url, params]
     * @return com.taotao.web.httpclient.HttpResult
     **/
    public HttpResult doPost(String url, Map<String, String> params) throws IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (null != params){
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);

        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpclient().execute(httpPost);
           return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * @Description 获取Httpclient 对象
     * @Param []
     * @return org.apache.http.impl.client.CloseableHttpClient
     **/
    public CloseableHttpClient getHttpclient(){
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
