package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.domain.ShiftPattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShiftPatternDAOImpl extends DAOImpl implements ShiftPatternDAO {

    public void create(ShiftPattern shiftPattern) throws DBException {
        if (shiftPattern == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
            connection.prepareStatement("insert into shift_patterns values (default, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shiftPattern.getPatternId());
            preparedStatement.setLong(2, shiftPattern.getShiftId());
            preparedStatement.setInt(3, shiftPattern.getSeqNo());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                shiftPattern.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftPatternDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    public ShiftPattern getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from shift_patterns where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ShiftPattern shiftPattern = null;
            if (resultSet.next()) {
                shiftPattern = new ShiftPattern();
                shiftPattern.setId(resultSet.getLong("id"));
                shiftPattern.setPatternId(resultSet.getLong("patternId"));
                shiftPattern.setShiftId(resultSet.getLong("shiftId"));
                shiftPattern.setSeqNo(resultSet.getInt("seqNo"));
            }
            return shiftPattern;
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftPatternDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<ShiftPattern> getAll() throws DBException {
        List<ShiftPattern> shiftPatterns = new ArrayList<ShiftPattern>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from shift_patterns");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShiftPattern shiftPattern = new ShiftPattern();
                shiftPattern.setId(resultSet.getLong("id"));
                shiftPattern.setPatternId(resultSet.getLong("patternId"));
                shiftPattern.setShiftId(resultSet.getLong("shiftId"));
                shiftPattern.setSeqNo(resultSet.getInt("seqNo"));
                shiftPatterns.add(shiftPattern);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list ShiftPatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return shiftPatterns;
    }

    public void delete(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from shift_patterns where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftPatternDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(ShiftPattern shiftPattern) throws DBException {
        if (shiftPattern == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update shift_patterns set patternId = ?, shiftId = ?, seqNo = ? "+
                            "where id = ?");
            preparedStatement.setLong(1, shiftPattern.getPatternId());
            preparedStatement.setLong(2, shiftPattern.getShiftId());
            preparedStatement.setInt(3, shiftPattern.getSeqNo());
            preparedStatement.setLong(4, shiftPattern.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftPatternDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

}