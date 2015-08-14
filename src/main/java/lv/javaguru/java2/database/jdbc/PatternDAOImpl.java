package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.domain.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatternDAOImpl extends DAOImpl implements PatternDAO {

    @Autowired
    private ShiftPatternDAO shiftPatternDAO = new ShiftPatternDAOImpl();

    public void create(Pattern pattern) throws DBException {
        if (pattern == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into patterns values (default, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pattern.getName());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                pattern.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    public Pattern getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from patterns where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Pattern pattern = null;
            if (resultSet.next()) {
                pattern = new Pattern();
                pattern.setId(resultSet.getLong("id"));
                pattern.setName(resultSet.getString("name"));
                pattern.setShiftPatterns(shiftPatternDAO.getAll(pattern.getId()));
            }

            return pattern;
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Pattern> getAll() throws DBException {
        List<Pattern> patterns = new ArrayList<Pattern>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from patterns");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pattern pattern = new Pattern();
                pattern.setId(resultSet.getLong("id"));
                pattern.setName(resultSet.getString("name"));
                patterns.add(pattern);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list PatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return patterns;
    }

    public void delete(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from patterns where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection
                    .prepareStatement("delete from patterns_shifts where patternId = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(Pattern pattern) throws DBException {
        if (pattern == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update patterns set name = ?"+
                            "where id = ?");
            preparedStatement.setString(1, pattern.getName());
            preparedStatement.setLong(2, pattern.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

}
