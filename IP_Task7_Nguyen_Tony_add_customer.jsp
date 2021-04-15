<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
<body>
<%@page import="project_jsp.DataHandler"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Array"%>
<%
// The handler is the one in charge of establishing the connection.
DataHandler handler = new DataHandler();
// Get the attribute values passed from the input form.
String name = request.getParameter("name");
String address = request.getParameter("address");
String category = request.getParameter("category");
/*
* If the user hasn't filled out all the name, address, and category. This is very simple checking.
*/
if (name.equals("") || address.equals("") || category.equals("")) {
response.sendRedirect("add_customer_form.jsp");
} else {
int customerCategory = Integer.parseInt(category);
// Now perform the query with the data from the form.
boolean success = handler.addCustomer(name, address, customerCategory);
if (!success) { // Something went wrong
%>
<h2>There was a problem inserting the course</h2>
<%
} else { // Confirm success to the user
%>
<h2>The Customer Data:</h2>
<ul>
<li>Name: <%=name%></li>
<li>Address: <%=address%></li>
<li>Category: <%=category%></li>
</ul>
<h2>Was successfully inserted.</h2>
<a href="get_all_customers.jsp">See all customers.</a>
<%
}
}
%>
</body>
</html>