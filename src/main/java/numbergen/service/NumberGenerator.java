package numbergen.service;

import numbergen.domain.JobStatus;
import numbergen.model.GenerateRequest;
import numbergen.model.Result;
import numbergen.dao.JobStatusDao;
import numbergen.repository.Status;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@EnableAsync
public class NumberGenerator {

    @Autowired
    JobStatusDao jobStatusDao;

    @Async
    public void generateNumbers(final String taskId,  GenerateRequest generateRequest) {
        int goal = Integer.parseInt(generateRequest.getGoal());
        int step = Integer.parseInt(generateRequest.getStep());
        JobStatus jobStatus = getJobStatus(taskId);
        try {
            boolean firstTime = true;
            File file = new File("/tmp/"+taskId+"_output.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            while(goal >= 0){
                if(!firstTime) bw.write(",");
                firstTime = false;
                bw.write(String.valueOf(goal));
                goal -= step;
            }

            bw.close();
            fos.flush();
            jobStatus.setResult(Status.SUCCESS.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            jobStatus.setResult(Status.ERROR.toString());
        }
        updateJobStatus(jobStatus);
    }

    public Result readNumbers(final String taskId) throws IOException {
        try {
            String pathname = "/tmp/" + taskId + "_output.txt";
            File file = new File(pathname);
            return new Result(FileUtils.readFileToString(file));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("Failed to write to path");
        }
    }

    public void updateJobStatus(JobStatus jobStatus) {
        jobStatusDao.updateJobStatus(jobStatus);
    }

    public JobStatus getJobStatus(String taskId) {
        return jobStatusDao.getJobStatus(taskId);
    }

    public boolean isTaskIdPresent(String taskId) {
        return jobStatusDao.isTaskIdPresent(taskId);
    }

}
