<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
                  <form action="${pageContext.request.contextPath }/PasswordChange" method="post">
<c:out value="${msg }"></c:out>
Username:<input type="text" name="username" value="<c:out value="${pageContext.request.userPrincipal.name }"/>" disabled="disabled"><BR><BR>
Old Password :<input type="password" name="old"><BR><BR>
New Password :<input type="password" name="new"><BR><BR>
Confirm New  Password :<input type="password" name="confirm"><BR><BR>
<input type="submit" value="Change Password">

</form>
                  
                    </div>
              </div>
              
              
             
              </div>
              
  
   
   
</body>
</html>
