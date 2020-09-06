package proxypattern.dynamic;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import proxypattern.statics.IRoom;
import proxypattern.statics.XiaoMing;

import java.lang.reflect.Method;

/**
 * @author xiangwei
 * @date 2020-08-14 3:58 下午
 */
public class CglibLogProxyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(" cglib dynamic proxy log begin ");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println(" cglib dynamic proxy log begin ");
        return result;
    }

    /**
     * 动态创建代理
     *
     * @param cls 委托类
     * @return
     */
    public static <T> T createProxy(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new CglibLogProxyInterceptor());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        IRoom proxy = CglibLogProxyInterceptor.createProxy(XiaoMing.class);
        proxy.finish();
    }
}
