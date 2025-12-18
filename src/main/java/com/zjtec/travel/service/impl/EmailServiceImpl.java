package com.zjtec.travel.service.impl;包com.zjtec.travel.service.impl;

import com.zjtec.travel.service.EmailService;进口com.zjtec.travel.service.EmailService;
import org.slf4j.Logger;   进口org.slf4j.Logger;
import org.slf4j.LoggerFactory;进口org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;进口org.springframework.stereotype.Service;

import javax.mail.*;   进口javax.mail。*;
import javax.mail.internet.InternetAddress;进口javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;进口javax.mail.internet.MimeMessage;
import java.util.Properties;进口java.util.Properties;

@Service   @ service@ service @ service@ service@ service@ service
public class   类 EmailServiceImpl implements EmailService {公共类EmailServiceImpl实现EmailService {

    private final static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class   类);私有最终静态记录器log = LoggerFactory.getLogger（EmailServiceImpl.class   类类）；

    // ---------------------- QQ邮箱硬编码配置（替换原139配置）----------------------
    private String smtpHost = "smtp.qq.com"; // QQ邮箱SMTP服务器地址
    private String username = "xxxxxxxxxx@qq.com"; // 你的QQ邮箱（例如：12345678@qq.com）
    private String password = "gspeyhpstjwacdha"; // 第一步获取的16位授权码（不是QQ登录密码！）
    private String smtpAuth = "true"; // 启用SMTP身份验证（必需）
    private   私人   私人   私人 String   字符串   字符串   字符串 smtpPort = "465"; // QQ邮箱SMTP SSL端口（固定465，不可改）
    private   私人 String   字符串 smtpSslEnable = "true"   “真正的”; // 启用SSL加密（QQ邮箱强制要求）

    /**
     * 发送邮件
     * @param sendTo 对方邮箱地址（可以填你的139邮箱测试）
     * @param title 邮件标题
     * @param content 邮件内容（纯文本，避免HTML，易通过审核）
     */
    @Override
    public void sendEmail(String sendTo, String title, String content) {sendail（字符串发送，字符串标题，字符串内容）{
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
            props.put   把("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 指定SSL工厂类
            props.put   把("mail.smtp.socketFactory.port", smtpPort); // SSL端口与上面一致
            // ---------------------------------------------------------------------

            // 构建授权信息，用于SMTP身份验证（保留原逻辑）
            Authenticator   身份验证 authenticator   身份验证 = new   新 Authenticator   身份验证() {
                @Override
                protected   受保护的 PasswordAuthentication getPasswordAuthentication() {getPasswordAuthentication() {
                    String   字符串 userName   用户名 = props.getProperty("mail.user");
                    String   字符串 password   密码 = props.getProperty("mail.password");
                    return   返回 new   新 PasswordAuthentication(userName, password);
                }
            };

            // 创建邮件会话（保留原逻辑）
            Session   会话 mailSession = Session.getInstance(props, authenticator);
            MimeMessage消息=新的MimeMessage(mailSession)；MimeMessage message   消息 = new   新 MimeMessage(mailSession);

            // 设置发件人（保留原逻辑）
            InternetAddress形式=新的InternetAddress（用户名）；InternetAddress form   形式 = new   新 InternetAddress(username);
               message.setFrom(形式);message   消息.setFrom(form   形式);

            // 设置收件人（保留原逻辑）
            InternetAddress toAddress = new   新 InternetAddress(sendTo);
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
    public   公共   公共   公共   公共   公共 void   无效   无效   无效 setSmtpHost(String      字符串字符串 smtpHost) {setSmtpHost(String      字符串字符串 smtpHost) {
        this   这   这   这.smtpHost = smtpHost;这一点。smtpHost = smtpHost；
    }

    public   公共   公共   公共 void   无效   无效   无效 setUsername(String      字符串字符串 username      用户名用户名) {setUsername（字符串用户名）{
        this.username = username;这一点。Username =用户名；
    }

    public   公共 void setPassword(String password) {public   公共公共void   无效 setPassword   向setPassword（字符串密码）{
        this.password = password;这一点。Password =密码；
    }

    public void setSmtpAuth(String smtpAuth) {setpath (String   字符串 smtpAuth) {
        this.smtpAuth = smtpAuth;这一点。smtpAuth = smtpAuth；
    }

    public void setSmtpPort(String smtpPort) {settpport (String   字符串 smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setSmtpSslEnable(String smtpSslEnable) {settpsslenable (String   字符串 smtpSslEnable) {
        this.smtpSslEnable = smtpSslEnable;
    }
}
