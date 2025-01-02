package com.hd.hd_backend.utils;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;



class APICaller {
    public static final String API_KEY = "TwJ32wK5MVr9I6eQ9V9ANm7E";
    public static final String SECRET_KEY = "UpqcVhFHbylg44iffY6PzXGhggsIEBeW";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static  sendimage(String []args) throws IOException{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        // image 可以通过 getFileContentAsBase64("C:\fakepath\test.png") 方法获取,如果Content-Type是application/x-www-form-urlencoded时,第二个参数传true
        //RequestBody body = RequestBody.create(mediaType, "image=iVBORw0KGgoAAAANSUhEUgAABpAAAAQaCAYAAABElztWAAAAAXNSR0IArs4c6QAAIABJREFUeF7MvcmuJFmSbSeqau3t3D2ajKys...");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/dish?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());

    }

    /**
     * 获取文件base64编码
     *
     * @param path      文件路径
     * @param urlEncode 如果Content-Type是application/x-www-form-urlencoded时,传true
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64(String path, boolean urlEncode) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, "utf-8");
        }
        return base64;
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

}