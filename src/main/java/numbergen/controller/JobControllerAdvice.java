package numbergen.controller;

import java.util.Optional;

import numbergen.util.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error")
@ResponseBody
public class JobControllerAdvice {


	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(JobFailedException.class)
	public VndErrors JobFailedException(JobFailedException e) {
		return this.error(e, HttpStatus.NOT_ACCEPTABLE.toString());
	}

	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(JobInProgressException.class)
	public VndErrors JobInProgressException(JobInProgressException e) {
		return this.error(e, HttpStatus.NOT_ACCEPTABLE.toString());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(JobNotFoundException.class)
	public VndErrors JobNotFoundException(JobNotFoundException e) {
		return this.error(e, HttpStatus.BAD_REQUEST.toString());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidActionTypeException.class)
	public VndErrors InvalidActionTypeException(InvalidActionTypeException e) {
		return this.error(e, HttpStatus.BAD_REQUEST.toString());
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(TempFileNotFoundException.class)
	public VndErrors TempFileNotFoundException(TempFileNotFoundException e) {
		return this.error(e, HttpStatus.INTERNAL_SERVER_ERROR.toString());
	}

	private <E extends Exception> VndErrors error(E e, String status) {
		String error = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
		return new VndErrors(status, error);
	}

}
