package dao;

import com.yuriy.entity.Question;
import com.yuriy.entity.User;

import java.util.List;

public interface DaoConnection {
    void connect();
    void disconnect();
    List<Question> selectAllQes();
    List<User> selectAllUser();
    public void createStudent(String name, int id);
    public void updateUser(String user);
}
