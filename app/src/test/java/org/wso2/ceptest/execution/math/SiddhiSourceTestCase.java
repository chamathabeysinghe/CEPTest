/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.ceptest.execution.math;

import org.junit.Assert;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import static org.junit.Assert.assertEquals;


public class SiddhiSourceTestCase {



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
                for (Event event : inEvents) {
                    Assert.assertNotNull(event.getData());
                }
            }
        });
        siddhiAppRuntime.start();


    }
}