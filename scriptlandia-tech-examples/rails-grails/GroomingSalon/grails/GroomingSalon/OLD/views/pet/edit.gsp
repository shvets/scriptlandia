

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Pet</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Pet List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Pet</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Pet</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pet}">
            <div class="errors">
                <g:renderErrors bean="${pet}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="pet" method="post" >
                <input type="hidden" name="id" value="${pet?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:pet,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sex">Sex:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'sex','errors')}">
                                    <g:select id="sex" name="sex" from="${pet.constraints.sex.inList.collect{it.encodeAsHTML()}}" value="${fieldValue(bean:pet,field:'sex')}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="breed">Breed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'breed','errors')}">
                                    <input type="text" id="breed" name="breed" value="${fieldValue(bean:pet,field:'breed')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="color">Color:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'color','errors')}">
                                    <input type="text" id="color" name="color" value="${fieldValue(bean:pet,field:'color')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="owner">Owner:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'owner','errors')}">
                                    <g:select optionKey="id" from="${PetOwner.list()}" name="owner.id" value="${pet?.owner?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="birthDate">Birth Date:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'birthDate','errors')}">
                                    <g:datePicker name="birthDate" value="${pet?.birthDate}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="size">Size:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'size','errors')}">
                                    <input type="text" id="size" name="size" value="${fieldValue(bean:pet,field:'size')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="behavior">Behavior:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'behavior','errors')}">
                                    <input type="text" id="behavior" name="behavior" value="${fieldValue(bean:pet,field:'behavior')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="veterinar">Veterinar:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'veterinar','errors')}">
                                    <input type="text" id="veterinar" name="veterinar" value="${fieldValue(bean:pet,field:'veterinar')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="referredBy">Referred By:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'referredBy','errors')}">
                                    <input type="text" id="referredBy" name="referredBy" value="${fieldValue(bean:pet,field:'referredBy')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="medicalProblems">Medical Problems:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'medicalProblems','errors')}">
                                    <input type="text" id="medicalProblems" name="medicalProblems" value="${fieldValue(bean:pet,field:'medicalProblems')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clip1">Clip1:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'clip1','errors')}">
                                    <input type="text" id="clip1" name="clip1" value="${fieldValue(bean:pet,field:'clip1')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clip2">Clip2:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'clip2','errors')}">
                                    <input type="text" id="clip2" name="clip2" value="${fieldValue(bean:pet,field:'clip2')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clip3">Clip3:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'clip3','errors')}">
                                    <input type="text" id="clip3" name="clip3" value="${fieldValue(bean:pet,field:'clip3')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="specialInstructions">Special Instructions:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pet,field:'specialInstructions','errors')}">
                                    <input type="text" id="specialInstructions" name="specialInstructions" value="${fieldValue(bean:pet,field:'specialInstructions')}"/>
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
