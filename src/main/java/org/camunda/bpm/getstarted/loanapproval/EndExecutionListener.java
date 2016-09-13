package org.camunda.bpm.getstarted.loanapproval;

import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class EndExecutionListener implements ExecutionListener {
	private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS");
	
    public void notify(DelegateExecution execution) throws Exception { 	
    	//Current Date
        Date processInstanceEnd = new Date();
    	Date processInstanceStart = (Date)execution.getVariable("processInstanceStart");
    	long timePeriod = processInstanceEnd.getTime() - processInstanceStart.getTime();
    	
    	LOGGER.info("\nEnd of process instance "+execution.getProcessInstanceId()+":"+processInstanceEnd
    			+"\nThe process instance ran:"
    			+"\n"+timePeriod / (24 * 60 * 60 * 1000)+" days"+
    			"\n"+timePeriod / (60 * 60 * 1000) % 24+" hours"+
    			"\n"+timePeriod / (60 * 1000) % 60+" minutes and "+
    			"\n"+timePeriod / 1000 % 60+" seconds");

    	/*in milliseconds
		long diff = d2.getTime() - d1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
    	*/
    }
  }
