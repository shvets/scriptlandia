

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Appointment</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Appointment List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Appointment</g:link></span>
        </div>
        <div class="body">
            <h1>Show Appointment</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${appointment.id}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date:</td>
                            
                            <td valign="top" class="value">${appointment.date}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Pet:</td>
                            
                            <td valign="top" class="value"><g:link controller="pet" action="show" id="${appointment?.pet?.id}">${appointment?.pet}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Pet Owner:</td>
                            
                            <td valign="top" class="value"><g:link controller="petOwner" action="show" id="${appointment?.petOwner?.id}">${appointment?.petOwner}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="appointment">
                    <input type="hidden" name="id" value="${appointment?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
