package numbergen.dao;

import numbergen.domain.JobStatus;
import numbergen.repository.JobStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class JobStatusDao {

    @Autowired
    JobStatusRepository jobStatusRepository;

    public void updateJobStatus(numbergen.domain.JobStatus jobStatus) {
        jobStatusRepository.save(jobStatus);
    }

    public JobStatus getJobStatus(String taskId) {
        return jobStatusRepository.findOne(taskId);
    }

    public boolean isTaskIdPresent(String taskId) {
        return jobStatusRepository.exists(taskId);
    }
}
