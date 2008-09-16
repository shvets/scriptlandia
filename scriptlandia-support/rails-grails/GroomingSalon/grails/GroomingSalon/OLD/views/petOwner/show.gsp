

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show PetOwner</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PetOwner List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New PetOwner</g:link></span>
        </div>
        <div class="body">
            <h1>Show PetOwner</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${petOwner.id}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">First Name:</td>
                            
                            <td valign="top" class="value">${petOwner.firstName}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Name:</td>
                            
                            <td valign="top" class="value">${petOwner.lastName}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Salutation:</td>
                            
                            <td valign="top" class="value">${petOwner.salutation}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Home Phone:</td>
                            
                            <td valign="top" class="value">${petOwner.homePhone}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Work Phone:</td>
                            
                            <td valign="top" class="value">${petOwner.workPhone}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Cell Phone:</td>
                            
                            <td valign="top" class="value">${petOwner.cellPhone}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Pet:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="p" in="${petOwner.pet}">
                                    <li><g:link controller="pet" action="show" id="${p.id}">${p}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="petOwner">
                    <input type="hidden" name="id" value="${petOwner?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
