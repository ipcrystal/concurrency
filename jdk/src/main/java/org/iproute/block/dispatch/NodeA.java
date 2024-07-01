package org.iproute.block.dispatch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * NodeA
 *
 * @author tech@intellij.io
 * @since 2023-08-28
 */
public class NodeA implements NodeId {
    private final BlockingQueue<RpcReq> send;
    private final BlockingQueue<RcpResp> receive;

    @Override
    public int getId() {
        return 1;
    }

    public NodeA(int sendCap, int receiveCap) {
        this.send = new LinkedBlockingDeque<>(sendCap);
        this.receive = new LinkedBlockingDeque<>(receiveCap);
    }


}
