<%@ page import="java.util.List" %>
<%@ page import="com.zumbaapp.Participant" %>

<html>
<body>
    <h2>Participants List</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Batch ID</th>
        </tr>
        <%
            List<Participant> participants = (List<Participant>) request.getAttribute("participants");
            for (Participant participant : participants) {
        %>
        <tr>
            <td><%= participant.id() %></td>
            <td><%= participant.name() %></td>
            <td><%= participant.email() %></td>
            <td><%= participant.phoneNumber() %></td>
            <td><%= participant.batchId() %></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
