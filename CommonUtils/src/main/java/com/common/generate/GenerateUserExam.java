package com.common.generate;

import com.common.enums.AnswerOptionsEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GenerateUserExam {

    public static void main(String[] args){
        Map<String,SchoolUserExamQuestions> examMap = handlerUserExamRecord();
        List<SchoolUserExamQuestions> questionMap =handlerUserExamQuestions(examMap);
        //System.out.println(questionMap.toString());
        handlerUserExamAnswer(questionMap);


    }

    public static Map<String,SchoolUserExamQuestions> handlerUserExamRecord(){
        Map<String,SchoolUserExamQuestions> examMap = new HashMap<String,SchoolUserExamQuestions>();
        try {

            String encoding = "UTF-8";
            // select id,user_id,school_subject_id,subject_section_id,subject_semester_id,create_time from t_school_user_exam_record where type in (0,1)；
            String dataPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试记录.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试记录.sql";

            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt = null;

                Set<String> userExamQuerys = new HashSet<String>();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] lineTxts = lineTxt.split("###");

                    String query = lineTxts[1]+"_"+lineTxts[2]+"_"+lineTxts[3];
                    if(lineTxts.length == 5){
                        query = lineTxts[1]+"_"+lineTxts[2]+"_"+lineTxts[3]+"_"+lineTxts[4];
                    }


                    if(!userExamQuerys.contains(query)){
                        userExamQuerys.add(query);
                        //buffer.append(lineTxts[0]).append("   ").append(query).append("\n");
                        buffer.append("'").append(lineTxts[0]).append("',");

//                        JSONObject userExam = new JSONObject();
//                        exam.put("userId",lineTxts[1]);
//                        exam.put("schoolSubjectId",lineTxts[2]);
//                        exam.put("subjectSemesterId",lineTxts[3]);
                        SchoolUserExamQuestions userExam = new SchoolUserExamQuestions();
                        userExam.setUserId(lineTxts[1]);
                        userExam.setSchoolSubjectId(lineTxts[2]);
                        userExam.setSubjectSemesterId(lineTxts[3]);
                        userExam.setType("1");
                        if(lineTxts.length == 5){
                            userExam.setSubjectSectionId(lineTxts[4]);
                            userExam.setType("0");
//                            exam.put("subjectSectionId",lineTxts[4]);
//                            exam.put("type","0");
                        }else{
//                            exam.put("type","1");
                        }
                        examMap.put(lineTxts[0],userExam);

                    }else{
                        //System.out.println(lineTxts.length + "   "+lineTxts[0]+"   "+query);
                    }
                    //buffer.append("'").append(query).append("',").append("\n");
                }

                String sql = "select a.record_id,a.question_id,q.type,q.`status`,a.order_num,"
                       + "(select sum(pow(2,order_num)) from t_answers where question_id=q.id and is_right='1' GROUP BY question_id) as target "
                       + "from t_questions q LEFT JOIN t_answer_log a ON q.id = a.question_id where a.record_id";
                sql = sql.concat(" in (").concat(buffer.toString()).concat(");");

                //System.out.println(examMap);

                p.println(sql);
                read.close();

                p.close();
                fs.close();
            } else {
                System.out.println("找不到指定的文件");
            }
            return examMap;
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return examMap;
        }
    }

    public static List<SchoolUserExamQuestions> handlerUserExamQuestions(Map<String,SchoolUserExamQuestions> examMap){
        Map<String, List<SchoolUserExamQuestions>> questionMap = new ConcurrentHashMap<String, List<SchoolUserExamQuestions>>();
        List<SchoolUserExamQuestions> userExamQuestionsList = new ArrayList<SchoolUserExamQuestions>();
        try {

            String encoding = "UTF-8";
            // select a.record_id,a.question_id,q.content,q.type,q.`status`,(select sum(pow(2,order_num)) from t_answers where question_id=q.id and is_right='1' GROUP BY question_id) as target from t_questions q LEFT JOIN t_answer_log a ON q.id = a.question_id where a.record_id in ()；
            String dataPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试考题列表.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试考题列表.sql";

            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt = null;

                Set<String> userExamQuerys = new HashSet<String>();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] lineTxts = lineTxt.split("###");
                    String recordId = lineTxts[0];
                    String questionId = lineTxts[1];
                    String questionType = lineTxts[2];

                    SchoolUserExamQuestions userExamQuestions = examMap.get(lineTxts[0]);
                    SchoolUserExamQuestions newUserExamQuestion = new SchoolUserExamQuestions();
                    newUserExamQuestion.setUserId(userExamQuestions.getUserId());
                    newUserExamQuestion.setSchoolSubjectId(userExamQuestions.getSchoolSubjectId());
                    newUserExamQuestion.setSubjectSemesterId(userExamQuestions.getSubjectSemesterId());
                    newUserExamQuestion.setSubjectSectionId(userExamQuestions.getSubjectSectionId());
                    newUserExamQuestion.setType(userExamQuestions.getType());
                    newUserExamQuestion.setQuestionId(questionId);
                    //userExamQuestions.setQuestionContent(lineTxts[3]);
                    newUserExamQuestion.setQuestionType(questionType);
                    newUserExamQuestion.setQuestionStatus(lineTxts[3]);
                    newUserExamQuestion.setOrderNum(lineTxts[4]);
                    if("0".equals(questionType) || "1".equals(questionType)){
                        //userExamQuestions.put("questionRightAnswer",Integer.valueOf(lineTxts[5]));
                        newUserExamQuestion.setQuestionRightAnswer(Integer.valueOf(lineTxts[5]));
                    }

                    String key = recordId.concat("_").concat(questionId);
                    //System.out.println("====key:" + key + "  questionId:" + questionId);
                    userExamQuestionsList.add(newUserExamQuestion);
                    String query = lineTxts[1]+"_"+lineTxts[2]+"_"+lineTxts[3]+"_"+lineTxts[4];

                    if(!userExamQuerys.contains(questionId)){
                        userExamQuerys.add(query);
                        //buffer.append(lineTxts[0]).append("   ").append(query).append("\n");
                        buffer.append("'").append(questionId).append("',");
                    }
                    //buffer.append("'").append(query).append("',").append("\n");
                }

                String sql = "select question_id,content,order_num,pow(2, order_num),is_right from t_answers where not ISNULL(content) and question_id";
                sql = sql.concat(" in (").concat(buffer.toString()).concat(") order by question_id,order_num;");

                //System.out.println(examMap);

                p.println(sql);
                read.close();

                p.close();
                fs.close();
            } else {
                System.out.println("找不到指定的文件");
            }
            return userExamQuestionsList;
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return userExamQuestionsList;
        }
    }

    public static void handlerUserExamAnswer(List<SchoolUserExamQuestions> questionMap){

        try {
            String encoding = "UTF-8";
            // select a.record_id,a.question_id,q.content,q.type,q.`status`,(select sum(pow(2,order_num)) from t_answers where question_id=q.id and is_right='1' GROUP BY question_id) as target from t_questions q LEFT JOIN t_answer_log a ON q.id = a.question_id where a.record_id in ()；
            String dataPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试答题答案选项.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\用户考试快照修复sql\\用户考试答题答案选项.sql";

            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            Map<String,JSONArray> answerMap = new HashMap<String, JSONArray>();
            File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;


                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] lineTxts = lineTxt.split("###");

                    String questionId = lineTxts[0];
                    String content = lineTxts[1];
                    String orderNum = lineTxts[2];
                    String target = lineTxts[3];

                    String isRight = lineTxts[4];
                    String orderNumText = AnswerOptionsEnum.fomartAnswerText(orderNum);

                    JSONObject answer =new JSONObject();
                    answer.put("content",content);
                    answer.put("target",target);
                    answer.put("order_num",orderNum);
                    answer.put("is_right",isRight);
                    answer.put("order_num_text",orderNumText);

                    if(answerMap.containsKey(questionId)){
                        answerMap.get(questionId).add(answer);
                    }else{
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(answer);
                        answerMap.put(questionId,jsonArray);
                    }
                }

                Set<String> userExamQuerys = new HashSet<String>();
                List<SchoolUserExamQuestions> userExamQuestionsList = new ArrayList<SchoolUserExamQuestions>();
                for (SchoolUserExamQuestions entry : questionMap){

                    String questionId = entry.getQuestionId();
                    //System.out.println("====questionId"+questionId );
                    //List<SchoolUserExamQuestions> userExamList = entry.getValue();
                    JSONArray answerArray = answerMap.get(questionId);
                    if(null == answerArray){
                        userExamQuerys.add(questionId);
                        continue;
                    }
                    entry.setAnswersContent(answerArray.toString());
                }

                String sql = "INSERT INTO `t_school_user_exam_questions` (`id`, `user_id`, `school_subject_id`, `subject_semester_id`, `subject_section_id`, `type`, `question_id`, `order_num`," +
                        " `question_content`, `question_type`, `question_status`, `question_right_answer`, `answers_content`, `answer_analyse_text`, `answer_analyse_video_url`, `answer_analyse_video_img`, " +
                        "`create_time`, `create_user`, `alter_time`, `alter_user`) VALUES ";
                StringBuffer buffer = new StringBuffer();
                for(SchoolUserExamQuestions userExamQuestions : questionMap){

                    StringBuffer childBuffer = new StringBuffer();

                    childBuffer.append(sql);
                    childBuffer.append("(");
                    childBuffer.append("'").append(getUuid()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getUserId()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getSchoolSubjectId()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getSubjectSemesterId()).append("',");
                    String sectionId = userExamQuestions.getSubjectSectionId();
                    if(!"".equals(sectionId) && sectionId != null){
                        childBuffer.append("'").append(sectionId).append("',");
                    }else{
                        childBuffer.append("null,");
                    }

                    childBuffer.append("'").append(userExamQuestions.getType()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getQuestionId()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getOrderNum()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getQuestionContent()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getQuestionType()).append("',");
                    childBuffer.append("'").append(userExamQuestions.getQuestionStatus()).append("',");
                    childBuffer.append(userExamQuestions.getQuestionRightAnswer()).append(",");
                    String answerContent = userExamQuestions.getAnswersContent();
                    System.out.println("userExamQuestions.getAnswersContent(): "+ (answerContent == null));
                    if(!"".equals(answerContent) && answerContent != null){
                        childBuffer.append("'").append(answerContent).append("',");
                    }else{
                        childBuffer.append("null,");
                    }

                    childBuffer.append("null,");
                    childBuffer.append("null,");
                    childBuffer.append("null,");
                    childBuffer.append("NOW()").append(",");
                    childBuffer.append("'手动录入',");
                    childBuffer.append("NOW()").append(",");
                    childBuffer.append("'手动录入');");
                    if(userExamQuerys.contains(userExamQuestions.getQuestionId())){
                        //System.out.println("================================="+userExamQuestions.getQuestionId()+"=================================");
                        //System.out.println(childBuffer.toString());
                    }
                    buffer.append(childBuffer.toString()).append("\n");
                }
//                for(SchoolUserExamQuestions userExamQuestions : userExamQuestionsList){
//
//                    StringBuffer childBuffer = new StringBuffer();
//
//                    childBuffer.append(sql);
//                    childBuffer.append("(");
////                    childBuffer.append("'").append(getUuid()).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("userId")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("schoolSubjectId")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("subjectSemesterId")).append("',");
////                    if(userExamQuestions.containsKey("subjectSectionId")){
////                        childBuffer.append("'").append(userExamQuestions.get("subjectSectionId")).append("',");
////                    }else{
////                        childBuffer.append("null,");
////                    }
////
////                    childBuffer.append("'").append(userExamQuestions.get("type")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("questionId")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("orderNum")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("questionContent")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("questionType")).append("',");
////                    childBuffer.append("'").append(userExamQuestions.get("questionStatus")).append("',");
////                    childBuffer.append(userExamQuestions.get("questionRightAnswer")).append(",");
////                    childBuffer.append("'").append(userExamQuestions.get("answersContent")).append("',");
////                    childBuffer.append("null,");
////                    childBuffer.append("null,");
////                    childBuffer.append("null,");
////                    childBuffer.append("NOW()").append(",");
////                    childBuffer.append("'手动录入'");
////                    childBuffer.append("NOW()").append(",");
////                    childBuffer.append("'手动录入');");
////                    if(userExamQuerys.contains(userExamQuestions.get("questionId"))){
////                        System.out.println("================================="+userExamQuestions.get("questionId")+"=================================");
////                        System.out.println(childBuffer.toString());
////                    }
//                    childBuffer.append("'").append(userExamQuestions.getUserId()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getSchoolSubjectId()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getSubjectSemesterId()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getSubjectSectionId()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getType()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getQuestionId()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getOrderNum()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getQuestionContent()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getQuestionType()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getQuestionStatus()).append("',");
//                    childBuffer.append(userExamQuestions.getQuestionRightAnswer()).append(",");
//                    childBuffer.append("'").append(userExamQuestions.getAnswersContent()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getAnswerAnalyseText()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getAnswerAnalyseVideoUrl()).append("',");
//                    childBuffer.append("'").append(userExamQuestions.getAnswerAnalyseVideoImg()).append("',");
//                    childBuffer.append("NOW()").append(",");
//                    childBuffer.append("'").append(userExamQuestions.getCreateUser()).append("',");
//                    childBuffer.append("NOW()").append(",");
//                    childBuffer.append("'").append(userExamQuestions.getAlterUser()).append("');");
//                    if(userExamQuerys.contains(userExamQuestions.getQuestionId())){
//                        System.out.println("================================="+userExamQuestions.getQuestionId()+"=================================");
//                        System.out.println(childBuffer.toString());
//                    }
//                    buffer.append(childBuffer.toString()).append("\n");
//                }

                p.println(buffer);
                read.close();

                p.close();
                fs.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    /**
     * 获得uuid
     */
    public static String getUuid()
    {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
