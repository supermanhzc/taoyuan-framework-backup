package com.taoyuan.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.catalina.manager.Constants.CHARSET;

/**
 * IP处理类
 */
@Slf4j
public class TyIpUtil {

    private static final String UNKNOWN = "unknown";

    private static final String LOCAL_IPADDR = "127.0.0.1";

    /**
     * 根据请求获取真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }

            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }

            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL_IPADDR.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("获取本地地址异常:", e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            log.error("获取IP异常:", e);
            return "";
        }

        log.info("id address : {}", ipAddress);
        return ipAddress;
    }



    public static String getAddressByIp(String ip){
        if(StringUtils.isEmpty(ip)){
            return "";
        }

        if(ip.startsWith("127")||ip.startsWith("192")){
            return "本地";
        }

        String source = "http://ip.ws.126.net/ipquery?ip=";
        String url = source+ip;
        try {
            return sendHttpGet(url,null);
        } catch (IOException e) {
            log.info("根据ip获取地址信息异常",e);
        }

        return null;
    }
    /**
     *
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String sendHttpGet(String url, Map<String, Object> params) throws ParseException, UnsupportedEncodingException, IOException{
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
        HttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        if(params !=null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (String key :params.keySet()){
                pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            url +="?"+ EntityUtils.toString(new UrlEncodedFormEntity(pairs), CHARSET);
        }

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = (CloseableHttpResponse) httpclient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode !=200){
            httpGet.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }

        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            response.close();
            String[] values = result.split("\\{")[1].split("\\}")[0].split(",");
            Map<String,String> addrMap = new HashMap<>();
            for(int i=0;i< values.length;i++){
                String[] addrInfs  =values[i].split(":");
                addrMap.put(addrInfs[0].trim(),addrInfs[1].trim().replace("\"",""));
            }
            return addrMap.get("province")+addrMap.get("city");
        }else{
            return null;
        }
    }

    public static void main(String[] args) {
        String ip = "221.232.245.73";
        try {
            String result = sendHttpGet("http://ip.ws.126.net/ipquery?ip="+ip,null);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
