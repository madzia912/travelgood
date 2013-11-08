<%-- 
    Document   : index
    Created on : Oct 28, 2013, 7:14:25 PM
    Author     : Bartosz Grzegorz Cichecki
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="dk.dtu.travelgood.commons.FlightType"%>
<%@page import="lameduck.model.FlightsHolder"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="serviceInspection" content="inspection.wsil">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lame Duck Airlines</title>
    </head>
    <body>
        <h1>Lame Duck Airlines</h1>

        <h2>Flights:</h2>
        <ul>
            <%
                List<FlightType> flights = FlightsHolder.getInstance().getFlights();
                for (FlightType ft : flights) {
            %>
            <li>
                Flight ID: <% out.print(ft.getId());%><br/>
                Booking Number: <% out.print(ft.getBookingNumber());%>
                <ul>
                    <li>From: <% out.print(ft.getFrom());%></li>
                    <li>To <% out.print(ft.getTo());%></li>
                    <li>Price: <% out.print(ft.getPrice());%></li>
                    <li>Carrier <% out.print(ft.getCarrier());%></li>
                    <li>Lift off: <% out.print(ft.getLiftOffDate().toString());%></li>
                    <li>Landing <% out.print(ft.getLandingDate().toString());%></li>
                </ul>
            </li>
            <%
                }
            %>
        </ul>

        <h2>Reservations:</h2>
        <ul>
            <%
                Map<String, FlightType> reservations = FlightsHolder.getInstance().getReservations();
                for (Map.Entry<String, FlightType> reservation : reservations.entrySet()) {
            %>
            <li>
                Reservation ID: <% out.print(reservation.getKey());%><br/>
                Flight ID: <% out.print(reservation.getValue().getBookingNumber());%>
            </li>
            <%
                }
            %>
        </ul>

    </body>
</html>
