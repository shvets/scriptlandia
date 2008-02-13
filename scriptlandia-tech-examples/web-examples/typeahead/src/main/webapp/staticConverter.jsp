<%@ page import="org.apache.struts.util.LabelValueBean"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<%@ page import="typeahead.Pair"%>

<%
  String listName = request.getParameter("list.name");
  String autosuggestId = request.getParameter("autosuggest.id");

  Object listObject = request.getAttribute(listName);
%>

<script type="text/javascript">
  function <%=autosuggestId%>_convertList() {
    var items = [
<%
      if(listObject instanceof List) {
        List list = (List)listObject;

        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
          Object object = iterator.next();

          String key = null;
          String value = null;

          if(object instanceof LabelValueBean) {
            LabelValueBean bean = (LabelValueBean)object;
            key = bean.getValue();
            value = bean.getLabel();
          }
          else if(object instanceof Pair) {
            Pair bean = (Pair)object;
            key = bean.getKey();
            value = bean.getContent();
          }
          else if(object instanceof String) {
            String bean = (String)object;
            int index = bean.indexOf(":");
            key = bean.substring(0, index);
            value = bean.substring(index+1);
          }

          if(key != null) {
%>
            new AutoSuggestPair('<%= key %>',
<%
              if(value.indexOf("\'") == -1) { %>
                '<%= value %>'
<%
              }
              else { %>
                "<%= value %>"
<%
              }
%>
            )
<%
            if(iterator.hasNext()) { %>
              ,
<%
            }
          }
        }
      }
%>
    ];

    return items;
  }

</script>
