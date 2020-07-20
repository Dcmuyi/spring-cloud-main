package generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @desc 自动生成代码类
 * @date 2019/11/4.
 */
@Slf4j
public class CodeGenerator {
    public static void main(String[] args) {
        //配置项
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/dc?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String userName = "root";
        String password = "muyi123";
        String parentPack = "com.muyi.servicestudy";        //包路径
        String outPutDir = "/service-study/src/main/java";        //项目输出路径
        String module = "muyi";        //模块名
        String tableStr = "album";        //表名

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        log.info("project path:"+projectPath);
        gc.setOutputDir(projectPath + outPutDir);
        gc.setAuthor("Muyi, dcmuyi@qq.com");
        gc.setOpen(false);
        gc.setFileOverride(true);    //是否覆盖原文件

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(userName);
        dsc.setPassword(password);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(module);
        pc.setParent(parentPack);
        pc.setEntity("entity."+module);
        pc.setMapper("mapper."+module);
        pc.setService("service."+module);
        pc.setServiceImpl("service."+module);
        pc.setController("controller."+module);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.muyi.servicestudy.mapper.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("com.muyi.servicestudy.controller.BaseController");
        // 写于父类中的公共字段：base entity中属性
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableStr.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置会被优先输出
        /*focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });*/

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        //全局配置
        mpg.setGlobalConfig(gc);
        //数据库配置
        mpg.setDataSource(dsc);
        //包配置
        mpg.setPackageInfo(pc);
        //自定义配置
        mpg.setCfg(cfg);
        //配置模板
        mpg.setTemplate(templateConfig);
        //策略配置
        mpg.setStrategy(strategy);
        //模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}