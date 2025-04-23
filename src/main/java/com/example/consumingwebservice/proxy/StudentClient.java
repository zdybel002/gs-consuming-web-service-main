
package com.example.consumingwebservice.proxy;
import com.example.consumingwebservice.wsdl.GetAllStudentsRequest;
import com.example.consumingwebservice.wsdl.GetAllStudentsResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class StudentClient extends WebServiceGatewaySupport {

	public GetAllStudentsResponse getAllStudents() {
		GetAllStudentsRequest request = new GetAllStudentsRequest();
		return (GetAllStudentsResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8080/ws", request,
						new SoapActionCallback(""));
	}
}
