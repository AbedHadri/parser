package com.ef.util.connection;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Abed
 */
public class ConnectionUtil {

    private Connection connection;
    private PreparedStatement pStatement;
    private ResultSet rs;

    public PreparedStatement getPreparedStatement() {
        return this.pStatement;
    }

    public void prepareStatement(String sqlQuery) {
        try {
            DataSource ds = DataSourceFactory.getDataSource();
            this.connection = ds.getConnection();
            this.pStatement = this.connection.prepareStatement(sqlQuery);
        } catch (SQLException ex) {
            closeConnection();
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }
            if (this.pStatement != null) {
                this.pStatement.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (this.pStatement != null) {
                this.pStatement.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setString(int index, String value) {
        try {
            this.pStatement.setString(index, value);
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setInteger(int index, Integer value) {
        try {
            this.pStatement.setInt(index, value);
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBoolean(int index, Boolean value) {
        try {
            this.pStatement.setBoolean(index, value);
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDate(int index, Date value) {
        try {
            this.pStatement.setTimestamp(index,new Timestamp( value.getTime()));
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addBatch() {
        try {
            this.pStatement.addBatch();
            this.pStatement.clearParameters();
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearBatch() {
        try {
            this.pStatement.clearBatch();
        } catch (SQLException ex) {
            if (this.connection == null || this.pStatement == null) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, "connection or perpared statement not initialized.");
            }
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeBatch() {
        try {
            this.pStatement.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean executeQuery() {
        boolean result = false;
        try {
            result = this.pStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ResultSet executeQueryWithResultSet() {
        try {
            this.rs = this.pStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int executeUpdateQuery() {
        int result = 0;
        try {
            result = this.pStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
