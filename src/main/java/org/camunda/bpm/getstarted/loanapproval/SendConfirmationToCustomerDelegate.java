package org.camunda.bpm.getstarted.loanapproval;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendConfirmationToCustomerDelegate implements JavaDelegate {

	private final static Logger LOGGER = Logger.getLogger("LOAN-REQUESTS-SEND-CONFIRMATION");

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("\n-Sending confirmation to customer...");
		
		if(execution.getVariable("customerEmail")==null || execution.getVariable("customerEmail").equals("fail@send.de")){
			throw new RuntimeException("Can't send confirmation to customer");
		}else{
			LOGGER.info("\n-Confirmation was send to: "+execution.getVariable("customerEmail"));	
		}
	}
}
