package org.wso2.ceptest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wso2.ceptest.proximity.ProximitySensor;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SiddhiSourceTest {


    private Context instrumentationCtx;
    private int eventCount=0;
    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
    }

    @Test
    public void testSensorSource1(){
        try {
            ProximitySensor.getInstance(instrumentationCtx);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("source:proximity",org.wso2.ceptest.proximity.ProximitySensorSource.class);
        String inStreamDefinition = "" +
                "@app:name('foo')" +
                "@source(type='proximity',classname='org.wso2.ceptest.MainActivity', @map(type='passThrough'))" +
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
                printOutput(timeStamp,inEvents,removeEvents);
            }
        });


        siddhiAppRuntime.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(eventCount,greaterThan(0));
    }

    private void printOutput(long timeStamp, Event[] inEvents, Event[] removeEvents){
        EventPrinter.print(timeStamp, inEvents, removeEvents);
        for (Event event : inEvents) {
            Log.e("Event from the source :",event.toString());
            eventCount++;
        }
    }


}
