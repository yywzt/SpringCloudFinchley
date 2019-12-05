package com.example.yywauth.account;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/11/13 11:34
 * @description
 */
@Slf4j
public class PasswordEncoderTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Test
    public void test_PasswordEncoder(){
        String password = "123456";
        String encode = bCryptPasswordEncoder.encode("secret");
        log.info("{} encode: {}", password, encode);
        boolean matches = bCryptPasswordEncoder.matches("$2a$10$0ElDK8sDmDENBu6EEz45QO.DlFupUdTa0tykLLx6vWhPl.xkZ6C.2", encode);
        log.info("matches: {}", matches);
    }
}
