package com.zds.gateway.config;

public enum ServiceEnum {

    service_user("service-user"),
    service_file("service-file"),
    service_flow("service-flow");

    private final String serviceName;

    ServiceEnum(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public static ServiceEnum query(String key){
        if (key != null && !key.isEmpty()) {
            ServiceEnum[] values = ServiceEnum.values();
            for (ServiceEnum result : values) {
                if (result.getServiceName().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
