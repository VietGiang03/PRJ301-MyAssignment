/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Employee;

/**
 *
 * @author Giang123
 */
public class EmployeeDBContext extends DBContext<Employee> {

    @Override
    public void insert(Employee model) {
        PreparedStatement stm_insert = null;
        PreparedStatement stm_query = null;
        try {
            connection.setAutoCommit(false);
            String sql_insert_emp = "INSERT INTO [Employee]\n"
                    + "           ([ename]\n"
                    + "           ,[gender]\n"
                    + "           ,[dob]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            String sql_get_eid = "SELECT @@IDENTITY as eid";

            stm_insert = connection.prepareStatement(sql_insert_emp);
            stm_insert.setString(1, model.getName());
            stm_insert.setInt(4, model.getDept().getId());
            stm_insert.setBoolean(2, model.isGender());
            stm_insert.setDate(3, model.getDob());
            stm_insert.executeUpdate();

            stm_query = connection.prepareStatement(sql_get_eid);
            ResultSet rs = stm_query.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("eid"));
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
                stm_insert.close();
                stm_query.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Employee model) {
        PreparedStatement stm_insert = null;
        try {
            String sql_insert_emp = "UPDATE [Employee]\n"
                    + "   SET [ename] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[dob] = ?\n"
                    + "      ,[did] = ?\n"
                    + " WHERE [eid] = ?";

            stm_insert = connection.prepareStatement(sql_insert_emp);
            stm_insert.setString(1, model.getName());
            stm_insert.setInt(4, model.getDept().getId());
            stm_insert.setBoolean(2, model.isGender());
            stm_insert.setDate(3, model.getDob());
            stm_insert.setInt(5, model.getId());
            stm_insert.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm_insert.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Employee model) {
        PreparedStatement stm_insert = null;
        try {
            String sql_insert_emp = "DELETE [Employee]\n"
                    + " WHERE [eid] = ?";
            stm_insert = connection.prepareStatement(sql_insert_emp);
            stm_insert.setInt(1, model.getId());
            stm_insert.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm_insert.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Employee get(int id) {
        PreparedStatement stm = null;
        Employee e = null;
        try {
            String sql = "SELECT e.eid,e.ename,e.gender,e.dob,d.did,d.dname \n"
                    + "FROM Employee e INNER JOIN Department d ON e.did = d.did WHERE e.eid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));

                e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setDept(d);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return e;
    }

    @Override
    public ArrayList<Employee> list() {
        PreparedStatement stm = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "SELECT e.eid,e.ename,e.gender,e.dob,d.did,d.dname \n"
                    + "FROM Employee e INNER JOIN Department d ON e.did = d.did";
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setDept(d);

                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return employees;
    }

    public ArrayList<Employee> search(Integer id, String name, Boolean gender, Date from, Date to, Integer did) {
        PreparedStatement stm = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Integer param_index = 0;
            HashMap<Integer, Object> params = new HashMap<>();
            String sql = "SELECT e.eid,e.ename,e.gender,e.dob,d.did,d.dname \n"
                    + "FROM Employee e INNER JOIN Department d ON e.did = d.did WHERE (1=1) ";
            if (id != null) {
                sql += " AND e.eid = ? ";
                param_index++;
                params.put(param_index, id);
            }

            if (name != null) {
                sql += " AND e.ename LIKE '%'+?+'%' ";
                param_index++;
                params.put(param_index, name);
            }

            if (gender != null) {
                sql += " AND e.gender = ? ";
                param_index++;
                params.put(param_index, gender);
            }
            if (from != null) {
                sql += " AND e.dob >= ? ";
                param_index++;
                params.put(param_index, from);
            }
            if (to != null) {
                sql += " AND e.dob <= ? ";
                param_index++;
                params.put(param_index, to);
            }
            if (did != null) {
                sql += " AND d.did = ? ";
                param_index++;
                params.put(param_index, did);
            }

            stm = connection.prepareStatement(sql);

            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                Integer key = entry.getKey();
                Object val = entry.getValue();
                stm.setObject(key, val);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setDept(d);

                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return employees;
    }

}
