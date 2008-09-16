

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Appointment</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Appointment List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Appointment</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Appointment</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${appointment}">
            <div class="errors">
                <g:renderErrors bean="${appointment}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="appointment" method="post" >
                <input type="hidden" name="id" value="${appointment?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appointment,field:'date','errors')}">
                                    <g:datePicker name="date" value="${appointment?.date}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pet">Pet:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appointment,field:'pet','errors')}">
                                    <g:select optionKey="id" from="${Pet.list()}" name="pet.id" value="${appointment?.pet?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="petOwner">Pet Owner:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:appointment,field:'petOwner','errors')}">
                                    <g:select optionKey="id" from="${PetOwner.list()}" name="petOwner.id" value="${appointment?.petOwner?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
