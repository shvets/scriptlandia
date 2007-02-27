<%--

 NOT the usual license stuff, SO PLEASE READ:

 This page gives any user who has access to it the ability to execute arbitrary Beanshell
 commands in your server JVM. Even though a primitive 'allowed' list is built-in to control
 which hosts have access (by default only the localhost, see below), you probably still
 DO NOT WANT TO KEEP THIS FILE DEPLOYED IN ANY PRODUCTION SERVER.

 ------------------------------------------------------------------------------------------

 Now the usual license stuff (MIT License):

 Copyright (c) 2006 digiZen Studio, LLC 

 Permission is hereby granted, free of charge, to any person obtaining a copy of this
 software and associated documentation files (the "Software"), to deal in the Software
 without restriction, including without limitation the rights to use, copy, modify, merge,
 publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or
 substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 DEALINGS IN THE SOFTWARE.

--%>
<%@ page import="bsh.*"%><%@ page import="java.util.*"%><%@ page import="java.io.*"%><%!

/******** Config section begins. **********/
// Edit this list to control who gets access to this page. Does NOT support wildcards.
final String[] _allowed = new String[] {
  "127.0.0.1",
  "localhost"
};
/********* Config section ends. *************/

final static String version = "0.5";

final static int HISTORY_ITEM_ARRAY_LEN = 4;
final static int IDX_EXPR = 0;
final static int IDX_RESULT = 1;
final static int IDX_TIMESTAMP = 2;
final static int IDX_HAS_ERROR = 3;

final static String KEY_EXPR = "expr";
final static String KEY_RESET = "reset";
final static String KEY_DOWNLOAD = "download";

final static String KEY_HISTORY = "bsh.history";
final static String KEY_INTERPRETER = "bsh.interpreter";

%><%  /* the "controller" */
if (shouldBoot(request, out)) {
  return;
}

if (request.getParameter(KEY_DOWNLOAD) != null) {
  onDownload(response, out, session);
  return;
}

boolean doReset = (request.getParameter(KEY_RESET) != null);
if (doReset) {
  onReset(session);
}

List history = getHistory(session);

String result = "";
boolean hasError = false;
String expr = request.getParameter(KEY_EXPR);
if (expr != null && !expr.equals("")) {
  String[] historyItem = runEval(getInterpreter(session), expr);
  history.add(historyItem);
  result = historyItem[IDX_RESULT];
  hasError = historyItem[IDX_HAS_ERROR] != null;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
  <title>Beanshell Web Console</title>
  <style type="text/css">
body {
  font-family: verdana, sans-serif;
  font-size: 10pt;
}

a {
  text-decoration:none;
  font-weight: 700;
}

a:link, a:visited, a:active {
  color: #8f8f8f;
}

a:hover {
  color: #0f0f0f;
}

.headline {
  font-family: times, sans-serif;
  font-size: 140%;
  font-weight: bold;
  font-variant: small-caps;
}

.content {
  border: 1px solid #e3e3e3;
  padding: 10px;
}

.utils {
  display: block;
  margin-bottom: 10px;
}

label {
  display: block;
}

textarea {
  display: block;
}

.errorBlock {
  color: #AA0000;
}

.errorBlock, .resultBlock {
  display: block;
  xbackground-color: #F8F8F8;
  border: 1px solid #c8c8c8;
  font-weight: 500;
  padding: 3px 8px;
  margin: 8px 0px;
}

.historyBlock {
  width: 100%;
}

caption {
  font-size: 110%;
  font-weight: bold;
  padding-up: 10px;
  padding-bottom: 8px;
}

.history {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
  margin-bottom: 10px;
}

td {
  border: 1px solid #c8c8c8;
  padding: 2px 7px 2px 7px;
}

.tableHeader {
  background-color: #e8e8e8;
  color: #282828;
  font-weight: 600;
}

.evenRow {
  background-color: #fbfbfb;
}

.versionBlock {
  clear:both;
  font-size: 80%;
  font-style: italic;
}
  </style>
</head>
<body>
  <div class="content"> 
    <div class="utils">
      <a href="bsh.jsp?<%=KEY_RESET%>=1"><acronym title="Reset current bsh session.">Reset</abbr></a>&nbsp;:&nbsp;<a href="bsh.jsp?<%=KEY_DOWNLOAD%>=1"><acronym title="Download the script accumulated in current bsh session.">Download Script</acronym></a>
    </div>
    <div class="formBlock">
      <form action="bsh.jsp" method="POST">
        <label for="expr">Script (<em>session</em> is an implicit object):</label>
        <textarea id="<%=KEY_EXPR%>" name="<%=KEY_EXPR%>" cols="80" rows="10"><%=hasError?expr:""%></textarea>
        <input type="submit" value="Run" />
      </form>
    </div>

    <% if (!result.equals("")) {%>
    <div class="<%=hasError?"errorBlock":"resultBlock"%>">
    <strong>Result:</strong><br />
    <%=nl2br(result)%>
    </div>
    <% } %>

    <div class="historyBlock">
    <table class="history">
      <caption>History</caption>
      <tr class="tableHeader"><td>Expr</td><td>Result</td><td>Time</td></tr>
      <%
      int counter = 0;
      for (Iterator i = history.iterator(); i.hasNext(); ) {
          counter++;
          String[] item = (String[])i.next();
          String rowStyle = "";
          if (counter % 2 == 0) {
            rowStyle = "evenRow";
          }
      %>
      <tr class="<%=rowStyle%>"><td><%=nl2br(item[IDX_EXPR])%></td><td><%=nl2br(item[IDX_RESULT])%></td><td><%=item[IDX_TIMESTAMP]%></td></tr>
      <%}%>
    </table>
    </div>
  </div>
  <div class="versionBlock">Version <%=version%></div>
</body>
</html>
<%!
private void onDownload(HttpServletResponse response, JspWriter out, HttpSession session) throws IOException {
  List history = getHistory(session);
  response.setContentType("text/plain");
  for (Iterator iItem = history.iterator(); iItem.hasNext(); ) {
    String[] item = (String[])iItem.next();
    // only print the commands that didn't cause an error.
    if (item[IDX_HAS_ERROR] == null) {
      out.println(item[IDX_EXPR]);
    }
  }
}

private void onReset(HttpSession session) {
  getHistory(session).clear();
  session.removeAttribute(KEY_HISTORY);
  session.removeAttribute(KEY_INTERPRETER);
}

private boolean shouldBoot(HttpServletRequest request, JspWriter out) throws IOException {
  String remoteHost = request.getRemoteHost();
  for (int i = 0; i < _allowed.length; i++) {
    if (_allowed[i].equalsIgnoreCase(remoteHost)) {
      return false;
    }
  }
  out.println("Your host name: <strong>" + remoteHost + "</strong> is not on the allowed list.");
  return true;
}

private Interpreter getInterpreter(HttpSession session) {
  Interpreter intpr = (Interpreter)session.getAttribute(KEY_INTERPRETER);
  if (intpr == null) {
    intpr = new Interpreter();
    session.setAttribute(KEY_INTERPRETER, intpr);
    try {
      intpr.set("session", session);
    } catch (EvalError e) { }
  } 
  return intpr;
}

private List getHistory(HttpSession session) {
  List history = (List)session.getAttribute(KEY_HISTORY);
  if (history == null) {
    history = new ArrayList();
    session.setAttribute(KEY_HISTORY, history);
  }
  return history;
}

private String[] runEval(Interpreter intpr, String expr) {
  String result = "";
  EvalError error = null;
  String errorTxt = null;
  try {
    Object resultObj = intpr.eval(expr);
    result = (resultObj != null) ? resultObj.toString() : "&lt;null&gt;";
  } catch (EvalError e) {
    error = e;
    errorTxt = "Error on line " + error.getErrorLineNumber() + ": " + error.getErrorText();
  }

  if (error != null) {
    result = errorTxt;
  }

  String[] historyItem = new String[HISTORY_ITEM_ARRAY_LEN];
  historyItem[IDX_EXPR] = expr;
  historyItem[IDX_RESULT] = result;
  historyItem[IDX_TIMESTAMP] = new Date().toString(); // the timestamp
  historyItem[IDX_HAS_ERROR] = (error != null ? "" : null); // whether this was an erroneous command.

  return historyItem;
}

private String nl2br(String str) {
  if (str == null) {
    return "&lt;null&gt;";
  } else {
    return str.replaceAll("\\n", "<br />");
  }
}
%>
