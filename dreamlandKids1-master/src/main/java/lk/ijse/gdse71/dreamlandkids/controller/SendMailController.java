package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;

    @FXML
    public void sendUsingSendgridOnAction(ActionEvent actionEvent) {
//        if (customerEmail == null) {
//            return;
//        }
//
//        // The sender's email address
//        final String FROM = "replace-your-email";
//
//        String subject = txtSubject.getText();
//        String body = txtBody.getText();
//
//        if (subject.isEmpty() || body.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
//            return;
//        }
//
//        sendEmailWithSendgrid(FROM, customerEmail, subject, body);
    }

    private void sendEmailWithSendgrid(String from, String to, String subject, String body) {
//        String USER_NAME = "apikey";
//
//        String PASSWORD = "pjhk cbfn myhi xufo";    //replace
//
//        Properties props = new Properties();
//
//        props.put("mail.smtp.auth", "true");
//
//        props.put("mail.smtp.starttls.enable", "true");
//
//        props.put("mail.smtp.host", "smtp.sendgrid.net");
//
//        props.put("mail.smtp.port", "587");
//
//        props.put("mail.smtp.ssl.trust", "smtp.sendgrid.net");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication(USER_NAME, PASSWORD);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//
//            message.setFrom(new InternetAddress(from));
//
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//            message.setSubject(subject);
//
//            message.setText(body);
//
//            Transport.send(message);
//
//            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
//        }
    }


    @FXML
    public void sendUsingGmailOnAction(ActionEvent actionEvent) {
        if (customerEmail == null) {
            return;
        }

        final String FROM = "replace-your-email";

        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        sendEmailWithGmail(FROM, customerEmail, subject, body);

    }

    private void sendEmailWithGmail(String from, String to, String subject, String messageBody) {
        String PASSWORD = "pjhk cbfn myhi xufo"; // replace

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {

            // Replace with your email and app password
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD); // Replace with your email and password
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);

            message.setText(messageBody);

            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }
}
