package niceview.ws;

import javax.jws.WebService;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@WebService(serviceName = "niceViewService", portName = "niceViewPortTypeBindingPort", endpointInterface = "dk.dtu.niceview.NiceViewPortType", targetNamespace = "urn:niceview.dtu.dk", wsdlLocation = "WEB-INF/wsdl/NiceViewWS/niceViewWSDL.wsdl")
public class NiceViewWS {

    public dk.dtu.niceview.GetHotelsResponse getHotels(dk.dtu.niceview.GetHotelsRequest getHotelsRequestPart) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
