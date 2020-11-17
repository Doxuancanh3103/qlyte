
package vt.qlkdtt.yte.passport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.viettel.passport package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetRolesOfAppResponse_QNAME = new QName("http://passport.viettel.com/", "getRolesOfAppResponse");
    private final static QName _GetRolesOfApp_QNAME = new QName("http://passport.viettel.com/", "getRolesOfApp");
    private final static QName _Validate_QNAME = new QName("http://passport.viettel.com/", "validate");
    private final static QName _ValidateIncludeIp_QNAME = new QName("http://passport.viettel.com/", "validateIncludeIp");
    private final static QName _ValidateIncludeIpResponse_QNAME = new QName("http://passport.viettel.com/", "validateIncludeIpResponse");
    private final static QName _GetAppFunctions_QNAME = new QName("http://passport.viettel.com/", "getAppFunctions");
    private final static QName _GetAppFunctionsResponse_QNAME = new QName("http://passport.viettel.com/", "getAppFunctionsResponse");
    private final static QName _ValidateResponse_QNAME = new QName("http://passport.viettel.com/", "validateResponse");
    private final static QName _GetAllowedAppResponse_QNAME = new QName("http://passport.viettel.com/", "getAllowedAppResponse");
    private final static QName _GetAllowedApp_QNAME = new QName("http://passport.viettel.com/", "getAllowedApp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.viettel.passport
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllowedApp }
     * 
     */
    public GetAllowedApp createGetAllowedApp() {
        return new GetAllowedApp();
    }

    /**
     * Create an instance of {@link GetAllowedAppResponse }
     * 
     */
    public GetAllowedAppResponse createGetAllowedAppResponse() {
        return new GetAllowedAppResponse();
    }

    /**
     * Create an instance of {@link ValidateResponse }
     * 
     */
    public ValidateResponse createValidateResponse() {
        return new ValidateResponse();
    }

    /**
     * Create an instance of {@link GetAppFunctionsResponse }
     * 
     */
    public GetAppFunctionsResponse createGetAppFunctionsResponse() {
        return new GetAppFunctionsResponse();
    }

    /**
     * Create an instance of {@link GetAppFunctions }
     * 
     */
    public GetAppFunctions createGetAppFunctions() {
        return new GetAppFunctions();
    }

    /**
     * Create an instance of {@link ValidateIncludeIpResponse }
     * 
     */
    public ValidateIncludeIpResponse createValidateIncludeIpResponse() {
        return new ValidateIncludeIpResponse();
    }

    /**
     * Create an instance of {@link ValidateIncludeIp }
     * 
     */
    public ValidateIncludeIp createValidateIncludeIp() {
        return new ValidateIncludeIp();
    }

    /**
     * Create an instance of {@link GetRolesOfAppResponse }
     * 
     */
    public GetRolesOfAppResponse createGetRolesOfAppResponse() {
        return new GetRolesOfAppResponse();
    }

    /**
     * Create an instance of {@link Validate }
     * 
     */
    public Validate createValidate() {
        return new Validate();
    }

    /**
     * Create an instance of {@link GetRolesOfApp }
     * 
     */
    public GetRolesOfApp createGetRolesOfApp() {
        return new GetRolesOfApp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRolesOfAppResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getRolesOfAppResponse")
    public JAXBElement<GetRolesOfAppResponse> createGetRolesOfAppResponse(GetRolesOfAppResponse value) {
        return new JAXBElement<GetRolesOfAppResponse>(_GetRolesOfAppResponse_QNAME, GetRolesOfAppResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRolesOfApp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getRolesOfApp")
    public JAXBElement<GetRolesOfApp> createGetRolesOfApp(GetRolesOfApp value) {
        return new JAXBElement<GetRolesOfApp>(_GetRolesOfApp_QNAME, GetRolesOfApp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Validate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "validate")
    public JAXBElement<Validate> createValidate(Validate value) {
        return new JAXBElement<Validate>(_Validate_QNAME, Validate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateIncludeIp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "validateIncludeIp")
    public JAXBElement<ValidateIncludeIp> createValidateIncludeIp(ValidateIncludeIp value) {
        return new JAXBElement<ValidateIncludeIp>(_ValidateIncludeIp_QNAME, ValidateIncludeIp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateIncludeIpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "validateIncludeIpResponse")
    public JAXBElement<ValidateIncludeIpResponse> createValidateIncludeIpResponse(ValidateIncludeIpResponse value) {
        return new JAXBElement<ValidateIncludeIpResponse>(_ValidateIncludeIpResponse_QNAME, ValidateIncludeIpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppFunctions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getAppFunctions")
    public JAXBElement<GetAppFunctions> createGetAppFunctions(GetAppFunctions value) {
        return new JAXBElement<GetAppFunctions>(_GetAppFunctions_QNAME, GetAppFunctions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppFunctionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getAppFunctionsResponse")
    public JAXBElement<GetAppFunctionsResponse> createGetAppFunctionsResponse(GetAppFunctionsResponse value) {
        return new JAXBElement<GetAppFunctionsResponse>(_GetAppFunctionsResponse_QNAME, GetAppFunctionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "validateResponse")
    public JAXBElement<ValidateResponse> createValidateResponse(ValidateResponse value) {
        return new JAXBElement<ValidateResponse>(_ValidateResponse_QNAME, ValidateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllowedAppResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getAllowedAppResponse")
    public JAXBElement<GetAllowedAppResponse> createGetAllowedAppResponse(GetAllowedAppResponse value) {
        return new JAXBElement<GetAllowedAppResponse>(_GetAllowedAppResponse_QNAME, GetAllowedAppResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllowedApp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://passport.viettel.com/", name = "getAllowedApp")
    public JAXBElement<GetAllowedApp> createGetAllowedApp(GetAllowedApp value) {
        return new JAXBElement<GetAllowedApp>(_GetAllowedApp_QNAME, GetAllowedApp.class, null, value);
    }

}
