package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Row")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuRowDTO {
    @XmlElement(name = "OBJECT_ID")
    String objectId;
    @XmlElement(name = "APP_ID")
    String appId;
    @XmlElement(name = "PARENT_ID")
    String parentId;
    @XmlElement(name = "STATUS")
    String status;
    @XmlElement(name = "ORD")
    String ord;
    @XmlElement(name = "OBJECT_URL")
    String objectUrl;
    @XmlElement(name = "OBJECT_NAME")
    String objectName;
    @XmlElement(name = "DESCRIPTION")
    String description;
    @XmlElement(name = "OBJECT_TYPE_ID")
    String objectTypeId;
    @XmlElement(name = "OBJECT_CODE")
    String objectCode;
    @XmlElement(name = "OBJECT_LEVEL")
    String objectLevel;
    @XmlElement(name = "CREATE_DATE")
    String createDate;
}
