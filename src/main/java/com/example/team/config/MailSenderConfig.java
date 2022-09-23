package com.example.team.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Properties;


@Data
@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "spring.mail")
@ToString
public class MailSenderConfig {
    private static final String MAIL_DEBUG = "mail.debug";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    private static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    private static final String MAIL_SMTP_SSL_PROTOCOLS = "mail.smtp.ssl.protocols";

    @Data
    public static class Smtp {
        private boolean auth;
        private boolean startTlsRequired;
        private boolean startTlsEnable;
    }

    @NotBlank
    private String host;
    private String protocol;
    private int port;
    private String username;
    private String password;
    private String defaultEncoding;

    @NotNull
    private Smtp smtp;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(getHost());
        mailSender.setProtocol(getProtocol());
        mailSender.setPort(getPort());
        mailSender.setUsername(getUsername());
        mailSender.setPassword(getPassword());

        mailSender.setDefaultEncoding(getDefaultEncoding());
        Properties properties = mailSender.getJavaMailProperties();

        properties.put(MAIL_SMTP_STARTTLS_REQUIRED, getSmtp().isStartTlsRequired());
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, getSmtp().isStartTlsEnable());
        properties.put(MAIL_SMTP_AUTH, getSmtp().isAuth());
        properties.put(MAIL_DEBUG, true);
        properties.put(MAIL_SMTP_SSL_TRUST, "smtp.gmail.com");
        properties.put(MAIL_SMTP_SSL_PROTOCOLS, "TLSv1.2");
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
