<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Torrent Information Page</title>
		
 
		<style type="text/css" media="screen">
			@import "stylesheet.css";
		</style>
		
	</head>
	
	<body>
		
		 <h1>Torrent Tracker and Seeder Host</h1>
		
		<p>
			On this page you can view the various statistics associated with the Torrent Tracker and
			Torrent Seeder Server.
		</p>
		
		
		<c:choose>
			<c:when test="${torrentSeederTrackerServer.running == false}">
				
				<p>
					You have not yet seeded a file for distribution over BitTorrent yet. 
					Please do so using the form below :
				</p>
				
			</c:when>
			<c:otherwise>
				<a href="download.torrent"><b>Download Torrent File</b></a>

				<br/><br/>
				
				<fieldset>
					<legend>Torrent Tracker Information</legend>
					
					<iframe scrolling="auto" border="0" frameborder="0" style="margin:auto;border:0px;" 
							height="60" width="700" src="tracker-info-iframe.jsp"></iframe>
					
				</fieldset>
		
				<br/><br/>
				
				<fieldset>
					<legend>Torrent Seeder Information</legend>
					
					<iframe scrolling="auto" border="0" frameborder="0" style="margin:auto;border:0px;" 
							height="112" width="700" src="seeder-info-iframe.jsp"></iframe>
				
				</fieldset>
				
				<br/><br/>
				
			</c:otherwise>
		</c:choose>
		
		<form action="uploadNewTorrentFile" method="POST" enctype="multipart/form-data">
				
					<fieldset style="text-align:center;">
						<legend>Start New Torrent</legend>
						
						<table id="uploadFields" cellspacing="0" cellpadding="0" style="width:70%; margin:auto; margin-top:8px; " border="0">
							<tr>
								<td class="head">External IP / Name of Server</td>
								<td><input size="25" type="text" name="externalIPAddress" value="${torrentSeederTrackerServer.hostname}"/></td>
							</tr>
							<tr>
								<td class="head">Upload File</td>
								<td><input size="25" type="file" name="torrentFile"/></td>
							</tr>
						</table>
						
						<table id="progressIcon" cellspacing="0" cellpadding="0" style="display:none; width:70%; margin:auto; margin-top:8px; " border="0">
							<tr>
								<td style="text-align:center;">
									Please Wait....
								</td>
							</tr>
							<tr>
								<td style="text-align:center;">
									<img src="loading.gif"/>
								</td>
							</tr>
						</table>
						
						<script>
							
							function submitForm(){
								document.getElementById('uploadFields').style.display = 'none';
								document.getElementById('submitButton').style.display = 'none';
								document.getElementById('progressIcon').style.display = '';
							}
						
						</script>
						
						<button id="submitButton"
								type="submit" 
								onclick="submitForm();">
							Upload File
						</button>
						
					</fieldset>
				
		</form>
	 

	</body>
	
</html>