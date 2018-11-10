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
                  <c:forEach var="e" items="${ehmlist }">
Message  : <c:out value="${e.getMessage() }"></c:out><BR>
Message from :<c:out value="${e.getUsername() }"></c:out><BR>
Date:<c:out value="${e.getDate() }"/><BR>
<a href="${pageContext.request.contextPath }/deletemessage?message=<c:out value="${e.getMessage() }"/>&toemp_id=<c:out value="${e.getToemp_id() }"/>">Mark as read and delete the message </a>
</c:forEach>
                  
                    </div>
              </div>
              
              
             
              </div>
              
  
   
   
</body>
</html>
