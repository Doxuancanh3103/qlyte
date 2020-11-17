package vt.qlkdtt.yte.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.coong.core.exception.ServiceBadException;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductStatusInvalidException extends ServiceBadException {

    public ProductStatusInvalidException(String status) {
        super(String.format("Status %s is invalid", status));
    }

    @Override
    public String getErrorCode() {
        return "PRODUCT_STATUS_INVALID";
    }
}
