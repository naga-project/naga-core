package javafx.scene.control.skin;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.Utils;
import dev.webfx.platform.resource.Resource;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import dev.webfx.kit.util.properties.FXProperties;

/**
 * Skin for tri state selection Control.
 */
public class CheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>> {

    private final StackPane box = new StackPane();
    //private StackPane innerbox;

    public CheckBoxSkin(CheckBox checkbox) {
        super(checkbox, new ButtonBehavior<>(checkbox));

        box.getStyleClass().setAll("box");
/*
        innerbox = new StackPane();
        innerbox.getStyleClass().setAll("mark");
        innerbox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
*/

        // Temporary resource image based checkbox
        ImageView innerbox = new ImageView();
        innerbox.imageUrlProperty().bind(FXProperties.compute(checkbox.selectedProperty(), selected -> Resource.toUrl(selected ? "checkbox/checked.png" : "checkbox/unchecked.png", getClass())));
        innerbox.setFitWidth(18d);
        innerbox.setFitHeight(18d);
        StackPane.setMargin(innerbox, new Insets(5));

        box.getChildren().add(innerbox);
        updateChildren();
    }

    @Override protected void updateChildren() {
        super.updateChildren();
        if (box != null) {
            getChildren().add(box);
        }
    }

    @Override protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(box.minWidth(-1));
    }

    @Override protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Math.max(super.computeMinHeight(width - box.minWidth(-1), topInset, rightInset, bottomInset, leftInset),
                topInset + box.minHeight(-1) + bottomInset);
    }

    @Override protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(box.prefWidth(-1));
    }

    @Override protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Math.max(super.computePrefHeight(width - box.prefWidth(-1), topInset, rightInset, bottomInset, leftInset),
                topInset + box.prefHeight(-1) + bottomInset);
    }

    @Override protected void layoutChildren(final double x, final double y,
                                            final double w, final double h) {
        final CheckBox checkBox = getSkinnable();
        final double boxWidth = snapSize(box.prefWidth(-1));
        final double boxHeight = snapSize(box.prefHeight(-1));
        final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
        final double labelWidth = Math.min( computeWidth - boxWidth, w - snapSize(boxWidth));
        final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(boxHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x;
        final double yOffset = Utils.computeYOffset(h, maxHeight, checkBox.getAlignment().getVpos()) + /*x*/ y;

        layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
        box.resize(boxWidth, boxHeight);
        positionInArea(box, xOffset, yOffset, boxWidth, maxHeight, 0, checkBox.getAlignment().getHpos(), checkBox.getAlignment().getVpos());
    }
}