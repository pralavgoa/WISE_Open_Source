package edu.ucla.wise.commons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Study space is the core of WISE system -- represents the core abstractions
 * for individual survey projects
 */

public class StudySpace {
    /** CLASS STATIC VARIABLES */
    private static Hashtable ALL_SPACE; // contains actual study spaces indexed
					// by name
    private static Hashtable SPACE_names; // contains index of all study names
					  // in the properties file by ID
    // NOTE: Properties are read once at startup, therefore must restart server
    // if a Study Space is added

    // public static String xml_loc;

    public static String font = "<font face='Verdana, Arial, Helvetica, sans-serif' size='-1'>";

    /** INSTANCE VARIABLES */
    public Hashtable surveys;
    public Preface preface;

    public String id; // the study_space's number, which can be encoded
    public String study_name;
    public String title;

    // DIRECTORIES AND PATHS
    public String server_url;
    public String dir_name;
    private String prefacePath;
    private String application;
    public String app_urlRoot, servlet_urlRoot;
    public String sharedFile_urlRoot, style_url, image_url;

    public DataBank db; // one DB per SS

    /** CLASS FUNCTIONS */

    /** static initializer */
    static {
	ALL_SPACE = new Hashtable();
	SPACE_names = new Hashtable();
	// better not to parse all ss's in advance
	// Load_Study_Spaces();
    }

    public static void setupStudies() {
	DataBank.SetupDB(WISEApplication.sharedProps);
	// Just read the names of all unique Studies and save the name:ID pairs
	// in a hash for quicker lookup later
	// note when called by a reload, does not drop already-parsed studies
	// but does reread props file to enable load of new studies
	// TODO (low): consider a private "stub" class to hold all values from
	// props file without parsing XML file
	Enumeration enu = WISEApplication.sharedProps.getKeys();
	while (enu.hasMoreElements()) {
	    String key = (String) enu.nextElement();
	    if (key.indexOf(".studyid") != -1) // pull out just the study ID
					       // properties
	    {
		String idNum = WISEApplication.sharedProps.getString(key);
		String study_name = key.substring(0, key.indexOf(".studyid"));
		SPACE_names.put(idNum, study_name);
	    }
	}
    }

    /** search by the numeric study ID and return the Study_Space instance */
    public static StudySpace get_Space(String studyID) {
	if (SPACE_names == null || ALL_SPACE == null)
	    WISELogger.logError(
		    "GET Study Space failure - hash uninitialized. Try server restart on "
			    + WISEApplication.rootURL + ", "
			    + SurveyorApplication.ApplicationName, null);
	StudySpace ss = (StudySpace) ALL_SPACE.get(studyID);
	if (ss == null) {
	    String sName = (String) SPACE_names.get(studyID);
	    if (sName != null) {
		ss = new StudySpace(sName);
		// put Study_Space in ALL_SPACE
		ALL_SPACE.put(ss.id, ss);
	    }
	}
	return ss;
    }

    /** Load all the Study_Space spaces applicable for the given local server */
    public static String Load_Study_Spaces() {
	String studyID = null, study_name = null, resultstr = "";
	try {
	    if (SPACE_names == null || SPACE_names.size() < 1)
		return "Error: No Study Spaces found in props file";
	    // get study space info from shared properties
	    Enumeration enu = SPACE_names.keys();
	    while (enu.hasMoreElements()) {
		studyID = (String) enu.nextElement();
		study_name = (String) SPACE_names.get(studyID);
		String studySvr = WISEApplication.sharedProps
			.getString(study_name + ".server");
		String studyApp = WISEApplication.sharedProps
			.getString(study_name + ".serverApp");
		if (studySvr.equalsIgnoreCase(WISEApplication.rootURL)
			&& studyApp
				.equalsIgnoreCase(SurveyorApplication.ApplicationName)
			&& study_name != null && !study_name.equals("")) {
		    // create new Study_Space
		    StudySpace ss = new StudySpace(study_name);
		    // put Study_Space in ALL_SPACE
		    ALL_SPACE.put(ss.id, ss);
		    resultstr += "Loaded Study Space: " + ss.id + " for user "
			    + ss.db.dbuser + " <BR>\n";
		}
	    }
	} catch (Exception e) {
	    WISELogger.logError("Load Study Spaces Error for ID "
		    + studyID + ", name " + study_name + "\n" + e, e);
	}
	return resultstr;
    }

    /**
     * constructor to create study space and initialize the surveys & messages
     * hashtables
     */
    public StudySpace(String studyName) {
	if (studyName == null || studyName.equals("")) // will still return an
						       // uninitialized
						       // instance
	    return;
	study_name = studyName;
	String filename = "";
	try {
	    // Construct instance variables for this particular study space
	    id = WISEApplication.sharedProps.getString(studyName + ".studyid");
	    title = WISEApplication.sharedProps.getString(studyName
		    + ".proj.title");

	    // SET UP all of the paths that will apply for this Study Space,
	    // regardless of the app instantiating it
	    server_url = WISEApplication.sharedProps.getString(studyName
		    + ".server");
	    String dir_in_props = WISEApplication.sharedProps
		    .getString(studyName + ".dirName");
	    if (dir_in_props == null)
		dir_name = study_name; // default
	    else
		dir_name = dir_in_props;
	    application = WISEApplication.sharedProps.getString(studyName
		    + ".serverApp");
	    app_urlRoot = server_url + "/" + application + "/";
	    // Manoj changes
	    // servlet_urlRoot = server_url + "/"+ application + "/servlet/";
	    servlet_urlRoot = server_url + "/" + application + "/";
	    sharedFile_urlRoot = app_urlRoot
		    + WISEApplication.sharedProps.getString(studyName
			    + ".sharedFiles_linkName") + "/";

	    // project-specific styles and images need to be in shared area so
	    // they can be uploaded by admin server
	    style_url = sharedFile_urlRoot + "style/" + dir_name + "/";
	    image_url = sharedFile_urlRoot + "images/" + dir_name + "/";
	    // create & initialize the Preface
	    prefacePath = SurveyorApplication.xml_loc + "/" + dir_name
		    + "/preface.xml";
	    load_preface();
	    // create the message sender
	    surveys = new Hashtable();
	    db = new DataBank(this); // one DB per SS
	    db.readSurveys();
	} catch (Exception e) {
	    WISELogger.logError("Study Space create failure: " + id
		    + " at survey : " + filename + ". Error: " + e, e);
	}
    }

    public static StudySpace[] get_all() {
	int n_spaces = ALL_SPACE.size();
	if (n_spaces < 1) {
	    Load_Study_Spaces();
	    n_spaces = ALL_SPACE.size();
	}
	StudySpace[] result = new StudySpace[n_spaces];
	Enumeration et = StudySpace.ALL_SPACE.elements();
	int i = 0;
	while (et.hasMoreElements() && i < n_spaces) {
	    result[i++] = (StudySpace) et.nextElement();
	}
	return result;
    }

    /** deconstructor to destroy the surveys and messages hashtables */
    public void destroy() {
	surveys = null;
    }

    /** INSTANCE FUNCTIONS */

    /** establish dbase connection and returns a Connection object */
    public Connection getDBConnection() throws SQLException {
	// return
	// DriverManager.getConnection(mysql_url+dbdata+"?user="+dbuser+"&password="+dbpwd);
	// return
	// DriverManager.getConnection("jdbc:mysql://"+mysql_server+"/"+dbdata+"?user="+dbuser+"&password="+dbpwd);
	return db.getDBConnection();
    }

    public DataBank getDB() {
	return db;
    }

    /** search by the survey ID and returns a specific survey */
    public Survey get_Survey(String survey_id) {
	Survey s = (Survey) surveys.get(survey_id);
	return s;
    }

    /**
     * load or reload a survey from file, return survey ID or null if
     * unsuccessful
     */
    public String load_survey(String filename) {
	String sid = null;
	Survey s;
	try {
	    String file_loc = SurveyorApplication.xml_loc
		    + System.getProperty("file.separator") + dir_name
		    + System.getProperty("file.separator") + filename;
	    DocumentBuilderFactory factory = DocumentBuilderFactory
		    .newInstance();
	    factory.setCoalescing(true);
	    factory.setExpandEntityReferences(false);
	    factory.setIgnoringComments(true);
	    factory.setIgnoringElementContentWhitespace(true);
	    Document xml_doc = factory.newDocumentBuilder().parse(
		    CommonUtils.loadResource(file_loc));
	    System.out.println(xml_doc);
	    s = new Survey(xml_doc, this);
	    if (s != null) {
		sid = s.id;
		surveys.put(sid, s);
	    }
	} catch (Exception e) {
	    WISELogger.logError("Study Space " + dir_name
		    + " failed to parse survey " + filename + ". Error: " + e,
		    e);
	}
	return sid;
    }

    public void drop_Survey(String survey_id) {
	surveys.remove(survey_id);
    }

    /** load the preface file */
    // TODO: check admin; call when new preface uploaded
    public boolean load_preface() {
	String resourceStream = CommonUtils.getAbsolutePath(prefacePath);
	if (resourceStream != null) {
	    preface = new Preface(prefacePath);
	    preface.setHrefs(servlet_urlRoot, image_url);
	    return true;
	}
	preface = null;
	return false;
    }

    /** get a preface */
    public Preface get_preface() {
	if (preface == null) // should happen only if there's been some major
			     // problem
	    if (!load_preface()) {
				WISELogger.logInfo("Study Space " + dir_name
			+ " failed to load its preface file ");
		return null;
	    }
	return preface;
    }

    public User get_User(String msg_id) {
	return db.makeUser_fromMsgID(msg_id);
    }

    public String sendInviteReturnDisplayMessage(String msg_type,
	    String message_seq_id, String survey_id, String whereStr,
	    boolean isReminder) {
	return send_messages(msg_type, message_seq_id, survey_id, whereStr,
		isReminder, true);
    }

    public String sendInviteReturnMsgSeqId(String msg_type,
	    String message_seq_id, String survey_id, String whereStr,
	    boolean isReminder) {
	return send_messages(msg_type, message_seq_id, survey_id, whereStr,
		isReminder, false);
    }

    // send message to all invitees who match on whereStr
    private String send_messages(String msg_type, String message_seq_id,
	    String survey_id, String whereStr, boolean isReminder,
	    boolean displayMessage) {

	String messageSequenceId = null;
	// look up the correct message sequence in preface
	MessageSequence msg_seq = this.preface
		.get_message_sequence(message_seq_id);
	if (msg_seq == null) {
			WISELogger
					.logInfo("ADMIN INFO - SEND MESSAGES: Can't get the requested  message sequence "
			    + message_seq_id + AdminInfo.class.getSimpleName());
	    return null;
	}
	Message msg = msg_seq.get_type_message(msg_type); // passes thru an
	// integer for
	// 'other'
	// messages
	if (msg == null) {
			WISELogger
					.logInfo("ADMIN INFO - SEND MESSAGES: Can't get the message from hash");
	    return null;
	}
	String outputString = "";
	MessageSender sender = new MessageSender(msg_seq);
	try {
	    Connection conn = getDBConnection();
	    Statement msgUseQry = conn.createStatement();
	    Statement inviteeQuery = conn.createStatement();
	    Statement usrSteQry = conn.createStatement();

	    // FIRST, to obtain new IDs, insert pending message_use records
	    // for
	    // each subject
	    String msgUse_sql = "INSERT INTO survey_message_use (invitee, survey, message) "
		    + "SELECT id, '"
		    + survey_id
		    + "', 'attempt' FROM invitee WHERE " + whereStr;
	    msgUseQry.execute(msgUse_sql);

	    List<String> success_ids = new ArrayList<String>();
	    outputString += "Sending message '" + msg.subject + "' to:<p>";

	    // Now get back newly-created message IDs and invitee data at
	    // the
	    // same time
	    String invitee_sql = "SELECT firstname, lastname, salutation, AES_DECRYPT(email,'"
		    + this.db.email_encryption_key
		    + "'), invitee.id, survey_message_use.id "
		    + "FROM invitee, survey_message_use WHERE invitee.id = survey_message_use.invitee "
		    + "AND message = 'attempt' AND survey = '"
		    + survey_id
		    + "' AND " + whereStr;
	    ResultSet rs = inviteeQuery.executeQuery(invitee_sql);

	    // send email message to each selected invitee
	    while (rs.next()) {
		String firstname = rs.getString(1);
		String lastname = rs.getString(2);
		String salutation = rs.getString(3);
		String email = rs.getString(4);
		String invitee_id = rs.getString(5);
		String message_id = rs.getString(6);
		// This is used when for anonymous user. We want to return the
		// message id to the calling function from save_anno_user so
		// that it can forward the survey request automatically.
		messageSequenceId = message_id;
		// print out the user information
		outputString += salutation + " " + firstname + " " + lastname
			+ " with email address &lt;" + email + "&gt; -&gt; ";
		String msg_result = sender.send_message(msg, message_id, email,
			salutation, lastname, this.id);

		if (msg_result.equalsIgnoreCase("")) {
		    outputString += "message sent.<br>";
		    success_ids.add(invitee_id);
		} else {
		    msgUse_sql = "UPDATE survey_message_use SET message= 'err:"
			    + msg_result
			    + "' WHERE message = 'attempt' AND survey = '"
			    + survey_id + "' AND invitee = " + invitee_id;
		    msgUseQry.execute(msgUse_sql);
		    outputString += msg_result + "<br><br>";
		}
		String state = msg_result.equalsIgnoreCase("") ? "invited"
			: "email_error";
		if (msg_type.equalsIgnoreCase("invite")) {
		    String sql_u = "insert into survey_user_state (invitee, state, survey, message_sequence) "
			    + "values("
			    + invitee_id
			    + ", '"
			    + state
			    + "', '"
			    + survey_id
			    + "', '"
			    + message_seq_id
			    + "') "
			    + "ON DUPLICATE KEY UPDATE state='"
			    + state
			    + "', state_count=1, message_sequence=VALUES(message_sequence)";
		    // note timestamp updates automatically
		    usrSteQry.execute(sql_u);
		}
	    }
	    if (success_ids.size() > 0) {
		String successLst = "(";
		for (int i = 0; i < (success_ids.size() - 1); i++) {
		    successLst += success_ids.get(i) + ",";
		}
		successLst += success_ids.get(success_ids.size() - 1) + ")";
		outputString += successLst + "<br><br>";
		// Update survey message use with successes
		msgUse_sql = "UPDATE survey_message_use SET message= '"
			+ msg.id + "' WHERE message = 'attempt' AND survey = '"
			+ survey_id + "' AND invitee in " + successLst;
		msgUseQry.execute(msgUse_sql);
	    }
	    conn.close();
	} catch (Exception e) {
			WISELogger.logError("ADMIN INFO - SEND MESSAGES: " + e.toString(),
		    e);
	}
	// If the call comes from UI, we return outputString, if the call comes
	// from the anno user trying to take the survey we return messageSeqid
	// to the caller.
	return displayMessage ? outputString : messageSequenceId;
    }

    /** parse the config file and load all the study spaces */
    /*
     * public static void load_all_study_spaces() { try {
     * DriverManager.registerDriver(new com.mysql.jdbc.Driver());
     * 
     * // Get parser and an XML document Document doc =
     * DocumentBuilderFactory.newInstance
     * ().newDocumentBuilder().parse(config_loc);
     * 
     * // parse all study elements in the config file NodeList nl =
     * doc.getElementsByTagName("Study"); for (int i = 0; i < nl.getLength();
     * i++) { // create new Study_Space Study_Space ss = new
     * Study_Space(nl.item(i)); // put Study_Space in ALL_SPACE
     * ALL_SPACE.put(ss.id,ss); }
     * 
     * } catch (Exception e) {
     * Study_Util.email_alert("WISE - STUDY SPACE - LOAD ALL STUDY SPACES: "
     * +e.toString()); return; }
     * 
     * }
     */
    /** prints all the study spaces */
    /*
     * public static String print_ALL() { Study_Space ss;
     * 
     * String s = "ALL Study Spaces:<p>"; Enumeration e1 = ALL_SPACE.elements();
     * while (e1.hasMoreElements()) { ss = (Study_Space) e1.nextElement(); s +=
     * ss.print(); } s += "<p>"; return s; }
     */
    /** look up if the user and password exists in the list of study spaces */
    /*
     * public static String lookup_study_space(String u, String p) { Study_Space
     * ss; Enumeration e1 = ALL_SPACE.elements(); while (e1.hasMoreElements()) {
     * ss = (Study_Space) e1.nextElement(); if (ss.dbuser.equalsIgnoreCase(u))
     * if (ss.dbpwd.equalsIgnoreCase(p)) return ss.id; } return null; }
     */

    /** returns if a specific Study_Space has been loaded */
    /*
     * public static boolean space_exists(String id) { Study_Space ss =
     * (Study_Space) ALL_SPACE.get(id); if (ss == null) return false; else
     * return true; }
     * 
     * /** constructor to initialize the surveys and messages hashtables
     */
    /*
     * public Study_Space(Node n) { try { // parse the config node id =
     * n.getAttributes().getNamedItem("ID").getNodeValue(); location =
     * n.getAttributes().getNamedItem("Location").getNodeValue(); dbdata =
     * n.getAttributes().getNamedItem("DB_Data").getNodeValue(); dbuser =
     * n.getAttributes().getNamedItem("DB_User").getNodeValue(); dbpwd =
     * n.getAttributes().getNamedItem("DB_Password").getNodeValue(); title =
     * n.getAttributes().getNamedItem("Title").getNodeValue();;
     * 
     * style_path = "/wise_test/file/style/" + location + "/";
     * 
     * 
     * msg = new Message(xml_loc + "/" + location + "/messages.xml", this);
     * if(msg==null) Study_Util.email_alert("study space msg can't be created");
     * 
     * // open database connection Connection conn = getDBConnection();
     * Statement stmt = conn.createStatement();
     * 
     * // load all the surveys surveys = new Hashtable(); String sql =
     * "SELECT filename from surveys, (select max(internal_id) as maxint from surveys group by id) maxes where maxes.maxint = surveys.internal_id"
     * ; boolean dbtype = stmt.execute(sql); ResultSet rs = stmt.getResultSet();
     * while (rs.next()) { String filename = rs.getString("filename"); Survey s
     * = new Survey(filename,this); surveys.put(s.id,s); }
     * 
     * // close database stmt.close(); conn.close(); } catch (Exception e) {
     * Study_Util
     * .email_alert("WISE - STUDY SPACE - CONSTRUCTOR: "+e.toString()); return;
     * } }
     */

    /** prints a specific study space */

    @Override
	public String toString() {
	String s = "STUDY SPACE<br>";
	s += "ID: " + id + "<br>";
	s += "Location: " + dir_name + "<br>";
	s += "Study Name: " + study_name + "<br>";
	// s += "DB Password: "+dbpwd+"<p>";

	// print surveys
	s += "<hr>SURVEYS<BR>";
	Survey svy;
	Enumeration e1 = surveys.elements();
	while (e1.hasMoreElements()) {
	    svy = (Survey) e1.nextElement();
	    s += svy.toString();
	}

	s += "<hr>PREFACE<BR>";
	s += preface.toString();
	return s;
    }

}
