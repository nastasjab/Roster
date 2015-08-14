package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ShiftDAOImpl extends DAOImpl implements ShiftDAO{
    public void create(Shift shift) throws DBException {
        if (shift == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "insert into shifts values (default, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, shift.getName());
            preparedStatement.setString(2, shift.getShiftStarts());
            preparedStatement.setString(3, shift.getShiftEnds());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                shift.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public Shift getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from shifts where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Shift shift = null;
            if (resultSet.next()) {
                shift = new Shift();
                shift.setId(resultSet.getLong("id"));
                shift.setName(resultSet.getString("name"));
                shift.setShiftStarts(resultSet.getString("start"));
                shift.setShiftEnds(resultSet.getString("end"));
            }
            return shift;
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftDAOImpl.getById()");
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
                    .prepareStatement("delete from shifts where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(Shift shift) throws DBException {
        if (shift == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update shifts set name = ?, start = ?, end = ? where id = ?");
            preparedStatement.setString(1, shift.getName());
            preparedStatement.setString(2, shift.getShiftStarts());
            preparedStatement.setString(3, shift.getShiftEnds());
            preparedStatement.setLong(4, shift.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Shift> getAll() throws DBException {
        List<Shift> shifts = new ArrayList<Shift>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from shifts");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Shift shift = new Shift();
                shift.setId(resultSet.getLong("id"));
                shift.setName(resultSet.getString("name"));
                shift.setShiftStarts(resultSet.getString("start"));
                shift.setShiftEnds(resultSet.getString("end"));
                shifts.add(shift);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting shift list ShiftDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return shifts;
    }
}
