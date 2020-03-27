package com.dhcc.order.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datetime 2019/7/13 11:57
 */
@Component
public class RestTemplateUtil {

    @Resource
    private RestTemplate restTemplate;

    public String post(String url, String jsonString) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        System.out.println("jsonString=="+jsonString);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        String personInfos = responseEntity.getBody();
        return personInfos;
    }

    public <T> T get(String url, Class<T> responseType) {
        return this.restTemplate.getForObject(url, responseType);
    }

    public String get(String url) {
        return this.get(url, String.class);
    }

    public <T> T get(String url, JSONObject jo, Class<T> responseType) throws URISyntaxException {
        // 创建uri
        URIBuilder builder = null;
        builder = new URIBuilder(url);

        for (String key : jo.keySet()) {
            builder.addParameter(key, jo.get(key).toString());
        }

        URI uri = builder.build();
        return this.restTemplate.getForObject(uri, responseType);
    }

    public String uploadFile(String url, File file, Map<String, Object> params) {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        System.out.println("请求的参数有" + params);
        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        if (params != null && params.size() != 0) {
            for (String key : params.keySet()) {
                form.add(key, params.get(key));
            }
        }
        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        return this.restTemplate.postForObject(url, files, String.class);
    }

    public String downFile() {
        String file_url = "http://gateway/tencent1/cgi-bin/media/get?access_token=PUoiLMoEYyKYw_p2RsD5VGhE9_hksjK-d0K_n2kTIGUvaQNly06kLIdj3fMlo3Q6zHJnVfaUxh8TNCZbz7rBpUWVnqCWgs2MjRCOXNxYOSavwtjm8K04_DHeYOmiWbRJpoQe4YOiX5avjp7Y7fYQ2OdxfKS2-QmFmDExgLjsZrvS1r3WAjajaY5zsx2rUgjXB2NT6hfgGZ8YqdFk0C7KKw&media_id=137lnjQ59VvmqZ8D0JLRlvAqAwyllrpGWs1fub3IyNgVzgvauTawdJ-VWKVHb1ggV";
        /*File file = restTemplate.execute(file_url, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        System.out.println(file.getPath());*/
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(file_url, HttpMethod.GET, entity, byte[].class);
        byte[] imageBytes = response.getBody();
        System.out.println("sd");
        return null;
    }

    public String uploadMetarial(String url, File file) {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentLength(file.length());
        headers.setConnection("Keep-Alive");
        headers.setContentDispositionFormData("media", file.getName());
        headers.setContentLength(file.length());
        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        String result = this.restTemplate.postForObject(url, files, String.class);
        return result;
    }
}
