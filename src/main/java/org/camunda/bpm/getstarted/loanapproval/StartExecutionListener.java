package org.camunda.bpm.getstarted.loanapproval;

import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
//import org.camunda.bpm.engine.variable.Variables;

public class StartExecutionListener implements ExecutionListener {
	
	private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS");
	
    public void notify(DelegateExecution execution) throws Exception { 	
        Date processInstanceStart = new Date();
    	execution.setVariable("processInstanceStart",  processInstanceStart);
    	
    	LOGGER.info("Start of process instance "+execution.getProcessInstanceId()+ ":"+processInstanceStart);
    	
    }
  }
