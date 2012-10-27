package edu.ucla.wise.commons;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

// Class to represent common elements for a given installation of the wise surveyor or admin java application
// Never instantiated; Handles most interfaces to the file system

/**
 * @author mrao
 * @author dbell
 * @author ssakdeo
 */
public class WISEApplication {

    static Logger log = Logger.getLogger(WISEApplication.class);

    public static String rootURL;
    // Commenting these out and moving them to Surveyor Class
    // public String servlet_url, shared_file_url,shared_image_url;
    public static String email_from;
    public static String alert_email;
    public static String email_host;
    public static String mail_username;
    public static String mail_password;
    public static String admin_server;
    public static String images_path;
    public static String styles_path;
    public static String survey_app;
    public static String shared_files_link;

    // public static
    public static java.util.Hashtable<String, SurveyorApplication> AppInstanceTbl = new java.util.Hashtable<String, SurveyorApplication>();

    private static final String localPropPath = "localwise";
    public static ResourceBundle localProps = null;

    public static ResourceBundle sharedProps = null;

    public static final String html_ext = ".htm";
    public static final String mailUserName_ext = ".username";
    public static final String mailPasswd_ext = ".password";
    // public static final String xml_ext = ".xml";
    private static final String wise_defaultAcct_propID = "wise.email";

    public static String xml_loc;
    public static Session mail_session; // Holds values for sending message;

    // doesn't actually connect 'till
    // runtime

    /** private class for JavaMail authentication */
    // private static class MyAuthenticator extends Authenticator
    // {
    // protected PasswordAuthentication getPasswordAuthentication()
    // {
    // String userName = WISE_Application.mail_username;
    // String password = WISE_Application.mail_password;
    // return new PasswordAuthentication(userName, password);
    // }
    // }

    // public static Surveyor_Application retrieveAppInstance(HttpSession s)
    // {
    // Surveyor_Application app =
    // (Surveyor_Application)s.getAttribute("SurveyorInst");
    // return app;
    // }

    private static class VarAuthenticator extends Authenticator // apparently
    // only 1
    // (static)
    // class but it
    // can have
    // instances
    {
	String userName = null;
	String password = null;

	public VarAuthenticator() {
	    super();
	    userName = sharedProps.getString("mail.username");
	    password = sharedProps.getString("mail.password");
	    System.out.println(userName + "/" + password);
	}

	public VarAuthenticator(String uName, String pword) {
	    super();
	    userName = uName;
	    password = pword;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(userName, password);
	}
    }

    // Not called
    // public static void check_init(String appContext, PrintWriter out)
    // {
    // if (ApplicationName == null)
    // initialize(appContext);
    // }

    public static String initialize() throws IOException {
	// Load server's local properties
	String sharedPropPath;
	localProps = ResourceBundle.getBundle(localPropPath,
		Locale.getDefault());
	try {
	    // Loading Local Properties
	    rootURL = localProps.getString("server.rootURL");
	    sharedPropPath = localProps.getString("shared.Properties.file");
	    if (CommonUtils.isEmpty(rootURL)
		    || CommonUtils.isEmpty(sharedPropPath)) {
		throw new Exception("Failed to read from local properties");
	    }
	    // loading shared properties file
	    sharedProps = ResourceBundle.getBundle(sharedPropPath);
	} catch (Exception e) {
	    log.error("WISE Application initialization Error: " + e);
	    return e.toString();
	}

	// New Single JAR changes, putting everything like it was before, in
	// WISE_Application
	// servlet_url = rootURL + ApplicationName + "/";
	// servlet_url = rootURL + "/wise_survey" + "/";
	email_from = sharedProps.getString("wise.email.from");
	alert_email = sharedProps.getString("alert.email");
	email_host = sharedProps.getString("email.host");
	mail_username = sharedProps.getString(wise_defaultAcct_propID
		+ mailUserName_ext);
	mail_password = sharedProps.getString(wise_defaultAcct_propID
		+ mailPasswd_ext);
	admin_server = sharedProps.getString("admin.server");
	xml_loc = sharedProps.getString("xml_root.path");
	// TODO: confirm
	styles_path = sharedProps.getString("shared_style.path");
	images_path = sharedProps.getString("shared_image.path");
	// images_path = sharedProps.getProperty("wise.images_path");
	shared_files_link = localProps
		.getString("default.sharedFiles_linkName");
	log_info("images_path read is [" + images_path + "]");
	// WISE_Application.shared_file_url = rootURL +
	// WISE_Application.ApplicationName + "/" +
	// localProps.getProperty("default.sharedFiles_linkName") + "/";
	// WISE_Application.shared_file_url = rootURL + "/wise_survey/" +
	// localProps.getProperty("default.sharedFiles_linkName") + "/";
	if (CommonUtils.isEmpty(xml_loc))
	    return "WISE Application initialization Error: Failed to read from Shared properties file "
		    + sharedPropPath + "\n";

	StudySpace.setupStudies(); // set up Study_Space class -- pre-reads
	// from sharedProps

	// setup default email session for sending messages -- WISE needs this
	// to send alerts
	try {
	    mail_session = get_mailSession(wise_defaultAcct_propID); // use
	    // default
	    // id;
	    // can
	    // also
	    // use
	    // null
	} catch (Exception e) {
	    log.error("WISE Application initialization Error: " + e);
	    return e.toString();
	}
	if (mail_session == null)
	    return "WISE Application initialization Error: Failed to initialize mail session\n";
	return null; // success!
    }

    static PrintStream Init_Error(String errStr) {
	PrintStream ps = null;
	try {
	    FileOutputStream fos = new FileOutputStream("WISE_errors.txt", true);
	    ps = new PrintStream(fos, true);
	    ps.print(errStr);
	} catch (Exception e) {
	    System.err.println(e.toString());
	    e.printStackTrace(System.err);
	}
	return ps;
    }

    /**
     * Send an email alert to someone. Older version of this application used to
     * send email alert for every error. This would flood inbox trememdously.
     * Replaced this by logging errors, an industry standard of recording events
     * having in an application.
     */
    @Deprecated
    public static void send_email(String email_to, String subject, String body) {
	try {
	    MimeMessage message = new MimeMessage(mail_session);
	    message.setFrom(new InternetAddress(email_from));
	    message.addRecipient(javax.mail.Message.RecipientType.TO,
		    new InternetAddress(email_to));
	    message.setSubject(subject);
	    message.setText(body);

	    // Send message
	    Transport.send(message);
	} catch (Exception e) {
	    log_error("WISE_Application - SEND_EMAIL error: " + "\n" + body, e);
	}
    }

    /**
     * Log error message to wise.log which is present in $CATALINA_HOME/bin The
     * only problem with this way of logging is every log will be named after
     * the {@link WISEApplication} class. But proper messagebody and exception
     * should help track where the error went wrong.
     */
    public static void log_error(String body, Exception e) {

	log.error(body, e);
    }

    public static void log_info(String body) {
	log.info(body);
    }
    
    public static void log_debug(String body){
    	log.debug(body);
    }

    /**
     * decoding - convert character-formatted ID to be the digit-formatted *
     * TOGGLE NAME OF THIS FUNCTION to move to production mode
     */
    public static String decode(String char_id) {
	String result = new String();
	int sum = 0;
	for (int i = char_id.length() - 1; i >= 0; i--) {
	    char c = char_id.charAt(i);
	    int remainder = (int) c - 65;
	    sum = sum * 26 + remainder;
	}

	sum = sum - 97654;
	int remain = sum % 31;
	if (remain == 0) {
	    sum = sum / 31;
	    result = Integer.toString(sum);
	} else {
	    result = "invalid";
	}
	return result;
    }

    public static String decode_test(String char_id) {
	return char_id;
    }

    /**
     * encoding - convert digit-formatted ID to be the character-formatted
     * TOGGLE NAME OF THIS FUNCTION to move to production mode
     */
    public static String encode(String user_id) {
	int base_numb = Integer.parseInt(user_id) * 31 + 97654;
	String s1 = Integer.toString(base_numb);
	String s2 = Integer.toString(26);
	BigInteger b1 = new BigInteger(s1);
	BigInteger b2 = new BigInteger(s2);

	int counter = 0;
	String char_id = new String();
	while (counter < 5) {
	    BigInteger[] bs = b1.divideAndRemainder(b2);
	    b1 = bs[0];
	    int encode_value = bs[1].intValue() + 65;
	    char_id = char_id + (new Character((char) encode_value).toString());
	    counter++;
	}
	return char_id;
    }

    public static String encode_test(String user_id) {
	return user_id;
    }

    // TODO not used
    // public static String read_localProp(String prop_name) {
    // File prop_file = new File(localPropPath);
    // try {
    // FileInputStream in = new FileInputStream(prop_file);
    // localProps.load(in);
    // in.close();
    // } catch (Exception e) {
    // email_alert("WISE Application Error reading local property: ", e);
    // }
    // return localProps.getString(prop_name);
    // }

    public static Session get_mailSession(String fromAcct) // return the default
    // session if null
    {
	if (fromAcct == null)
	    fromAcct = wise_defaultAcct_propID;
	String uname = sharedProps.getString(fromAcct + mailUserName_ext);
	String pwd = sharedProps.getString(fromAcct + mailPasswd_ext);

	String smtpAuthUser = sharedProps.getString("SMTP_AUTH_USER");
	String smtpAuthPassword = sharedProps.getString("SMTP_AUTH_PASSWORD");
	String smtpAuthPort = sharedProps.getString("SMTP_AUTH_PORT");
	/*
	 * Pralav has commented old code Properties sys_props =
	 * System.getProperties(); // setup the mail server in system properties
	 * sys_props.put("mail.smtp.host", email_host);
	 * sys_props.put("mail.smtp.auth", CommonUtils.isEmpty(pwd) ? "false" :
	 * "true"); Authenticator auth = CommonUtils.isEmpty(pwd) ? null : new
	 * VarAuthenticator(uname, pwd);
	 */// Pralav's comment ends here

	// Set the host smtp address
	Properties props = System.getProperties();
	props.put("mail.smtp.host", email_host);
	props.put("mail.smtp.auth", "true");

	props.put("mail.smtp.port", smtpAuthPort);
	props.put("mail.smtp.user", smtpAuthUser);
	props.put("mail.smtp.password", smtpAuthPassword);
	props.put("mail.smtp.starttls.enable", "true");
	String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	props.setProperty("mail.smtp.socketFactory.port", smtpAuthPort);
	props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	props.setProperty("mail.smtp.socketFactory.fallback", "false");
	
	props.setProperty("mail.smtp.connectiontimeout", "10000");
	props.setProperty("mail.smtp.timeout", "10000");

	Authenticator auth = new VarAuthenticator(uname, pwd);

	// create the message session
	return Session.getInstance(props, auth);
    }

}
