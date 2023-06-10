package org.example.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.vo.Blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BlogMapper {

	public Blog selectByDbid(@Param("dbid") int dbid);

	public List<Blog> list();
}
