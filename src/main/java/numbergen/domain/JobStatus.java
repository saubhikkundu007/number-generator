package numbergen.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobStatus {

	@Id
	String id;
	private String result;

	public JobStatus() {}

	public JobStatus(String taskId, String result) {
		super();
		this.id = taskId;
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
