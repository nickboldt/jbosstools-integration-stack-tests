package org.jboss.tools.teiid.reddeer.wizard;

import java.io.File;

import org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * 
 * @author apodhrad
 *
 */
public class ConnectionProfileHsqlPage extends ConnectionProfileDatabasePage{

	public static final String LABEL_DATABASE = "Database:";
	//public static final String LABEL_DATABASE_LOCATION = "Database location:";
	public static final String LABEL_USER_NAME = "User name:";
	public static final String LABEL_PASSWORD = "Password:";
	public static final String LABEL_SAVE_PASSWORD = "Save Password";
	
	protected ConnectionProfileHsqlPage(WizardDialog wizardDialog, int pageIndex) {
		super(wizardDialog, pageIndex);
	}

	@Override
	public void setDatabase(String database) {
		new LabeledText(LABEL_DATABASE).setText(database);
		new DefaultCombo(1).setText(database);
	}

	@Override
	public void setHostname(String hostname) {
		//new LabeledText("URL:").setText(hostname+";hsqldb.lock_file=false");
		//new LabeledText(LABEL_DATABASE_LOCATION).setText("jdbc:hsqldb:"+hostname+";hsqldb.lock_file=false");
		
		//prepare absolute path + add lock
		String database = new DefaultCombo(1).getText();
		hostname = new File(hostname).getAbsolutePath();
		new DefaultCombo(1).setText(hostname + "/" + database + ";hsqldb.lock_file=false");
	}

	@Override
	public void setPort(String port) {
		// there is no port
	}

	@Override
	public void setUsername(String username) {
		new LabeledText(LABEL_USER_NAME).setText(username);
	}

	@Override
	public void setPassword(String password) {
		new LabeledText(LABEL_PASSWORD).setText(password);
		new CheckBox(LABEL_SAVE_PASSWORD).click();
	}

}
