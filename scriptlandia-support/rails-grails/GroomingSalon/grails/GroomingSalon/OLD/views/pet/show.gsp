

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Pet</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Pet List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Pet</g:link></span>
        </div>
        <div class="body">
            <h1>Show Pet</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${pet.id}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${pet.name}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sex:</td>
                            
                            <td valign="top" class="value">${pet.sex}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Breed:</td>
                            
                            <td valign="top" class="value">${pet.breed}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Color:</td>
                            
                            <td valign="top" class="value">${pet.color}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Owner:</td>
                            
                            <td valign="top" class="value"><g:link controller="petOwner" action="show" id="${pet?.owner?.id}">${pet?.owner}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Birth Date:</td>
                            
                            <td valign="top" class="value">${pet.birthDate}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Size:</td>
                            
                            <td valign="top" class="value">${pet.size}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Behavior:</td>
                            
                            <td valign="top" class="value">${pet.behavior}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Veterinar:</td>
                            
                            <td valign="top" class="value">${pet.veterinar}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Referred By:</td>
                            
                            <td valign="top" class="value">${pet.referredBy}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Medical Problems:</td>
                            
                            <td valign="top" class="value">${pet.medicalProblems}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Clip1:</td>
                            
                            <td valign="top" class="value">${pet.clip1}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Clip2:</td>
                            
                            <td valign="top" class="value">${pet.clip2}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Clip3:</td>
                            
                            <td valign="top" class="value">${pet.clip3}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Special Instructions:</td>
                            
                            <td valign="top" class="value">${pet.specialInstructions}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="pet">
                    <input type="hidden" name="id" value="${pet?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
