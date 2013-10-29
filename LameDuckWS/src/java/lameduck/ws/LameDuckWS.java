package lameduck.ws;

import dk.dtu.lameduck.GetFlightsResponse;
import dk.dtu.travelgood.commons.FlightsType;
import javax.jws.WebService;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@WebService(serviceName = "lameDuckService", portName = "lameDuckPort", endpointInterface = "dk.dtu.lameduck.LameDuckPortType", targetNamespace = "urn:lameduck.dtu.dk", wsdlLocation = "WEB-INF/wsdl/LameDuckWS/lameDuckWSDL.wsdl")
public class LameDuckWS {

    public dk.dtu.lameduck.GetFlightsResponse getFlights(dk.dtu.lameduck.GetFlightsRequest getFlightsRequestPart) {
        FlightsType flights = new FlightsType();
        
        GetFlightsResponse res = new GetFlightsResponse();
        res.setFlights(flights);
        return res;
    }
    
}
