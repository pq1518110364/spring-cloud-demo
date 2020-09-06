//package utils;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.system.integration.callcenter.base.DataDict;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/29 11:43
// * @Description:
// */
//@Slf4j
//public class DataDictTreeUtil{
//
//
////    private DataDictTreUtil(){
////        if(InnerClass.dictTreUtil != null){
////            throw new RuntimeException("单例构造器禁止反射调用");
////        }
////    }
////
////    private static class InnerClass{
////        private static DataDictTreUtil dictTreUtil = new DataDictTreUtil();
////    }
////
////    public static DataDictTreUtil getInstance(){
////        return InnerClass.dictTreUtil;
////    }
//
//
//    public static List<DataDict> rootNodes = Lists.newArrayList();
//    public static List<DataDict> nodes = Lists.newArrayList();
//    /**
//     * 构建树形结构
//     */
//    public static void buildTree(List<DataDict> dataDicts) {
//        log.info("数据字典树初始化...");
//        List<DataDict> root = getRootNodes(dataDicts);
//        Set<String> idSet= Sets.newHashSet();
//        for (DataDict rootNode : root) {
//            buildChildNodes(rootNode,dataDicts,idSet);
//        }
//        rootNodes.addAll(new ArrayList<>(root));
//        nodes.addAll(new ArrayList<>(dataDicts));
//    }
//
//    /**
//     * 递归子节点
//     */
//    private static void buildChildNodes(DataDict rootNode,List<DataDict> treeNodes,Set<String> idSet) {
//        LinkedList<DataDict> child = Lists.newLinkedList();
//        //id过滤
//        treeNodes.stream().filter(c ->!idSet.contains(c.getId()))
//                //父子过滤
//                .filter(c ->rootNode.getId().equalsIgnoreCase(c.getIdParent()))
//                .filter(c ->idSet.size()<=treeNodes.size())
//                .forEach(c ->{
//                    idSet.add(c.getId());
//                    buildChildNodes(c,treeNodes,idSet);
//                    child.add(c);
//                });
//        rootNode.setChildren(child);
//        log.info("rootNode:{}",rootNode);
//    }
//
//    /**
//     * 判断是否为根节点
//     */
//    private static boolean isRootNode(DataDict node) {
//        return BeanUtils.isEmpty(node.getIdParent());
//    }
//
//    /**
//     * 获取集合中所有的根节点
//     */
//    private static List<DataDict> getRootNodes(List<DataDict> nodes) {
//        List<DataDict> rootNodes = new ArrayList<>();
//        for (DataDict n : nodes) {
//            if (isRootNode(n)) {
//                rootNodes.add(n);
//            }
//        }
//        return rootNodes;
//    }
//
//    /**
//     * code获取
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/29 14:54
//     *
//     * @param code code
//     * @return com.hypersmart.system.integration.callcenter.base.DataDict
//     */
//    public static DataDict getByCode(String code){
//        log.info("code数据字典:{}",code);
//        if (BeanUtils.isEmpty(code)){
//            return null;
//        }
//        for (DataDict n: nodes) {
//            if (code.equalsIgnoreCase(n.getCode())){
//                log.info("code数据字典:{}",n);
//                return n;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * id获取
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/29 14:54
//     * @param id 主键
//     * @return com.hypersmart.system.integration.callcenter.base.DataDict
//     */
//    public static DataDict getById(String id){
//        log.info("id数据字典:{}",id);
//        if (BeanUtils.isEmpty(id)){
//            return null;
//        }
//        for (DataDict n: nodes) {
//            if (id.equalsIgnoreCase(n.getId())){
//                log.info("id数据字典:{}",n);
//                return n;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 取子节点以及本身
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/29 15:50
//     *
//     * @param id id
//     * @param itself 是否包含本身
//     * @return java.util.List<com.hypersmart.system.integration.callcenter.base.DataDict>
//     */
//    public static List<DataDict> getChildById(String id,boolean itself){
//        List<DataDict> list = Lists.newLinkedList();
//        if (BeanUtils.isEmpty(id)){
//            return list;
//        }
//        DataDict byId = getById(id);
//        if (itself && BeanUtils.isNotEmpty(byId)){
//            list.add(byId);
//        }
//        findChildById(rootNodes,id,list);
//        return list;
//    }
//
//    /**
//     * 获取子节点以及本身
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/29 15:27
//     * @param code code
//     * @param itself 是否包含本身
//     * @return java.util.List<com.hypersmart.system.integration.callcenter.base.DataDict>
//     */
//    public static List<DataDict> getChildByCode(String code,boolean itself){
//        List<DataDict> list = Lists.newLinkedList();
//        if (BeanUtils.isEmpty(code)){
//            return list;
//        }
//         DataDict byCode = getByCode(code);
//        if (itself && BeanUtils.isNotEmpty(byCode)){
//            list.add(byCode);
//        }
//        if (BeanUtils.isEmpty(byCode)){
//            return list;
//        }
//        findChildById(rootNodes,byCode.getId(),list);
//        return list;
//    }
//
//    private static List<DataDict> findChildById(List<DataDict> root,String id,List<DataDict> list){
//        for (DataDict d:root) {
//            if (id.equalsIgnoreCase(d.getIdParent())){
//                list.add(d);
//                findChildById(d.getChildren(),d.getId(),list);
//            }else {
//                findChildById(d.getChildren(),id,list);
//            }
//        }
//        return list;
//    }
//
//
//}
