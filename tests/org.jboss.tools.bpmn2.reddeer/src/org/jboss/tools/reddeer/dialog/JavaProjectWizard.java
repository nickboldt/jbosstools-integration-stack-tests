package org.jboss.tools.reddeer.dialog;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * 
 */
public class JavaProjectWizard extends NewWizardDialog {

	/**
	 * 
	 */
	public JavaProjectWizard() {
		super("Java", "Java Project");
	}
	
	@Override
	public WizardPage getFirstPage() {
		return null;
	}	
	
	/**
	 * 
	 * @param projectName
	 */
	public void execute(String projectName) {
		open();
		new LabeledText("Project name:").setText(projectName);
		finish();
	}
	
}
