package com.scs.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.scs.security.filter.LoginFilter.KAPTCHA_KEY;

@Controller
public class VerifyCodeController {
    private final Producer producer;

    @Autowired
    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }


    @RequestMapping("/vc.jpg")
    public void verifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
        String verifyCode = producer.createText();
        BufferedImage image = producer.createImage(verifyCode);

        System.out.println("session = " + session);

        // 可以换成redis存放
        session.setAttribute(KAPTCHA_KEY, verifyCode);

        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentType(MediaType.IMAGE_JPEG_VALUE);
        ImageIO.write(image, "jpeg", outputStream);
    }
}
