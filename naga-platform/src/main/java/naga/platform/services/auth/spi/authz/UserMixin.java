package naga.platform.services.auth.spi.authz;

import naga.util.async.Future;

/**
 * @author Bruno Salmon
 */
public interface UserMixin extends User {

    User getUser();

    default Future<Boolean> isAuthorized(Object operationAuthorizationRequest) {
        return getUser().isAuthorized(operationAuthorizationRequest);
    }
}
