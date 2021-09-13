package com.study.community.cache;

import com.study.community.dto.TagDTO;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO>  tagDTOs = new ArrayList<>();
        TagDTO program=new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","java","python","C++","JavaScript","html5","node.js","shell","asp.net"));
        tagDTOs.add(program);

        TagDTO framework=new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","django","express","yii","koa","struts"));
        tagDTOs.add(framework);

        TagDTO server=new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","tomcat","unix","windows","centos","ubuntu","apache"));
        tagDTOs.add(server);

        TagDTO db=new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","oracle","ms sqlserver","h2","redis","sqllite"));
        tagDTOs.add(db);

        TagDTO devTool=new TagDTO();
        devTool.setCategoryName("开发工具");
        devTool.setTags(Arrays.asList("git","github","vim","eclipse","maven","idea","svn","xcode"));
        tagDTOs.add(devTool);
        return tagDTOs;
    }

    public static String filterisValid(String tags){
        String[] split= StringUtils.split(tags,",");
        List<TagDTO> tagDTOS=get();
        List<String> tagList= tagDTOS.stream().flatMap(tagDTO ->tagDTO.getTags().stream()).collect(Collectors.toList());
        //拿到不在列表中的列表
        String invalid = Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
