package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;

    @FXML
    public void sendUsingGmailOnAction(ActionEvent actionEvent) {
        if (customerEmail == null || customerEmail.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Customer email is missing!").show();
            return;
        }

        String from = "sithusilva94@gmail.com";
        String password = "pjhk cbfn myhi xufo";

        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        boolean isSent = sendEmailWithGmail(from, password, customerEmail, subject, body);

        if (isSent) {
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to send the email.").show();
        }
    }

    /**
     * Sends an email using the Gmail SMTP server.
     */
    private boolean sendEmailWithGmail(String from, String password, String to, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            System.out.println("Email sent successfully!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

