<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
Enumeration enu=request.getParameterNames();  
System.out.println("--------------------------------llll");
while(enu.hasMoreElements()){  
	String paraName=(String)enu.nextElement();  
	System.out.println(paraName+": "+request.getParameter(paraName));  
}  
System.out.println("--------------------------------2222");
	
String body = request.getQueryString();
System.out.println("body=="+body);
	
%>


</head>
<body>
			hello world1
</body>
</html>