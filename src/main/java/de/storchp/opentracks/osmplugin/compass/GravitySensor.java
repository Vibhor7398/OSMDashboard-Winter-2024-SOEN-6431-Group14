package de.storchp.opentracks.osmplugin.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

/**
 * Derived from https://github.com/kylecorry31/Trail-Sense/blob/master/app/src/main/java/com/kylecorry/trail_sense/shared/sensors/GravitySensor.kt
 */
public class GravitySensor extends AbstractLowPassSensor {

    public GravitySensor(final Context context)  {
        super(context, Sensor.TYPE_GRAVITY, SensorManager.SENSOR_DELAY_FASTEST, 0.03f);
    }

}
