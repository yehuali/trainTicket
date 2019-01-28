package com.example.security.core.context;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;

/**
 * 将给定的SecurityContext与当前执行线程关联
 * 这个类提供了一系列的静态方法,这些方法委托给的实例SecurityContextHolderStrategy
 * --->SecurityContextHolderStrategy目的:提供一种方便的方法来指定应该使用的策略用于给定的JVM
 * 这是一个jvm范围的设置，因为这个类中的所有内容都是static，便于调用代码时使用。
 * 要指定应该使用哪种策略，必须提供模式设置
 *  --->一种模式设置是定义为的三个有效的MODE_设置之一tatic final,或一个完全限定的类名实施
 *SecurityContextHolderStrategy提供一个公共的无参数构造函数,有两种方法可以指定所需的策略模式
 *   1.通过键入{@link #SYSTEM_PROPERTY}的系统属性来指定它
 *   2.是在使用类之前调用{@link #setStrategyName(String)}
 *   3.如果没有方法,类将默认使用{@link #MODE_THREADLOCAL}，这是向后的兼容，JVM不兼容性更少，适用于服务器(反之),{@link #MODE_GLOBAL}绝对不适合服务器使用)。
 */
public class SecurityContextHolder {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
    public static final String MODE_GLOBAL = "MODE_GLOBAL";
    public static final String SYSTEM_PROPERTY = "spring.security.strategy";
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);
    private static SecurityContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * Explicitly clears the context value from the current thread.
     */
    public static void clearContext() {
        strategy.clearContext();
    }

    /**
     * Obtain the current <code>SecurityContext</code>.
     *
     * @return the security context (never <code>null</code>)
     */
    public static SecurityContext getContext() {
        return strategy.getContext();
    }

    /**
     * Primarily for troubleshooting purposes, this method shows how many times the class
     * has re-initialized its <code>SecurityContextHolderStrategy</code>.
     *
     * @return the count (should be one unless you've called
     * {@link #setStrategyName(String)} to switch to an alternate strategy.
     */
    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        if (!StringUtils.hasText(strategyName)) {
            // Set default
            strategyName = MODE_THREADLOCAL;
        }

        if (strategyName.equals(MODE_THREADLOCAL)) {
            strategy = new ThreadLocalSecurityContextHolderStrategy();
        }
//        else if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
//            strategy = new InheritableThreadLocalSecurityContextHolderStrategy();
//        }
//        else if (strategyName.equals(MODE_GLOBAL)) {
//            strategy = new GlobalSecurityContextHolderStrategy();
//        }
        else {
            // Try to load a custom strategy
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = (SecurityContextHolderStrategy) customStrategy.newInstance();
            }
            catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }

        initializeCount++;
    }

    /**
     * Associates a new <code>SecurityContext</code> with the current thread of execution.
     *
     * @param context the new <code>SecurityContext</code> (may not be <code>null</code>)
     */
    public static void setContext(SecurityContext context) {
        strategy.setContext(context);
    }

    /**
     * Changes the preferred strategy. Do <em>NOT</em> call this method more than once for
     * a given JVM, as it will re-initialize the strategy and adversely affect any
     * existing threads using the old strategy.
     *
     * @param strategyName the fully qualified class name of the strategy that should be
     * used.
     */
    public static void setStrategyName(String strategyName) {
        SecurityContextHolder.strategyName = strategyName;
        initialize();
    }

    /**
     * Allows retrieval of the context strategy. See SEC-1188.
     *
     * @return the configured strategy for storing the security context.
     */
    public static SecurityContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    /**
     * Delegates the creation of a new, empty context to the configured strategy.
     */
    public static SecurityContext createEmptyContext() {
        return strategy.createEmptyContext();
    }

    public String toString() {
        return "SecurityContextHolder[strategy='" + strategyName + "'; initializeCount="
                + initializeCount + "]";
    }
}

