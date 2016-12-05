package by.test.services.workflows.multiSteps;


import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.*;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

@Component(label = "Test workflow - process step for right brunches ", immediate = true, metatype = true)
@Service(WorkflowProcess.class)
@Property(name = "process.label", value = "TestWorkflowMultiStepSetRightBrunch")
public class TestWorkflowMultiStepSetRightBrunch implements WorkflowProcess {

    @Property(label = "first props", description = "test props", value = "")
    private static final String NODE_PATH_1 = "myProps_1";

    @Property(label = "second props", description = "test props", value = "")
    private static final String NODE_PATH_2 = "myProps_2";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(TestWorkflowMultiStepSetDescription.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("My workfolw process for Right brunch executing");


        Workflow workflow = workflowSession.getWorkflow(workItem.getWorkflow().getId());
        ResourceResolver resolver ;
        Resource resource;
        ModifiableValueMap map;
        String path = "/content/test-project/bin/testResultMultiStepsWorkflow";

        resolver =  getResourceResolver(workflowSession.adaptTo(Session.class));

        try {
            if(resolver!= null){
                LocalDate ld = LocalDate.now();
                LocalTime lt = LocalTime.now();
                LocalDateTime ldt = LocalDateTime.of(ld, lt);
                DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

                resource = resolver.getResource(path);
                map = resource.adaptTo(ModifiableValueMap.class);
                String text = "Right brunches was executed in  " + fmt.format(ldt);
                if(map !=null){
                    map.put("jcr:workflowBrunch", text);
                }
                resource.getResourceResolver().commit();
            }
            workflowSession.terminateWorkflow(workflow);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }
    private ResourceResolver getResourceResolver(final Session session) {
        try {
            final Map<String, Object> authenticationMap = new HashMap<>();
            authenticationMap.put(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, session);
            return resourceResolverFactory.getResourceResolver(authenticationMap);

        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
