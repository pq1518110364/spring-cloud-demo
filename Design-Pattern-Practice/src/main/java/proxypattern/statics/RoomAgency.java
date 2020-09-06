package proxypattern.statics;

/**
 * @author xiangwei
 * @date 2020-08-14 3:27 下午
 */
//代理人，房产中介，即ProxySubject角色
public class RoomAgency implements IRoom {

    private IRoom mRoom;//持有一个被代理人（小明）的引用

    public RoomAgency(IRoom room){
        this.mRoom = room;
    }

    @Override
    public void seekRoom() {
        mRoom.seekRoom();
    }

    @Override
    public void watchRoom() {
        mRoom.watchRoom();
    }

    @Override
    public void room() {
        mRoom.room();
    }

    @Override
    public void finish() {
        mRoom.finish();
    }
}


