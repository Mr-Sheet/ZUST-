package com.zjtec.travel.service.impl;

import com.zjtec.travel.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private final static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    // ---------------------- QQ邮箱硬编码配置（替换原139配置）----------------------
    private String smtpHost = "smtp.qq.com"; // QQ邮箱SMTP服务器地址
    private String username = "2035745264@qq.com"; // 你的QQ邮箱（例如：12345678@qq.com）
    private String password = "gspeyhpstjwacdha"; // 第一步获取的16位授权码（不是QQ登录密码！）
    private String smtpAuth = "true"; // 启用SMTP身份验证（必需）
    private String smtpPort = "465"; // QQ邮箱SMTP SSL端口（固定465，不可改）
    private String smtpSslEnable = "true"; // 启用SSL加密（QQ邮箱强制要求）

    /**
     * 发送邮件
     * @param sendTo 对方邮箱地址（可以填你的139邮箱测试）
     * @param title 邮件标题
     * @param content 邮件内容（纯文本，避免HTML，易通过审核）
     */
    @Override
    public void sendEmail(String sendTo, String title, String content) {
        try {
            final Properties props = new Properties();
            // 基础配置（保留原逻辑）
            props.put("mail.smtp.auth", smtpAuth);
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.user", username);
            props.put("mail.password", password);

            // ---------------------- 新增QQ邮箱必需的SSL配置 ----------------------
            props.put("mail.smtp.port", smtpPort); // SSL端口465
            props.put("mail.smtp.ssl.enable", smtpSslEnable); // 强制启用SSL
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 指定SSL工厂类
            props.put("mail.smtp.socketFactory.port", smtpPort); // SSL端口与上面一致
            // ---------------------------------------------------------------------

            // 构建授权信息，用于SMTP身份验证（保留原逻辑）
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // 创建邮件会话（保留原逻辑）
            Session mailSession = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(mailSession);

            // 设置发件人（保留原逻辑）
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人（保留原逻辑）
            InternetAddress toAddress = new InternetAddress(sendTo);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题（建议简化，无特殊字符）
            message.setSubject(title);

            // ---------------------- 优化邮件内容（避免被判定为垃圾邮件）----------------------
            message.setContent(content, "text/plain;charset=UTF-8"); // 改为纯文本（text/plain），而非HTML
            // ---------------------------------------------------------------------------------

            // 发送邮件（保留原逻辑）
            Transport.send(message);
            log.info("邮件发送成功！收件人：{}", sendTo);
        } catch (Exception e) {
            log.error("邮件发送失败：{}", e.getMessage(), e);
        }
    }

    // getter/setter方法（保留原逻辑，可忽略）
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setSmtpSslEnable(String smtpSslEnable) {
        this.smtpSslEnable = smtpSslEnable;
    }
}
