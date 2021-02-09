package ch.supsi.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ServletException {
}
