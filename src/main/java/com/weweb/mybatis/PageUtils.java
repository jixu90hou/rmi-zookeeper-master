package com.weweb.mybatis;

/**
 * Created by wshen on 6/8/2017.
 */

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.Configuration;
public class PageUtils extends Configuration{
    private Integer start;
    private Integer pageSize;
    private Long total;

    public PageUtils(Integer start, Integer pageSize) {
        this.start = start;
        this.pageSize = pageSize;
    }

    private void splitPage() {
    }

}

