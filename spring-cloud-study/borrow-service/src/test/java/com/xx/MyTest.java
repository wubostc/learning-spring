package com.xx;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
public class MyTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

        String encoded = encoder.encode("123");

        System.out.println("encoded = " + encoded);
    }
}
