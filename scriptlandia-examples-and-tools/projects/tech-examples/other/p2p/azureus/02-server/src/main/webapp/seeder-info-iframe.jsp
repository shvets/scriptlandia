<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Torrent Seeder Information</title>
		<meta http-equiv="refresh" content="5"/>
	
		<style type="text/css" media="screen">
			@import "stylesheet.css";
		</style>
		
	</head>
	
	<body style="margin:0px;padding:0px; padding-top:8px;">  
	
			<table cellspacing="0" cellpadding="0" border="0">
				<tr class="odd">
					<td class="head">Hostname : </td>
					<td><c:out value="${torrentSeederTrackerServer.hostname}"/></td>
			 
					<td class="head">Highest Data Send Rate : </td>
					<td>
						<!--  Current : <fmt:formatNumber value="${torrentSeederTrackerServer.maxDataSendRate / 1000}" pattern="###,###,###,###"/> kB/s -->
							<fmt:formatNumber value="${torrentSeederTrackerServer.maxDataSendRate / 1000}" pattern="###,###,###,###"/> kB/s 
						
					</td>
				</tr> 
				
				<tr class="even">
					<td style="width:25%" class="head">Seed File Size : </td>
					<td style="width:25%"><fmt:formatNumber value="${torrentSeederTrackerServer.seedFileSize}" pattern="###,###,###,###"/> bytes</td>
			 	
			 		<c:choose>
			 			<c:when test="${torrentSeederTrackerServer.dataSendRate != 0}">
			 	
					 		<td colspan="2" class="ticker">
					 			
					 			<c:set var="maxBound" value="${torrentSeederTrackerServer.maxDataSendRate}"/>
					 			<c:set var="totalSize" value="${100}"/>
					 			<c:set var="currentTransfer" value="${torrentSeederTrackerServer.dataSendRate}"/>
					 			<c:set var="length" value="${(currentTransfer div maxBound) * totalSize}"/>
					 			
					 			<table cellspacing="0" cellpadding="1" border="0" style="width:100%;">
					 				<tr>
					 					<td style="width:<fmt:formatNumber value="${length}" pattern="####"/>%" 
					 						class="indicator-cell-active">
					 						<br/>
										</td>
										
										<td style="width:<fmt:formatNumber value="${(totalSize - length)}" pattern="####"/>%" 
											class="indicator-cell-inactive">
					 						<br/>
										</td>
									</tr>
									<tr>		
					 					<td class="head"
					 						colspan="2" 
					 						style="text-align:center;white-space:nowrap;">
					 						Currently Sending @ <fmt:formatNumber value="${torrentSeederTrackerServer.dataSendRate / 1000}" 
					 										  pattern="###,###,###,###.#"/> Kb/s
					 					</td>
					 				</tr>
					 			</table>
					 		
					 		</td>
			 		</c:when>
			 		<c:otherwise>
			 			<td colspan="2">
			 				
			 			</td>
			 		</c:otherwise>
			 		
			 		</c:choose>
					
				</tr>
				
				<tr class="odd">
					
					<td class="head">File Pieces :</td>
					<td><fmt:formatNumber value="${torrentSeederTrackerServer.numberOfPiecesForFile}"/></td>
				
					<td class="head">Connected Seeds :</td>
					<td><fmt:formatNumber value="${torrentSeederTrackerServer.numberOfConnectedSeeds}"/></td>
					
				</tr>
				
				<tr class="even">
					
					<td class="head">Connected Peers : </td>
					<td><fmt:formatNumber value="${torrentSeederTrackerServer.numberOfConnectedPeers}"/></td>
					
					<td style="width:25%" class="head">Creation Time :</td>
					<td style="width:25%"><fmt:formatDate type="both" value="${torrentSeederTrackerServer.creationTime}"/></td>
					
				</tr>
				
				<tr class="odd">
					
					<td class="head">Seeder Last Status :  </td>
					<td colspan="3">
						<c:out value="${torrentSeederTrackerServer.lastStatus}"/>
					</td>
					
				</tr>
				
			</table>
			
	</body>
 
	
</html>