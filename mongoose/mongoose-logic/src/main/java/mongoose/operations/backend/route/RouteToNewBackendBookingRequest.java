package mongoose.operations.backend.route;

import naga.framework.operation.HasOperationCode;
import naga.framework.operations.route.RouteHistoryRequest;
import naga.platform.client.url.history.History;
import naga.util.async.AsyncFunction;

/**
 * @author Bruno Salmon
 */
public class RouteToNewBackendBookingRequest extends RouteHistoryRequest<RouteToNewBackendBookingRequest>
        implements HasOperationCode {

    private final static String OPERATION_CODE = "NEW_BACKEND_BOOKING";

    private Object eventId;

    public RouteToNewBackendBookingRequest(Object eventId, History history) {
        super(history);
        this.eventId = eventId;
    }

    @Override
    public Object getOperationCode() {
        return OPERATION_CODE;
    }

    public Object getEventId() {
        return eventId;
    }

    public RouteToNewBackendBookingRequest setEventId(Object eventId) {
        this.eventId = eventId;
        return this;
    }

    @Override
    public AsyncFunction<RouteToNewBackendBookingRequest, Void> getOperationExecutor() {
        return RouteToNewBackendBookingExecutor::executeRequest;
    }

}
