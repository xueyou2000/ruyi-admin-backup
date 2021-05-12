package com.xueyou.admin.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求工具类
 * @author xueyou
 * @date 2021/5/11
 */
@Slf4j
public class HttpUtils {

    public static String request(String url, String data, String menthod, Map<String, String> headers) throws IOException {
        log.info(String.format("HttpUtil::request: %s  params=[%s]", url, data));
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out;
        StringBuilder respContent = new StringBuilder();
        try {
            URL aURL = new URL(url);
            urlConnection = (HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod(menthod);

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);


            urlConnection.setRequestProperty("Connection", "close");
            if (data != null) {
                urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes(StandardCharsets.UTF_8).length));
            }

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            if (data != null) {
                out = new BufferedOutputStream(urlConnection.getOutputStream());
                out.write(data.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            }

            int responseCode = urlConnection.getResponseCode();

            if (responseCode != 200) {
                throw new Exception("请求[" + url + "]失败");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String line;

            while ((line = reader.readLine()) != null) {
                respContent.append(line);
            }
        } catch (Exception e) {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            throw new IOException(e.getMessage());
        }
        urlConnection.disconnect();
        return respContent.toString();
    }

    public static String request(String url, String data, String menthod) throws IOException {
        return request(url, data, menthod, new HashMap<String, String>(){{ put("Content-Type", "text/html"); }});
    }

    public static String request(String url, String menthod) throws IOException {
        return request(url, null, menthod, new HashMap<String, String>(){{ put("Content-Type", "text/html"); }});
    }

    public static String requestJson(String url, String data, String menthod) throws IOException {
        return request(url, data, menthod, new HashMap<String, String>(){{ put("Content-Type", "application/json;charset=UTF-8"); put("Accept", "application/json"); }});
    }

    /**
     * 发起ssl的请求
     * PKCS12证书格式
     * @param url   请求地址
     * @param data  请求数据
     * @param certFilePath  证书地址
     * @param certPassword  证书密码
     */
    public static String reqeuestWichSSL(String url, String data, String certFilePath, String certPassword) throws Exception {
        log.info(String.format("HttpUtil::reqeuestWichSSL: %s  params=[%s]", url, data));
        StringBuilder buffer = new StringBuilder();
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(new FileInputStream(certFilePath), certPassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, certPassword.toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
//        KeyStore trustStore = KeyStore.getInstance("JKS");
//        try {
//            trustStore.load(new FileInputStream(certFilePath), certPassword.toCharArray());// 信任域名证书jssecacerts
//        } catch (Exception e) {
//            trustStore.load(new FileInputStream(certFilePath), certPassword.toCharArray());// 信任域名证书jssecacerts
//        }
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(trustStore);
//        TrustManager[] tms = tmf.getTrustManagers();
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(kms, null, new SecureRandom());
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection) new URL(url).openConnection();

        conn.setSSLSocketFactory(ssf);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();
        // 当有数据需要提交时
        if (StringUtils.isNotBlank(data)) {
            OutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            // 注意编码格式，防止中文乱码
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }
        int code = conn.getResponseCode();

        // 将返回的输入流转换成字符串
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        // 释放资源
        inputStream.close();
        log.info(String.format("HttpUtil::reqeuestWichSSL: %s  response=[%s]", url, buffer));
        conn.disconnect();
        if (code != 200) {
            throw new RuntimeException(buffer.toString());
        }
        return buffer.toString();
    }


}
