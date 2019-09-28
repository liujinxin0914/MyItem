/*package com.message.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

*//**
 * <p>
 * 代码生成器演示
 * </p>
 *
 * <p>
 * MySQL 生成演示
 * </p>
 * 数据库配置这块可以自己手写读取配置文件里的配置项，为了省事我直接copy
 **//*
public class MpGenerator {

    *//**
 * <p>
 * MySQL 生成演示
 * </p>
 *//*
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
    // 选择 freemarker 引擎，默认 Veloctiy
    // mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:/wuxiadong/collect_message/collect_message_model/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setDateType(DateType.ONLY_DATE); //只使用 java.util.date 代替
    // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor("fanxialong");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
         gc.setMapperName("%sMapper");
         gc.setXmlName("%sMapper");
         gc.setServiceName("%sService");
         gc.setServiceImplName("%sServiceimpl");
         mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new OracleTypeConvert() {

			@Override
			public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
				// TODO Auto-generated method stub
				return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
			}
            // 自定义数据库表字段类型转换【可选】
            
        });
        *//**数据库配置这块可以自己手写读取配置文件里的配置项，为了省事我直接copy**//*
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Hwkjit1111.");
        dsc.setUrl("jdbc:mysql://192.168.2.235:3306/collect_message");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "Fs_" });// 此处可以修改为您的表前缀
         strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
         strategy.setInclude(new String[] { "images","student","sys_organize","sys_role","sys_user","teacher","teacher_classes" }); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("com.message.model");
        pc.setMapper("com.message.dao");
        pc.setXml("com.message.dao.mapping");
        pc.setService("com.message.service");
        pc.setServiceImpl("com.message.service.impl");
//        pc.setController("com.message.controller");
        mpg.setPackageInfo(pc);

        mpg.execute();

    }

}*/