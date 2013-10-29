package niceview.ws;

import javax.jws.WebService;

/**
 *
 * @author Magdalena Furman
 */
@WebService(serviceName = "niceViewService", portName = "niceViewPortTypeBindingPort", endpointInterface = "dk.dtu.niceview.NiceViewPortType", targetNamespace = "urn:niceview.dtu.dk", wsdlLocation = "WEB-INF/wsdl/NiceViewWS/niceViewWSDL.wsdl")
public class NiceViewWS {

    public dk.dtu.niceview.GetHotelResponse getHotel(dk.dtu.niceview.GetHotelRequest getHotelRequestPart)
    {
      //TODO implement this method
      throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public dk.dtu.niceview.BookHotelResponse bookHotel(dk.dtu.niceview.BookHotelRequest bookHotelRequestPart)
    {
      //TODO implement this method
      throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public dk.dtu.niceview.CancelHotelResponse cancelHotel(dk.dtu.niceview.CancelHotelRequest cancelHotelRequestPart)
    {
      //TODO implement this method
      throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    
}
