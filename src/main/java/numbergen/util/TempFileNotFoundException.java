package numbergen.util;

public class TempFileNotFoundException extends RuntimeException {

	public TempFileNotFoundException(String taskId) {
		super("Temp file for "+ taskId + " not found");
	}

}
