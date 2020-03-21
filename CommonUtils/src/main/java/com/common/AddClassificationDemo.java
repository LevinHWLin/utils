package com.common;

import java.util.HashMap;
import java.util.Map;

public class AddClassificationDemo {

    /**
     * 技术
     */
    private static Map<String,String> typeMap = new HashMap<String,String>();
    /**
     * 地区
     */
    private static Map<String,String> regionMap = new HashMap<String,String>();
    /**
     * 季节
     */
    private static Map<String,String> seasonMap = new HashMap<String,String>();
    /**
     * 生育期
     */
    private static Map<String,String> growthPeriodMap = new HashMap<String,String>();
    /**
     * 类型
     */
    private static Map<String,String> plantTypeMap = new HashMap<String,String>();

    public static void main(String[] args){

        // 初始化基本数据
        initData();
        String parCode = "00010002";
        String classCode = "";
        String attribute = "";
        String insertSql = "";
        StringBuilder builder = new StringBuilder();

        classCode = "000100020015";
        builder.append("-- 分类：西瓜 ").append(classCode);
        builder.append("\r\n");
        insertSql = initInsertClassSql(classCode, "西瓜", parCode, "xigua", "10", "XG");
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "黑美人、京欣系列、早春红玉、西农8号、硒砂瓜、小麒麟、早佳8424、特小凤、小兰、京秀、甜王、新红宝、春光、黑金刚、春雷、冠龙、豫艺系列、绿宝系列、无籽西瓜";
        builder.append("-- 西瓜-品种：").append(attribute).append("\r\n");
        insertSql = initInsertBreadsSql(classCode, attribute,50000);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "露天栽培、设施栽培、高架基质、绿色有机";
        builder.append("-- 西瓜-类型：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, plantTypeMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "内蒙古、山东、贵州、湖南、海南、广西、湖北、安徽、浙江、江苏、河南、甘肃、山西、四川、河北、陕西、辽宁、吉林、黑龙江、江西";
        builder.append("-- 西瓜-地区：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, regionMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "选种育苗、建园改造、土壤改良、保花保果、综合管理、营养施肥、整形修剪、病害防治、虫害防治、温湿光气、采后管理";
        builder.append("-- 西瓜-技术：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, typeMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "育苗期、定植期、伸蔓期、开花期、膨大期、成熟期、采收期、采收后期";
        builder.append("-- 西瓜-生育期：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, growthPeriodMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");
        builder.append("\r\n").append("\r\n");

        classCode = "000100020016";
        builder.append("-- 分类：辣椒 ").append(classCode);
        builder.append("\r\n");
        insertSql = initInsertClassSql(classCode, "辣椒", parCode, "lajiao", "11", "LJ");
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "线椒，牛角椒、羊角椒、圆椒、尖椒、朝天椒、泡椒、甜椒、灯笼椒、指型椒、螺丝椒、彩椒";
        builder.append("-- 辣椒-品种：").append(attribute).append("\r\n");
        insertSql = initInsertBreadsSql(classCode, attribute,60000);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "露天栽培、设施栽培、高架基质、绿色有机";
        builder.append("-- 辣椒-类型：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, plantTypeMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "内蒙古、山东、贵州、湖南、海南、四川、河北、陕西";
        builder.append("-- 辣椒-地区：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, regionMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "选种育苗、建园改造、土壤改良、保花保果、综合管理、营养施肥、整形修剪、病害防治、虫害防治、温湿光气、采后管理";
        builder.append("-- 辣椒-技术：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, typeMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "育苗期、定植期、生长期、开花期、膨大期、成熟期、采收期、采收后期";
        builder.append("-- 辣椒-生育期：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, growthPeriodMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");
        builder.append("\r\n").append("\r\n");


        classCode = "000100020017";
        builder.append("-- 分类：苹果 ").append(classCode);
        builder.append("\r\n");
        insertSql = initInsertClassSql(classCode, "苹果", parCode, "pingguo", "12", "PG");
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "富士、嘎啦";
        builder.append("-- 苹果-品种：").append(attribute).append("\r\n");
        insertSql = initInsertBreadsSql(classCode, attribute,70000);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "山东、四川、陕西、甘肃、云南、新疆";
        builder.append("-- 苹果-地区：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, regionMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        attribute = "建园改造、砧木嫁接、营养施肥、整形修剪、病害防治、虫害防治";
        builder.append("-- 苹果-技术：").append(attribute).append("\r\n");
        insertSql = initInsertAttributeSql(classCode, attribute, typeMap);
        builder.append(insertSql);
        builder.append("\r\n").append("\r\n");

        System.out.println(builder.toString());
    }

    /**
     * 新增分类
     * @param classCode
     * @param className
     * @param parCode
     * @param desc
     * @param orderNum
     * @param dictCde
     * @return
     */
    private static String initInsertClassSql(String classCode, String className, String parCode, String desc, String orderNum,String dictCde){
        String insertSql = "INSERT INTO `t_common_dict` (`id`, `code`, `par_code`, `name`, `img`, `desc`, `state`, `order_num`, `dict_code`, `create_time`, `create_user`, `alter_time`, `alter_user`, `deleted`)";
        StringBuilder builder = new StringBuilder();
        builder.append(insertSql).append(" VALUES (");
        builder.append("'").append(classCode).append("',");
        builder.append("'").append(classCode).append("',");
        builder.append("'").append(parCode).append("',");
        builder.append("'").append(className).append("',");
        builder.append("'',");
        builder.append("'").append(desc).append("',");
        builder.append("'1',");
        builder.append("'").append(orderNum).append("',");
        builder.append("'").append(dictCde).append("',");
        builder.append("NOW(), '脚本导入', NOW(), '脚本导入', '0');");
        return builder.toString();
    }

    /**
     * 新增子品种
     * @param parCode
     * @param bread
     * @return
     */
    private static String initInsertBreadsSql(String parCode, String bread,int codeIndex) {
        StringBuilder builder = new StringBuilder();
        if(null == bread || "" == bread){
            return "";
        }

        String[] breads = bread.split("、");

        String insertSql = "INSERT INTO `t_common_dict` (`id`, `code`, `par_code`, `name`, `img`, `desc`, `state`, `order_num`, `dict_code`, `create_time`, `create_user`, `alter_time`, `alter_user`, `deleted`) ";
        String defaultCode = "00010002001";
        for (int i = 0; i < breads.length; i++) {

            String code = defaultCode + (codeIndex + i + 1);
            String name = breads[i];

            builder.append(insertSql).append(" VALUES (");
            builder.append("'").append(code).append("',");
            builder.append("'").append(code).append("',");
            builder.append("'").append(parCode).append("',");
            builder.append("'").append(name).append("',");
            builder.append("'',");
            builder.append("NULL,");
            builder.append("1,");
            builder.append("'").append(i + 1).append("',");
            builder.append("'',");
            builder.append("NOW(), '脚本导入', NOW(), '脚本导入', '0');");
            builder.append("\r\n");
        }
        return builder.toString();
    }

    /**
     * 关联属性
     *
     * @param classification
     * @param key
     * @return
     */
    private static String initInsertAttributeSql(String classification, String key, Map<String, String> propertyMap) {
        StringBuilder builder = new StringBuilder();
        if (null == key || "" == key) {
            return "";
        }

        String[] keys = key.split("、");

        String insertSql = "INSERT INTO `r_dict_relate` (`id`, `classification`, `property`, `recommend`, `order_num`, `create_time`, `create_user`, `alter_time`, `alter_user`, `deleted`) ";

        for (int i = 0; i < keys.length; i++) {
            String name = keys[i];
            String property = propertyMap.get(name);

            builder.append(insertSql).append(" VALUES (");
            builder.append("replace(uuid(),\"-\",\"\")").append(",");
            builder.append("'").append(classification).append("',");
            builder.append("'").append(property).append("',");
            builder.append("'',");
            builder.append("'").append(i + 1).append("',");
            builder.append("NOW(), '脚本导入', NOW(), '脚本导入', '0');");
            builder.append("\r\n");
        }
        return builder.toString();
    }

    /**
     * 初始化数据
     *  技术：select CONCAT('typeMap.put("',`name`,'","',`code`,'");') from t_common_dict where `par_code`='00060001';
     *  地区：select CONCAT('regionMap.put("',`name`,'","',`code`,'");') from t_common_dict where `par_code`='00060002';
     *  季节：select CONCAT('seasonMap.put("',`name`,'","',`code`,'");') from t_common_dict where `par_code`='00060003';
     *  生育期：select CONCAT('growthPeriodMap.put("',`name`,'","',`code`,'");') from t_common_dict where `par_code`='00060004';
     *  类型：select CONCAT('plantTypeMap.put("',`name`,'","',`code`,'");') from t_common_dict where `par_code`='00060006';;
     */
    private static void initData(){
        // 技术
        typeMap.put("选种育苗","000600010001");
        typeMap.put("砧木嫁接","000600010002");
        typeMap.put("整形修剪","000600010003");
        typeMap.put("营养施肥","000600010004");
        typeMap.put("病害防治","000600010005");
        typeMap.put("虫害防治","000600010006");
        typeMap.put("疑难杂症","000600010007");
        typeMap.put("建园改造","000600010008");
        typeMap.put("环境管理","000600010009");
        typeMap.put("疏花修穗","000600010010");
        typeMap.put("催花技术","000600010011");
        typeMap.put("疏花疏果","000600010012");
        typeMap.put("综合管理","000600010013");
        typeMap.put("土壤改良","000600010014");
        typeMap.put("保花保果","000600010015");
        typeMap.put("温湿光气","000600010016");
        typeMap.put("采后管理","000600010017");

        // 地区
        regionMap.put("北京","000600020001");
        regionMap.put("上海","000600020002");
        regionMap.put("天津","000600020003");
        regionMap.put("重庆","000600020004");
        regionMap.put("河北","000600020005");
        regionMap.put("山西","000600020006");
        regionMap.put("内蒙古","000600020007");
        regionMap.put("辽宁","000600020008");
        regionMap.put("吉林","000600020009");
        regionMap.put("黑龙江","000600020010");
        regionMap.put("江苏","000600020011");
        regionMap.put("浙江","000600020012");
        regionMap.put("安徽","000600020013");
        regionMap.put("福建","000600020014");
        regionMap.put("江西","000600020015");
        regionMap.put("山东","000600020016");
        regionMap.put("河南","000600020017");
        regionMap.put("湖北","000600020018");
        regionMap.put("湖南","000600020019");
        regionMap.put("广东","000600020020");
        regionMap.put("广西","000600020021");
        regionMap.put("海南","000600020022");
        regionMap.put("四川","000600020023");
        regionMap.put("贵州","000600020024");
        regionMap.put("云南","000600020025");
        regionMap.put("西藏","000600020026");
        regionMap.put("陕西","000600020027");
        regionMap.put("甘肃","000600020028");
        regionMap.put("青海","000600020029");
        regionMap.put("宁夏","000600020030");
        regionMap.put("新疆","000600020031");
        regionMap.put("香港","000600020032");
        regionMap.put("全国","000600020033");

        // 季节
        seasonMap.put("春季","000600030001");
        seasonMap.put("夏季","000600030002");
        seasonMap.put("秋季","000600030003");
        seasonMap.put("冬季","000600030004");
        seasonMap.put("全年","000600030005");

        // 生育期
        growthPeriodMap.put("春梢期","000600040001");
        growthPeriodMap.put("开花期","000600040002");
        growthPeriodMap.put("幼果期","000600040003");
        growthPeriodMap.put("夏梢期","000600040004");
        growthPeriodMap.put("膨大期","000600040005");
        growthPeriodMap.put("秋梢期","000600040006");
        growthPeriodMap.put("成熟期","000600040007");
        growthPeriodMap.put("采收期","000600040008");
        growthPeriodMap.put("休眠期","000600040009");
        growthPeriodMap.put("硬核期","000600040010");
        growthPeriodMap.put("定植期","000600040011");
        growthPeriodMap.put("生长期","000600040012");
        growthPeriodMap.put("育苗期","000600040013");
        growthPeriodMap.put("花芽分化期","000600040014");
        growthPeriodMap.put("伸蔓期","000600040015");
        growthPeriodMap.put("采收后期","000600040016");

        // 类型
        plantTypeMap.put("露天栽培","000600060001");
        plantTypeMap.put("设施栽培","000600060002");
        plantTypeMap.put("高架基质","000600060003");
        plantTypeMap.put("绿色有机","000600060004");
    }

}
