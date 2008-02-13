<%
  String debugStr = request.getParameter("debug");
  boolean isDebug = (debugStr != null) && debugStr.equalsIgnoreCase("true");

  String strutsAwareStr = request.getParameter("struts.aware");
  boolean isStrutsAware = (strutsAwareStr != null && strutsAwareStr.equalsIgnoreCase("true"));

  String ajaxAwareStr = request.getParameter("ajax.aware");
  boolean isAjaxAware = (ajaxAwareStr != null && ajaxAwareStr.equalsIgnoreCase("true"));

  String displayCompleteList = request.getParameter("display.complete.list.on.dblclick");
  boolean isDisplayCompleteList = (displayCompleteList != null && displayCompleteList.equalsIgnoreCase("true"));

  String disabledStr = request.getParameter("disabled");
  boolean isDisabled = disabledStr != null && disabledStr.equalsIgnoreCase("true");

  String styleClass = request.getParameter("styleClass");

  String highlightColor = request.getParameter("highlight.color");

  String autosuggestId = request.getParameter("autosuggest.id");

  String itemName = request.getParameter("item.name");
  String itemId = request.getParameter("item.id");

  String layerId = autosuggestId + "." + itemId + "_div1";

  String listName = request.getParameter("list.name");
  String listSize = request.getParameter("list.size");

  String textFieldSize = request.getParameter("textfield.size");

  String defaultValue = request.getParameter("default.value");
  defaultValue = (defaultValue == null || defaultValue.equalsIgnoreCase("null")) ? "" : defaultValue;

  String defaultId = request.getParameter("default.id");
  defaultId = (defaultId == null || defaultValue.equalsIgnoreCase("null")) ? "" : defaultId;

  if(isStrutsAware) { %>
    <%@ include file="/WEB-INF/templates/taglibs.inc.jsp" %>
<%
  }

  if(!isAjaxAware) { %>
  <%--
  <jsp:include page="staticConverter.jsp">
    <jsp:param name="autosuggest.id" value="<%=autosuggestId%>"/>
    <jsp:param name="list.name" value="<%=listName%>"/>
  </jsp:include>
--%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/anyname.staticConverter?autosuggestId=<%=autosuggestId%>&listName=<%=listName%>"></script>
<%
  }
%>

<script type="text/javascript">
  function <%=autosuggestId%>_function() {
    var suggestionsProvider = new SuggesionsProvider('<%=autosuggestId%>', '<%=listName%>');

    if(<%=!isAjaxAware%>) {
      suggestionsProvider.setAllSuggestions(<%=autosuggestId%>_convertList());
    }

    var autoSuggestControl = new AutoSuggestControl(suggestionsProvider, "<%=itemName%>", "<%=itemId%>",
            "<%=layerId%>", <%=isAjaxAware%>);

    autoSuggestControl.setSize(<%=listSize%>);
    autoSuggestControl.setDisplayCompleteList(<%=isDisplayCompleteList%>);
    autoSuggestControl.setDefaults("<%=defaultId%>", "<%=defaultValue%>");
<%
    if(highlightColor != null) { %>
      autoSuggestControl.setHighlightColor('<%=highlightColor%>');
<%
    }
%>
  }

  if(window.addEventListener) {
    window.addEventListener('load', <%=autosuggestId%>_function, false);
  }
  else if (window.attachEvent) {
    window.attachEvent('onload', <%=autosuggestId%>_function);
  }
</script>

<div id="<%=layerId%>">
<%
  if(isStrutsAware) {
%>
    <html:text property="<%=itemName%>" size="<%=textFieldSize%>" value="<%=defaultValue%>"
               styleClass="<%=styleClass%>"
               disabled="<%=isDisabled%>"/>
<%
    if(isDebug) { %>
      <input type="text" id="<%=itemId%>" disabled value="<%=defaultId%>"/>
<%
    }
%>
    <html:hidden property="<%=itemId%>"/>
<%
  }
  else { %>
    <input type="text" id="<%=itemName%>" size="<%=textFieldSize%>" value="<%=defaultValue%>"
           style="<%=styleClass%>"
      <% if(isDisabled) { %>
        disabled
      <%}%>
    />
<%
    if(isDebug) { %>
      <input type="text" id="<%=itemId%>" disabled value="<%=defaultId%>"/>
<%
    }
%>
    <input type="hidden" id="<%=itemId%>"/>
<%
  }
%>
</div>
