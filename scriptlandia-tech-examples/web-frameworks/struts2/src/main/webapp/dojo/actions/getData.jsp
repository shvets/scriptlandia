<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.json.JSONArray" %>

<%!
   private List list1 = new ArrayList();
	{
		list1.add(new LabelValueBean("Honda", "1"));
		list1.add(new LabelValueBean("Toyota", "2"));
		list1.add(new LabelValueBean("Mazda", "3"));
	}

	private List list21 = new ArrayList();
	{
		list21.add(new LabelValueBean("Accord", "1"));
		list21.add(new LabelValueBean("CRV", "2"));
	}

	private List list22 = new ArrayList();
	{
		list22.add(new LabelValueBean("Prius", "1"));
		list22.add(new LabelValueBean("RAV-4", "2"));
		list22.add(new LabelValueBean("Avalon", "3"));
	}

	private List list23 = new ArrayList();
	{
		list23.add(new LabelValueBean("M1", "1"));
		list23.add(new LabelValueBean("M2", "2"));
		list23.add(new LabelValueBean("M3", "3"));
	}
	private Map map2 = new HashMap();
	{
		map2.put("1", list21);
		map2.put("2", list22);
		map2.put("3", list23);
	}
%>

<%
	String command = request.getParameter("command");

	if(command.equals("list1Changed")) {
	  String id1 = request.getParameter("id1");

		List list = (List)map2.get(id1);

      JSONArray jsonArray = new JSONArray();

		for(int i=0; i < list.size(); i++) {
			LabelValueBean lvb = (LabelValueBean)list.get(i);

          JSONObject jsonObj = new JSONObject();
	      jsonObj.put("label", lvb.getLabel());
	      jsonObj.put("value", lvb.getValue());

			jsonArray.put(jsonObj);
		}
        out.print( jsonArray.toString());
	}
	else if(command.equals("list2Changed")) {
	  String id1 = request.getParameter("id1");
	  String id2 = request.getParameter("id2");

		LabelValueBean lvb = null;

		List list = (List)map2.get(id1);

		for(int i=0; i < list.size() && lvb == null; i++) {
			LabelValueBean lvbi = (LabelValueBean)list.get(i);

			if( lvbi.getValue().equals(id2)) {
			  lvb = lvbi;
		    }
		}

	  out.print("You have selected: " + lvb.getLabel());
	}
%>