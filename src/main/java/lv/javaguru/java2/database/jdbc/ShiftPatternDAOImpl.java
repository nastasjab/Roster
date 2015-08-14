package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.ShiftPattern;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShiftPatternDAOImpl extends DAOImpl implements ShiftPatternDAO {

    public void create(ShiftPattern shiftPattern) throws DBException {
        if (shiftPattern == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
            connection.prepareStatement("insert into patterns_shifts values (default, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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
                    .prepareStatement("select patterns_shifts.id, patterns_shifts.patternId," +
                            " patterns_shifts.shiftId, shifts.name, patterns_shifts.seqNo " +
                            "from patterns_shifts left join shifts on patterns_shifts.shiftId=shifts.id " +
                            "where patterns_shifts.id = ? ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ShiftPattern shiftPattern = null;
            if (resultSet.next()) {
                shiftPattern = new ShiftPattern();
                shiftPattern.setId(resultSet.getLong(1));
                shiftPattern.setPatternId(resultSet.getLong(2));
                shiftPattern.setShiftId(resultSet.getLong(3));
                shiftPattern.setShiftName(resultSet.getString(4));
                shiftPattern.setSeqNo(resultSet.getInt(5));
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

    public List<ShiftPattern> getAll(long patternId) throws DBException {
        List<ShiftPattern> shiftPatterns = new ArrayList<ShiftPattern>();
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
                ShiftPattern shiftPattern = new ShiftPattern();
                shiftPattern.setId(resultSet.getLong(1));
                shiftPattern.setPatternId(resultSet.getLong(2));
                shiftPattern.setShiftId(resultSet.getLong(3));
                shiftPattern.setShiftName(resultSet.getString(4));
                shiftPattern.setSeqNo(resultSet.getInt(5));
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
                    .prepareStatement("delete from patterns_shifts where id = ?");
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
                    .prepareStatement("update patterns_shifts set patternId = ?, shiftId = ?, seqNo = ? "+
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

    public void updateShiftId(long id, long shiftId) throws DBException {
        if (id == 0) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update patterns_shifts set shiftId = ? where id = ?");
            preparedStatement.setLong(1, shiftId);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ShiftPatternDAOImpl.updateShiftId()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Long> getShiftIdsByPatternId(long id) throws DBException {
        List<Long> shifts = new ArrayList<Long>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select shiftId from patterns_shifts WHERE patternId = ? ORDER BY seqNo ASC ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shifts.add(resultSet.getLong("shiftId"));
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting getShiftIdsByPatternId list ShiftPatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return shifts;
    }

    public List<Shift> getShiftsByPatternId(long id) throws DBException {
        List<Shift> shifts = new ArrayList<Shift>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select shifts.id, shifts.name, shifts.start, shifts.end from shifts, patterns_shifts " +
                            "WHERE patterns_shifts.patternId = ? AND shifts.id = patterns_shifts.shiftId " +
                            "ORDER BY patterns_shifts.seqNo ASC ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Shift s = new Shift();
                s.setId(resultSet.getLong(1));
                s.setName(resultSet.getString(2));
                s.setShiftStarts(resultSet.getString(3));
                s.setShiftEnds(resultSet.getString(4));
                shifts.add(s);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting ShiftsByPatternId list ShiftPatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return shifts;
    }

    public int getNextSequenceNo(long id) throws DBException {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select max(seqNo) + 1 from patterns_shifts where patternid = ? limit 1");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (Throwable e) {
            System.out.println("Exception while executing getNextSequenceNo list ShiftPatternDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return result;
    }
}
