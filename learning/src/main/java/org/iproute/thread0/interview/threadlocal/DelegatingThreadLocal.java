package org.iproute.thread0.interview.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * DelegatingThreadLocal
 * <p>
 * <a href="https://www.bilibili.com/video/BV1US421P7vt">https://www.bilibili.com/video/BV1US421P7vt</a>
 *
 * @author tech@intellij.io
 */
public class DelegatingThreadLocal<T> extends ThreadLocal<T> {
    private static ThreadLocal<Map<DelegatingThreadLocal<Object>, Object>> holder;
    private static ThreadLocal<Map<DelegatingThreadLocal<Object>, Object>> holder1;

    static {
        holder = new ThreadLocal<>();
        holder.set(new HashMap<>());
    }

    @Override
    public final void set(T value) {
        holder.get().put((DelegatingThreadLocal<Object>) this, value);
    }

    @Override
    public T get() {
        return (T) holder.get().get(this);
    }


    public static Map<DelegatingThreadLocal<Object>, Object> copyFrom() {
        Map<DelegatingThreadLocal<Object>, Object> contextMap = holder.get();
        Map<DelegatingThreadLocal<Object>, Object> snapshot = new HashMap<>();
        contextMap.forEach(snapshot::put);
        return snapshot;
    }

    public static void copy(Map<DelegatingThreadLocal<Object>, Object> holderFromRunnable) {

        Map<DelegatingThreadLocal<Object>, Object> contextMap = holder.get();

        if (contextMap == null || contextMap.isEmpty()) {
            holder.set(holderFromRunnable);
            return;
        }

        contextMap.forEach((key, value) -> {
            if (holderFromRunnable.containsKey(key)) {
                holderFromRunnable.put(key, value);
            }
        });

    }

}
