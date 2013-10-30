package niceview.ws;

import dk.dtu.niceview.BookHotelFault_Exception;
import dk.dtu.niceview.BookHotelRequest;
import dk.dtu.niceview.BookHotelResponse;
import dk.dtu.niceview.CancelHotelFault_Exception;
import dk.dtu.niceview.CancelHotelRequest;
import dk.dtu.niceview.CancelHotelResponse;
import dk.dtu.niceview.GetHotelsRequest;
import dk.dtu.niceview.GetHotelsResponse;
import javax.jws.WebService;

/**
 * @author Magdalena Furman
 */
@WebService(serviceName = "niceViewService", portName = "niceViewPortTypeBindingPort", endpointInterface = "dk.dtu.niceview.NiceViewPortType", targetNamespace = "urn:niceview.dtu.dk", wsdlLocation = "WEB-INF/wsdl/NiceViewWS/niceView.wsdl")
public class NiceViewWS {

    public BookHotelResponse bookHotel(BookHotelRequest request) throws BookHotelFault_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public CancelHotelResponse cancelHotel(CancelHotelRequest request) throws CancelHotelFault_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public GetHotelsResponse getHotels(GetHotelsRequest request) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
