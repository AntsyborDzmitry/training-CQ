package by.test.services.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.*;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import org.osgi.service.component.ComponentContext;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Dictionary;

@Component(label = "Test workflow  - process step   JCRlvl", immediate = true, metatype = true)
@Service
@Property(name = "process.label", value = "TestWorkflowProcessStepJCRlvl")
public class TestWorkflowProcessStepJCRlvl implements WorkflowProcess{
    @Property(label = "first path", description = "path for adding props", value = "")
    private static final String NODE_PATH_1 = "myPath_1";

    @Property(label = "second path", description = "path for adding props", value = "")
    private static final String NODE_PATH_2 = "myPath_2";

    private static String PATH_1;
    private static String PATH_2;

    private static final Logger LOG = LoggerFactory.getLogger(TestWorkflowProcessStepJCRlvl.class);

    @Activate
    protected void activate(ComponentContext context){
        LOG.info("My workfolw process JCRlvl service was activated");
        Dictionary<?, ?> props = context.getProperties();
        if(props != null){
            PATH_1 = PropertiesUtil.toString(props.get(NODE_PATH_1), null);
            PATH_2 = PropertiesUtil.toString(props.get(NODE_PATH_2), null);
        }
    }

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("My workfolw proces JCRlvl start executing");
        LOG.info(PATH_1);
        LOG.info(PATH_2);
        Workflow workflow = workflowSession.getWorkflow(workItem.getWorkflow().getId());

        try {
            Session jcrSession = workflowSession.adaptTo(Session.class);
            Node node = (Node) jcrSession.getItem( "/content/test-project/bin/testWorkflow");
            if (node != null) {
                long i = 0;
                if(node.hasProperty("cq:myTestProps")){
                    i = node.getProperty("cq:myTestProps").getLong();
                    ++i;
                }
                node.setProperty("cq:myTestProps", i);
                jcrSession.save();
            }
            workflowSession.terminateWorkflow(workflow);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}

