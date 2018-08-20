package mongoose.activities.backend.events;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import mongoose.activities.bothends.generic.organizationdependent.OrganizationDependentGenericTablePresentationModel;

/**
 * @author Bruno Salmon
 */
final class EventsPresentationModel extends OrganizationDependentGenericTablePresentationModel {

    // Display input

    private final Property<Boolean> withBookingsProperty = new SimpleObjectProperty<>(true); // Limit initially set to true
    Property<Boolean> withBookingsProperty() { return withBookingsProperty; }

}
