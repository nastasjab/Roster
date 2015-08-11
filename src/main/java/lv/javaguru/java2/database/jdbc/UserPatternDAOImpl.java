package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserPatternDAO;
import lv.javaguru.java2.domain.UserPattern;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserPatternDAOImpl extends DAOImpl implements UserPatternDAO{
    public void create(UserPattern userPattern) throws DBException {
        if (userPattern == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "insert into user_patterns values (default, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, userPattern.getUserId());
            preparedStatement.setLong(2, userPattern.getShiftPatternId());
            preparedStatement.setDate(3, userPattern.getStartDay());
            preparedStatement.setDate(4, userPattern.getEndDay());
            preparedStatement.setInt(5, userPattern.getPatternStartDay());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                userPattern.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute UserPatternDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public UserPattern getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from user_patterns where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserPattern userPattern = null;
            if (resultSet.next()) {
                userPattern = new UserPattern();
                userPattern.setId(resultSet.getLong("id"));
                userPattern.setUserId(resultSet.getLong("userId"));
                userPattern.setShiftPatternId(resultSet.getLong("shiftPatternId"));
                userPattern.setStartDay(resultSet.getDate("startDay"));
                userPattern.setEndDay(resultSet.getDate("endDay"));
                userPattern.setPatternStartDay(resultSet.getInt("patternStartDay"));
            }
            return userPattern;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserPatternDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

     public void delete(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from user_patterns where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute UserPatternDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(UserPattern userPattern) throws DBException {
        if (userPattern == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update user_patterns set userId = ?, shiftPatternId = ?, " +
                            "startDay = ?, endDay = ?, patternStartDay = ? "+
                            "where id = ?");
            preparedStatement.setLong(1, userPattern.getUserId());
            preparedStatement.setLong(2, userPattern.getShiftPatternId());
            preparedStatement.setDate(3, userPattern.getStartDay());
            preparedStatement.setDate(4, userPattern.getEndDay());
            preparedStatement.setInt(5, userPattern.getPatternStartDay());
            preparedStatement.setLong(6, userPattern.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute UserPatternDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<UserPattern> getAll() throws DBException {
        List<UserPattern> userPatterns = new ArrayList<UserPattern>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user_patterns");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserPattern userPattern = new UserPattern();
                userPattern.setId(resultSet.getLong("id"));
                userPattern.setUserId(resultSet.getLong("userId"));
                userPattern.setShiftPatternId(resultSet.getLong("shiftPatternId"));
                userPattern.setStartDay(resultSet.getDate("startDay"));
                userPattern.setEndDay(resultSet.getDate("endDay"));
                userPattern.setPatternStartDay(resultSet.getInt("patternStartDay"));
                userPatterns.add(userPattern);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserPatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return userPatterns;
    }

    public List<UserPattern> getByUserId(long id) throws DBException {

        List<UserPattern> userPatterns = new ArrayList<UserPattern>();

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select u.id, u.shiftPatternId, p.name, u.startDay, u.endDay, u.patternStartDay from user_patterns AS u, patterns AS p where u.userId = ? AND u.shiftPatternId = p.id ORDER BY u.startDay ASC");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserPattern userPattern = new UserPattern();
                userPattern.setId(resultSet.getLong("u.id"));
                userPattern.setUserId(id);
                userPattern.setShiftPatternId(resultSet.getLong("u.shiftPatternId"));
                userPattern.setShiftPatternName(resultSet.getString("p.name"));
                userPattern.setStartDay(resultSet.getDate("u.startDay"));
                userPattern.setEndDay(resultSet.getDate("u.endDay"));
                userPattern.setPatternStartDay(resultSet.getInt("u.patternStartDay"));
                userPatterns.add(userPattern);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserPatternDAOImpl.getByUserId()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return userPatterns;
    }


}
