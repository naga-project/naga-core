package mongoose.activities.frontend.cart;

import naga.framework.orm.entity.Entity;
import naga.framework.ui.filter.ReactiveExpressionFilter;
import naga.framework.ui.presentation.PresentationActivity;
import naga.toolkit.fxdata.control.DataGrid;
import naga.toolkit.fx.scene.layout.VBox;

/**
 * @author Bruno Salmon
 */
public class CartActivity extends PresentationActivity<CartViewModel, CartPresentationModel> {

    public CartActivity() {
        super(CartPresentationModel::new);
    }

    @Override
    protected CartViewModel buildView() {
        // Building the UI components
        DataGrid documentTable = new DataGrid();
        DataGrid documentLineTable = new DataGrid();
        DataGrid paymentTable = new DataGrid();

        // Displaying the UI
        return new CartViewModel(new VBox(documentTable, documentLineTable, paymentTable),
                documentTable, documentLineTable, paymentTable);
    }

    @Override
    protected void bindViewModelWithPresentationModel(CartViewModel vm, CartPresentationModel pm) {
        // Binding the UI with the presentation model for further state changes
        // User inputs: the UI state changes are transferred in the presentation model
        vm.getDocumentTable().displaySelectionProperty().bindBidirectional(pm.documentDisplaySelectionProperty());
        // User outputs: the presentation model changes are transferred in the UI
        vm.getDocumentTable().displayResultSetProperty().bind(pm.documentDisplayResultSetProperty());
        vm.getDocumentLineTable().displayResultSetProperty().bind(pm.documentLineDisplayResultSetProperty());
        vm.getPaymentTable().displayResultSetProperty().bind(pm.paymentDisplayResultSetProperty());
    }

    @Override
    protected void initializePresentationModel(CartPresentationModel pm) {
        pm.cartUuidProperty().setValue(getParameter("cartUuid"));
    }

    @Override
    protected void bindPresentationModelWithLogic(CartPresentationModel pm) {
        // Setting up the documents filter
        ReactiveExpressionFilter documentFilter = createReactiveExpressionFilter("{class: 'Document', orderBy: 'creationDate desc'}")
                // Condition
                .combine(pm.cartUuidProperty(), s -> "{where: 'cart.uuid=`" + s + "`'}")
                //.registerParameter(new Parameter("cartUuid", "constant"))
                //.registerParameter(new Parameter("cartUuid", pm.cartUuidProperty()))
                //.combine("{where: 'cart.uuid=?cartUuid'}")
                .setExpressionColumns("[" +
                        "'ref'," +
                        "'person_firstName'," +
                        "'person_lastName'," +
                        "{expression: 'price_net', format: 'price'}," +
                        "{expression: 'price_deposit', format: 'price'}," +
                        "{expression: 'price_balance', format: 'price'}" +
                        "]")
                .applyDomainModelRowStyle()
                .displayResultSetInto(pm.documentDisplayResultSetProperty())
                .selectFirstRowOnFirstDisplay(pm.documentDisplaySelectionProperty(), pm.cartUuidProperty())
                .start();

        // Setting up the document lines filter
        createReactiveExpressionFilter("{class: 'DocumentLine', where: 'item.family.code!=`round`', orderBy: 'item.family.ord,item.ord'}")
                // Condition
                .combine(pm.cartUuidProperty(), s -> "{where: 'document.cart.uuid=`" + s + "`'}")
                .combine(documentFilter.getDisplaySelectionProperty(), displaySelection -> {
                    Entity selectedEntity = documentFilter.getSelectedEntity();
                    return selectedEntity == null ? "{where: 'false'}" : "{where: 'document=" + selectedEntity.getPrimaryKey() + "'}";
                })
                //.combine("{where: 'document=?documentDisplaySelection'}")
                .setExpressionColumns("[" +
                        "{expression: 'site.name', label: 'Site'}," +
                        "{expression: 'item.name', label: 'Item'}," +
                        "'dates'," +
                        "{expression: 'price_net', label: 'Fees', format: 'price'}" +
                        "]")
                .applyDomainModelRowStyle()
                .displayResultSetInto(pm.documentLineDisplayResultSetProperty())
                .start();

        // Setting up the payments filter
        createReactiveExpressionFilter("{class: 'MoneyTransfer', orderBy: 'date'}")
                // Condition
                .combine(pm.cartUuidProperty(), s -> "{where: 'document.cart.uuid=`" + s + "`'}")
                //.combine("{where: 'document.cart.uuid=?cartUuid'}")
                .setExpressionColumns("[" +
                        "{expression: 'date', format: 'dateTime'}," +
                        "{expression: 'document.ref', label: 'Booking ref'}," +
                        "{expression: 'method.name', label: 'Method'}," +
                        "{expression: 'amount', format: 'price'}," +
                        "{expression: 'pending ? `Pending` : successful ? `Success` : `Failed`', label: 'Status'}" +
                        "]")
                .applyDomainModelRowStyle()
                .displayResultSetInto(pm.paymentDisplayResultSetProperty())
                .start();
    }
}
