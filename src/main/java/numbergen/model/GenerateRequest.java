package numbergen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Goal", "Step" })
public class GenerateRequest {

	@JsonProperty("Goal")
	String Goal;

	@JsonProperty("Step")
	String Step;

	public String getGoal() {
		return Goal;
	}

	public void setGoal(String goal) {
		this.Goal = goal;
	}

	public String getStep() {
		return Step;
	}

	public void setStep(String step) {
		this.Step = step;
	}

	public GenerateRequest(String goal, String step) {
		Goal = goal;
		Step = step;
	}

	public GenerateRequest() {
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{}";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

}
