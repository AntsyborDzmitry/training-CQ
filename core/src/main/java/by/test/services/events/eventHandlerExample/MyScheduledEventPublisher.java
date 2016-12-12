package by.test.services.events.eventHandlerExample;

import by.test.services.helpers.MySharedConstant;
import org.apache.felix.scr.annotations.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import java.util.Dictionary;
import java.util.Hashtable;

@Component(immediate=true)
@Service (Runnable.class)
@Properties({
        @Property(name="scheduler.expression" ,value="0 0/1 * * * ?")/*every minute*/,
        @Property(name="scheduler.concurrent",boolValue=false)
})
public class MyScheduledEventPublisher implements Runnable{

    @Reference
    private EventAdmin eventAdmin;


    private void triggerEvent (String name, String description){
        final Dictionary<String,Object> properties = new Hashtable();

        properties.put(MySharedConstant.PROP_NAME, name);
        properties.put(MySharedConstant.PROP_DESCR,description);
        Event event = new Event(MySharedConstant.EVENT_TOPIC, properties);

        /*
         *   postEvent : Initiate asynchronous delivery of an event. This method returns to the caller before delivery of the event is completed.
         *   sendEvent: Initiate synchronous delivery of an event. This method does not return to the caller until delivery of the event is completed.
         */
        eventAdmin.postEvent(event);
    }

    @Override
    public void run() {
        triggerEvent("test_name", "Test_description");
    }
}
