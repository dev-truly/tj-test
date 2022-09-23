package com.example.team.dto;

import lombok.Data;

@Data
public class ContactusMailDto {
    private String toAddress;
    private String fromAddress;
    private String subject;
    private String content;
    private String name;
}
