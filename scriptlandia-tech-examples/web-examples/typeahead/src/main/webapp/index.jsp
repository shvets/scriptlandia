<%@ page import="typeahead.Suggestions"%>
<%@ page import="java.util.Collection"%>
<%@ page import="typeahead.StatesCountriesSuggestionsProvider"%>

<%
  Suggestions suggestions = new Suggestions();
  Suggestions.setImplementation(new StatesCountriesSuggestionsProvider());

  Collection list1 = suggestions.getSuggestions("countries");
  Collection list2 = suggestions.getSuggestions("states");
%>

<html>

<head>
  <title>Autosuggest Example 2</title>

  <!--script type="text/javascript" src="log4javascript.js"></script>
  <link rel="stylesheet" type="text/css" href="log4javascript.css" /-->

  <link rel="stylesheet" href="<%=request.getContextPath()%>/autosuggest.css" media="all"/>

  <script type="text/javascript" src="<%=request.getContextPath()%>/autosuggest.js"></script>
  <!--script type="text/javascript" src="<%=request.getContextPath()%>/autosuggest_ajax.js"></script-->

  <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Suggestions.js'></script>
  <!--script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Pair.js'></script-->
  <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
  <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

  <script type="text/javascript">
    function test_index() {
      document.getElementById("country").focus();
    }

    if(window.addEventListener) {
      window.addEventListener('load', test_index, false);
    }
    else if (window.attachEvent) {
      window.attachEvent('onload', test_index);
    }
  </script>
</head>

<body>
  <p>The type ahead example.</p>
  <%
    session.setAttribute("countries", list1);
  %>

  <jsp:include page="autosuggest.jsp">
    <jsp:param name="autosuggest.id" value="countries"/>

    <jsp:param name="debug" value="true"/>
    <jsp:param name="struts.aware" value="false"/>
    <jsp:param name="ajax.aware" value="false"/>
    <jsp:param name="display.complete.list.on.dblclick" value="false"/>
    <jsp:param name="highlight.color" value="yellow"/>

    <jsp:param name="list.name" value="countries"/>
    <jsp:param name="list.size" value="10"/>

    <jsp:param name="item.name" value="country"/>
    <jsp:param name="item.id" value="countryId"/>

    <jsp:param name="textfield.size" value="25"/>
  </jsp:include>

  <%
    session.setAttribute("countries2", list1);
  %>

  <jsp:include page="autosuggest.jsp">
    <jsp:param name="autosuggest.id" value="countries2"/>

    <jsp:param name="debug" value="true"/>
    <jsp:param name="ajax.aware" value="false"/>

    <jsp:param name="list.name" value="countries2"/>
    <jsp:param name="list.size" value="10"/>

    <jsp:param name="item.name" value="country"/>
    <jsp:param name="item.id" value="countryId"/>

    <jsp:param name="default.value" value="Canada"/>
    <jsp:param name="default.id" value="ca"/>

    <jsp:param name="textfield.size" value="25"/>
  </jsp:include>

  <%
    session.setAttribute("states", list2);
  %>

  <jsp:include page="autosuggest.jsp">
    <jsp:param name="autosuggest.id" value="states"/>

    <jsp:param name="debug" value="false"/>
    <jsp:param name="struts.aware" value="false"/>
    <jsp:param name="ajax.aware" value="false"/>

    <jsp:param name="list.name" value="states"/>
    <jsp:param name="list.size" value="15"/>

    <jsp:param name="item.name" value="state"/>
    <jsp:param name="item.id" value="stateId"/>

    <jsp:param name="textfield.size" value="25"/>
  </jsp:include>

<%--
  <select id="regionCode" onchange="form.submit();">
<%
    Iterator iterator3 = list2.iterator();

    while (iterator3.hasNext()) {
      Pair pair = (Pair)iterator3.next();
      String key = pair.getKey();
      String value = pair.getContent();
%>
      <option value="<%=key%>"><%=value%></option>
<%
    }
%>
  </select>
--%>
</body>

</html>

