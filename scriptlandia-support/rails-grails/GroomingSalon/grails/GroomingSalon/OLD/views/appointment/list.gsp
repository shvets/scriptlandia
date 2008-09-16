

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Appointment List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Appointment</g:link></span>
        </div>
        <div class="body">
            <h1>Appointment List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="date" title="Date" />
                        
                   	        <th>Pet</th>
                   	    
                   	        <th>Pet Owner</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${appointmentList}" status="i" var="appointment">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${appointment.id}">${appointment.id?.encodeAsHTML()}</g:link></td>
                        
                            <td>${appointment.date?.encodeAsHTML()}</td>
                        
                            <td>${appointment.pet?.encodeAsHTML()}</td>
                        
                            <td>${appointment.petOwner?.encodeAsHTML()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Appointment.count()}" />
            </div>
        </div>
    </body>
</html>
