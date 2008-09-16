

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Pet List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Pet</g:link></span>
        </div>
        <div class="body">
            <h1>Pet List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="sex" title="Sex" />
                        
                   	        <g:sortableColumn property="breed" title="Breed" />
                        
                   	        <g:sortableColumn property="color" title="Color" />
                        
                   	        <th>Owner</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${petList}" status="i" var="pet">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pet.id}">${pet.id?.encodeAsHTML()}</g:link></td>
                        
                            <td>${pet.name?.encodeAsHTML()}</td>
                        
                            <td>${pet.sex?.encodeAsHTML()}</td>
                        
                            <td>${pet.breed?.encodeAsHTML()}</td>
                        
                            <td>${pet.color?.encodeAsHTML()}</td>
                        
                            <td>${pet.owner?.encodeAsHTML()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Pet.count()}" />
            </div>
        </div>
    </body>
</html>
