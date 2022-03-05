package de.dafriedmann.analytics;

import de.dafriedmann.data.Address;
import io.quarkus.arc.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

/**
 * Stub for Analytics: Disable in tests
 */
@Alternative
@Priority(1)
@ApplicationScoped
public class AnalyticsRecorderStub extends AnalyticsRecorder {

    @Override
    public void recordPerson(long personId, Address address) {
        // nothing to do in test here
    }
}
