package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Administrator;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.Post;
import com.hd.hd_backend.entity.Comment;
import com.hd.hd_backend.entity.Notification;
import com.hd.hd_backend.mapper.AdministratorMapper;
import com.hd.hd_backend.mapper.UserMapper;
import com.hd.hd_backend.mapper.PostMapper;
import com.hd.hd_backend.mapper.CommentMapper;
import com.hd.hd_backend.mapper.NotificationMapper;
import com.hd.hd_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Administrator login(Administrator admin) throws Exception {
        if (admin.getPhone() == null || admin.getPhone().trim().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new Exception("密码不能为空");
        }

        Administrator existingAdmin = adminMapper.findByPhone(admin.getPhone());
        if (existingAdmin == null) {
            throw new Exception("管理员不存在");
        }

        if (!existingAdmin.getPassword().equals(admin.getPassword())) {
            throw new Exception("密码错误");
        }

        return existingAdmin;
    }

    @Override
    public void updateAdmin(Integer adminId, Administrator updateInfo) throws Exception {
        Administrator admin = adminMapper.findById(adminId);
        if (admin == null) {
            throw new Exception("管理员不存在");
        }

        if (updateInfo.getPhone() != null && !updateInfo.getPhone().isEmpty()) {
            Administrator existingAdmin = adminMapper.findByPhone(updateInfo.getPhone());
            if (existingAdmin != null && !existingAdmin.getId().equals(adminId)) {
                throw new Exception("手机号已被使用");
            }
        }

        updateInfo.setId(adminId);
        adminMapper.updateAdmin(updateInfo);
    }

    @Override
    public Administrator getAdminById(Integer adminId) throws Exception {
        Administrator admin = adminMapper.findById(adminId);
        if (admin == null) {
            throw new Exception("管理员不存在");
        }
        return admin;
    }

    @Override
    public List<NormalUser> getAllUsers() throws Exception {
        return userMapper.findAllNormalUsers();
    }

    @Override
    public void blockUser(Integer userId) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        user.setIsBlocked(1);
        userMapper.update(user);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setData("您的账号已被管理员封禁");
        notification.setType(1); // 1表示系统通知
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void unblockUser(Integer userId) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        user.setIsBlocked(0);
        userMapper.update(user);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setData("您的账号已被管理员解封");
        notification.setType(1);
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void offendPost(Integer postId) throws Exception {
        Post post = postMapper.findById(postId);
        if (post == null) {
            throw new Exception("帖子不存在");
        }
        post.setIsOffending(1);
        postMapper.updatePost(post);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(post.getUserId());
        notification.setData("您的帖子(标题:" + post.getTitle() + ")已被标记为违规");
        notification.setType(1);
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void unoffendPost(Integer postId) throws Exception {
        Post post = postMapper.findById(postId);
        if (post == null) {
            throw new Exception("帖子不存在");
        }
        post.setIsOffending(0);
        postMapper.updatePost(post);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(post.getUserId());
        notification.setData("您的帖子(标题:" + post.getTitle()  + ")已被取消违规标记");
        notification.setType(1);
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void offendComment(Integer commentId) throws Exception {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            throw new Exception("评论不存在");
        }
        comment.setIsOffending(1);
        commentMapper.updateComment(comment);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(comment.getUserId());
        notification.setData("您的评论(内容:" + comment.getContent() .substring(0,10)+ "……)已被标记为违规");
        notification.setType(1);
        notificationMapper.insertNotification(notification);
    }

    @Override
    public void unoffendComment(Integer commentId) throws Exception {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            throw new Exception("评论不存在");
        }
        comment.setIsOffending(0);
        commentMapper.updateComment(comment);

        // 创建并发送通知
        Notification notification = new Notification();
        notification.setUserId(comment.getUserId());
        notification.setData("您的评论(内容:" + comment.getContent() .substring(0,10)+ "……)已被取消违规标记");
        notification.setType(1);
        notificationMapper.insertNotification(notification);
    }
}
