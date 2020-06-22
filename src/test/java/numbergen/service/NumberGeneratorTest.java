package numbergen.service;

import numbergen.domain.JobStatus;
import numbergen.model.GenerateRequest;
import numbergen.dao.JobStatusDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class NumberGeneratorTest {

    @InjectMocks
    private NumberGenerator numberGenerator;

    @Mock
    private JobStatusDao jobStatusDao;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateAndReadNums() throws Exception {
        String taskId = "taskId";
        when(jobStatusDao.getJobStatus(anyString())).thenReturn(new JobStatus(taskId, ""));
        numberGenerator.generateNumbers(taskId, new GenerateRequest("10", "2"));
        String result = numberGenerator.readNumbers(taskId).getResult();
        assertEquals("10,8,6,4,2,0" , result);
    }
}