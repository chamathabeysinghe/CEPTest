package org.wso2.ceptest.streams;

/**
 * Created by chamath on 7/10/17.
 */

public class PositionSensorStream {

    public final static String streamTypeGameRotation= "@Source(type = ‘tcp’)" +
            "define streamGameRotation ( sensorName string, timestamp long, accuracy int, rotationX float," +
            "rotationY float,rotationZ float,);";

    public final static String streamTypeGeomagneticRotationVector= "@Source(type = ‘tcp’)" +
            "define streamGeomagneticRotationVector ( sensorName string, timestamp long, accuracy int,rotationX float,rotationY float, rotationZ float  );";
    public final static String streamTypeMagneticField= "@Source(type = ‘tcp’)" +
            "define streamMagneticField ( sensorName string, timestamp long, accuracy int,strengthX float,strengthY float, strengthZ float );";
    public final static String getStreamTypeMagneticFieldUncalibrated= "@Source(type = ‘tcp’)" +
            "define streamMagneticFieldUncalibrated ( sensorName string, timestamp long, accuracy int, strengthX float,strengthY float, strengthX float, ironBaseX float,ironBaseY float,ironBaseZ float);";


    public final static String streamTypeOrientation= "@Source(type = ‘tcp’)" +
            "define streamOrientation ( sensorName string, timestamp long, accuracy int, azimuthZ float, pitchX float, rollY float );";

    public final static String streamTypeProximity= "@Source(type = ‘tcp’)" +
            "define streamProximity ( sensorName string, timestamp long, accuracy int,distance float );";

}
