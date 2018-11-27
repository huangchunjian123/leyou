/**
 *
 */
package com.system.common.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yzz
 * @create 2016年4月9日 上午10:39:37
 *  映射数据库，自动生成Entity
 * @author WWF
 */
public class CreateModel {

	private static Connection conn = null;
	private static final String URL;
	private static final String JDBC_DRIVER;
	private static final String USER_NAME;
	private static final String PASSWORD;
	private static final String DATABASENAME;
	private static final String PACKAGENAME;
	private static final String FILE_PATH;
	static {
		/*DATABASENAME = "数据库名";
		URL = "jdbc:postgresql://localhost:5432/" + DATABASENAME;
		JDBC_DRIVER = "org.postgresql.Driver";
		USER_NAME = "数据库帐号";
		PASSWORD = "密码";
		PACKAGENAME = "导入包路径";*/
		FILE_PATH = "main/java/com/system/bean";
		DATABASENAME = "admin_system";
		URL = "jdbc:mysql://123.207.61.212:3306/" + DATABASENAME;
		JDBC_DRIVER = "com.mysql.jdbc.Driver";
		USER_NAME = "root";
		PASSWORD = "yangzhenze6712";

		PACKAGENAME = FILE_PATH.substring(FILE_PATH.indexOf("java")+5,FILE_PATH.length()).replace("/",".");
	}

	/**
	 * 获得连接
	 *
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库
	 */
	public static void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成字段静态声明
	 *
	 * @param tableName
	 * @return
	 */
	private static String CreateEntityString(String tableName) {
		String result = "package " + PACKAGENAME + ";\n\n";
		result +="import java.io.Serializable;\n" +
				"import com.system.common.anotation.Table;\n" +
				"import com.system.common.anotation.Column;\n" +
				"import lombok.Getter;\n" +
				"import lombok.Setter;\n";

		String sql = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+DATABASENAME+"' AND table_name='"
				+ tableName + "';";
		String fieldStr = "";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				FieldMeta fieldMeta = new FieldMeta();
				fieldMeta.setFieldName(toFieldName(resultSet.getString(1)));
				fieldMeta.setFieldDataType(resultSet.getString(2));
				fieldMeta.setFieldComment(resultSet.getString(3));

				if(fieldMeta.getFieldComment() != "" || null != fieldMeta.getFieldComment()){
					fieldStr += "    /**\n";
					fieldStr += "     *"+fieldMeta.getFieldComment()+"\n";
					fieldStr += "     */\n";
				}


				fieldStr +="    @Getter\n";
				fieldStr +="    @Setter\n";

				if(resultSet.getString(4).equals("PRI")){
					fieldStr +="    @Column(name=\""+resultSet.getString(1)+"\",isPK = true)\n";
				}else{
					fieldStr +="    @Column(name=\""+resultSet.getString(1)+"\")\n";
				}

				fieldStr +="    private "+fieldMeta.getFieldDataType()+" "+fieldMeta.getFieldName()+";\n\n";

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		result += "/**\n";
		result += "* @author auto create\n";
		result += "* @Date "+DateUtil.formatDate(new Date(),"yyyy/mm/dd HH:mm:ss")+"\n";
		result += "*/\n";
		result += "@Table(name=\""+tableName+"\")\n";
		result += "public class "
				+ toClassName(tableName)
				+ " implements Serializable{\n\n";
		result += "    private static final long serialVersionUID = 1L;\n";

		result +=fieldStr;





		result +="}";


		return result;
	}



	/**
	 * 获得数据库的所有表名
	 *
	 * @return
	 */
	public static List<String> getAllTables() {
		String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '"+DATABASENAME+"'";
		try {
			List<String> result = new ArrayList<String>();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (resultSet.getString(1) != null
						&& !resultSet.getString(1).isEmpty()) {
					result.add(resultSet.getString(1));
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成Entity
	 */
	public static void CreateEntity() {
		try {
			getConnection();
			List<String> tables = getAllTables();
			for (int i = 0; i < tables.size(); i++) {
				File createFolder = new File(System.getProperty("user.dir")
						+ "/src/" + FILE_PATH.replace(".", "/"));
				// 路径不存在，生成文件夹
				if (!createFolder.exists()) {
					createFolder.mkdirs();
				}
				String entityString = CreateEntityString(tables.get(i).trim());
				File entity = new File(createFolder.getAbsolutePath() + "/"
						+ toClassName(tables.get(i))
						+ ".java");
				if (entity.exists()) {
					entity.delete();
				}
				// 写入文件
				BufferedWriter out = new BufferedWriter(new FileWriter(
						entity, false));
				out.write(entityString);
				out.close();
				out = null;
				entity = null;
			}
			closeConnection();
			System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String toClassName(String str){
			StringBuffer sb = new StringBuffer();

			if(str.indexOf("_") > -1){
				str = str.substring(str.indexOf("_")+1,str.length());
			}

			if(str!=null){

				for(int i=0;i<str.length();i++){
					char c = str.charAt(i);

					if(i == 0){
						sb.append(Character.toUpperCase(c));
					}else if(c == '_'){
						++i;
						c = str.charAt(i);
						sb.append(Character.toUpperCase(c));
					}else{

						sb.append(Character.toLowerCase(c));
					}
				}
			}

			return sb.toString();
	}

	private static String toFieldName(String str){
		StringBuffer sb = new StringBuffer();

		if(str!=null){

			for(int i=0;i<str.length();i++){
				char c = str.charAt(i);

				if(i == 0){

					sb.append(Character.toLowerCase(c));
				}else if(c == '_'){

					++i;
					c = str.charAt(i);
					sb.append(Character.toUpperCase(c));
				}else{

					sb.append(Character.toLowerCase(c));
				}
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		CreateEntity();
	}

}
