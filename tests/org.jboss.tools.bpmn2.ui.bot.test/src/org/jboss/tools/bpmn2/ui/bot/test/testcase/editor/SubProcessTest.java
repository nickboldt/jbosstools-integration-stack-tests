package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import org.jboss.tools.bpmn2.reddeer.editor.ElementType;
import org.jboss.tools.bpmn2.reddeer.editor.Position;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ScriptTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.SubProcess;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;

@ProcessDefinition(name="BPMN2-SubProcess", project="EditorTestProject")
public class SubProcessTest extends JBPM6BaseTest {

	@Override
	public void buildProcessModel() {
		StartEvent start = new StartEvent("StartProcess");
		start.append("Hello Subprocess", ElementType.SUB_PROCESS);

		SubProcess subProcess = new SubProcess("Hello Subprocess");
		subProcess.addLocalVariable("x", "String");
		subProcess.append("Goodbye", ElementType.SCRIPT_TASK);

		ScriptTask script4 = new ScriptTask("Goodbye");
		script4.setScript("Java", "System.out.println(\"Goodbye World\");");
		script4.append("EndProcess", ElementType.TERMINATE_END_EVENT);
		
		// Now create the inner of the sub process.
		subProcess.add("StartSubProcess", ElementType.START_EVENT);
		
		StartEvent start2 = new StartEvent("StartSubProcess");
		start2.append("Hello1", ElementType.SCRIPT_TASK);
		
		ScriptTask script1 = new ScriptTask("Hello1");
		script1.setScript("Java", "System.out.println(\"x = \" + x);");
		script1.append("Hello2", ElementType.SCRIPT_TASK, Position.SOUTH);
		
		ScriptTask script2 = new ScriptTask("Hello2");
		script2.setScript("Java", "kcontext.setVariable(\"x\", \"Hello\");");
		script2.append("EndSubProcess", ElementType.END_EVENT, Position.SOUTH);
	}
	
}