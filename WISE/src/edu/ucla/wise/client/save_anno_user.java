package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.commons.Message_Sequence;
import edu.ucla.wise.commons.Study_Space;
import edu.ucla.wise.commons.WISE_Application;

/**
 * Servlet implementation class save_anno_user
 */
public class save_anno_user extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public save_anno_user() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	PrintWriter pw = response.getWriter();

	// get the ecoded study space ID
	String spaceid_encode = request.getParameter("t");
	// decode study space ID
	String spaceid_decode = WISE_Application.decode(spaceid_encode);

	Study_Space theStudy = Study_Space.get_Space(spaceid_decode);

	/**
	 * 1. Adding the New User
	 */
	int userId = theStudy.db.addInviteeAndReturnUserId(request
		.getParameterMap());

	/**
	 * 2. Sending the New User initial invite
	 */
	// Get the Message Sequence associated with invite.
	String surveyIdString = theStudy.db.getCurrentSurveyIdString();
	Message_Sequence[] msg_seqs = theStudy.preface
		.get_message_sequences(surveyIdString);
	if (msg_seqs.length == 0)
	    pw.println("No message sequences found in Preface file for selected Survey");
	String msgSeqId = theStudy.sendInviteReturnMsgSeqId("invite",
		msg_seqs[0].id, surveyIdString, " invitee.id in ( " + userId
			+ " )", false);

	request.setAttribute("msg", WISE_Application.encode(msgSeqId));
	StringBuffer destination = new StringBuffer();
	destination.append("/WISE/survey").append("?msg=")
		.append(WISE_Application.encode(msgSeqId))
		.append("&t=" + WISE_Application.encode(theStudy.id));
	response.sendRedirect(destination.toString());

    }
}
