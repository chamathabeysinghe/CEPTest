/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.ceptest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.log4j.BasicConfigurator;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

import java.util.Arrays;

public class SimpleFilterSample {

    public static void execute(final Context context) throws InterruptedException {
        BasicConfigurator.configure();

        Log.d("Siddhi", "Creating siddhi manager");

        // Creating Siddhi Manager
        SiddhiManager siddhiManager = new SiddhiManager();
        Log.d("Siddhi", "siddhiManager instance made successfully");

        String executionPlan = "" +
                "define stream cseEventStream (symbol string, price float, volume long); " +
                "" +
                "@info(name = 'query1') " +
                "from cseEventStream[volume < 150] " +
                "select symbol,price " +
                "insert into outputStream ;";

        //Generating runtime
        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);
        Log.d("Siddhi", "siddhiManager execution plan made successfully");

        //Adding callback to retrieve output events from query
        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                final StringBuilder sb = new StringBuilder();
                sb.append("Events{ @timeStamp = ").append(timeStamp).append(", inEvents = ").append(Arrays.deepToString(inEvents)).append(", RemoveEvents = ").append(Arrays.deepToString(removeEvents)).append(" }");

                ((Activity)context).runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                EventPrinter.print(timeStamp, inEvents, removeEvents);
            }
        });
        Log.d("Siddhi", "siddhiManager callback added successfully");

        //Retrieving InputHandler to push events into Siddhi
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("cseEventStream");
        Log.d("Siddhi", "siddhiManager input handler made successfully");

        Log.d("Siddhi", "Starting executionPlanRuntime");
        //Starting event processing
        executionPlanRuntime.start();
        Log.d("Siddhi", "Started executionPlanRuntime");

        //Sending events to Siddhi
        inputHandler.send(new Object[]{"IBM", 700f, 100l});
        inputHandler.send(new Object[]{"WSO2", 60.5f, 200l});
        inputHandler.send(new Object[]{"GOOG", 50f, 30l});
        inputHandler.send(new Object[]{"IBM", 76.6f, 400l});
        inputHandler.send(new Object[]{"WSO2", 45.6f, 50l});
        Thread.sleep(100);
        Log.d("Siddhi", "Events sent successfully");

        //Shutting down the runtime
        executionPlanRuntime.shutdown();
        Log.d("Siddhi", "Execution plan shutdown successfully");

        //Shutting down Siddhi
        siddhiManager.shutdown();
        Log.d("Siddhi", "SiddhiManager shutdown successfully");

    }
}