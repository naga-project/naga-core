package naga.fxdata.displaydata;

import javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */

public interface HasDisplayResultProperty {

    Property<DisplayResult> displayResultProperty();
    default void setDisplayResult(DisplayResult displayResult) { displayResultProperty().setValue(displayResult); }
    default DisplayResult getDisplayResult() { return displayResultProperty().getValue(); }

}
