package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GlobalListSdo {
    Long id;
    String code;
    String name;
    String value;
    String description;
    String status;
    ArrayList<GlobalListValueSdo> values;
}
