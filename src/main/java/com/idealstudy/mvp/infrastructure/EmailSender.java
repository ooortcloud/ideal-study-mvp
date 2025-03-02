package com.idealstudy.mvp.infrastructure;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private final JavaMailSender mailSender;

    @Value("${server.frontend-domain-url}")
    private String frontendDomainUrl;

    // 이메일 이미지 첨부용(yml 등으로 빼도 됨)
    private final String logoContentId = "logo";

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String userEmail, String token) throws Exception{
        // Alternative way to prepare MimeMessage instances,
        // instead of createMimeMessage() and send(MimeMessage) calls.
        mailSender.send(new MimeMessagePreparator() {
            // Prepare the given new MimeMessage instance.
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                message.setFrom("noreply@idealstudy.com");
                message.setTo(userEmail);
                // Set the subject of the message, using the correct encoding.
                message.setSubject("회원가입 인증 메일");
                /*
                 NOTE: Invoke addInline(java.lang.String, jakarta.activation.DataSource) after setText;
                 else, mail readers might not be able to resolve inline references correctly.
                 Will end up as "Content-ID" header in the body part, surrounded by angle brackets:
                 for example, "myId" → "<myId>". Can be referenced in HTML source via src="cid:myId" expressions.
                */
                message.setText(getMailContents(userEmail, token), true);
                /*
                 Add an inline element to the MimeMessage, taking the content from a jakarta.activation.DataSource.
                 Note that the InputStream returned by the DataSource implementation needs to be a fresh one
                 on each call, as JavaMail will invoke getInputStream() multiple times.
                 */
                message.addInline(logoContentId, new ClassPathResource("static/img/logo.webp"));
            }
        });
    }

    private String getMailContents(String email, String token) {

        String authenticationUrl = frontendDomainUrl + "/signup-complete"
                + "?emailToken=" + token + "&email=" + email;

        return  "<h3>이메일 인증</h3>" +
                "<img src='cid:"+logoContentId+"'>" +
                "<p>이상한 과외에 오신 것을 환영합니다.</p>" +
                "<a href='"+authenticationUrl+"'>Click here to verify your email</a>";
    }
}
