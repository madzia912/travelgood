<%-- 
    Document   : index
    Created on : Nov 18, 2013, 11:27:25 PM
    Author     : Magdalena Furman
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="dk.dtu.travelgood.commons.HotelType"%>
<%@page import="dk.dtu.travelgood.commons.AddressType"%>
<%@page import="niceview.model.HotelsHolder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="serviceInspection" content="inspection.wsil">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nice View Hotel Reservation</title>
    </head>
    <body>
        <h1>Nice View Hotel Reservation</h1>

        <h2>Hotels:</h2>
        <ul>
            <%
                List<HotelType> hotels = HotelsHolder.getInstance().getHotels();
                for (HotelType ht : hotels) {
            %>
            <li>
                Hotel ID: <% out.print(ht.getId());%><br/>
                Booking Number: <% out.print(ht.getBookingNumber());%>
                <ul>
                    <li>Name: <% out.print(ht.getName());%></li>
					<li>Provider: <% out.print(ht.getProvider());%></li>
                    <li>Street: <% out.print(ht.getAddress().getStreet());%></li>
					<li>Zip code: <% out.print(ht.getAddress().getZipCode());%></li>
					<li>City: <% out.print(ht.getAddress().getCity());%></li>
                    <li>Price: <% out.print(ht.getPrice());%></li>
                    <li>Arrival date: <% out.print(ht.getArrivalDate().toString());%></li>
                    <li>Departure date: <% out.print(ht.getDepartureDate().toString());%></li>
					<li>Credit card guarantee: <% out.print(ht.isCreditCardGuarantee());%></li>
                </ul>
            </li>
            <%
                }
            %>
        </ul>
		
		<h2>Reservations:</h2>
        <ul>
            <%
                Map<String, HotelType> reservations = HotelsHolder.getInstance().getReservations();
                for (Map.Entry<String, HotelType> reservation : reservations.entrySet()) {
            %>
            <li>
                Reservation ID: <% out.print(reservation.getKey());%><br/>
                Hotel ID: <% out.print(reservation.getValue().getBookingNumber());%>
            </li>
            <%
                }
            %>
        </ul>
    </body>
</html>
