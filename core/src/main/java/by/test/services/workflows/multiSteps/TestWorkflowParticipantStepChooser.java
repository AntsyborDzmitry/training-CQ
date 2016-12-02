package by.test.services.workflows.multiSteps;

import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.exec.ParticipantStepChooser;
import com.day.cq.workflow.exec.WorkflowData;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(label = "Test workflow - participant step chooser ", immediate = true, metatype = true)
@Service(ParticipantStepChooser.class)
@Property(name = "process.label", value = "TestWorkflowParticipantStepChooser")
public class TestWorkflowParticipantStepChooser implements ParticipantStepChooser {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private static final Logger LOG = LoggerFactory.getLogger(TestWorkflowParticipantStepChooser.class);

    @Override
    public String getParticipant(com.day.cq.workflow.exec.WorkItem workItem, com.day.cq.workflow.WorkflowSession workflowSession, com.day.cq.workflow.metadata.MetaDataMap metaDataMap) {
        LOG.info("Start get participant step");
        String approver = (String) workItem.getMetaDataMap().get("approver");
        //Call query to find approver if not found, happens in cases where approver changes groups
        if (approver==null || approver.equals("")) {
            approver = "myOwnApruver";
        }
        WorkflowData workflowData = workItem.getWorkflowData();
        workflowData.getMetaDataMap().put("approver",approver);
        return StringUtils.defaultIfEmpty(approver, workItem.getWorkflow().getInitiator());
    }

}

