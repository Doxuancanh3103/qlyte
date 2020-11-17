package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ObjectAll")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuDTO {
    @XmlElement(name = "Row")
    List<MenuRowDTO> row;
}
