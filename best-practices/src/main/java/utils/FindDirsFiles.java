package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author xiangwei
 * @date 2020-08-25 10:41 上午
 */
public class FindDirsFiles extends RecursiveAction {

    /**
     * 当前任务需要搜寻的目录
     */
    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    public static void main(String [] args){
        try {
            // 用一个 ForkJoinPool 实例调度总任务
            ForkJoinPool pool = new ForkJoinPool();
            FindDirsFiles task = new FindDirsFiles(new File("/Users/xiangwei"));

            //异步调用
            pool.execute(task);

            System.out.println("Task is Running......");
            Thread.sleep(1);
            int otherWork = 0;
            for(int i=0;i<1000000;i++){
                otherWork = otherWork+i;
            }
            System.out.println("Main Thread done sth......,otherWork=" + otherWork);
            //阻塞的方法
            task.join();
            System.out.println("Task end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void compute() {

        List<FindDirsFiles> subTasks = new ArrayList<>();

        File[] files = path.listFiles();
        if(files!=null) {
            for(File file:files) {
                if(file.isDirectory()) {
                    subTasks.add(new FindDirsFiles(file));
                }else {
                    //遇到文件，检查
                    if(file.getAbsolutePath().endsWith("txt")) {
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }
            if(!subTasks.isEmpty()) {
                for (FindDirsFiles subTask : invokeAll(subTasks)) {
                    //等待子任务执行完成
                    subTask.join();
                }
            }
        }

    }
}
