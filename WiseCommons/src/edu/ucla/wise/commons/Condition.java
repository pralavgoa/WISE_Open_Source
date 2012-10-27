package edu.ucla.wise.commons;

import java.util.Hashtable;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is a subclass of Page_Item and represents a Precondition object on
 * the page
 */
public class Condition extends PageItem {
    /** Instance Variables */
    private String pre_field, pre_field_second = "";
    private Integer int_constant = null;
    private int operatr_int = 0;
    Condition cond, cond2;
    private StringBuffer js_expression = new StringBuffer("");

    // private Page page;

    /** constructor: parse a Precondition node from XML */
    public Condition(Node n) {
	// get the page item properties
	super(n);
	try {
	    // assign various attributes
	    NodeList subnodes = n.getChildNodes();
	    Node node1 = subnodes.item(1);
	    Node node2 = subnodes.item(3);
	    Node node3 = subnodes.item(5);
	    String node1_name = node1 != null ? node1.getNodeName() : "";
	    String node2_name = node2 != null ? node2.getNodeName() : "";
	    String node3_name = node3 != null ? node3.getNodeName() : "";

	    // option 1: if the Condition is a leaf node, first node is a
	    // "field"
	    if (node1_name.equalsIgnoreCase("field")) {
		// parse the leaf node
		// note XML Schema enforces order of: field, operator, (constant
		// OR field)
		pre_field = node1.getFirstChild().getNodeValue();
		js_expression.append("(a['" + pre_field + "']");
		if (pre_field.equals(""))
		    throw new Exception(
			    "Invalid Precondition: Empty field name before "
				    + node2_name);
		// represent comparison operator as integer, since eval needs
		// 'switch,' which can't take strings
		if (node2_name.equalsIgnoreCase("gt")) {
		    operatr_int = 11;
		    js_expression.append(" > ");
		} else if (node2_name.equalsIgnoreCase("lt")) {
		    operatr_int = 12;
		    js_expression.append(" < ");
		} else if (node2_name.equalsIgnoreCase("geq")) {
		    operatr_int = 13;
		    js_expression.append(" >= ");
		} else if (node2_name.equalsIgnoreCase("leq")) {
		    operatr_int = 14;
		    js_expression.append(" <= ");
		} else if (node2_name.equalsIgnoreCase("eq")) {
		    operatr_int = 15;
		    js_expression.append(" = ");
		} else if (node2_name.equalsIgnoreCase("neq")) {
		    operatr_int = 16;
		    js_expression.append(" != ");
		} else
		    throw new Exception("Invalid operator in Precondition: "
			    + node2_name);
		// obtain the value for comparison
		if (node3_name.equalsIgnoreCase("cn")) {
		    String const_str = node3.getFirstChild().getNodeValue();
		    if (const_str != null) {
			int_constant = new Integer(const_str);
			js_expression.append(const_str);
			js_expression.append(")");
		    }
		} else if (node3_name.equalsIgnoreCase("field")) {
		    pre_field_second = node3.getFirstChild().getNodeValue();
		    js_expression.append(pre_field_second);
		    js_expression.append(")");
		    if (pre_field_second.equals(""))
			throw new Exception(
				"Invalid Precondition: Empty field name after "
					+ node1_name);
		} else
		    throw new Exception("Invalid comparator in Precondition: "
			    + node3_name);
	    }
	    // option 2, syntax: apply, and|or, apply
	    else if (node1_name.equalsIgnoreCase("apply")) {
		// recursively parse the nested preconditions
		cond = new Condition(node1); // the apply node is itself a
					     // predicate
		js_expression.append(cond.getJs_expression());
		if (node2_name.equalsIgnoreCase("and")) {
		    operatr_int = 1;
		    js_expression.append(" && ");
		} else if (node2_name.equalsIgnoreCase("or")) {
		    operatr_int = 2;
		    js_expression.append(" || ");
		} else
		    throw new Exception(
			    "Invalid boolean operator in Precondition: "
				    + node2_name);
		// recursively parse the 2nd apply node - another nested
		// precondition
		if (node3_name.equalsIgnoreCase("apply")) {
		    cond2 = new Condition(node3);
		    js_expression.append(cond2.getJs_expression());
		}

		else
		    throw new Exception(
			    "Invalid righthand predicate in Precondition: "
				    + node3_name);
	    } else
		throw new Exception("Invalid Precondition node starting at: "
			+ node1_name);

	}// end of try
	catch (Exception e) {
	    WISEApplication.log_error(
		    "WISE - CONDITION parse: " + e.toString(), null);
	    return;
	}
    }

    public int countFields() // should never be called
    {
	return 0;
    }

    /** recursively execute the condition check */
    public boolean check_condition(User u) {
	boolean result = false;
	// if the recursion has not reached to the leaf level, then continue to
	// check the condition
	if (operatr_int < 10) // not a leaf node
	{
	    boolean apply_result = cond.check_condition(u);
	    boolean apply2_result = cond2.check_condition(u);
	    switch (operatr_int) {
	    case 1:
		result = (apply_result && apply2_result);
		break;
	    case 2:
		result = (apply_result || apply2_result);
		break;
	    }
	}
	// recursion has reached a leaf node -
	else {
	    // attempt lookup of value for field name(s) from user
	    Integer fieldVal1 = u.get_field_value(pre_field);
	    // check whether a 2-field compare vs. field-constant compare
	    // uses pre_field_second to signal since pre_cn can't hold null as
	    // an int
	    if (pre_field_second.equals(""))
		result = compare(fieldVal1, operatr_int, int_constant);
	    else
		result = compare(fieldVal1, operatr_int,
			u.get_field_value(pre_field_second));
	}
	return result;
    }

    private boolean compare(Integer fieldInt1, int op, Integer fieldInt2) {
	boolean result = false;
	// check for 2 special cases of nulls; all others containing null should
	// be false
	if (fieldInt1 == null && fieldInt2 == null && op == 15)
	    return true;
	else if (fieldInt1 == null || fieldInt2 == null)
	    return op == 16; // if one is null then other must not be here and
			     // != would be true
	int fieldVal1 = fieldInt1.intValue();
	int fieldVal2 = fieldInt2.intValue();
	switch (op) {
	case 11:
	    result = (fieldVal1 > fieldVal2);
	    break;
	case 12:
	    result = (fieldVal1 < fieldVal2);
	    break;
	case 13:
	    result = (fieldVal1 >= fieldVal2);
	    break;
	case 14:
	    result = (fieldVal1 <= fieldVal2);
	    break;
	case 15:
	    result = (fieldVal1 == fieldVal2);
	    break;
	case 16:
	    result = (fieldVal1 != fieldVal2);
	    break;
	default:
	    break;
	}
	return result;
    }

    /**
     * Check precondition for each member of a SubjectSet (to display stems in a
     * question block)
     */
    public boolean[] check_condition(String SubjectSetName,
	    String[] SubjectSet, User theUser) {
	boolean[] resultVector = new boolean[SubjectSet.length];
	int i;
	// the current prediction node has the apply child
	if (operatr_int < 10) // not a leaf node
	{
	    // each sub-call returns the full vector of results for each string
	    boolean[] apply_result = cond.check_condition(SubjectSetName,
		    SubjectSet, theUser);
	    boolean[] apply2_result = cond2.check_condition(SubjectSetName,
		    SubjectSet, theUser);
	    // apply comparison to each paired element
	    switch (operatr_int) {
	    case 1:
		for (i = 0; i < SubjectSet.length; i++)
		    resultVector[i] = (apply_result[i] && apply2_result[i]);
		break;
	    case 2:
		for (i = 0; i < SubjectSet.length; i++)
		    resultVector[i] = (apply_result[i] || apply2_result[i]);
		break;
	    }
	} else {
	    // get the value set for the field name
	    int[] pre_fv = get_valuelist(theUser, SubjectSetName, SubjectSet,
		    pre_field);
	    // if the comparison pair is field vs. field, then get another value
	    // set of the 2nd field name
	    if (pre_field_second.equals("")) {
		i = 0;
		while (i < SubjectSet.length) {
		    // resultVector[i] = compare(pre_fv[i], operatr_int,
		    // int_constant);
		    i++;
		}
	    } else {
		int[] pre_fv2 = get_valuelist(theUser, SubjectSetName,
			SubjectSet, pre_field_second);
		i = 0;
		while (i < SubjectSet.length) {
		    // resultVector[i] = compare(pre_fv[i], operatr_int, new
		    // Integer (pre_fv2[i])); //Quick patch to compile here
		    i++;
		}
	    }
	}
	return resultVector;
    }

    /**
     * search by field name, get the value set from the subject data table that
     * user conducted
     */
    public int[] get_valuelist(User theUser, String SubjectSetName,
	    String[] SubjectSet, String field_name) {
	int[] list_value = new int[SubjectSet.length];
	String[] list_v = new String[SubjectSet.length];
	Hashtable DataSet;
	// try
	// {
	// //connect to the database
	// Connection conn = page.survey.getDBConnection();
	// Statement stmt = conn.createStatement();
	// String sql="";
	// DataSet = new Hashtable();
	// //get data from database for subject
	// sql = "select subject, "+ field_name.toUpperCase()+" from ";
	// sql += page.survey.id + "_"+SubjectSetName+ "_data ";
	// sql += "where invitee = "+ theUser.id;
	// boolean dbtype = stmt.execute(sql);
	// ResultSet rs = stmt.getResultSet();
	// while(rs.next())
	// {
	// String val=rs.getString(field_name.toUpperCase());
	// if(val==null || val.equalsIgnoreCase(""))
	// val="0";
	// DataSet.put(rs.getString("subject"), val);
	// }
	// stmt.close();
	// conn.close();
	//
	// //get the array of column values from hashtable
	// if(!DataSet.isEmpty())
	// {
	// for(int i=0; i<SubjectSet.length; i++)
	// {
	// String
	// current_key=SubjectSet[i].substring(SubjectSet[i].lastIndexOf("_")+1);
	// //Study_Util.email_alert("CONDITION GET VALUELIST: - check keys: " +
	// current_key);
	// list_v[i]= (String) DataSet.get(current_key);
	// if(list_v[i]==null || list_v[i].equalsIgnoreCase("null") ||
	// list_v[i].equalsIgnoreCase("") )
	// list_value[i]=0;
	// else
	// list_value[i]= Integer.parseInt(list_v[i]);
	// }
	// }
	// else
	// {
	// Study_Util.email_alert("CONDITION GET VALUELIST: the hashtable is empty");
	// }
	//
	// }
	// catch (Exception e)
	// {
	// Study_Util.email_alert("CONDITION GET VALUELIST: "+e.toString());
	// }
	return list_value;
    }

    public String toString() {
	if (operatr_int < 10) // not a leaf node
	    return "apply node<br>";
	else if (pre_field_second.equals(""))
	    return "leaf node: (" + pre_field + ") op ("
		    + int_constant.toString() + ")<br>";
	else
	    return "leaf node: (" + pre_field + ") op (" + pre_field_second
		    + ")<br>";
    }

    public String getJs_expression() {
	return this.js_expression.toString();
    }
}
