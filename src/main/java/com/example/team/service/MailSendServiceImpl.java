package com.example.team.service;

import com.example.team.common.Util;
import com.example.team.dto.ContactusMailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailSendServiceImpl implements MailSendService {
    private static final String MAIL_ENCODING = "UTF-8";
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean simpleSend(ContactusMailDto mailDto) {
        boolean result = false;
        MimeMessage msg = null;
        try {
            msg = javaMailSender.createMimeMessage();
            msg.setSubject("팀 프로젝트 컨택 메일 입니다.", MAIL_ENCODING);
            msg.addFrom(new Address[]{ new InternetAddress(mailDto.getFromAddress())});
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(mailDto.getToAddress(),
                            "팀 프로젝트",
                            MAIL_ENCODING
                    )
            );
            String htmlStr =
                    String.format("<h3>요청자 정보 : [%s] <a href='mailto://%s'>%s</a></h3><br /><br />요청 내용 : <div>%s</div>",
                            mailDto.getName(),
                            mailDto.getFromAddress(),
                            mailDto.getFromAddress(),
                            Util.nl2br(mailDto.getContent())
                    );

            msg.setText(htmlStr, MAIL_ENCODING, "html");

            javaMailSender.send(msg);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }
}
