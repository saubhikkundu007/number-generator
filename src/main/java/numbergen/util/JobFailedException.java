package numbergen.util;

public class JobFailedException extends RuntimeException {

	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public JobFailedException(String taskId) {
		super("task with id: " + taskId + " has failed");
		this.taskId = taskId;
	}

}
