package vt.qlkdtt.yte.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String errorCode;
    private String message;
    List<String> errorField;
}

