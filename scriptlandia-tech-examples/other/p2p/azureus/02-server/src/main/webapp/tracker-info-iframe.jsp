<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Torrent Tracker Information</title>
		<meta http-equiv="refresh" content="5"/>
		<style type="text/css" media="screen">
			@import "stylesheet.css";
		</style>
	</head>
	
	<body style="margin:0px;padding:0px; padding-top:8px;"> 
			
			<table cellspacing="0" cellpadding="0" border="0">
				<tr class="odd">
					<td class="head" style="width:25%">Hostname : </td> 
					<td style="width:25%"><c:out value="${torrentSeederTrackerServer.hostname}"/></td>
					
					<td style="width:25%" class="head">Tracker Port Number : </td>
					<td style="width:25%"><c:out value="${torrentSeederTrackerServer.trackerPortNumber}"/></td>
				</tr>	
				<tr class="even">
					<td class="head">Total Completed : </td>
					<td><c:out value="${torrentSeederTrackerServer.completedCount}"/></td>
					
					<td class="head">Total Downloaded :  </td>

					<td><fmt:formatNumber value="${torrentSeederTrackerServer.totalDownloaded / 1000000}" 
					 										  pattern="###,###,###,###.#"/> MB</td>
				</tr>
				
				<tr class="odd">
					<td class="head">Leecher Count: </td>
					<td><c:out value="${torrentSeederTrackerServer.leecherCount}"/></td>
					
					<td class="head">Peer Count :  </td>
					<td><c:out value="${torrentSeederTrackerServer.peerCount}"/></td>
				</tr>
			</table> 

	</body>
	
</html>