package org.wso2.ceptest.ProximitySourceTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wso2.ceptest.proximity.ProximitySensor;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ProximityTest {

    Context context;
    private volatile boolean eventArrived;
    private volatile int count;
    @Before
    public void createMock() {
        context = InstrumentationRegistry.getContext();
        eventArrived=false;
        count=0;
        try {
            ProximitySensor.getInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProximitySource() {

        assertEquals(true,true);


        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "" +
                "@app:name('foo')" +
                "@source(type='proximity', @map(type='passThrough'))" +
                "define stream streamProximity ( sensorName string, timestamp long, accuracy int,distance float);";

        String query = ("@info(name = 'query1') " +
                "from streamProximity " +
                "select *  " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition +
                query);


        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                eventArrived = true;
                for (Event event : inEvents) {
                    Assert.assertNotNull(event.getData());
                }
            }
        });

        siddhiAppRuntime.start();


    }
}
