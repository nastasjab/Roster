package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftOnExactDayDAO;
import lv.javaguru.java2.domain.ShiftOnExactDay;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShiftOnExactDayDAOImpl extends DAOImpl implements ShiftOnExactDayDAO {
    public void create(ShiftOnExactDay shiftOnExactDay) throws DBException {
        if (shiftOnExactDay == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "insert into shifts_on_exact_day values (default, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shiftOnExactDay.getUserId());
            preparedStatement.setDate(2, shiftOnExactDay.getDate());
            preparedStatement.setLong(3, shiftOnExactDay.getShiftId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                shiftOnExactDay.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftOnExactDayDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public ShiftOnExactDay getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from shifts_on_exact_day where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ShiftOnExactDay shiftOnExactDay = null;
            if (resultSet.next()) {
                shiftOnExactDay = new ShiftOnExactDay();
                shiftOnExactDay.setId(resultSet.getLong("id"));
                shiftOnExactDay.setUserId(resultSet.getLong("userId"));
                shiftOnExactDay.setDate(resultSet.getDate("date"));
                shiftOnExactDay.setShiftId(resultSet.getLong("shiftId"));
            }
            return shiftOnExactDay;
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftOnExactDayDAOImpl.getById()");
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
                    .prepareStatement("delete from shifts_on_exact_day where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftOnExactDayDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(ShiftOnExactDay shiftOnExactDay) throws DBException {
        if (shiftOnExactDay == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update shifts_on_exact_day set userId = ?, date = ?, " +
                            "shiftId = ? where id = ?");
            preparedStatement.setLong(1, shiftOnExactDay.getUserId());
            preparedStatement.setDate(2, shiftOnExactDay.getDate());
            preparedStatement.setLong(3, shiftOnExactDay.getShiftId());
            preparedStatement.setLong(4, shiftOnExactDay.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftOnExactDayDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<ShiftOnExactDay> getAll() throws DBException {
        List<ShiftOnExactDay> userPatterns = new ArrayList<ShiftOnExactDay>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from shifts_on_exact_day");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShiftOnExactDay shift = new ShiftOnExactDay();
                shift.setId(resultSet.getLong("id"));
                shift.setUserId(resultSet.getLong("userId"));
                shift.setDate(resultSet.getDate("date"));
                shift.setShiftId(resultSet.getLong("shiftId"));
                userPatterns.add(shift);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting data in ShiftOnExactDayDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return userPatterns;
    }
}
