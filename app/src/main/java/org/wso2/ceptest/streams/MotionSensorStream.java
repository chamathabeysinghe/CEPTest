/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.ceptest.streams;



public final class MotionSensorStream {

    public final static String streamTypeAccelerometer= "@Source(type = ‘tcp’)" +
            "define stream accelerometerStream ( sensorName string, timestamp long, accuracy int, accelerationX float,accelerationY float, accelerationZ float);";

    public final static String streamTypeGravity= "@Source(type = ‘tcp’)" +
            "define stream gravityStream ( sensorName string, timestamp long, accuracy int, gravityX float,gravityY float, gravityZ float);";

    public final static String streamTypeGyroscope= "@Source(type = ‘tcp’)" +
            "define stream accelerometerStream ( sensorName string, timestamp long, accuracy int, rotationX float,rotationY float, rotationZ float);";

    public final static String streamTypeGyroscopeUncalibrated= "@Source(type = ‘tcp’)" +
            "define stream accelerometerStream ( sensorName string, timestamp long, accuracy int, rateOfRotationX float,rateOfRotationY float, rateOfRotationZ float);";

    public final static String getStreamTypeLinearAcceleration= "@Source(type = ‘tcp’)" +
            "define streamLinearAcceleration ( sensorName string, timestamp long, accuracy int, accelerationForceX float,accelerationForceY float,accelerationForceZ float);";

    public final static String streamTypeRotationVector= "@Source(type = ‘tcp’)" +
            "define streamRotationVector ( sensorName string, timestamp long, accuracy int,rotationX float,rotationY float,rotationZ float, scalar float );";

    public final static String streamTypeSignificantMotion= "@Source(type = ‘tcp’)" +
            "define streamSignificantMotion ( sensorName string, timestamp long, accuracy int );";

    public final static String streamTypeStepCounter= "@Source(type = ‘tcp’)" +
            "define streamStepCounter ( sensorName string, timestamp long, accuracy int, stepCount float);";

    public final static String streamTypeStepDetector= "@Source(type = ‘tcp’)" +
            "define streamStepDetector ( sensorName string, timestamp long, accuracy int );";



}
