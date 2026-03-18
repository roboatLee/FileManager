package com.lee;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
public class CodeGenerator {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/trustful_platform?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "Lcx0618.";

        try {
            List<String> allModules = getModules();
            System.out.println("当前模块：");
            allModules.forEach(System.out::println);
        }catch (Exception e){
            System.out.println("连接失败");
        }

        String moduleName = scanner("请输入模块名（如 module-user）");
        String projectPath = System.getProperty("user.dir");

        String outputDir = projectPath + "/" + moduleName + "/src/main/java";
        List<String> allTables = new ArrayList<>();

        try {
            allTables = getAllTables(url, username, password);
            System.out.println("当前数据库表：");
            allTables.forEach(System.out::println);
        }catch (Exception e){
            System.out.println("数据库连接失败");
        }

        String tableInput = scanner("请输入表名（all 或 user,order）");

        List<String> tables = "all".equals(tableInput)
                ? allTables
                : Arrays.asList(tableInput.split(","));


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("lee")
                        .outputDir(outputDir)
                )
                .packageConfig(builder -> builder
                        .parent("com.lee")
                )
                .strategyConfig(builder -> builder
                        .addInclude(tables)
                        .mapperBuilder().enableFileOverride()
                        .entityBuilder().enableFileOverride()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    /**
     * 列出所有表
     * */
    public static List<String> getAllTables(String url, String username, String password) {
        List<String> tables = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String catalog = conn.getCatalog();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(catalog, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    //得到Moudules类型
    public static List<String> getModules() {
        String projectPath = System.getProperty("user.dir");
        File root = new File(projectPath);

        File[] files = root.listFiles();

        List<String> modules = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                // 判断是否是目录 + 是否包含 pom.xml
                if (file.isDirectory()) {
                    File pom = new File(file, "pom.xml");
                    if (pom.exists()) {
                        modules.add(file.getName());
                    }
                }
            }
        }
        return modules;
    }

    public static String scanner(String tip) {
        System.out.print(tip + "：");
        if (SCANNER.hasNextLine()) {
            return SCANNER.nextLine();
        }
        throw new RuntimeException("输入不能为空");
    }
}
