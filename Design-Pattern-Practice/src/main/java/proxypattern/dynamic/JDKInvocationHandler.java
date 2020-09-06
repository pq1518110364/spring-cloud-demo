package proxypattern.dynamic;

import proxypattern.statics.IRoom;
import proxypattern.statics.XiaoMing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiangwei
 * @date 2020-08-14 3:42 下午
 */
public class JDKInvocationHandler implements InvocationHandler {

    private Object targetObject;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 增强逻辑
        System.out.println("PROXY : " + proxy.getClass().getName());
        // 反射调用，目标方法
        Object result = method.invoke(targetObject, args);

        // 增强逻辑
        System.out.println(method.getName() + " : " + result);

        return result;
    }

    /**
     * 根据委托类动态产生代理类
     * @param targetObject 委托类对象
     * @return 代理类
     */
    public Object createPorxy(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader()
                ,targetObject.getClass().getInterfaces(),this);
    }

    public static void main(String[] args) {
        JDKInvocationHandler JDKInvocationHandler =new JDKInvocationHandler();
        IRoom xiaoMing = (IRoom) JDKInvocationHandler.createPorxy(new XiaoMing());
        xiaoMing.watchRoom();
    }
}
