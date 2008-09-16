

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>PetOwner List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New PetOwner</g:link></span>
        </div>
        <div class="body">
            <h1>PetOwner List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="firstName" title="First Name" />
                        
                   	        <g:sortableColumn property="lastName" title="Last Name" />
                        
                   	        <g:sortableColumn property="salutation" title="Salutation" />
                        
                   	        <g:sortableColumn property="homePhone" title="Home Phone" />
                        
                   	        <g:sortableColumn property="workPhone" title="Work Phone" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${petOwnerList}" status="i" var="petOwner">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${petOwner.id}">${petOwner.id?.encodeAsHTML()}</g:link></td>
                        
                            <td>${petOwner.firstName?.encodeAsHTML()}</td>
                        
                            <td>${petOwner.lastName?.encodeAsHTML()}</td>
                        
                            <td>${petOwner.salutation?.encodeAsHTML()}</td>
                        
                            <td>${petOwner.homePhone?.encodeAsHTML()}</td>
                        
                            <td>${petOwner.workPhone?.encodeAsHTML()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${PetOwner.count()}" />
            </div>
        </div>
    </body>
</html>
