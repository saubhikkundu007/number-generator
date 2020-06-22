package numbergen.controller;

import java.util.UUID;

import numbergen.model.Task;
import numbergen.dao.JobStatusDao;
import numbergen.repository.Status;
import numbergen.service.NumberGenerator;
import numbergen.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import numbergen.domain.JobStatus;
import numbergen.model.GenerateRequest;
import numbergen.model.Result;

@RestController
@RequestMapping("/api")
public class JobController {

	@Autowired
	NumberGenerator numberGenerator;

	@RequestMapping(value = "/tasks/{taskId}", produces = {"application/JSON"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Result> getResultForTaskId(@RequestParam(name = "action") String action, @PathVariable String taskId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		validateAction(action);
		if (numberGenerator.isTaskIdPresent(taskId)) {
			return getResultResponseEntity(taskId, httpHeaders);
		} else {
			throw new JobNotFoundException(taskId);
		}
	}

	@RequestMapping(value = "/tasks/{taskId}/status", method = RequestMethod.GET)
	public ResponseEntity<Result> getStatusForTaskID(@PathVariable String taskId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String result = "ERROR";
		if (numberGenerator.isTaskIdPresent(taskId)) {
			result = numberGenerator.getJobStatus(taskId).getResult();
		}
		return new ResponseEntity<Result>(new Result(result), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/generate", consumes = {"application/JSON"}, produces = {
			"application/JSON"}, method = RequestMethod.POST)
	public ResponseEntity<Task> generate(@RequestBody GenerateRequest generateRequest) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String taskId = UUID.randomUUID().toString();
		numberGenerator.updateJobStatus(new JobStatus(taskId, Status.IN_PROGRESS.toString()));
		numberGenerator.generateNumbers(taskId, generateRequest);
		return new ResponseEntity<>(new Task(taskId), httpHeaders, HttpStatus.ACCEPTED);
	}

	private ResponseEntity<Result> getResultResponseEntity(@PathVariable String taskId, HttpHeaders httpHeaders) {
		Status status = Status.valueOf(numberGenerator.getJobStatus(taskId).getResult());
		switch (status) {
			case ERROR:
				throw new JobFailedException(taskId);
			case IN_PROGRESS:
				throw new JobInProgressException(taskId);
			default:
				try {
					return new ResponseEntity<Result>(numberGenerator.readNumbers(taskId), httpHeaders, HttpStatus.OK);
				} catch (Exception ex){
					throw new TempFileNotFoundException(taskId);
				}
		}
	}

	private void validateAction(@RequestParam(name = "action") String action) {
		switch (action) {
			case "get_numlist":
				break;
			default:
				throw new InvalidActionTypeException(action);
		}
	}
}
