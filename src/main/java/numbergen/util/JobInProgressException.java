package numbergen.util;

public class JobInProgressException extends RuntimeException {
	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public JobInProgressException(String taskId) {
		super("task with id: " + taskId + " is still in progress");
		this.taskId = taskId;
	}

}
