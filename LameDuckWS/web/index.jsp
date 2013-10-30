<%-- 
    Document   : index
    Created on : Oct 28, 2013, 7:14:25 PM
    Author     : Bartosz
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="dk.dtu.travelgood.commons.FlightType"%>
<%@page import="lameduck.model.FlightsHolder"%>
<%@page import="lameduck.model.ReservationData"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
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
                Flight ID: <% out.print(ft.getId());%>
                <ul>
                    <li>From: <% out.print(ft.getFrom());%></li>
                    <li>To <% out.print(ft.getTo());%></li>
                    <li>Places left: <% out.print(ft.getPlacesLeft());%></li>
                    <li>Price: <% out.print(ft.getPrice());%></li>
                    <li>Departure: <% out.print(new Date(ft.getTimestamp()).toString());%> (Timestamp: <% out.print(ft.getTimestamp());%>)</li>
                </ul>
            </li>
            <%
                }
            %>
        </ul>

        <h2>Reservations:</h2>
        <ul>
            <%
                Map<String, ReservationData> reservations = FlightsHolder.getInstance().getReservations();
                for (Map.Entry<String, ReservationData> reservation : reservations.entrySet()) {
            %>
            <li>
                Reservation ID: <% out.print(reservation.getKey());%>
                <ul>
                    <li>Flight ID: <% out.print(reservation.getValue().getFlight().getId());%></li>
                    <li>Places reserved: <% out.print(reservation.getValue().getPlacesReserved());%></li>
                </ul>
            </li>
            <%
                }
            %>
        </ul>



    </body>
</html>
