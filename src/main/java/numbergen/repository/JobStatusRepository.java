package numbergen.repository;

import org.springframework.data.repository.CrudRepository;

import numbergen.domain.JobStatus;

public interface JobStatusRepository extends CrudRepository<JobStatus, String> {}
