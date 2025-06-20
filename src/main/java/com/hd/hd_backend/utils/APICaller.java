package com.hd.hd_backend.utils;

import okhttp3.*;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.net.URLEncoder;

public class APICaller {
    private static final String API_KEY = "UE5jOV27ZSU52adlaFg92ggV";
    private static final String SECRET_KEY = "MND3HgJKqFFozpyNYfeJUyAwzdZxwDYT";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 识别图片中的菜品
     * @param imageBase64 图片的base64编码字符串
     * @param topNum 返回结果的数量
     * @return 识别结果的JSON字符串
     */
    public static String identifyDish(String imageBase64, int topNum) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        
        // 构建请求体
        String requestBody = String.format("image=%s&top_num=%d", 
            URLEncoder.encode(imageBase64, "UTF-8"), 
            topNum);
            
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // 构建请求
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/dish?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        // 发送请求并获取响应
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 通过图片URL识别菜品
     * @param imageUrl 图片的URL地址
     * @param topNum 返回结果的数量
     * @return 识别结果的JSON字符串
     */
    public static String identifyDishByUrl(String imageUrl, int topNum) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        
        // 构建请求体
        String requestBody = String.format("url=%s&top_num=%d", 
            URLEncoder.encode(imageUrl, "UTF-8"), 
            topNum);
            
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // 构建请求
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/dish?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        // 发送请求并获取响应
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 获取文件base64编码
     * @param path 文件路径
     * @param urlEncode 是否需要URL编码
     * @return base64编码信息，不带文件头
     */
    public static String getFileContentAsBase64(String path, boolean urlEncode) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, "UTF-8");
        }
        return base64;
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     * @return 鉴权签名（Access Token）
     */
    private static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return new JSONObject(response.body().string()).getString("access_token");
        }
    }

    // 使用示例
//    public static void main(String[] args) {
//        try {
//            // 使用图片文件识别
//            String imageBase64 = getFileContentAsBase64("path/to/your/image.jpg", false);
//            String result1 = identifyDish(imageBase64, 5);
//            System.out.println("File recognition result: " + result1);
//
//            // 使用图片URL识别
//            String result2 = identifyDishByUrl("http://example.com/image.jpg", 5);
//            System.out.println("URL recognition result: " + result2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}