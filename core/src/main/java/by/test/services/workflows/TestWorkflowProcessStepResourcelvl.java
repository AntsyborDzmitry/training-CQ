package by.test.services.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.*;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.PersistenceException;
import org.osgi.service.component.ComponentContext;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import javax.jcr.Session;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


@Component(label = "Test workflow - process step Resource_lvl", immediate = true, metatype = true)
@Service(WorkflowProcess.class)

@Property(name = "process.label", value = "TestWorkflowProcessStepResourcelvl")
public class TestWorkflowProcessStepResourcelvl implements WorkflowProcess{

    @Property(label = "first path", description = "path for adding props", value = "/content/test-project/bin/testWorkflow")
    private static final String NODE_PATH_1 = "myPath_1";

    @Property(label = "second path", description = "path for adding props", value = "")
    private static final String NODE_PATH_2 = "myPath_2";

    private static String PATH_1;
    private static String PATH_2;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;


    private static final Logger LOG = LoggerFactory.getLogger(TestWorkflowProcessStepResourcelvl.class);

    @Activate
    protected void activate(ComponentContext context){
        LOG.info("My workfolw process Resource_LvL service was activated");
        Dictionary<?, ?> props = context.getProperties();
        if(props != null){
            PATH_1 = PropertiesUtil.toString(props.get(NODE_PATH_1), null);
            PATH_2 = PropertiesUtil.toString(props.get(NODE_PATH_2), null);

        }
    }

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("My workfolw process Resource_LvL start executing");
        LOG.info(PATH_1);
        LOG.info(PATH_2);

        Workflow workflow = workflowSession.getWorkflow(workItem.getWorkflow().getId());
        ResourceResolver resolver ;
        Resource resource;
        ModifiableValueMap map;
        String path = PATH_1;

        resolver =  getResourceResolver(workflowSession.adaptTo(Session.class));

        try {
            if(resolver!= null){
                resource = resolver.getResource(path);
                map = resource.adaptTo(ModifiableValueMap.class);
                long i = 0;
                if(map !=null){
                    if(map.containsKey("cq:myTestProps")){
                        i = map.get("cq:myTestProps", Long.class);
                        ++i;
                    }
                    map.put("cq:myTestProps", i);
                }
                resource.getResourceResolver().commit();
            }
            workflowSession.terminateWorkflow(workflow);
        } catch (PersistenceException  e) {
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
    private boolean readArgument(MetaDataMap args) {
        String argument = args.get("PROCESS_ARGS", "false");
        return argument.equalsIgnoreCase("true");
    }
}

