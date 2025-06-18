package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.dto.*;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.service.*;
import com.hd.hd_backend.utils.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private WeightService weightService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AdminService adminService;

    private final ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 实例

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("文本消息"); // 输出解析后的用户信息
        String payload = message.getPayload();
        // 解析消息并执行相应的操作
        // 假设消息格式为 "action:payload"
        String[] parts = payload.split(":", 2); // 限制分割为 2 部分
        String action = parts[0];
        


        switch (action) {
            case "register":{
                //userDTO = parseUserDTO(parts[1]);
                NormalUser normalUser =JsonUtils.fromJson(parts[1], NormalUser.class);
                if (normalUser != null) {
                    System.out.println("解析后的用户信息: " + normalUser ); // 输出解析后的用户信息
                    try {
                        NormalUser user = userService.register(normalUser );




                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.REGISTER_SUCCESS.ordinal(),user,"user")) );

                        WebSocketSessionManager.addSession(user.getId(), session);
                        session.getAttributes().put("userId", user.getId());
                        System.out.println(user.getId()); // 输出
                    } catch (Exception e) {
                        e.printStackTrace();
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.REGISTER_FAIL.ordinal(),e.getMessage(),"error_message")));

                    }

                } else {
                    session.sendMessage(new TextMessage("解析用户信息失败"));
                }
                break;}
            case "login":
                User User =JsonUtils.fromJson(parts[1], User.class);
                if (User != null) {
                    System.out.println("解析后的用户信息: " + User); // 输出解析后的用户信息
                    try {

                        User user = userService.login( User);
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.LOGIN_SUCCESS.ordinal(),user,"data")) );
                        WebSocketSessionManager.addSession(user.getId(), session);
                        if(user.getIsAdmin()==0)
                            session.getAttributes().put("userId", user.getId());
                        else
                            session.getAttributes().put("adminId", user.getId());
                        System.out.println(user.getId()); // 输出
                    }catch (Exception e) {
                       e.printStackTrace();
                        // 根据异常消息返回相应的错误码和错误信息
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.LOGIN_FAIL.ordinal(),e.getMessage(),"error_message")));
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.LOGIN_FAIL.ordinal(),"数据格式错误","error_message")));
                }
                break;
            case "getAllFood":
                List<FoodItem> allFood = foodService.getAllFoodItems();
                session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_LIST_SUCCESS.ordinal(),allFood,"data")) );
                break;
            case "getFoodByName":
                if (parts.length > 1) {
                    FoodItem food = foodService.getFoodItemByName(parts[1]);
                    if (food != null) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_GET_SUCCESS.ordinal(),food,"data")) );
                    } else {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_GET_FAIL.ordinal(),"食物未找到","error_message")) );
                    }
                }
                break;
            case "getAllExerciseItem":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_LIST_FAIL.ordinal(),"用户未登录","error_message")) );
                }
                else{
                    try {
                        List<ExerciseItem> exercises= exerciseService.getAllExerciseItem();
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_LIST_SUCCESS.ordinal(),exercises,"data")) );
                    }
                    catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_LIST_FAIL.ordinal(),"食物未找到","error_message")) );
                    }

                }
                break;
            case "addExerciseItem":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_ITEM_ADD_FAIL.ordinal(),"用户未登录","error_message")) );
                }
                else{
                    if (parts.length > 1) {
                        try{
                            ExerciseItem newExerciseItem = JsonUtils.fromJson(parts[1], ExerciseItem.class);
                            exerciseService.addExerciseItem(newExerciseItem);
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_ITEM_ADD_SUCCESS.ordinal(),"添加成功","message")) );

                        }
                        catch (Exception e) {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_ITEM_ADD_FAIL.ordinal(),"添加失败","error_message")) );
                        }

                    }
                }
                break;

            case "addExerciseRecord":
                ExerciseRecord exerciseRecord = JsonUtils.fromJson(parts[1], ExerciseRecord.class);

                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_ADD_FAIL.ordinal(),"用户未登录","error_message")) );
                }
                else{
                    if (parts.length > 1) {
                        try{
                            int user_id= Integer.parseInt(session.getAttributes().get("userId").toString());
                            exerciseRecord.setUserId(user_id);
                            exerciseService.addExerciseRecord(exerciseRecord);
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_ADD_SUCCESS.ordinal(),"success","message")) );

                        }
                        catch (Exception e){
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_ADD_FAIL.ordinal(),"添加失败","error_message")) );
                        }

                    }
                }
                break;

            case "getUserExerciseRecord":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_GET_FAIL.ordinal(),"请先登录","error_message")) );

                }
                else{
                    if (parts.length > 1) {
                        try {
                            int user_id= Integer.parseInt(session.getAttributes().get("userId").toString());
                            List<ExerciseRecordDTO> exercises= exerciseService.getUserExerciseRecord(user_id);
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_GET_SUCCESS.ordinal(),exercises,"data")) );

                        }
                        catch (Exception e) {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_GET_FAIL.ordinal(),"用户未登录","error_message")) );
                            System.out.println(e.getMessage());

                        }
                    }
                    else {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_GET_FAIL.ordinal(),"获取失败","error_message")) );
                    }
                }
                break;
            case "getAllFoodRecord":
                if (parts.length > 1) {
                    int userId = Integer.parseInt(parts[1]);
                    try {
                        List<FoodRecordDTO> foodRecords = foodService.getFoodRecordsByUserId(userId);
                        if (foodRecords != null && !foodRecords.isEmpty()) {

                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_SUCCESS.ordinal(),foodRecords,"data")) );
                        } else {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"未找到食物记录","error_message")));
                        }
                    } catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"获取食物记录失败","error_message")));
                        System.out.println(e.getMessage());
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"缺少参数","error_message")));
                }
                break;
            case "getFoodRecord":
                if (parts.length > 1) {
                    int foodRecordId = Integer.parseInt(parts[1]);
                    try {
                        FoodRecordDTO foodRecord = foodService.getFoodRecordById(foodRecordId);
                        if (foodRecord != null) {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_SUCCESS.ordinal(),foodRecord,"data")) );
                        } else {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"未找到食物记录","error_message")));
                        }
                    } catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"获取食物记录失败","error_message")));
                        System.out.println(e.getMessage());
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_GET_FAIL.ordinal(),"缺少参数","error_message")));
                }
                break;
            case "addFoodRecord":
                if (parts.length > 1) {
                    try {
                        System.out.println("Received data: " + parts[1]);
                        FoodRecord foodRecord = objectMapper.readValue(parts[1], FoodRecord.class);
                        foodService.addFoodRecord(foodRecord);
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_ADD_SUCCESS.ordinal(),"食物记录添加成功","message")) );
                    } catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_ADD_FAIL.ordinal(),e.getMessage(),"error_message")) );
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_ADD_FAIL.ordinal(),"缺少食物记录数据","error_message")) );
                }
                break;
            case "updateFoodRecord":
                if (parts.length > 1) {
                    try {
                        FoodRecord foodRecord = objectMapper.readValue(parts[1], FoodRecord.class);
                        foodService.updateFoodRecord(foodRecord);
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_UPDATE_SUCCESS.ordinal(),"食物记录更新成功","message")) );
                    } catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_UPDATE_FAIL.ordinal(),e.getMessage(),"error_message")) );
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_UPDATE_FAIL.ordinal(),"缺少食物记录数据","error_message")) );
                }
                break;
            case "deleteFoodRecord":
                if (parts.length > 1) {
                    try {
                        int foodRecordId = Integer.parseInt(parts[1]);
                        foodService.deleteFoodRecord(foodRecordId);
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_DELETE_SUCCESS.ordinal(),"食物记录删除成功","message")) );
                    } catch (Exception e) {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")) );
                    }
                } else {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_RECORD_DELETE_FAIL.ordinal(), "缺少食物记录ID","error_message")) );
                }
                break;
            case "updateUser":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.UPDATE_USER_FAIL.ordinal(), "用户未登录","error_message")) );
                    break;
                }
                
                try {
                    // 直接使用NormalUser接收更新信息
                    NormalUser updateInfo = objectMapper.readValue(parts[1], NormalUser.class);
                    int userId = (Integer) session.getAttributes().get("userId");
                    updateInfo.setId(userId); // 设置userId确保更新正确的用户
                    
                    userService.updateUser(userId, updateInfo);
                    
                    // 获取更新后的用户信息
                    NormalUser updatedUser = userService.getUserById(userId);  // 使用service而不是直接访问mapper

                    session.sendMessage(new TextMessage("{\"code\":"+String.valueOf(WebSocketCode.UPDATE_USER_SUCCESS.ordinal())+",\"status\":200,\"message\":\"用户信息更新成功\",\"data\":" + JsonUtils.toJson(updatedUser) + "}"));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.UPDATE_USER_FAIL.ordinal(), e.getMessage(),"error_message")) );
                    e.printStackTrace();
                }
                break;
            case "addFoodItem":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_ADD_FAIL.ordinal(), "用户未登录","error_message")) );
                    break;
                }
                
                try {
                    FoodItem newFoodItem = objectMapper.readValue(parts[1], FoodItem.class);
                    foodService.addFoodItem(newFoodItem);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_ADD_SUCCESS.ordinal(), "食物添加成功","message")) );
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_ADD_FAIL.ordinal(), e.getMessage(),"error_message")) );
                }
                break;

            case "updateFoodItem":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_UPDATE_FAIL.ordinal(), "用户未登录","error_message")) );
                    break;
                }
                
                try {
                    FoodItem updateFoodItem = objectMapper.readValue(parts[1], FoodItem.class);
                    foodService.updateFoodItem(updateFoodItem);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_UPDATE_SUCCESS.ordinal(), "食物更新成功","message")) );
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_UPDATE_FAIL.ordinal(), e.getMessage(),"error_message")) );

                }
                break;

            case "deleteFoodItem":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_DELETE_FAIL.ordinal(), "用户未登录","error_message")) );

                    break;
                }
                
                try {
                    Integer foodId = Integer.parseInt(parts[1]);
                    foodService.deleteFoodItem(foodId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_DELETE_SUCCESS.ordinal(), "食物删除成功","message")) );
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_ITEM_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")) );

                }
                break;
            case "deleteExerciseRecord":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_DELETE_FAIL.ordinal(), "用户未登录","error_message")) );

                    break;
                }

                try {
                    int recordId = Integer.parseInt(parts[1]);
                    exerciseService.deleteExerciseRecord(recordId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_DELETE_SUCCESS.ordinal(),  "运动记录删除成功","message")) );
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.EXERCISE_RECORD_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")) );

                }
                break;
            case "createPost":
                if ((!session.getAttributes().containsKey("userId"))&&(!session.getAttributes().containsKey("adminId"))) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_CREATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Post post = objectMapper.readValue(parts[1], Post.class);
                    if(session.getAttributes().containsKey("userId"))
                        post.setUserId((Integer) session.getAttributes().get("userId"));
                    else
                        post.setUserId((Integer) session.getAttributes().get("adminId"));

                    //post.setTimestamp(time);

                    postService.createPost(post);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_CREATE_SUCCESS.ordinal(), "帖子创建成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_CREATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "deletePost":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_DELETE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    postService.deletePost(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_DELETE_SUCCESS.ordinal(), "帖子删除成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "updatePost":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_UPDATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Post post = objectMapper.readValue(parts[1], Post.class);
                    postService.updatePost(post);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_UPDATE_SUCCESS.ordinal(), "帖子更新成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_UPDATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getVisiblePosts":
                try {
                    List<PostDTO> posts = postService.findVisiblePosts();
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_GET_SUCCESS.ordinal(), posts,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getUserPosts":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_GET_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    List<PostDTO> posts = postService.findUserPosts(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_GET_SUCCESS.ordinal(), posts,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.POST_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "createComment":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_CREATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Comment comment = objectMapper.readValue(parts[1], Comment.class);
                    comment.setUserId((Integer) session.getAttributes().get("userId"));

                    commentService.createComment(comment);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_CREATE_SUCCESS.ordinal(), "评论创建成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_CREATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "deleteComment":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_DELETE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer commentId = Integer.parseInt(parts[1]);
                    commentService.deleteComment(commentId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_DELETE_SUCCESS.ordinal(), "评论删除成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "updateComment":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_UPDATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Comment comment = objectMapper.readValue(parts[1], Comment.class);
                    commentService.updateComment(comment);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_UPDATE_SUCCESS.ordinal(), "评论更新成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_UPDATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getPostComments":
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    List<CommentDTO> comments = commentService.getPostComments(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_GET_SUCCESS.ordinal(), comments,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getUserComments":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_GET_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    List<CommentDTO> comments = commentService.getUserComments(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_GET_SUCCESS.ordinal(), comments,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.COMMENT_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "addWeight":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.WEIGHT_ADD_FAIL.ordinal(),
                        "用户未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    // 创建Weight对象并设置属性
                    Weight weight = new Weight();
                    weight.setUserId((Integer) session.getAttributes().get("userId"));
                    weight.setWeight(Double.parseDouble(parts[1]));
                    
                    weightService.addWeight(weight);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.WEIGHT_ADD_SUCCESS.ordinal(),
                        "体重记录添加成功",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.WEIGHT_ADD_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;
            case "deleteWeight":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_DELETE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    String time = parts[1];
                    weightService.deleteWeight(userId, time);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_DELETE_SUCCESS.ordinal(), "体重记录删除成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "updateWeight":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_UPDATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Weight weight = objectMapper.readValue(parts[1], Weight.class);
                    weight.setUserId((Integer) session.getAttributes().get("userId"));
                    weightService.updateWeight(weight);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_UPDATE_SUCCESS.ordinal(), "体重记录更新成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_UPDATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getUserWeights":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_GET_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    List<Weight> weights = weightService.getUserWeights(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_GET_SUCCESS.ordinal(), weights,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getLatestWeight":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_LATEST_GET_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    Weight weight = weightService.getLatestWeight(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_LATEST_GET_SUCCESS.ordinal(), weight,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.WEIGHT_LATEST_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "identify":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.FOOD_IDENTIFY_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    // 获取图片的base64编码
                    String imageBase64 = parts[1];
                    
                    // 调用百度API识别菜品
                    String result = APICaller.identifyDish(imageBase64, 1);  // 只取置信度最高的结果
                    JSONObject jsonResult = new JSONObject(result);
                    
                    if (jsonResult.has("result") && jsonResult.getJSONArray("result").length() > 0) {
                        JSONObject dish = jsonResult.getJSONArray("result").getJSONObject(0);
                        String dishName = dish.getString("name");
                        
                        // 判断是否为"非菜"
                        if ("非菜".equals(dishName)) {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                                WebSocketCode.FOOD_IDENTIFY_FAIL.ordinal(),
                                "未能识别到食物，请重新拍照",
                                "error_message"
                            )));
                            break;
                        }
                        
                        double calories = dish.getDouble("calorie");
                        
                        // 修改为模糊搜索
                        FoodItem existingFood = foodService.findByNameLike("%" + dishName + "%");
                        
                        if (existingFood != null) {

                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                                    WebSocketCode.FOOD_IDENTIFY_SUCCESS.ordinal(),
                                    existingFood,
                                    "data"
                            )));
                        }
                        
                        // 返回食物信息给前端
                        else {
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                                    WebSocketCode.FOOD_IDENTIFY_FAIL.ordinal(),
                                    "未能识别菜品",
                                    "error_message"
                            )));
                        }

                    } else {
                        session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.FOOD_IDENTIFY_FAIL.ordinal(),
                            "未能识别菜品",
                            "error_message"
                        )));
                    }
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.FOOD_IDENTIFY_FAIL.ordinal(),
                        "识别失败: " + e.getMessage(),
                        "error_message"
                    )));
                }
                break;
            case "createNotification":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_CREATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Notification notification = objectMapper.readValue(parts[1], Notification.class);
                    notification.setUserId((Integer) session.getAttributes().get("userId"));
                    notificationService.createNotification(notification);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_CREATE_SUCCESS.ordinal(), "通知创建成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_CREATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "deleteNotification":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_DELETE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer notificationId = Integer.parseInt(parts[1]);
                    notificationService.deleteNotification(notificationId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_DELETE_SUCCESS.ordinal(), "通知删除成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_DELETE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "updateNotification":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_UPDATE_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Notification notification = objectMapper.readValue(parts[1], Notification.class);
                    notificationService.updateNotification(notification);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_UPDATE_SUCCESS.ordinal(), "通知更新成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_UPDATE_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getUserNotifications":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_GET_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    List<Notification> notifications = notificationService.getUserNotifications(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_GET_SUCCESS.ordinal(), notifications,"data")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_GET_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "markNotificationAsSent":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_MARK_FAIL.ordinal(), "用户未登录","error_message")));
                    break;
                }
                try {
                    Integer notificationId = Integer.parseInt(parts[1]);
                    notificationService.markAsSent(notificationId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_MARK_SUCCESS.ordinal(), "通知标记成功","message")));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(WebSocketCode.NOTIFICATION_MARK_FAIL.ordinal(), e.getMessage(),"error_message")));
                }
                break;
            case "getAllComments":
                try {
                    List<CommentDTO> comments = commentService.getAllComments();
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode. COMMENT_GET_ALL_SUCCESS.ordinal(),
                        comments,
                        "data"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.COMMENT_GET_ALL_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "updateAdmin":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.ADMIN_UPDATE_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Administrator updateInfo = objectMapper.readValue(parts[1], Administrator.class);
                    Integer adminId = (Integer) session.getAttributes().get("adminId");
                    adminService.updateAdmin(adminId, updateInfo);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.ADMIN_UPDATE_SUCCESS.ordinal(),
                            "更新成功",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.ADMIN_UPDATE_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "getAllUsers":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.GET_ALL_USERS_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    List<NormalUser> users = adminService.getAllUsers();
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.GET_ALL_USERS_SUCCESS.ordinal(),
                            users,
                            "data"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.GET_ALL_USERS_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "blockUser":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.BLOCK_USER_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer userId = Integer.parseInt(parts[1]);
                    adminService.blockUser(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.BLOCK_USER_SUCCESS.ordinal(),
                            "封禁成功",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.BLOCK_USER_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "unblockUser":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNBLOCK_USER_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer userId = Integer.parseInt(parts[1]);
                    adminService.unblockUser(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNBLOCK_USER_SUCCESS.ordinal(),
                            "解封成功",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNBLOCK_USER_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "offendPost":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_POST_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    adminService.offendPost(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_POST_SUCCESS.ordinal(),
                            "帖子已标记违规",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_POST_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "unoffendPost":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_POST_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    adminService.unoffendPost(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_POST_SUCCESS.ordinal(),
                            "帖子已解封",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_POST_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "offendComment":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_COMMENT_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer commentId = Integer.parseInt(parts[1]);
                    adminService.offendComment(commentId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_COMMENT_SUCCESS.ordinal(),
                            "评论已标记违规",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.OFFEND_COMMENT_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "unoffendComment":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_COMMENT_FAIL.ordinal(),
                            "管理员未登录",
                            "error_message"
                    )));
                    break;
                }
                try {
                    Integer commentId = Integer.parseInt(parts[1]);
                    adminService.unoffendComment(commentId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_COMMENT_SUCCESS.ordinal(),
                            "评论已解封",
                            "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.UNOFFEND_COMMENT_FAIL.ordinal(),
                            e.getMessage(),
                            "error_message"
                    )));
                }
                break;

            case "getAllPosts":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.POST_GET_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    List<PostDTO> allPosts = postService.getAllPosts();
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.POST_GET_ALL_SUCCESS.ordinal(),
                        allPosts,
                        "data"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.POST_GET_ALL_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "askLLM":
                if (!session.getAttributes().containsKey("userId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.LLM_QUERY_FAIL.ordinal(),
                            "用户未登录",
                            "error_message")));
                    break;
                }
                try {
                    Integer userId = (Integer) session.getAttributes().get("userId");
                    String userQuestion = parts[1];

                    // 从session或数据库获取历史对话（保持最多10轮）
                    List<Message> messages = (List<Message>) session.getAttributes()
                            .computeIfAbsent("llmMessages", k -> new ArrayList<Message>());

                    // 如果是首轮对话，添加系统提示
                    if (messages.isEmpty()) {
                        messages.add(LLMCaller.createMessage(Role.SYSTEM,
                                "你是一个专业的健康顾问，请用中文回答用户关于饮食、运动和健康的问题"));
                    }

                    // 添加用户问题
                    messages.add(LLMCaller.createMessage(Role.USER, userQuestion));

                    // 使用流式输出调用LLM
                    GenerationParam param = LLMCaller.createStreamGenerationParam(messages);
                    
                    // 流式调用LLM，实时发送每个chunk
                    String responseContent = LLMCaller.callStreamGenerationWithMessages(param, chunk -> {
                        try {
                            // 发送每个chunk到前端
                            session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                                    WebSocketCode.LLM_STREAM_CHUNK.ordinal(),
                                    chunk,
                                    "stream_chunk")));
                        } catch (Exception e) {
                            System.err.println("Error sending stream chunk: " + e.getMessage());
                        }
                    });

                    // 保存AI回复到对话历史（同时保持session大小可控）
                    Message aiResponse = LLMCaller.createMessage(Role.ASSISTANT, responseContent);
                    messages.add(aiResponse);
                    if (messages.size() > 20) { // 最多保留10轮对话（USER+ASSISTANT算一轮）
                        messages.subList(1, 3).clear(); // 保留系统提示，移除最早的一轮对话
                    }

                    // 发送流式输出完成信号（在所有chunk发送完成后）
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.LLM_STREAM_COMPLETE.ordinal(),
                            "流式输出完成",
                            "stream_complete")));

                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                            WebSocketCode.LLM_QUERY_FAIL.ordinal(),
                            "处理请求时出错: " + e.getMessage(),
                            "error_message")));
                    // 发生错误时清空对话历史避免状态异常
                    session.getAttributes().remove("llmMessages");
                }
                break;

            // 添加清除对话历史的case
            case "clearLLMHistory":
                session.getAttributes().remove("llmMessages");
                session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.LLM_HISTORY_CLEARED.ordinal(),
                        "对话历史已清除",
                        "message")));
                break;

            default:
                session.sendMessage(new TextMessage("未知操作"));
        }
    }






}