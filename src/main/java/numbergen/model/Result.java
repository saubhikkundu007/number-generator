package numbergen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Result {

	@JsonProperty("result")
	private String result;

	public Result(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
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
