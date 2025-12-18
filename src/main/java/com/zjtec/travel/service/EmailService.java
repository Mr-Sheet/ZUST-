package com.zjtec.travel.service;

public interface EmailService {
    void sendEmail(String sendTo, String title, String content);
}
