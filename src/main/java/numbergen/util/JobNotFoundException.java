package numbergen.util;

public class JobNotFoundException extends RuntimeException {

	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public JobNotFoundException(String taskId) {
		super("task with id: " + taskId + " is not found");
		this.taskId = taskId;
	}

}
