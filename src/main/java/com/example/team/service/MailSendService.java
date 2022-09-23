package com.example.team.service;

import com.example.team.dto.ContactusMailDto;

public interface MailSendService {
    public boolean simpleSend(ContactusMailDto mailDto);
}
