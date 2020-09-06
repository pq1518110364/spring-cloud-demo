package proxypattern.statics;

/**
 * @author xiangwei
 * @date 2020-08-14 3:28 下午
 */
//复制代码在该类中会持有一个被代理人的引用，在这里指小明，可以看到房产中介所执行的方法的实质就是简单的调用被代理人中的方法，下面来看看Client中具体的执行关系：
//客户端，即Client角色
public class Client {
    public static void main(String[] args){
        //小明想租房
        XiaoMing xiaoMing = new XiaoMing();
        //找一个代理人，房产中介
        RoomAgency roomAgency = new RoomAgency(xiaoMing);
        //房产中介找房
        roomAgency.watchRoom();
        //房产中介看房
        roomAgency.seekRoom();
        //房产中介租房
        roomAgency.room();
        //房产中介完成租房
        roomAgency.finish();
    }
}
