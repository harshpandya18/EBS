<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<body>
     
           
          
    <div id="wrapper">
         <c:import url="header.jsp"/>
        
       <c:import url="sidebar.jsp"/>
        
       
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-lg-12">
                     <h2>EBS  - User Dashboard</h2>   
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr />
                <div class="row">
                    <div class="col-lg-12 ">
                        <div class="alert alert-info">
                           <strong>Welcome <c:out value="${pageContext.request.userPrincipal.name}"></c:out> ! </strong> You Have <c:out value="${count}"/> pending New Messages.
                        </div>
                       
                    </div>
                    </div>
                  <!-- /. ROW  --> 
                 <c:import url="main.jsp"/>
                  
                  Assign Manager To Project
                  <form method="post" action="${pageContext.request.contextPath }/AssignEmployeeToProject">
                                <p>
                               <select name="employee" class="form-control">
                               <c:forEach var="e" items="${emp_list }">
                               <option value='<c:out value="${e.getId() }"/>'><c:out value="${e.getName() }"/> -- <c:out value="${e.getJob_title() }"/></option>
                               </c:forEach>
                               
                               </select><BR>
                               <select name="project" class="form-control">
                               <c:forEach var="p" items="${proj_list }">
                               <option value='<c:out value="${p.getId() }"/>'><c:out value="${p.getName() }"/> -- <c:out value="${p.getClient_name() }"/></option>
                               </c:forEach>
                               </select>
                  </form>
                  
                  
                  
                    </div>
              </div>
              
              </div>
              
  
   
   
</body>
</html>
