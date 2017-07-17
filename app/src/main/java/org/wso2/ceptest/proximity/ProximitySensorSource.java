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

package org.wso2.ceptest.proximity;

import android.app.Activity;
import android.util.Log;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.ConnectionUnavailableException;
import org.wso2.siddhi.core.stream.input.source.Source;
import org.wso2.siddhi.core.stream.input.source.SourceEventListener;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.core.util.statistics.memory.ObjectSizeCalculator;
import org.wso2.siddhi.core.util.transport.OptionHolder;
import org.wso2.siddhi.query.api.definition.StreamDefinition;

import java.lang.reflect.Method;
import java.util.Map;

@Extension(
        name = "proximity",
        namespace="source",
        description = "TBD",
        examples = @Example(description = "TBD",syntax = "TBD")
)
public class ProximitySensorSource extends Source {

    private String CONTEXT="context";
    private SourceEventListener sourceEventListener;
    private StreamDefinition streamDefinition;
    private OptionHolder optionHolder;
    private String context;

    @Override
    public void init(SourceEventListener sourceEventListener, OptionHolder optionHolder, String[] strings, ConfigReader configReader, SiddhiAppContext siddhiAppContext) {
        this.sourceEventListener=sourceEventListener;
        context=optionHolder.validateAndGetStaticValue(CONTEXT,
                siddhiAppContext.getName()+"/"+sourceEventListener.getStreamDefinition().getId());
        streamDefinition=StreamDefinition.id(context);
        streamDefinition.getAttributeList().addAll(sourceEventListener.getStreamDefinition().getAttributeList());
        this.optionHolder=optionHolder;
    }

    @Override
    public Class[] getOutputEventClasses() {
        return new Class[0];
    }

    @Override
    public void connect(ConnectionCallback connectionCallback) throws ConnectionUnavailableException {
        /*
        This is assuming initialization happens withing the stream name
        String className=optionHolder.validateAndGetStaticValue("classname");
        try {
            Class<?> act = Class.forName(className);
            Method method = act.getMethod("getInstance");
            Object o = method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Class",className);
        */
        ProximitySensor.getInstance().connectSensor(this.sourceEventListener);
    }

    @Override
    public void disconnect() {
        ProximitySensor.getInstance().disconnectSensor();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void pause() {
        ProximitySensor.getInstance().disconnectSensor();
    }

    @Override
    public void resume() {
        ProximitySensor.getInstance().connectSensor(this.sourceEventListener);
    }

    @Override
    public Map<String, Object> currentState() {
        return null;
    }

    @Override
    public void restoreState(Map<String, Object> map) {

    }
}
