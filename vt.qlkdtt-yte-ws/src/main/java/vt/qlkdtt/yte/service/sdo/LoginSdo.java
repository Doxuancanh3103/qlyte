package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.dto.MenuDTO;
import vt.qlkdtt.yte.dto.RoleDTO;
import vt.qlkdtt.yte.dto.UserDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "Results")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginSdo {
    @XmlElement(name = "Roles")
    RoleDTO lstRole;
    @XmlElement(name = "UserData")
    UserDTO userData;
    @XmlElement(name = "ObjectAll")
    MenuDTO objectAll;
}
