package by.test.services.workflows.multiSteps;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;

import com.adobe.granite.workflow.exec.*;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import javax.jcr.Session;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component(label = "Test workflow - process step Description ", immediate = true, metatype = true)
@Service(WorkflowProcess.class)
@Property(name = "process.label", value = "TestWorkflowMultiStepSetDescription")
public class TestWorkflowMultiStepSetDescription implements WorkflowProcess{

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(TestWorkflowMultiStepSetDescription.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.info("My workfolw process description executing");


        Workflow workflow = workflowSession.getWorkflow(workItem.getWorkflow().getId());
        ResourceResolver resolver ;
        Resource resource;
        ModifiableValueMap map;
        String path = "/content/test-project/bin/testResultMultiStepsWorkflow";

        resolver =  getResourceResolver(workflowSession.adaptTo(Session.class));
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String text = "my description: " + fmt.format(ldt);
        String side = "";
        try {
            if(resolver!= null){
                resource = resolver.getResource(path);

                if(resource != null){
                    map = resource.adaptTo(ModifiableValueMap.class);
                    boolean hasSideKey = map.containsKey("jcr:side");

                    map.put("jcr:description", text);

                    if(hasSideKey){
                        side = map.get("jcr:side", String.class);
                    }

                    if(!hasSideKey || StringUtils.isEmpty(side)){
                        side = "left";
                        map.put("jcr:side", "left");
                    }
                    resource.getResourceResolver().commit();
                }

            }
            //put in scoupe current workflowsession - this data will be available on next step workflow
            //WorkflowData data = workItem.getWorkflow().getWorkflowData();//it seems like will be saved in global scoupe
            /* first version instead persist method
            WorkflowData workflowData = workItem.getWorkflowData();
            workflowData.getMetaDataMap().put("side",side);
            */
            //check "Handler advance" checkbox
            // if(metaDataMap.containsKey("PROCESS_AUTO_ADVANCE")){ metaDataMap.get(""PROCESS_AUTO_ADVANCE"")}

            persistData(workItem, workflowSession,"side",side);
           /* List<Route> routes = workflowSession.getRoutes(workItem, false);
            if(CollectionUtils.isNotEmpty(routes)) {
                for (Route route : routes) {
                    LOG.info("Route: " + route.getName() + " route: " + route);
                }
                workflowSession.complete(workItem, routes.get(0));
            }
*/
            //workflowSession.terminateWorkflow(workflow);
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

    private <T> boolean persistData(WorkItem workItem, WorkflowSession workflowSession, String key, T val) {
        WorkflowData data = workItem.getWorkflowData();
        if (data.getMetaDataMap() == null) {
            return false;
        }

        data.getMetaDataMap().put(key, val);
        workflowSession.updateWorkflowData(workItem.getWorkflow(), data);

        return true;
    }
}

