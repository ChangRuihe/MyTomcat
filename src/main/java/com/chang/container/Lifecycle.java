/*
 * 文 件 名:  Lifecycle.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年2月3日
 */
package com.chang.container;

/**
 * @author 13097
 * @since 2016年2月3日
 */
public abstract interface Lifecycle {

    public static final String INIT_EVENT = "init";
    public static final String START_EVENT = "start";
    public static final String BEFORE_START_EVENT = "before_start";
    public static final String AFTER_START_EVENT = "after_start";
    public static final String STOP_EVENT = "stop";
    public static final String BEFORE_STOP_EVENT = "before_stop";
    public static final String AFTER_STOP_EVENT = "after_stop";
    public static final String DESTROY_EVENT = "destroy";
    public static final String PERIODIC_EVENT = "periodic";

    // public abstract void addLifecycleListener(LifecycleListener paramLifecycleListener);

    // public abstract LifecycleListener[] findLifecycleListeners();

    // public abstract void removeLifecycleListener(LifecycleListener paramLifecycleListener);

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;

}
