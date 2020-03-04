package com.common.generate;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import com.common.enums.AnswerOptionsEnum;


public class GenerateSql
{

    public static void main(String[] args){

    }

    private static void batchSchoolQuestionInsert(){

//        generateInsert(generPath);

        String subjectId="d781476f4ea4471bacfd5309db96b8f4";
        String[] sectionIds = {"4587fede7e1c4cdb84a66d0d4dfa86f5","71062ab711394a18bee5d519dc80728a"};
        String[] sectionTitles = {"奥特曼变身","小怪兽出没"};
        //String[] sectionIds = {"852bd8993bc8446499c354bac795e66e"};
        int questionSize=10;
        for (int i = 0; i < sectionIds.length; i++ ) {
            String sectionId = sectionIds[i];
            String generPath = "C:\\Users\\Administrator\\Desktop\\question\\insert_question_answer_"+sectionId+".sql";
            String defaultContent = sectionTitles[i] + "-题";
            generateInsert(generPath,subjectId, sectionId, defaultContent, questionSize);
        }

//        String subjectId = "69d3b951ed224aadbf9ea4ce82bc4ec9";
//        String[] sectionIds = {"d0a29abe1a3d44de9f1b110150aef207",
//                "2af558eca49c477dae953afbd958cd6e", "022a72c6aa0f4bb6be4c44ad2d4bccba",
//                "0b3d5d637a784c89a718b05d1c247f22", "a2f09e8975b948e68e67a6abf28299ef",
//                "e47726307d5f415eb07b573889ed8684", "07ef9784831b4c7396a039f5be3e9696",
//                "22eda069f51443ef922b2af592e258dd", "d7dd1540bd994de2bea826f261ce3e4b",
//                "a348d6e76ed548bb95d56499c699aa9b", "01ee738a13ff4ed3adaeaaddf7fc84e5",
//                "31013675a7af4f6d8162c3efe8429d5b", "73eea2b2dcaf4497bcfd691e1b78bce5",
//                "40d71a1f0ab84c1cbb3c20bc8aa8b59f", "12122c0abe7145cd855e9d245defbff5"};
//
//        for (int i = 0; i < sectionIds.length; i++ ) {
//            String sectionId = sectionIds[i];
//            int size = 10;
//            String defaultContent = "课节" + (i + 1) + "-题";
//            generateInsert(subjectId, sectionId, size, defaultContent);
//        }
    }

    /**
     * 生成insert语句
     *
     * @param sqlStr
     *                      insert语句，例如insert into table_name[(xxx,xxx,.....)]
     * @param dataPath
     *                      数据源文件路径
     * @param generPath
     *                      文件存放路径
     */
    private static void generateInsert(String generPath,String subjectId ,String sectionId,String defaultContent,int questionSize )
    {
        // INSERT INTO `testdb_dev`.`t_questions` (`id`, `group_id`, `subject_id`, `section_id`,
        // `content`, `type`, `img`, `answering_time`, `status`, `answer_analyse_text`,
        // `answer_analyse_video_url`, `create_time`, `create_user`, `alter_time`, `alter_user`)
        // VALUES ('0113ae4c1ead428181e250d1747a3112', '', '27463a2ab8504053a4406990eebd8006',
        // 'abd82c0d0e2e43bb94ac785f1b27f10f', '芒果采摘4', '0', '', '-1', '1',
        // '<p>选项选项选项选项选项选项选项选项选项选项选项选项选项选项</p>', '2e84eb5175f24bb09b94a7b240ac7b7c', '2019-11-01
        // 11:54:53', '超级管理员', '2019-11-05 12:23:50', '超级管理员');

        //String subjectId = "71dffc3c75ee4b06a4f1b09a7c1d53c9";
        //String sectionId = "8745aeaeb6784ec79143be19ab559a71";
        //String defaultContent = "青龙柑橘-题";

        String questionsSql = "INSERT INTO `t_questions` (`id`, `group_id`, `subject_id`, `section_id`,`content`, `type`, "
                + "`status`, `create_time`, "
                + "`create_user`, `alter_time`, `alter_user`) VALUES";

        String answerSql = "INSERT INTO `t_answers` (`id`, `question_id`, `content`, `is_right`,"
                + "`order_num`, `create_time`, `create_user`, `alter_time`, `alter_user`) VALUES";

        //int questionSize = 10;

        Random rand = new Random();

        try {
            String encoding = "UTF-8";
            Map<String, Integer> questionMap = new HashMap<String, Integer>();
            StringBuffer questionsBuffer = new StringBuffer();
            for (int i = 0; i < questionSize; i++ ) {

                String id = getUuid();
                int type = rand.nextInt(2);

                questionMap.put(id, type);

                StringBuffer buffer = new StringBuffer();
                buffer.append(questionsSql);
                buffer.append("('").append(id).append("',");
                buffer.append("'',");
                buffer.append("'").append(subjectId).append("',");
                buffer.append("'").append(sectionId).append("',");
                buffer.append("'").append(defaultContent + (i + 1)).append("',");
                buffer.append("'").append(type).append("',");
                buffer.append("1").append(",");
                buffer.append("NOW(),");
                buffer.append("'手动录入',");
                buffer.append("NOW(),");
                buffer.append("'手动录入');");
                buffer.append("\n\r");

                questionsBuffer.append(buffer.toString());
            }

            int answerSize = 4;

            StringBuffer answerBuffer = new StringBuffer();
            for (Entry<String, Integer> entry : questionMap.entrySet()) {

                String questionId = entry.getKey();
                int type = entry.getValue();

                // INSERT INTO `testdb_dev`.`t_answers` (`id`, `question_id`, `content`,
                // `is_right`,
                // `order_num`, `create_time`, `create_user`, `alter_time`, `alter_user`) VALUES
                // ('0073116488104a21abd830421a68b706', 'e2435d3e9ea24133b98d02637d9e5a7e', NULL,
                // '1', '1',
                // '2019-11-04 14:40:35', '超级管理员', '2019-11-04 14:40:35', '超级管理员');
                if (type == 0) {
                    int isRightIndex = rand.nextInt(answerSize);
                    for (int i = 0; i < answerSize; i++ ) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(answerSql);
                        buffer.append("('").append(getUuid()).append("',");
                        buffer.append("'").append(questionId).append("',");
                        int isRight = i == isRightIndex ? 1 : 0;
                        String answer = AnswerOptionsEnum.fomartAnswerText((i + 1) + "");
                        if(isRight == 1) {
                            buffer.append("'").append("选项").append(answer+"-正确").append("',");
                        }else {
                            buffer.append("'").append("选项").append(answer+"-错误").append("',");
                        }

                        buffer.append("").append(isRight).append(",");
                        buffer.append("").append((i + 1)).append(",");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入',");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入');");
                        buffer.append("\n\r");

                        answerBuffer.append(buffer.toString());
                    }
                } else {
                    int isRightCount = 0;
                    for (int i = 0; i < answerSize; i++ ) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(answerSql);
                        buffer.append("('").append(getUuid()).append("',");
                        buffer.append("'").append(questionId).append("',");

                        int isRight = rand.nextInt(2);
                        String answer = AnswerOptionsEnum.fomartAnswerText((i + 1) + "");
                        if (isRight == 1 || isRightCount == (answerSize - 1)) {
                            buffer.append("'").append("选项").append(answer + "-正确").append("',");
                        } else {
                            buffer.append("'").append("选项").append(answer + "-错误").append("',");
                            isRightCount = isRightCount + 1;
                        }

                        buffer.append("").append(isRight).append(",");
                        buffer.append("").append((i + 1)).append(",");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入',");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入');");
                        buffer.append("\n\r");

                        answerBuffer.append(buffer.toString());
                    }
                }

            }

            System.out.println(questionsBuffer);
            System.out.println(answerBuffer);
            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            p.write(questionsBuffer.toString().getBytes());
            p.write(answerBuffer.toString().getBytes());

            p.close();
            fs.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成insert语句
     *
     * @param sqlStr
     *                      insert语句，例如insert into table_name[(xxx,xxx,.....)]
     * @param dataPath
     *                      数据源文件路径
     * @param generPath
     *                      文件存放路径
     */
    private static void generateInsert(String subjectId, String sectionId, int questionSize,
                                       String defaultContent) {

        String generPath = "C:\\Users\\Administrator\\Desktop\\question\\insert_question_answer_"
                + sectionId + ".sql";
        // INSERT INTO `testdb_dev`.`t_questions` (`id`, `group_id`, `subject_id`, `section_id`,
        // `content`, `type`, `img`, `answering_time`, `status`, `answer_analyse_text`,
        // `answer_analyse_video_url`, `create_time`, `create_user`, `alter_time`, `alter_user`)
        // VALUES ('0113ae4c1ead428181e250d1747a3112', '', '27463a2ab8504053a4406990eebd8006',
        // 'abd82c0d0e2e43bb94ac785f1b27f10f', '芒果采摘4', '0', '', '-1', '1',
        // '<p>选项选项选项选项选项选项选项选项选项选项选项选项选项选项</p>', '2e84eb5175f24bb09b94a7b240ac7b7c', '2019-11-01
        // 11:54:53', '超级管理员', '2019-11-05 12:23:50', '超级管理员');

        String questionsSql = "INSERT INTO `t_questions` (`id`, `group_id`, `subject_id`, `section_id`,`content`, `type`, "
                + "`status`, `create_time`, "
                + "`create_user`, `alter_time`, `alter_user`) VALUES";

        String answerSql = "INSERT INTO `t_answers` (`id`, `question_id`, `content`, `is_right`,"
                + "`order_num`, `create_time`, `create_user`, `alter_time`, `alter_user`) VALUES";

        Random rand = new Random();

        try {
            String encoding = "UTF-8";
            Map<String, Integer> questionMap = new HashMap<String, Integer>();
            StringBuffer questionsBuffer = new StringBuffer();
            for (int i = 0; i < questionSize; i++ ) {

                String id = getUuid();
                int type = rand.nextInt(2);

                questionMap.put(id, type);

                StringBuffer buffer = new StringBuffer();
                buffer.append(questionsSql);
                buffer.append("('").append(id).append("',");
                buffer.append("'',");
                buffer.append("'").append(subjectId).append("',");
                buffer.append("'").append(sectionId).append("',");
                buffer.append("'").append(defaultContent + (i + 1)).append("',");
                buffer.append("'").append(type).append("',");
                buffer.append("1").append(",");
                buffer.append("NOW(),");
                buffer.append("'手动录入',");
                buffer.append("NOW(),");
                buffer.append("'手动录入');");
                buffer.append("\n\r");

                questionsBuffer.append(buffer.toString());
            }

            int answerSize = 4;

            StringBuffer answerBuffer = new StringBuffer();
            for (Entry<String, Integer> entry : questionMap.entrySet()) {

                String questionId = entry.getKey();
                int type = entry.getValue();

                // INSERT INTO `testdb_dev`.`t_answers` (`id`, `question_id`, `content`,
                // `is_right`,
                // `order_num`, `create_time`, `create_user`, `alter_time`, `alter_user`) VALUES
                // ('0073116488104a21abd830421a68b706', 'e2435d3e9ea24133b98d02637d9e5a7e', NULL,
                // '1', '1',
                // '2019-11-04 14:40:35', '超级管理员', '2019-11-04 14:40:35', '超级管理员');
                if (type == 0) {
                    int isRightIndex = rand.nextInt(answerSize);
                    for (int i = 0; i < answerSize; i++ ) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(answerSql);
                        buffer.append("('").append(getUuid()).append("',");
                        buffer.append("'").append(questionId).append("',");

                        int isRight = i == isRightIndex ? 1 : 0;
                        String answer = AnswerOptionsEnum.fomartAnswerText((i + 1) + "");
                        if(isRight == 1) {
                            buffer.append("'").append("选项").append(answer+"-正确").append("',");
                        }else {
                            buffer.append("'").append("选项").append(answer+"-错误").append("',");
                        }

                        buffer.append("").append(isRight).append(",");
                        buffer.append("").append((i + 1)).append(",");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入',");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入');");
                        buffer.append("\n\r");

                        answerBuffer.append(buffer.toString());
                    }
                } else {
                    int isRightCount = 0;
                    for (int i = 0; i < answerSize; i++ ) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(answerSql);
                        buffer.append("('").append(getUuid()).append("',");
                        buffer.append("'").append(questionId).append("',");

                        int isRight = rand.nextInt(2);
                        String answer = AnswerOptionsEnum.fomartAnswerText((i + 1) + "");
                        if (isRight == 1 || isRightCount == (answerSize - 1)) {
                            buffer.append("'").append("选项").append(answer + "-正确").append("',");
                            isRight = 1;
                        } else {
                            buffer.append("'").append("选项").append(answer + "-错误").append("',");
                            isRightCount = isRightCount + 1;
                        }

                        buffer.append("").append(isRight).append(",");
                        buffer.append("").append((i + 1)).append(",");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入',");
                        buffer.append("NOW(),");
                        buffer.append("'手动录入');");
                        buffer.append("\n\r");

                        answerBuffer.append(buffer.toString());
                    }
                }

            }

            System.out.println(questionsBuffer);
            System.out.println(answerBuffer);
            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            p.write(questionsBuffer.toString().getBytes());
            p.write(answerBuffer.toString().getBytes());

            p.close();
            fs.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得uuid
     */
    private static String getUuid()
    {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
