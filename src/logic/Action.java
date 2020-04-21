package logic;

import com.yuriy.entity.Question;
import com.yuriy.entity.User;
import dao.OracleDaoConnection;

import java.util.List;

public class Action {
    private static List<User> users = OracleDaoConnection.getInstance().selectAllUser();
    private static List<Question> questions = OracleDaoConnection.getInstance().selectAllQes();
    private static OracleDaoConnection oracleDaoConnection = new OracleDaoConnection();

    public static String getQest(int index) {
        return questions.get(index).getQest();
    }

    public static void answers(String answer, String user, int index) {
        if (answer.equals(questions.get(index).getAnsw())) {
            for (int i = 0; i < users.size(); i++) {
                if (user.equals(users.get(i).getName())) {
                    oracleDaoConnection.updateUser(users.get(i).getName());
                }
            }
        }
    }


    private static boolean boll(String name) {
        for (User user : users) {
            if (name.equals(user.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void getUser(String name) {
        if (!boll(name)) {
            oracleDaoConnection.createStudent(name, users.size() + 1);
        }
    }

}
