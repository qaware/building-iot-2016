/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.nest.handler;

import static org.eclipse.smarthome.binding.nest.NestBindingConstants.TARGET_TEMPERATURE;

import org.eclipse.smarthome.binding.nest.NestWebservice;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * The {@link NestHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author phxql - Initial contribution
 */
public class NestHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(NestHandler.class);

    public NestHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(TARGET_TEMPERATURE)) {
            String thermostatId = getThing().getProperties().get("thermostat-id");

            double value = ((DecimalType) command).doubleValue();
            NestWebservice.INSTANCE.getClient().child("/devices/thermostats").child(thermostatId)
                    .child("target_temperature_c").setValue(value);
        }
    }

    @Override
    public void initialize() {
        updateStatus(ThingStatus.OFFLINE);

        String thermostatId = getThing().getProperties().get("thermostat-id");

        NestWebservice.INSTANCE.getClient().child("/devices/thermostats").child(thermostatId)
                .child("target_temperature_c").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot arg0) {
                        updateStatus(ThingStatus.ONLINE);

                        double value = (double) arg0.getValue();

                        ChannelUID channel = new ChannelUID(getThing().getUID(), TARGET_TEMPERATURE);
                        updateState(channel, new DecimalType(value));
                    }

                    @Override
                    public void onCancelled(FirebaseError arg0) {
                        // TODO Auto-generated method stub

                    }
                });
    }
}
