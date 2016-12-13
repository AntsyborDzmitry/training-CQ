package by.test.services.events.eventHandlerExample;


import by.test.services.helpers.MySharedConstant;
import by.test.services.helpers.MyUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Map;


@Component(immediate = true)
@Service(EventHandler.class)
@Properties(
        {
                @Property(name = "event.topics", value = "it/can/be/any/string"),

                /* Event filters support LDAP filter syntax and have access to event.getProperty(..) values */
                /* LDAP Query syntax: https://goo.gl/MCX2or */
                /*@Property(
                        label = "Event Filters",
                        // Only listen on events associated with nodes that end with /jcr:content
                        value =   "(path=/content/test-project/bin/testEventType)",
                        description = "[Optional] Event Filters used to further restrict this event handler; Uses LDAP expression against event properties.",
                        name = EventConstants.EVENT_FILTER,
                        propertyPrivate = true
                )*/
        }
)
public class MyEventHandler implements EventHandler{
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(MyEventHandler.class);

    @Override
    public void handleEvent(Event event) {
        LOG.info("Handled event for MyEventHandler");

        ModifiableValueMap map;

        final Map<String, Object> authInfo = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                (Object) "Anc.D");

        ResourceResolver resourceResolver = null;
        try {
            // Always use service users; never admin resource resolvers for "real" code
            /*
            * Steps for creating a new "system user" through CRX Explorer tool:
                Open http://localhost:4502/crx/explorer/index.jsp
                Login as admin
                Click User Administration
                Click Create System User
                Set the UserId Click green button with tick (cannot see a SAVE button)
                And after that need to subscribe my user for necessary bundle via "Apache sling Service User Mapper Service Amendment"
            * */
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo);

            String propName = (String)event.getProperty(MySharedConstant.PROP_NAME);

            String propDescription = (String)event.getProperty(MySharedConstant.PROP_DESCR);
            propDescription += MyUtils.getFormatedCurrentDate();

            Resource resource = resourceResolver.getResource("/content/test-project/bin/testEventType");

            if (resource!=null) {
                map = resource.adaptTo(ModifiableValueMap.class);
                map.put(propName, propDescription);
                resource.getResourceResolver().commit();
            }

        }catch (LoginException | PersistenceException e){
            LOG.error("Could not get service resolver", e);
        } finally {
            // Always close resource resolvers you open
            if (resourceResolver != null) {
                resourceResolver.close();
            }
        }
    }
}
