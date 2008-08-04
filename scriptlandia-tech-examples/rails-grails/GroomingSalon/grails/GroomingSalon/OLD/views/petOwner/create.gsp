

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create PetOwner</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PetOwner List</g:link></span>
        </div>
        <div class="body">
            <h1>Create PetOwner</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${petOwner}">
            <div class="errors">
                <g:renderErrors bean="${petOwner}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstName">First Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'firstName','errors')}">
                                    <input type="text" maxlength="25" id="firstName" name="firstName" value="${fieldValue(bean:petOwner,field:'firstName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastName">Last Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'lastName','errors')}">
                                    <input type="text" maxlength="25" id="lastName" name="lastName" value="${fieldValue(bean:petOwner,field:'lastName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="salutation">Salutation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'salutation','errors')}">
                                    <input type="text" id="salutation" name="salutation" value="${fieldValue(bean:petOwner,field:'salutation')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="homePhone">Home Phone:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'homePhone','errors')}">
                                    <input type="text" maxlength="15" id="homePhone" name="homePhone" value="${fieldValue(bean:petOwner,field:'homePhone')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="workPhone">Work Phone:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'workPhone','errors')}">
                                    <input type="text" maxlength="15" id="workPhone" name="workPhone" value="${fieldValue(bean:petOwner,field:'workPhone')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cellPhone">Cell Phone:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:petOwner,field:'cellPhone','errors')}">
                                    <input type="text" maxlength="15" id="cellPhone" name="cellPhone" value="${fieldValue(bean:petOwner,field:'cellPhone')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
