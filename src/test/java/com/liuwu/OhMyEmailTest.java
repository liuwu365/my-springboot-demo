package com.liuwu;

import com.liuwu.util.OhMyEmail;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import static com.liuwu.util.OhMyEmail.SMTP_office365;

/**
 * 邮件发送测试
 */
public class OhMyEmailTest {
    private static final String fromEmail = "work@moba.games";
    private static final String fromEmailPwd = "121525.t";

    @Before
    public void before() throws GeneralSecurityException {
        // 配置，一次即可
        OhMyEmail.config(SMTP_office365(false, "TLS"), fromEmail, fromEmailPwd);
        //OhMyEmail.config(SMTP_gmail(false, "SSL"), "liuwu1189@gmail.com", "rocking123");
    }

    @Test
    public void testSendText() throws MessagingException {
        OhMyEmail.subject("这是一封测试 office365 TEXT邮件")
                .from("王爵的QQ邮箱")
                .to("1257435195@qq.com")
                .text("信件内容")
                .send();
    }

    @Test
    public void testHelper() throws MessagingException {
        try {
            testSendText();
        } catch (Exception e) {
            if (e.toString().indexOf("Connection timed out") > 0) {
                System.out.println("超时了换一种调用方式");
                OhMyEmail.config(SMTP_office365(false, "SSL"), fromEmail, fromEmailPwd);
                testSendText();
            }
        }
    }

    @Test
    public void testSendHtml() throws MessagingException {
        OhMyEmail.subject("这是一封测试HTML邮件")
                .from("王爵的QQ邮箱")
                .to("liuwu_eva@163.com")
                .html("<h1 font=red>信件内容</h1>")
                .send();
    }

    @Test
    public void testSendAttach() throws MessagingException {
        OhMyEmail.subject("这是一封测试附件邮件")
                .from("王爵的QQ邮箱")
                .to("liuwu_eva@163.com")
                .html("<h1 font=red>信件内容</h1>")
                .attach(new File("/Users/biezhi/Downloads/hello.jpeg"), "测试图片.jpeg")
                .send();
    }

    @Test
    public void testPebble() throws IOException, PebbleException, MessagingException {
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("register.html");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "liuwu");
        context.put("email", "admin@java-china.org");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        OhMyEmail.subject("这是一封测试Pebble模板邮件")
                .from("王爵的QQ邮箱")
                .to("1257435195@qq.com")
                .html(output)
                .send();
    }

    @Test
    public void testJetx() throws IOException, PebbleException, MessagingException {
        JetEngine engine = JetEngine.create();
        JetTemplate template = engine.getTemplate("/register.jetx");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "biezhi");
        context.put("email", "admin@java-china.org");
        context.put("url", "<a href='http://java-china.org'>https://java-china.org/active/asdkjajdasjdkaweoi</a>");

        StringWriter writer = new StringWriter();
        template.render(context, writer);
        String output = writer.toString();
        System.out.println(output);

        OhMyEmail.subject("这是一封测试Jetx模板邮件")
                .from("王爵的QQ邮箱")
                .to("liuwu_eva@163.com")
                .html(output)
                .send();
    }

}