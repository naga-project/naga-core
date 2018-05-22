package mongoose.operations.shared.route;

import mongoose.activities.shared.book.fees.FeesRouting;
import naga.framework.operations.route.RoutePushRequest;
import naga.platform.client.url.history.History;

/**
 * @author Bruno Salmon
 */
public class RouteToFeesRequest extends RoutePushRequest {

    public RouteToFeesRequest(Object eventId, History history) {
        super(FeesRouting.getFeesPath(eventId), history);
    }

}
