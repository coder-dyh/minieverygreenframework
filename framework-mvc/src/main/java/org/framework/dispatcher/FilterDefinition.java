package org.framework.dispatcher;

public class FilterDefinition {

    private Class<?> clazz;
    private String requestMapName;
    private int order;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getRequestMapName() {
        return requestMapName;
    }

    public void setRequestMapName(String requestMapName) {
        this.requestMapName = requestMapName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
