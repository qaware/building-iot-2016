package org.eclipse.smarthome.binding.nest.discovery;

import java.util.Collections;

import org.eclipse.smarthome.binding.nest.NestBindingConstants;
import org.eclipse.smarthome.binding.nest.NestWebservice;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingUID;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class NestDiscoveryService extends AbstractDiscoveryService {

    public NestDiscoveryService() {
        super(Collections.singleton(NestBindingConstants.THING_TYPE_SAMPLE), 30);
    }

    @Override
    protected void startScan() {
        NestWebservice.INSTANCE.getClient().child("/devices/thermostats")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildRemoved(DataSnapshot arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onChildMoved(DataSnapshot arg0, String arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onChildChanged(DataSnapshot arg0, String arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onChildAdded(DataSnapshot arg0, String arg1) {
                        String id = (String) arg0.child("device_id").getValue();

                        ThingUID uid = new ThingUID(NestBindingConstants.THING_TYPE_SAMPLE, "thermostat");

                        DiscoveryResult result = DiscoveryResultBuilder.create(uid).withLabel("Nest thermostat")
                                .withProperty("thermostat-id", id).build();

                        thingDiscovered(result);

                    }

                    @Override
                    public void onCancelled(FirebaseError arg0) {
                        // TODO Auto-generated method stub

                    }
                });

    }

}
