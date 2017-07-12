package org.wso2.ceptest.streams;

/**
 * Created by chamath on 7/10/17.
 */

public class EnvironmentSensorStream {

    public final static String streamTypeAmbientTemperature= "@Source(type = ‘tcp’)" +
            "define streamAmbientTemperature ( sensorName string, timestamp long, accuracy int,ambientTemperature float );";
    public final static String streamTypeLight= "@Source(type = ‘tcp’)" +
            "define streamLight ( sensorName string, timestamp long, accuracy int, illuminance float);";
    public final static String streamTypePressure= "@Source(type = ‘tcp’)" +
            "define streamPressure ( sensorName string, timestamp long, accuracy int, pressure float );";
    public final static String streamTypeRelativeHumidity= "@Source(type = ‘tcp’)" +
            "define streamRelativeHumidity ( sensorName string, timestamp long, accuracy int, relativeHumidity float );";
    public final static String streamTypeTemperature= "@Source(type = ‘tcp’)" +
            "define streamTemperature ( sensorName string, timestamp long, accuracy int, temperature float );";
}
