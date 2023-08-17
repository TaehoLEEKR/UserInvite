package com.example.userinviteassgin.mailgun;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "${mailgun.key.url}")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping(value = "${mailgun.key.domainName}" + "/messages")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);

}

