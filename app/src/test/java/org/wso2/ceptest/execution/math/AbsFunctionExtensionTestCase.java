package org.wso2.ceptest.execution.math;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

import static org.junit.Assert.assertEquals;


public class AbsFunctionExtensionTestCase {


    protected static SiddhiManager siddhiManager;
    private static Logger logger = Logger.getLogger(AbsFunctionExtensionTestCase.class);

    @Test
    public void testProcess() throws Exception {
        logger.info("AbsFunctionExtension TestCase");

        siddhiManager = new SiddhiManager();
        String inValueStream = "define stream InValueStream (inValue double);";

        String eventFuseExecutionPlan = ("@info(name = 'query1') from InValueStream "
                + "select math:abs(inValue) as absValue "
                + "insert into OutMediationStream;");
        SiddhiAppRuntime executionPlanRuntime =
                siddhiManager.createSiddhiAppRuntime(inValueStream + eventFuseExecutionPlan);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                Double result;
                for (Event event : inEvents) {
                    result = (Double) event.getData(0);
                    assertEquals((Double) 6.0, result);
                }
            }
        });
        InputHandler inputHandler = executionPlanRuntime
                .getInputHandler("InValueStream");
        executionPlanRuntime.start();
        inputHandler.send(new Double[]{6d});
        Thread.sleep(100);
        executionPlanRuntime.shutdown();
    }


}