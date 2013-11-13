package travelgood.bpel.utils;

import dk.dtu.travelgood.bpel.utils.GetProcessDeadlineRequest;
import dk.dtu.travelgood.bpel.utils.GetProcessDeadlineResponse;
import dk.dtu.travelgood.commons.FlightType;
import dk.dtu.travelgood.commons.HotelType;
import dk.dtu.travelgood.commons.ItineraryType;
import java.util.Date;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.collections4.CollectionUtils;
import travelgood.utils.DateUtils;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@WebService(serviceName = "travelGoodBpelUtilsService", portName = "travelGoodBpelUtilsPort", endpointInterface = "dk.dtu.travelgood.bpel.utils.TravelGoodBpelUtilsPortType", targetNamespace = "urn:utils.bpel.travelgood.dtu.dk", wsdlLocation = "WEB-INF/wsdl/TravelGoodBpelUtilsWS/travelGoodBpelUtils.wsdl")
public class TravelGoodBpelUtilsWS {

    public GetProcessDeadlineResponse getProcessDeadline(GetProcessDeadlineRequest req) {
        ItineraryType i = req.getItinerary();

        XMLGregorianCalendar earliestDate = DateUtils.getXmlGregorianCalendar(2099, 1, 1);

        if (i.getHotels() != null && CollectionUtils.isNotEmpty(i.getHotels().getHotel())) {
            for (HotelType h : i.getHotels().getHotel()) {
                if (h.getArrivalDate().compare(earliestDate) == DatatypeConstants.LESSER) {
                    earliestDate = h.getArrivalDate();
                }
            }
        }


        if (i.getFlights() != null && CollectionUtils.isNotEmpty(i.getFlights().getFlight())) {
            for (FlightType f : i.getFlights().getFlight()) {
                if (f.getLiftOffDate().compare(earliestDate) == DatatypeConstants.LESSER) {
                    earliestDate = f.getLiftOffDate();
                }
            }
        }

        Date deadline = org.apache.commons.lang3.time.DateUtils.addDays(DateUtils.toDate(earliestDate), -1);

        GetProcessDeadlineResponse res = new GetProcessDeadlineResponse();
        res.setDeadline(DateUtils.toXmlGregorianCalendar(deadline));
        return res;
    }
}
