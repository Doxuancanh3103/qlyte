/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vt.qlkdtt.yte.ews;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;


public class CommonWebservice {

    /**
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public static String marshal(Object object) throws Exception {

        if (object == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller unmarshaller = jaxbContext.createMarshaller();
        unmarshaller.marshal(object, sw);

        return sw.toString().replaceAll("<\\?xml version=\"1\\.0\" encoding=\"UTF-8\" standalone=\"yes\"\\?>", "");
    }

    /**
     *
     * @param responseBody
     * @param aClass
     * @return
     * @throws Exception
     */
    public static Object unmarshal(String responseBody, Class aClass) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(responseBody));
    }

    public static Object unmarshal(File file, Class aClass) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }

//    public static String sendRequest(String requestContent, String wsdlUrl, int recvTimeout, int connectTimeout) throws Exception {
//        HttpClient httpclient = new HttpClient();
//        httpclient.getParams().setParameter("http.socket.timeout", recvTimeout);
//        httpclient.getParams().setParameter("http.connection.timeout", connectTimeout);
//        //create an instance PostMethod
//        PostMethod post = new PostMethod(wsdlUrl);
//        String result = "";
//        try {
//
//            System.out.println(requestContent);
//            RequestEntity entity = new StringRequestEntity(requestContent, "text/xml", "UTF-8");
//            post.setRequestEntity(entity);
//            httpclient.executeMethod(post);
//            result = parseResult(post.getResponseBodyAsString());
//
//            System.out.println(result);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw ex;
//        } finally {
//
//        }
//        return result;
//    }

    public static String parseResult(String response) throws Exception {
        String result = "";
        try {
            MessageFactory mf = MessageFactory.newInstance();
            // Create a message.  This example works with the SOAPPART.
            SOAPMessage soapMsg = mf.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            //InputStream is = new ByteArrayInputStream(response.getBytes());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response));
            Document document = builder.parse(is);
            DOMSource domSource = new DOMSource(document);
            part.setContent(domSource);

            SOAPElement resResultSet = (SOAPElement) soapMsg.getSOAPBody().getChildElements().next();

            DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
            LSSerializer serializer = domImplLS.createLSSerializer();
            result = serializer.writeToString(resResultSet);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return result;
    }

}
