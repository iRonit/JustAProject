<%-- 
    Document   : b
    Created on : May 3, 2016, 3:47:45 PM
    Author     : ronit01.TRN
--%>

<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Confirmation</title>
    </head>
    <body>
        <jsp:useBean id="ProviderDiscover" scope="page" class="org.infosys.jtapi.ProviderDiscover" /> 
        <%
            String uid = request.getParameter("uid");
            String ip = request.getParameter("ip_addr");
            String pass = request.getParameter("pass");
//-------now pass parameter "name" to your sample java file
        if(true)
        {
            String site = new String("home.html");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        }
        else
        {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("location='index.html';");
            out.println("</script>");
        }
        %>
    </body>
</html>
