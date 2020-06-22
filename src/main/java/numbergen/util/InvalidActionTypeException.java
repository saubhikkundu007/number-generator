package numbergen.util;

public class InvalidActionTypeException extends RuntimeException {

	private String action;

	public String getAction() {
		return action;
	}

	public InvalidActionTypeException(String action) {
		super("Action: " + action + " is not valid");
		this.action = action;
	}

}
