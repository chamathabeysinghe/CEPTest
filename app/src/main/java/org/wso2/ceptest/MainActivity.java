package org.wso2.ceptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.wso2.ceptest.proximity.ProximitySensor;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.util.EventPrinter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        runSiddhiApp();
    }

    public void buttonClickSimpleFilter(View view){
        try {
            SimpleFilterSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickExtension(View view){
        try {
            ExtensionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickFunction(View view){
        try {
            FunctionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickPartition(View view){
        try {
            PartitionSample.execute(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runSiddhiApp(){
        try {
            ProximitySensor.getInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("source:proximity",org.wso2.ceptest.proximity.ProximitySensorSource.class);
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
                for (Event event : inEvents) {
                    Log.e("Event from the source :",event.toString());
                }
            }
        });

        siddhiAppRuntime.start();
    }
}
