package edu.ucla.wise.commons;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains a translation item and all its possible properties
 */

public class Translation_Item {
    /** Instance Variables */
    public String id;
    public String name;
    public String charset;
    public String selector;

    public String text;
    public String stem;

    public ArrayList responses;
    public ArrayList values;

    public String[] stems;
    public String[] stem_names;
    public ArrayList subjects;

    public Survey survey;

    /** constructor: parse a translation item node from the XML */
    public Translation_Item(Node n, Survey s) {
	try {
	    survey = s;
	    // parse the translation node and assign its properties
	    id = n.getAttributes().getNamedItem("ID").getNodeValue();
	    name = n.getAttributes().getNamedItem("name").getNodeValue();
	    // charset is the encoding type
	    charset = n.getAttributes().getNamedItem("charset").getNodeValue();

	    Node node1 = n.getAttributes().getNamedItem("selector");
	    if (node1 != null)
		selector = node1.getNodeValue();
	    else
		selector = "0";

	    NodeList nodelist = n.getChildNodes();
	    responses = new ArrayList();
	    values = new ArrayList();

	    // get the size of the subject stem array
	    int num_stems = 0;
	    for (int i = 0; i < nodelist.getLength(); i++) {
		if (nodelist.item(i).getNodeName().equalsIgnoreCase("Sub_Stem"))
		    num_stems++;
	    }
	    // declare the stem array
	    stems = new String[num_stems];
	    stem_names = new String[num_stems];
	    int id_num = 1;

	    for (int i = 0, j = 0; i < nodelist.getLength(); i++) {
		Node nc = nodelist.item(i);
		// parse the translted directive
		if (nc.getNodeName().equalsIgnoreCase("Directive")) {
		    stem = nc.getFirstChild().getNodeValue();
		}
		// parse the stem - translated version (open question/closed
		// question/question block)
		if (nc.getNodeName().equalsIgnoreCase("Stem")) {
		    stem = nc.getFirstChild().getNodeValue();
		    // Study_Util.email_alert("get the translated stem: " +
		    // stem);
		    /*
		     * Transformer transformer =
		     * TransformerFactory.newInstance().newTransformer();
		     * StringWriter sw = new StringWriter();
		     * transformer.transform(new DOMSource(nc), new
		     * StreamResult(sw)); stem = sw.toString();
		     * Study_Util.email_alert("get the translated stem: " +
		     * stem);
		     */
		}
		// parse the translated response option (closed
		// question/question block)
		if (nc.getNodeName().equalsIgnoreCase("Response_Option")) {
		    String str = nc.getFirstChild().getNodeValue();
		    responses.add(str);
		    Node node2 = nodelist.item(i).getAttributes()
			    .getNamedItem("value");
		    if (node2 != null)
			values.add(node2.getNodeValue());
		    else
			values.add("-1");
		}
		// parse the translated sub stem (question block)
		if (nc.getNodeName().equalsIgnoreCase("Sub_Stem")) {
		    stems[j] = nc.getFirstChild().getNodeValue();
		    stem_names[j] = name + "_" + (j + 1);
		    j++;
		}
		// parse the translated sub set reference (question block)
		if (nc.getNodeName().equalsIgnoreCase("Subject")) {
		    String[] str = new String[2];
		    if (nc.getAttributes().getNamedItem("IDnum") != null)
			str[0] = nc.getAttributes().getNamedItem("IDnum")
				.getNodeValue();
		    else
			str[0] = Integer.toString(id_num++);
		    str[1] = nc.getFirstChild().getNodeValue();
		    subjects.add(str);
		}
	    }
	} catch (Exception e) {
	    WISELogger.logError("WISE - Translation Item: ID = " + id
		    + "; Survey = " + s.id + "; Study = " + s.study_space.id
		    + " --> " + e.toString(), null);
	    return;
	}
    }

}
