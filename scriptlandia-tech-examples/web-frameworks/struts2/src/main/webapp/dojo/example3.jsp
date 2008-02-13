<html>

<head>
<title>Example 3</title>

	<script language="javascript" src="../js/dojo.js"></script>

	<script language="javascript">
	 dojo.require("dojo.io.*");
	 dojo.require("dojo.event.*");
     dojo.require("dojo.html.*");

	 function onLoad() {
		 var listObj1 = document.getElementById("list1");
		 dojo.event.connect(listObj1, "onchange", this, "onchange_list1");

		 var listObj2 = document.getElementById("list2");
		 dojo.event.connect(listObj2, "onchange", this, "onchange_list2");
	 }

	 function onchange_list1() {
	    var id1 = document.getElementById("list1").value;
		 var list2 = document.getElementById("list2");
		 //list2.size = 0;

		 if (list2.options) {
		   for (var i = 0; i < list2.options.length; i++) {
		     list2.removeChild(list2.options[i]);
		   }
		 }

		 list2.options.length = 0;
		 list2.selectedIndex = -1;

		 var params = new Array();
		 params['command'] = 'list1Changed';
		 params['id1'] = id1;

	  var bindArgs = {
	   url: "actions/getData.jsp",
	   error: function(type, data, evt){
	    alert(data);
	   },
	   load: function(type, data, evt){
		   for(var i=0; i <data.length; i++) {
			// alert(data[i].label);

			   var oOption = null;

			   /*if (this.textbox.createTextRange) { // Internet Explorer
			     oOption = new Option(aSuggestions[i].key);

			     oOption.appendChild(document.createTextNode(aSuggestions[i].value));
			   }
			   else {   */
			     oOption = document.createElement("option");
			   //}

			   oOption.value = data[i].value;
			   oOption.text = data[i].label;

			   list2.appendChild(oOption);
		   }
	   },
	   mimetype: "text/json",
	   formNode: document.getElementById("myForm"),
	   content: params
	  };

      dojo.io.bind(bindArgs);
	 }

	 function onchange_list2() {
	    var id1 = document.getElementById("list1").value;
	  var id2 = document.getElementById("list2").value;

		 var params = new Array();
		 params['command'] = 'list2Changed';
		 params['id1'] = id1;
		 params['id2'] = id2;


	  var bindArgs = {
	   url: "actions/getData.jsp",
	   error: function(type, data, evt){
	    alert(data);
	   },
	   load: function(type, data, evt){
	    alert(data);
	   },
	   mimetype: "text/plain",
	   formNode: document.getElementById("myForm"),
	   content: params
	  };
	  dojo.io.bind(bindArgs);
	 }
	</script>
</head>

<body onLoad="onLoad();">
  <h1>Dependent lists</h1>

  <form id="myForm">
  <table>
	  <tr>
		  <td>
			  List1:
		  </td>
          <td>
	          <select id="list1">
		          <option value="0">Select...</option>
		          <option value="1">Honda</option>
		          <option value="2">Toyota</option>
	              <option value="3">Mazda</option>
	          </select>
		  </td>
	  </tr>

	  <tr>
		  <td>
			  List2:
		  </td>
          <td>
	          <select id="list2"/>
		  </td>
	  </tr>

  </table>
</form>
</body>
</html>

