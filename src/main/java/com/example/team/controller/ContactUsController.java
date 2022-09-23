package com.example.team.controller;

import com.example.team.dto.ContactusMailDto;
import com.example.team.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = {"/contactus"})
public class ContactUsController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailSendService mailSendService;

    @GetMapping
    public String getContanctUs() {
        return "contactus";
    }

    @ResponseBody
    @PostMapping(path = {"/mail-send"})
    public Map<String, Boolean> postMailSend(ContactusMailDto mailDto) {
        Map<String, Boolean> resultList = new HashMap<>();
        resultList.put("result", mailSendService.simpleSend(mailDto));

        return resultList;
    }
}
