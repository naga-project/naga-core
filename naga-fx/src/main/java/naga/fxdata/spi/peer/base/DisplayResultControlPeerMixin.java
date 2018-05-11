package naga.fxdata.spi.peer.base;

import naga.fx.spi.peer.base.ControlPeerMixin;
import naga.fxdata.displaydata.DisplayResult;
import naga.fxdata.control.DisplayResultControl;

/**
 * @author Bruno Salmon
 */
public interface DisplayResultControlPeerMixin
        <C, N extends DisplayResultControl, NB extends DisplayResultControlPeerBase<C, N, NB, NM>, NM extends DisplayResultControlPeerMixin<C, N, NB, NM>>

        extends ControlPeerMixin<N, NB, NM> {

    void updateResult(DisplayResult rs);

}
