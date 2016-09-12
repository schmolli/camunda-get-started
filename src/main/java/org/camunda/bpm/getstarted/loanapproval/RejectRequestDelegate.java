package org.camunda.bpm.getstarted.loanapproval;

import java.util.Set;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RejectRequestDelegate implements JavaDelegate {

	private final static Logger LOGGER = Logger.getLogger("LOAN-REQUEST-REJECTION");

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info((String) execution.getVariable("customerEmail"));
		LOGGER.info("Your request for a loan of " + execution.getVariable("amount") + " EUR was rejected!");
	}

}