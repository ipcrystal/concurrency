package org.iproute.block.dispatch;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * BlockQueueDispatch
 *
 * @author tech@intellij.io
 * @since 2023-08-28
 */
public class BlockQueueDispatch {
    private final BlockingDeque<RpcReq> requests;
    private final BlockingDeque<RcpResp> responses;

    public BlockQueueDispatch(int reqQueueSize, int respQueueSize) {
        this.requests = new LinkedBlockingDeque<>(reqQueueSize);
        this.responses = new LinkedBlockingDeque<>(respQueueSize);
    }

    public BlockQueueDispatch() {
        this(10, 10);
    }

    // todo
}
