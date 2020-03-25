package com.bishop.common.model;

import java.util.List;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:50
 * @Description:
 */
public interface Tree {
    String getId();

    String getParentId();

    String getText();

    List<Tree> getChildren();

    void setChildren(List<Tree> var1);

    void setIsParent(String var1);
}
