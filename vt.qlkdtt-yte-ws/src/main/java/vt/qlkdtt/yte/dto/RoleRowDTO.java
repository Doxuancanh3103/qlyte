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
public class RoleRowDTO {
    @XmlElement(name = "STATUS")
    String status;
    @XmlElement(name = "ROLE_ID")
    String roleId;
    @XmlElement(name = "ROLE_NAME")
    String roleName;
    @XmlElement(name = "DESCRIPTION")
    String description;
    @XmlElement(name = "ROLE_CODE")
    String roleCode;
    @XmlElement(name = "IP_OFFICE_WAN")
    String ipOfficeWan;
}