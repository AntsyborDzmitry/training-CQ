package by.test.services.events.eventHandlerExample;


import by.test.services.helpers.MyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Properties;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.*;
import org.osgi.service.event.Event;

import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component(immediate=true)
@Service
@Properties({
        // choose appropriate topic values
        @Property(name = EventConstants.EVENT_TOPIC, value = {
                SlingConstants.TOPIC_RESOURCE_CHANGED/*,SlingConstants.TOPIC_RESOURCE_ADDED, SlingConstants.TOPIC_RESOURCE_REMOVED*/
        })


      /*
        @Property(label = "Event Topics",
                value = {SlingConstants.TOPIC_RESOURCE_CHANGED, SlingConstants.TOPIC_RESOURCE_ADDED, SlingConstants.TOPIC_RESOURCE_REMOVED},
                description = "This handler responds to resource modification event.",
                name = EventConstants.EVENT_TOPIC,
                propertyPrivate = true),
        @Property(label = "JCR paths to watch for changes.",
                value = "(|(" + SlingConstants.PROPERTY_PATH + "=" + "/content*)(" + SlingConstants.PROPERTY_PATH + "=" + "/etc*))",
                description = "Paths expressed in LDAP syntax. Example: (|(path=/content*)(path=/etc*))" + " - Watches for changes under /content or /etc. ",
                name = EventConstants.EVENT_FILTER)
     */
})
public class MyResourceManipulationEventHandler implements EventHandler{

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(MyResourceManipulationEventHandler.class);

    private   Map <String,String> trackedProps = new HashMap<String, String>(){{
                                                                                put(SlingConstants.PROPERTY_CHANGED_ATTRIBUTES, "changed");
                                                                                put(SlingConstants.PROPERTY_ADDED_ATTRIBUTES, "added");
                                                                                put(SlingConstants.PROPERTY_REMOVED_ATTRIBUTES, "removed");
                                                                            }};;
    private   Map <String,String> changes = new HashMap<String, String>();

    @Override
    public void handleEvent(Event event) {
        String path = (String) event.getProperty(SlingConstants.PROPERTY_PATH);
        LOG.info("Handled event for MyResourceManipulationEventHandler");

        ModifiableValueMap map;

        final Map<String, Object> authInfo = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                (Object) "Anc.D");

        ResourceResolver resourceResolver = null;
        Resource resource = null;
        try {

            if (StringUtils.contains(path, "/content/test-project/bin/testEventType")) {
                String name = "test_Event_resource_manipulation";
                String descriptionProp = "";
                String [] propNames = event.getPropertyNames();

                if(propNames != null){
                   /* Arrays.asList(propNames).forEach(prop -> findProps(prop, event));
                   * Arrays.asList(propNames)stream().forEach(prop -> findProps(prop, event));*/
                    List result = Arrays.stream(propNames).filter(res -> trackedProps.keySet().contains(res)).collect(Collectors.toList());
                    if (result != null && !result.isEmpty()) {
                        StringBuilder description = new StringBuilder();
                        result.stream().forEach( item -> { description.append(trackedProps.get(item)).append("; ");});
                        descriptionProp = description.toString();
                    }
                }
                descriptionProp +=  MyUtils.getFormatedCurrentDate();
                resourceResolver = getResolver();

                if (resourceResolver != null){
                    resource = resourceResolver.getResource(path);
                }

                if (resource!=null) {
                    map = resource.adaptTo(ModifiableValueMap.class);
                    map.put(name, descriptionProp);
                    resource.getResourceResolver().commit();
                }

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

    private ResourceResolver getResolver () throws LoginException {
        final Map<String, Object> authInfo = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                (Object) "Anc.D");
        ResourceResolver resourceResolver = null;
        resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo);
        return resourceResolver;
    }
}
