package com.hd.hd_backend.utils;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.common.ResultCallback;
import java.util.Scanner;
import java.util.function.Consumer;

public class LLMCaller {
    public static GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                // 若没有配置环境变量，请用阿里云百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey("sk-8df2b05653a94491a3a0822cc3c447a6")
                // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model("qwen-plus")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
    }

    /**
     * 创建流式输出的参数
     * @param messages 消息列表
     * @return 流式参数
     */
    public static GenerationParam createStreamGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .apiKey("sk-8df2b05653a94491a3a0822cc3c447a6")
                .model("qwen-plus")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();
    }

    public static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        return gen.call(param);
    }

    /**
     * 流式调用LLM
     * @param param 流式参数
     * @param chunkHandler 处理每个chunk的回调函数
     * @return 完整的响应内容
     */
    public static String callStreamGenerationWithMessages(GenerationParam param, Consumer<String> chunkHandler) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        StringBuilder fullContent = new StringBuilder();
        
        // 使用CountDownLatch来等待流式调用完成
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
        Exception[] exceptionHolder = new Exception[1];
        
        gen.streamCall(param, new ResultCallback<GenerationResult>() {
            @Override
            public void onEvent(GenerationResult result) {
                // 如果stream_options.include_usage为True，则最后一个chunk的choices字段为空列表，需要跳过
                if (result.getOutput() != null && result.getOutput().getChoices() != null && !result.getOutput().getChoices().isEmpty()) {
                    String content = result.getOutput().getChoices().get(0).getMessage().getContent();
                    if (content != null) {
                        fullContent.append(content);
                        chunkHandler.accept(content);
                    }
                }
            }
            
            @Override
            public void onError(Exception e) {
                // 处理错误
                System.err.println("Stream call error: " + e.getMessage());
                exceptionHolder[0] = e;
                latch.countDown();
            }
            
            @Override
            public void onComplete() {
                // 流式调用完成
                System.out.println("Stream call completed");
                latch.countDown();
            }
        });
        
        try {
            // 等待流式调用完成
            latch.await();
            
            // 如果有异常，抛出异常
            if (exceptionHolder[0] != null) {
                throw new RuntimeException("Stream call failed", exceptionHolder[0]);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Stream call interrupted", e);
        }
        
        return fullContent.toString();
    }

    public static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }
}
