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
                     <h2>HR System - User Dashboard</h2>   
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
                   <div class="row">
                    <div class="col-lg-12 ">
                       <h5>Search Zone</h5>
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#employee" data-toggle="tab">Employee</a>
                            </li>
                            <li class=""><a href="#projects" data-toggle="tab">Projects</a>
                            </li>
                            <li class=""><a href="#clients" data-toggle="tab">Clients</a>
                            </li>
                           

                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade active in" id="employee">
                                <h4>Search Employee</h4>
                               <p>
                                <form method="post" action="${pageContext.request.contextPath }/SearchEmployeeByName" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Employee Name" >
								<input type="submit" value="Search by Name" class="btn btn-primary">
								</form>
								<hr>
								<form method="post" action="${pageContext.request.contextPath }/SearchEmployeeByEmail" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Employee Email" >
								<input type="submit" value="Search by Email" class="btn btn-info">
								</form>
								<hr>
								<form method="post" action="${pageContext.request.contextPath }/SearchEmployeeByProject" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Employee Project Name" >
								<input type="submit" value="Search by Project Name" class="btn btn-warning">
								</form>
                                </p>
                            </div>
                            <div class="tab-pane fade" id="projects">
                                <h4>Search Projects</h4>
                                <p>
                                   
                                    <form method="post" action="${pageContext.request.contextPath }/SearchProjectByName" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Project Name" >
								<input type="submit" value="Search by Name" class="btn btn-primary">
								</form>
                                   <BR>
                                   <BR>
                                   	<form method="post" action="${pageContext.request.contextPath }/SearchProjectById" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Project Id" >
								<input type="submit" value="Search by Id" class="btn btn-info">
								</form>
                                     </p>

                            </div>
                            <div class="tab-pane fade" id="clients">
                                <h4>Search Clients</h4>
                                <p>
                                   
                                   <form method="post" action="${pageContext.request.contextPath }/SearchClientByName" >
                                <input type="text" name="search" class="form-control" placeholder="Enter Project Name" >
								<input type="submit" value="Search by Name" class="btn btn-primary">
								</form>
                                     </p>

                            </div>
                            
	
                       
                    </div>
                    </div>
              </div>
              
              
              <div class="col-lg-4 col-md-4">
                        
              </div>
              </div>
              </div>
             
              
    </div>
    </div>
  
   
   
</body>
</html>
