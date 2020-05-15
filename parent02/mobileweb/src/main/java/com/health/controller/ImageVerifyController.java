package com.health.controller;

import com.health.pojo.TestVo;
import com.health.utils.ImageVerify;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



@RestController
public class ImageVerifyController {
    /**
     * Session Key
     */
    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 生成图片验证码接口
     */
    @GetMapping("/imageVerify")
    public void imageVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ImageVerify.ImageCode imageCode = new ImageVerify(
                68,/** 验证码图片宽度68*/
                36,/** 验证码图片长度36*/
                4,/** 验证码位数*/
                32,/** 验证码字体大小32*/
                60,/** 验证码有效时间(秒)*/
                2,/** x轴距*/
                28/** y轴距*/
        ).create();

        sessionStrategy.setAttribute(
                new ServletWebRequest(request),
                SESSION_KEY_IMAGE_CODE,
                imageCode
        );

        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
        response.getOutputStream().flush();
    }

    /**
     * 验证接口
     */
    @PostMapping("/verify")
    public Map<String, String> verify(TestVo testVo, ServletWebRequest servletWebRequest, @RequestParam("code") String code) {
        final ImageVerify.ImageCode imageCode = (ImageVerify.ImageCode) sessionStrategy.getAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);

        Map<String, String> m = new HashMap<>();

        if (StringUtils.isBlank(code)) {
            m.put("message", "验证码不能为空！");
            return m;
        }

        if (imageCode == null) {
            m.put("message", "验证码不存在！");
            return m;
        }

        if (imageCode.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);
            m.put("message", "验证码已过期！");
            return m;
        }

        if (!StringUtils.equalsIgnoreCase(imageCode.getCode(), code)) {
            m.put("message", "验证码不正确！");
            return m;
        }

        sessionStrategy.removeAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE);

        m.put("message", "成功");
        return m;
    }

    /**
     * 验证页面
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}