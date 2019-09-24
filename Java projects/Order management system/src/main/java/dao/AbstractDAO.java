package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDAO.
 *
 * @param <T> the generic type
 */
public class AbstractDAO<T> {
	// protected static final Logger
	// LOGGER=Logger.getLogger(AbstractDAO.class.getName());

	/** The type. */
	private final Class<T> type;

	/**
	 * Instantiates a new abstract DAO.
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Creates the insert query.
	 *
	 * @return the string
	 */
	private String createInsertQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append("(");
		int sizeFields = type.getDeclaredFields().length;
		int index = 2;
		for (Field field : type.getDeclaredFields()) {
			if (!field.getName().equals("id")) {
				sb.append(field.getName());
				if (index == sizeFields) {
					break;
				}
				index++;
				sb.append(", ");
			}
		}
		sb.append(") VALUES (");
		index = 2;
		while (index <= sizeFields) {
			sb.append("?");
			if (index == sizeFields) {
				break;
			}
			index++;
			sb.append(", ");
		}
		sb.append(");");
		return sb.toString();
	}

	/**
	 * Creates the select query.
	 *
	 * @param filters the filters
	 * @return the string
	 */
	private String createSelectQuery(List<String> filters) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append(" WHERE ");
		int index = 0;
		for (Field field : type.getDeclaredFields()) {
			sb.append(field.getName() + " like '" + filters.get(index) + "%' ");
			if (index >= 2) {
				break;
			}
			sb.append(" and ");
			index++;
		}
		sb.append("; ");
		return sb.toString();
	}

	/**
	 * Inser object.
	 *
	 * @param object the object
	 */
	public void inserObject(T object) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		@SuppressWarnings("unused")
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(createInsertQuery());
			int index = 1;
			Object value = null;
			for (Field field : object.getClass().getDeclaredFields()) {
				if (!field.getName().equals("id")) {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getReadMethod();
					value = method.invoke(object);
					preparedStatement.setObject(index, value);
					index++;
				}
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates the delete query.
	 *
	 * @return the string
	 */
	private String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append(" WHERE id=?;");
		return sb.toString();
	}

	/**
	 * Delete object.
	 *
	 * @param id the id
	 */
	public void deleteObject(int id) {
		if (id != 0) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			@SuppressWarnings("unused")
			ResultSet resultSet = null;

			connection = ConnectionFactory.getConnection();
			try {
				preparedStatement = connection.prepareStatement(createDeleteQuery());
				preparedStatement.setInt(1, id);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates the update query.
	 *
	 * @return the string
	 */
	private String createUpdateQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append(" SET ");
		int sizeFields = type.getDeclaredFields().length;
		int index = 2;
		for (Field field : type.getDeclaredFields()) {
			if (!field.getName().equals("id")) {
				sb.append(field.getName());
				sb.append("=?");
				if (index == sizeFields) {
					break;
				}
				index++;
				sb.append(", ");
			}
		}
		sb.append("WHERE id=?;");
		return sb.toString();
	}

	/**
	 * Update object.
	 *
	 * @param object the object
	 */
	public void updateObject(Object object) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = ConnectionFactory.getConnection();
		try {
			preparedStatement = connection.prepareStatement(createUpdateQuery());
			int index = 1;
			Object value = null;
			for (Field field : object.getClass().getDeclaredFields()) {
				PropertyDescriptor propertyDescriptor;
				propertyDescriptor = new PropertyDescriptor(field.getName(), type);
				Method method = propertyDescriptor.getReadMethod();
				value = method.invoke(object);
				if (!field.getName().equals("id")) {
					preparedStatement.setObject(index, value);
					index++;
				} else {
					preparedStatement.setObject(object.getClass().getDeclaredFields().length, value);
				}
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Update table.
	 *
	 * @param list the list
	 */
	public void updateTable(ObservableList<T> list) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = ConnectionFactory.getConnection();
		try {

			for (T element : list) {
				preparedStatement = connection.prepareStatement(createUpdateQuery());
				int index = 1;
				Object value = null;
				for (Field field : element.getClass().getDeclaredFields()) {
					PropertyDescriptor propertyDescriptor;
					propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getReadMethod();
					value = method.invoke(element);
					if (!field.getName().equals("id")) {
						preparedStatement.setObject(index, value);
						index++;
					} else {
						preparedStatement.setObject(element.getClass().getDeclaredFields().length, value);
					}
				}
				preparedStatement.executeUpdate();
			}

		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Find by filters.
	 *
	 * @param filter1 the filter 1
	 * @param filter2 the filter 2
	 * @param filter3 the filter 3
	 * @return the observable list
	 */
	public ObservableList<T> findByFilters(String filter1, String filter2, String filter3) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			List<String> filters = new ArrayList<String>();
			filters.add(filter1);
			filters.add(filter2);
			filters.add(filter3);
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(createSelectQuery(filters));
			return createObjects(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// ConnectionFactory.close(resultSet);
			// ConnectionFactory.close(statement);
			// ConnectionFactory.close(connection);
		}
		return null;

	}

	/**
	 * Creates the objects.
	 *
	 * @param resultSet the result set
	 * @return the observable list
	 */
	private ObservableList<T> createObjects(ResultSet resultSet) {
		ObservableList<T> list = FXCollections.observableArrayList();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
