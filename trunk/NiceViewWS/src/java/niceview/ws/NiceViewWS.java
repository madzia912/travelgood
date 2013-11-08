package niceview.ws;

import dk.dtu.niceview.BookHotelFault_Exception;
import dk.dtu.niceview.BookHotelRequest;
import dk.dtu.niceview.BookHotelResponse;
import dk.dtu.niceview.CancelHotelFault_Exception;
import dk.dtu.niceview.CancelHotelRequest;
import dk.dtu.niceview.CancelHotelResponse;
import dk.dtu.niceview.GetHotelsRequest;
import dk.dtu.niceview.GetHotelsResponse;
import dk.dtu.travelgood.commons.HotelsType;
import javax.jws.WebService;
import niceview.model.HotelsHelper;

/**
 * @author Magdalena Furman
 */
@WebService(serviceName = "niceViewService", portName = "niceViewPortTypeBindingPort", endpointInterface = "dk.dtu.niceview.NiceViewPortType", targetNamespace = "urn:niceview.dtu.dk", wsdlLocation = "WEB-INF/wsdl/NiceViewWS/niceView.wsdl")
public class NiceViewWS {

    HotelsHelper hh = new HotelsHelper();
    
    public BookHotelResponse bookHotel(BookHotelRequest request) throws BookHotelFault_Exception {
        return hh.bookHotel(request.getBookingNumber(), request.getCreditCard());
    }

    public CancelHotelResponse cancelHotel(CancelHotelRequest request) throws CancelHotelFault_Exception {
        return hh.cancelHotel(request.getBookingNumber());
    }
    public GetHotelsResponse getHotels(GetHotelsRequest request) {
        HotelsType hotelsType = hh.getHotels(request.getBookingNumber(), request.getCity(), request.getArrivalDate(), request.getDepartureDate()).getHotels();
        GetHotelsResponse response = new GetHotelsResponse();
        response.setHotels(hotelsType);
        return response;
    }
  
}
