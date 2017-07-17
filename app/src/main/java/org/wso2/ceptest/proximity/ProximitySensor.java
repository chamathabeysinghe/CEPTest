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

package org.wso2.ceptest.proximity;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import org.wso2.siddhi.core.stream.input.source.SourceEventListener;

public class ProximitySensor implements SensorEventListener {

    private static ProximitySensor proximitySensor;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

    private SourceEventListener sourceEventListener;

    private float previousValue=-1;
    private ProximitySensor(Context context) throws Exception {
        this.context=context;
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sensor==null)
            throw new Exception("Proximity Device is not supported in the device");
    }

    public static ProximitySensor getInstance(Context context) throws Exception {
        if(proximitySensor==null)
            proximitySensor=new ProximitySensor(context);
        return proximitySensor;
    }

    public static ProximitySensor getInstance(){
        if(proximitySensor==null)
            throw new NullPointerException("android.content.Context is not initialized");
        return proximitySensor;
    }

    public void connectSensor(SourceEventListener sourceEventListener){
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        this.sourceEventListener=sourceEventListener;
    }

    public void disconnectSensor(){
        sensorManager.unregisterListener(this);
        this.sourceEventListener=null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0]==previousValue)return;
        previousValue=event.values[0];
        Object eventOutput[] ={event.sensor.getName(),event.timestamp,event.accuracy,event.values[0]};
        Log.e("Sensor","Sensor Changed");
        this.sourceEventListener.onEvent(eventOutput,null);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
