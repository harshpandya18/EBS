<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <div id="wrapper">
         <c:import url="header.jsp"/>
        
       <c:import url="sidebar.jsp"/>
        
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-lg-12">
                     <h2>EBS System - Admin Dashboard</h2>   
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
                       <h5>Project Record</h5>
                     <div class="alert alert-danger">
                             
                        </div>
                  
						
							Name: <c:out value="${p.getName() }"/><BR>
							Start Date: <c:out value="${p.getStart_date() }"/><BR>
							Estimated End Date: <c:out value="${p.getEstimated_end_date() }"/> <BR>
							
							Client Name: <c:out value="${p.getClient_name() }"/>
						
							<BR>
							Budget: <c:out value="${p.getBudget() }"/>
							<BR>
							Status: <c:out value="${p.getStatus() }"/>
							<!-- Manager of the project:<c:out value="${e.getJob_title() }"/> -->
								
					
                    </div>
                  </div>     
        </div>
      </div>
     </div>  
</body>
</html>