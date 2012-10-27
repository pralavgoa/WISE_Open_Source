package edu.ucla.wise.commons;

import javax.activation.DataHandler;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This class encapsulates some specific methods to send messages from Message
 * Sequences
 */

public class MessageSender {
    /** Instance Variables */
    public Session session;
    private String from_str, reply_str;

    public MessageSender() {
	session = WISEApplication.get_mailSession(null);
    }

    public MessageSender(MessageSequence msg_seq) {
	String myFromID = msg_seq.emailID();
	try {
	    // session = WISE_Application.get_mailSession(myFromID);
	    // //WISE_Application knows how to look up passwords
	    session = WISEApplication.get_mailSession(null);
	} catch (Exception e) {
	    WISEApplication
		    .log_error(
			    "WISE Message_Sender can't get authenticated email session: ",
			    e);
	}
	from_str = msg_seq.getFromString();
	reply_str = msg_seq.getReplyString();
    }

    // public void set_fromString(String fromString)
    // {
    //
    // }

    /** look up, compose, and send email message */
    public String send_message(Message msg, String message_useID, User toUser) {
	String salutation = toUser.salutation;
	String lastname = toUser.last_name;
	String email = toUser.email;
	String ssid = toUser.currentSurvey.study_space.id; // kludge for now --
							   // these are all
							   // pretty fixed
							   // relationships
	return send_message(msg, message_useID, email, salutation, lastname,
		ssid);
    }

    // returns empty string if successful
    public String send_message(Message msg, String message_useID,
	    String toEmail, String salutation, String lastname, String ssid) {
	String outputString = "uncaught exception";
	String message = null;
	try {
	    // create message object
	    MimeMessage mMessage = new MimeMessage(session);
	    // send message to each of the users
	    InternetAddress tmpAddr = new InternetAddress(from_str);
	    mMessage.setFrom(tmpAddr);
	    if (reply_str != null) {
		tmpAddr = new InternetAddress(reply_str);
		mMessage.setReplyTo(new InternetAddress[] { tmpAddr });
	    }
	    java.util.Date today = new java.util.Date();
	    mMessage.setSentDate(today);
	    mMessage.addRecipient(javax.mail.Message.RecipientType.TO,
		    new InternetAddress(toEmail));
	    mMessage.setSubject(msg.subject);
	    // check if message produces an html body; null indicates no
	    message = msg.compose_html_body(salutation, lastname, ssid,
		    message_useID);
	    // if message is null go ahead and prepare a text body
	    if (message == null) {
		message = msg.compose_text_body(salutation, lastname, ssid,
			message_useID);
		mMessage.setText(message);
	    } else {
		// create an "Alternative" Multipart message to send both html &
		// text email
		Multipart mp = new MimeMultipart("alternative");
		// add text body part
		BodyPart bp_text = new MimeBodyPart();
		bp_text.setDataHandler(new DataHandler(msg.compose_text_body(
			salutation, lastname, ssid, message_useID),
			"text/plain"));
		mp.addBodyPart(bp_text);
		// add html body part
		BodyPart bp_html = new MimeBodyPart();
		bp_html.setDataHandler(new DataHandler(message, "text/html"));
		mp.addBodyPart(bp_html);
		// set the message body
		mMessage.setContent(mp);
	    }
	    System.out.println(message);
	    // send message and return the result
	    outputString = mailing_process(mMessage);
	} catch (Exception e) {
	    WISEApplication.log_error(
		    "\r\nWISE - MESSAGE SENDER on email message: " + message
			    + ".\r\n Full error: " + e.toString(), e);
	}
	return outputString;
    }

    public String send_test(String msg_text) {
	String outputString = "";
	try {

	    // create message object
	    MimeMessage message = new MimeMessage(session);
	    // send message to each of the users
	    message.setFrom(new InternetAddress("merg@mednet.ucla.edu"));
	    java.util.Date today = new java.util.Date();
	    message.setSentDate(today);
	    message.addRecipient(javax.mail.Message.RecipientType.TO,
		    new InternetAddress("dbell@mednet.ucla.edu"));
	    message.setSubject("This is a test");
	    message.setText(msg_text);
	    // send message and analyze the mailing failure
	    String msg_result = mailing_process(message);
	    if (msg_result.equalsIgnoreCase("")) {
		outputString = "D";
	    } else {
		outputString = msg_result;
	    }

	} catch (Exception e) {
	    WISEApplication.log_error(
		    "WISE EMAIL - MESSAGE SENDER - SEND REMINDER: "
			    + e.toString(), null);
	}
	return outputString;
    }

    public String mailing_process(MimeMessage msg) throws MessagingException,
	    Exception {
	String mailing_result = "";
	if (msg == null) {
	    System.exit(0);
	    return "msg is null";
	}

	try {
	    if (session == null) {
		WISEApplication.log_info("Session is null!!");
	    }
	    Transport tr = session.getTransport("smtp");

	    if (tr == null) {
		WISEApplication.log_info("tr is null!!");
	    }

	    String MailHost = null;
	    String user = null;
	    String pass = null;

	    pass = WISEApplication.sharedProps
		    .getString("wise.email.password");
	    user = WISEApplication.sharedProps
		    .getString("wise.email.username");//
	    MailHost = WISEApplication.sharedProps.getString("email.host");
	    tr.connect(MailHost, user, pass);
	    if ((MailHost == null) || (user == null) || (pass == null)) {
		WISEApplication.log_info("MailHost or user or pass is null");
	    }
	    msg.saveChanges(); // don't forget this
	    if (msg.getAllRecipients() == null) {
		WISEApplication.log_info("Get All Recepients is null");
	    }
	    tr.sendMessage(msg, msg.getAllRecipients());
	    tr.close();

	    // Transport.send(msg);
	}

	catch (AuthenticationFailedException e) {
	    WISEApplication.log_error(
		    "Message_Sender - Authentication failed. From string: "
			    + from_str + "; Reply: " + reply_str + ". \n"
			    + e.toString(), e);
	    mailing_result = "Authentication process failed";
	    return mailing_result;
	} catch (SendFailedException e) {
	    WISEApplication.log_error(
		    "Message_Sender - Invalid email address. " + e.toString(),
		    e);
	    mailing_result = "Email address is invalid.";
	    return mailing_result;
	}

	catch (MethodNotSupportedException e) {
	    WISEApplication.log_error(
		    "Message_Sender - Unsupported message type. "
			    + e.toString(), e);
	    mailing_result = "Message is not supported.";
	    return mailing_result;
	}

	catch (Exception e) {
	    WISEApplication
		    .log_error(
			    "Message_Sender - mailing_process failure: "
				    + e.toString(), e);
	    mailing_result = "Email failed (null pointer error): "
		    + e.toString();
	    throw e;
	    // mailing_result = "abcdefg";
	}
	return mailing_result;
    }
    /***************************************************************************
     * Old Send_message() String mailing_result=""; try { Transport tr =
     * session.getTransport("smtp"); String MailHost =
     * WISE_Application.email_host; String user =
     * WISE_Application.mail_username; String pass =
     * WISE_Application.mail_password; tr.connect(MailHost, user, pass);
     * msg.saveChanges(); // don't forget this tr.sendMessage(msg,
     * msg.getAllRecipients()); tr.close(); //Transport.send(msg); }
     * 
     * catch (AuthenticationFailedException e) { WISE_Application.email_alert(
     * "Message_Sender - Authentication failed. From string: " + from_str +
     * "; Reply: "+ reply_str +". \n" + e.toString(), e);
     * mailing_result="Authentication process failed"; return mailing_result; }
     * catch (SendFailedException e) {
     * WISE_Application.email_alert("Message_Sender - Invalid email address. "
     * +e.toString(), e); mailing_result="Email address is invalid."; return
     * mailing_result; }
     * 
     * catch (MethodNotSupportedException e) { WISE_Application.email_alert(
     * "Message_Sender - Unsupported message type. "+e.toString(), e);
     * mailing_result="Message is not supported."; return mailing_result; }
     * 
     * catch (Exception e) { WISE_Application.email_alert(
     * "Message_Sender - mailing_process failure: "+e.toString(), e);
     * mailing_result = "Email failed (unknown error): " + e.toString(); }
     * return mailing_result; }
     *********************************************************************/

}
