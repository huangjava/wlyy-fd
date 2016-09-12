package com.yihu.wlyy.util;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Map;

/**
 * Created by Airhead on 2016/9/10.
 */
public class SoapClient {
    private String endpoint;
    private String namespace;
    private String serviceName;
    private String servicePort;
    private String protocol;

    SoapClient(String endpoint, String namespace, String serviceName, String servicePort) {
        this.endpoint = endpoint;
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.servicePort = servicePort;
    }

    public SOAPMessage invoke(String action, Map<String, String> params) throws SOAPException {
        QName serviceName = new QName(namespace, this.serviceName);
        QName portName = new QName(namespace, this.servicePort);

        //Create a service and add at least one port to it.
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpoint);

        //Create a Dispatch instance from a service.
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        // compose a request message
        MessageFactory mf = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

        // Create a message.
        SOAPMessage request = mf.createMessage();
        SOAPPart part = request.getSOAPPart();

        // Obtain the SOAPEnvelope and header and body elements.
        SOAPEnvelope env = part.getEnvelope();
        //        SOAPHeader header = env.getHeader();  //no use
        SOAPBody body = env.getBody();
        env.addNamespaceDeclaration("ns", namespace);

        // Construct the message payload.
        SOAPElement operation = body.addChildElement(action, "ns");
        params.forEach((key, value) -> {
            SOAPElement element = null;
            try {
                element = operation.addChildElement(key);
                element.addTextNode(value);
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        });
        request.saveChanges();

        // Invoke the service endpoint.
        return dispatch.invoke(request);
    }
}
