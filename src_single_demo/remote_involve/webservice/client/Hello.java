
package remote_involve.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "Hello", targetNamespace = "http://webservice.remote_involve/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Hello {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://webservice.remote_involve/", className = "remote_involve.webservice.Hello_Type")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://webservice.remote_involve/", className = "remote_involve.webservice.HelloResponse")
    public String hello(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}