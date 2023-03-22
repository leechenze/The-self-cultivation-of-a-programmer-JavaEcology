package com.lee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class Generator {

    public static void main(String[] args) {

        if(true) {
            //1.获取代码生成器的对象
            AutoGenerator autoGenerator = new AutoGenerator();

            //设置数据库相关配置
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
            dataSourceConfig.setUrl("jdbc:mysql:///mybatisplus_db");
            dataSourceConfig.setUsername("root");
            dataSourceConfig.setPassword("lcz19930316");
            autoGenerator.setDataSource(dataSourceConfig);

            //设置全局配置
            GlobalConfig globalConfig = new GlobalConfig();
            globalConfig.setOutputDir(System.getProperty("user.dir")+"/mybatisplus_04_generator/src/main/java");    //设置代码生成的位置
            globalConfig.setOpen(false);    //设置生成完毕后是否打开生成代码所在的目录
            globalConfig.setAuthor("leechenze");    //设置作者
            globalConfig.setFileOverride(true);     //设置是否覆盖原始生成的文件
            globalConfig.setMapperName("%sDao");    //设置数据层接口名，%s为占位符，指代模块名称
            globalConfig.setIdType(IdType.ASSIGN_ID);   //设置Id生成策略
            autoGenerator.setGlobalConfig(globalConfig);

            //设置包名相关配置
            PackageConfig packageInfo = new PackageConfig();
            packageInfo.setParent("com.aaa");   //设置生成的包名，与代码所在位置不冲突，二者叠加组成完整路径 (默认包是 com.baomidou)
            packageInfo.setEntity("domain");    //设置实体类包名，（默认生成的包是 entity）
            packageInfo.setMapper("dao");   //设置数据层包名，（默认生成的包是 mapper）
            autoGenerator.setPackageInfo(packageInfo);

            //策略设置
            StrategyConfig strategyConfig = new StrategyConfig();
            strategyConfig.setInclude("tb_user", "tb_book");  //设置当前参与生成的表名，参数为可变参数，（由于数据库里没有 tb_book这张表，所以不会生成）
            strategyConfig.setTablePrefix("tb_");  //设置数据库表的前缀名称，模块名 = 数据库表名 - 前缀名  例如： User = tbl_user - tbl_
            strategyConfig.setRestControllerStyle(true);    //设置是否启用Rest风格
            strategyConfig.setVersionFieldName("version");  //设置乐观锁字段名
            strategyConfig.setLogicDeleteFieldName("deleted");  //设置逻辑删除字段名
            strategyConfig.setEntityLombokModel(true);  //设置是否启用lombok
            autoGenerator.setStrategy(strategyConfig);
            //2.执行生成操作
            autoGenerator.execute();
        }
    }
}
