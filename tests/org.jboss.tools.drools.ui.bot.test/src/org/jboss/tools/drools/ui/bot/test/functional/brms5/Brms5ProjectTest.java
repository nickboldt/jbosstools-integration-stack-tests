package org.jboss.tools.drools.ui.bot.test.functional.brms5;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.WithRegexMatchers;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.drools.reddeer.perspective.DroolsPerspective;
import org.jboss.tools.drools.reddeer.wizard.NewDroolsProjectSelectRuntimeWizardPage.CodeCompatibility;
import org.jboss.tools.drools.reddeer.wizard.NewDroolsProjectWizard;
import org.jboss.tools.drools.ui.bot.test.annotation.UsePerspective;
import org.jboss.tools.drools.ui.bot.test.group.Brms5Test;
import org.jboss.tools.drools.ui.bot.test.group.SmokeTest;
import org.jboss.tools.drools.ui.bot.test.util.RuntimeVersion;
import org.jboss.tools.drools.ui.bot.test.util.TestParent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class Brms5ProjectTest extends TestParent {
    private static final Logger LOGGER = Logger.getLogger(Brms5ProjectTest.class);

    public Brms5ProjectTest() {
        super(RuntimeVersion.BRMS_5);
    }

    @Test @Category({ Brms5Test.class, SmokeTest.class })
    @UsePerspective(DroolsPerspective.class)
    public void testProjectCreationAndDeletion() {
        final String projectName = "testProjectCreationAndDeletion";
        ProblemsView problems = new ProblemsView();
        problems.open();
        waitASecond();
        final int errors = problems.getAllErrors().size();
        final int warnings = problems.getAllWarnings().size();

        NewDroolsProjectWizard wiz = new NewDroolsProjectWizard();
        wiz.open();
        wiz.getFirstPage().setProjectName(projectName);
        wiz.getSelectSamplesPage().checkAll();
        wiz.getDroolsRuntimePage().setUseDefaultRuntime(true);
        wiz.getDroolsRuntimePage().setCodeCompatibleWithVersion(CodeCompatibility.Drools51OrAbove);
        wiz.finish();
        new WaitWhile(new JobIsRunning());

        PackageExplorer explorer = new PackageExplorer();
        Assert.assertTrue("Project was not created.", explorer.containsProject(projectName));

        Assert.assertTrue("Project does not have Drools dependencies.", explorer.getProject(projectName).containsItem("Drools Library"));
        Assert.assertTrue("Wrong drools runtime used.", findDroolsCoreJar(projectName).contains(DROOLS5_RUNTIME_LOCATION));

        problems = new ProblemsView();
        problems.open();
        waitASecond();
        Assert.assertEquals("There are errors in newly created project.", errors ,problems.getAllErrors().size());
        Assert.assertEquals("There are warnings in newly created project.", warnings, problems.getAllWarnings().size());

        explorer.getProject(projectName).delete(true);
        Assert.assertFalse("Project was not deleted.", explorer.containsProject(projectName));
    }

    @Test @Category(Brms5Test.class)
    @UsePerspective(JavaPerspective.class)
    public void testConvertJavaProject() {
        final String projectName = "testJavaProject";
        NewJavaProjectWizardDialog diag = new NewJavaProjectWizardDialog();
        diag.open();
        diag.getFirstPage().setProjectName("testJavaProject");
        try {
            diag.finish();
        } catch (SWTLayerException ex) {
            LOGGER.debug("'Open Associated Perspective' dialog was not shown");
        }

        PackageExplorer explorer = new PackageExplorer();
        Assert.assertTrue("Project was not created", explorer.containsProject(projectName));
        Assert.assertFalse("Project already has Drools dependencies.", explorer.getProject(projectName).containsItem("Drools Library"));

        explorer.getProject(projectName).select();
        new ContextMenu(new WithRegexMatchers("Configure.*", "Convert to Drools Project.*").getMatchers()).select();
        new WaitWhile(new JobIsRunning());

        Assert.assertTrue("Project does not have Drools dependencies.", explorer.getProject(projectName).containsItem("Drools Library"));
    }

    private String findDroolsCoreJar(String projectName) {
        new PackageExplorer().open();
        TreeItem lib = new DefaultTreeItem(projectName, "Drools Library");
        for (TreeItem libItem : lib.getItems()) {
            if (libItem.getText().startsWith("drools-core")) {
                return libItem.getText();
            }
        }

        return null;
    }

}
