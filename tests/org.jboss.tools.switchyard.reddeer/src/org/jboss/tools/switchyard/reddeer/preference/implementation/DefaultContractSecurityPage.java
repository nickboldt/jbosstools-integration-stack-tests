package org.jboss.tools.switchyard.reddeer.preference.implementation;

import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

/**
 * Represents a properties page "Contract --> Security Policy".
 * 
 * @author tsedmik
 */
public class DefaultContractSecurityPage extends DefaultPage {
	
	private static final String CKCK_AUTHENTICATION = "Client Authentication";
	private static final String CONFIDENTALITY = "Confidentiality";
	private static final String COMBOBOX_SECURITY = "Security Configuration";
		
	public boolean isAuthenticationChecked() {
		
		return isCheckBoxChecked(CKCK_AUTHENTICATION);
	}
	
	public boolean isAuthenticationEnabled() {
		
		return isCheckBoxEnabled(CKCK_AUTHENTICATION);
	}
	
	public DefaultContractSecurityPage setAuthentication(boolean value) {
		
		setCheckBox(CKCK_AUTHENTICATION, value);
		return this;
	}
	
	public boolean isConfidentalityChecked() {
		
		return isCheckBoxChecked(CONFIDENTALITY);
	}
	
	public boolean isConfidentalityEnabled() {
		
		return isCheckBoxEnabled(CONFIDENTALITY);
	}
	
	public DefaultContractSecurityPage setConfidentality(boolean value) {
		
		setCheckBox(CONFIDENTALITY, value);
		return this;
	}
	
	public boolean isSecurityConfComboEnabled() {
		
		return isComboEnabled(COMBOBOX_SECURITY);
	}
	
	public DefaultContractSecurityPage setSecurityConf(String conf) {
		
		new LabeledCombo(COMBOBOX_SECURITY).setText(conf);
		return this;
	}
}
