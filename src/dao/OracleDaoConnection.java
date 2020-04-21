package dao;


import com.yuriy.entity.Question;
import com.yuriy.entity.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class OracleDaoConnection implements DaoConnection {

    private static OracleDaoConnection oracleDaoConnection;
    private Context context;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    //private Statement statement;

    public static OracleDaoConnection getInstance() {
        if (oracleDaoConnection != null) {
            return oracleDaoConnection;
        }
        return new OracleDaoConnection();
        //fef
    }

    //CONNECT
    @Override
    public void connect() {
        try {
            Hashtable hashtable = new Hashtable();
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY,
                    "weblogic.jndi.WLInitialContextFactory");
            hashtable.put(Context.PROVIDER_URL, "t3://localhost:7001"); //jdbc:oracle:thin:@localhost:1521:STUDENTS
            context = new InitialContext(hashtable);
            DataSource dataSource = (DataSource) context.lookup("my2");
            connection = dataSource.getConnection();
            System.out.println("Connect is ok");
        } catch (SQLException | NamingException e) {
            System.out.println("CONNECTION ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(String name) {
        connect();
        try {
            statement = connection.prepareStatement("UPDATE USERS SET MAX_TOTAL_POINTS = MAX_TOTAL_POINTS + 1  WHERE NAME = '" + name + "'");
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
            resultSet.close();
            statement.close();
            context.close();
            if (connection.isClosed()) {
                System.out.println("closed");
            }
        } catch (SQLException | NamingException e) {
            System.out.println("DISCONNECTION ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> selectAllQes() {
        connect();
        List<Question> students = new ArrayList<>();
        exceptionSolver(students, "QUESTIONS");
        disconnect();
        return students;
    }

    @Override
    public List<User> selectAllUser() {
        connect();
        List<User> users = new ArrayList<>();
        exceptionSolver(users, "USERS");
        disconnect();
        return users;
    }

    private Question parseQest(ResultSet resultSet) {
        Question question = null;
        try {
            int id = resultSet.getInt("ID");
            String questions = resultSet.getString("QUESTIONS");
            String answer = resultSet.getString("ANSWER");
            int point = resultSet.getInt("POINTS");
            question = new Question(id, questions, answer, point);
        } catch (SQLException e) {
            System.out.println("SQL EXEPTION");
            e.printStackTrace();
        }
        return question;
    }

    private User parseUser(ResultSet resultSet) {
        User user = null;
        try {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            int point = resultSet.getInt("MAX_TOTAL_POINTS");
            user = new User(id, name, point);
        } catch (SQLException e) {
            System.out.println("SQL EXEPTION");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void createStudent(String name, int id) {
        connect();
        try {
            statement = connection.prepareStatement("INSERT INTO USERS " +
                    "VALUES (" + id + " ,'" + name + "', 0)");
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    private void exceptionSolver(List students, String table) {
        try {
            statement = connection.prepareStatement("SELECT * FROM " + table);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (table.equals("USERS")) {
                    students.add(parseUser(resultSet));
                } else {
                    students.add(parseQest(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL EXCEPTION");
            e.printStackTrace();
        }
    }
}
