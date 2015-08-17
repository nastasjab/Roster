package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.PatternShift;
import lv.javaguru.java2.domain.Shift;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatternShiftDAOImpl extends DAOImpl implements PatternShiftDAO {

    public void create(PatternShift patternShift) throws DBException {
        if (patternShift == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
            connection.prepareStatement("insert into patterns_shifts values (default, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, patternShift.getPatternId());
            preparedStatement.setLong(2, patternShift.getShift().getId());
            preparedStatement.setInt(3, patternShift.getSeqNo());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                patternShift.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternShiftDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    public PatternShift getById(long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select patterns_shifts.id, patterns_shifts.patternId," +
                            " patterns_shifts.shiftId, shifts.name, patterns_shifts.seqNo " +
                            "from patterns_shifts left join shifts on patterns_shifts.shiftId=shifts.id " +
                            "where patterns_shifts.id = ? ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PatternShift patternShift = null;
            if (resultSet.next()) {
                patternShift = new PatternShift();
                patternShift.setId(resultSet.getLong(1));
                patternShift.setPatternId(resultSet.getLong(2));
                patternShift.getShift().setId(resultSet.getLong(3));
                patternShift.getShift().setName(resultSet.getString(4));
                patternShift.setSeqNo(resultSet.getInt(5));
            }
            return patternShift;
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternShiftDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<PatternShift> getAll(long patternId) throws DBException {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select patterns_shifts.id, patterns_shifts.patternId, patterns_shifts.shiftId, shifts.name, patterns_shifts.seqNo " +
                            "from patterns_shifts left join shifts on patterns_shifts.shiftId=shifts.id " +
                            "where patterns_shifts.patternId = ? " +
                            "ORDER BY patterns_shifts.seqNo ASC");
            preparedStatement.setLong(1, patternId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PatternShift patternShift = new PatternShift();
                patternShift.setId(resultSet.getLong(1));
                patternShift.setPatternId(resultSet.getLong(2));
                patternShift.getShift().setId(resultSet.getLong(3));
                patternShift.getShift().setName(resultSet.getString(4));
                patternShift.setSeqNo(resultSet.getInt(5));
                patternShifts.add(patternShift);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list PatternShiftDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return patternShifts;
    }

    public void deleteByPatternId(long patternId) throws DBException {

    }

    public void delete(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from patterns_shifts where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternShiftDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(PatternShift patternShift) throws DBException {
        if (patternShift == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update patterns_shifts set patternId = ?, shiftId = ?, seqNo = ? "+
                            "where id = ?");
            preparedStatement.setLong(1, patternShift.getPatternId());
            preparedStatement.setLong(2, patternShift.getShift().getId());
            preparedStatement.setInt(3, patternShift.getSeqNo());
            preparedStatement.setLong(4, patternShift.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute PatternShiftDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<PatternShift> getAll() throws DBException {
        return null;
    }

}
