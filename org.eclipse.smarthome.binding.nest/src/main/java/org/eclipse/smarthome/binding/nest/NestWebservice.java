package org.eclipse.smarthome.binding.nest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;

public class NestWebservice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final NestWebservice INSTANCE = new NestWebservice();

    private final Firebase client;

    private NestWebservice() {
        client = new Firebase("wss://developer-api.nest.com/");
        client.authWithCustomToken(NestBindingConstants.ACCESS_TOKEN, new AuthResultHandler() {

            @Override
            public void onAuthenticationError(FirebaseError arg0) {
                logger.error("Error while auth: {}", arg0.getMessage());
            }

            @Override
            public void onAuthenticated(AuthData arg0) {
                logger.info("Auth successful");
            }
        });
    }

    public Firebase getClient() {
        return client;
    }

}
