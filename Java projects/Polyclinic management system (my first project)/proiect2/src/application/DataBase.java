package application;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.*;
import java.lang.Integer;
import java.lang.Double;

public class DataBase {

//	private static String url = "jdbc:mysql://sql7.freemysqlhosting.net/sql7276759";
//	private static String uid = "sql7276759";
//	private static String pw = "eJ7mmNz3pn";
	private static String url = "jdbc:mysql://35.198.106.56 /lant_de_policlinici";
	private static String uid = "root";
	private static String pw = "daN19988";
	// private BufferedReader reader;
	private static Connection con;
	
	public static DataBase app;
	public static Statement stm;


	
	public static void init() {
		/*
		 * try { Class.forName("com.mysql.jdbc.Driver"); } catch
		 * (java.lang.ClassNotFoundException e) {
		 * System.err.println("ClassNotFoundException: " +e); }
		 */
		con = null;
		try {
			con = DriverManager.getConnection(url, uid, pw);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex);
			System.exit(1);
		}

		app =new DataBase();
		
	}

	public boolean checkAccount(String username, String password) throws SQLException {
		String sql = "select username,password from utilizator where username='" + username
				+ "' and  password=aes_encrypt('" + password + "','key1234');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		if (!rst.next())
			return false;

		return true;
	}

	public List<String> getSpecialties() throws SQLException {

		List<String> specialties = new ArrayList<String>();
		String sql = "select nume_specialitate from specialitati";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		while (rst.next()) {
			specialties.add(rst.getString(1));
		}

		return specialties;
	}

	public List<String> getSkills() throws SQLException {

		List<String> skills = new ArrayList<String>();
		String sql = "select nume_competenta from competente";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		while (rst.next()) {
			skills.add(rst.getString(1));
		}

		return skills;
	}

	public String decryptPassword(String userName) throws SQLException {
		String sql = "select cast(aes_decrypt(password,'key1234') as char(100)) from utilizator where username='"
				+ userName + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

		if (rst.next()) {
			return rst.getString(1);
		}

		return null;
	}

	public void insertUser(String CNP, String nume, String prenume, String username, String password, String tip,
			String functia, String adresa, String telefon, String email, String cont_iban, String nrContract,
			String salariu, String ore, String dataAngajarii) throws SQLException {

		String sql = "call insert_angajat('" + CNP + "','" + nume + "','" + prenume + "','" + username + "','"
				+ password + "','" + tip + "','" + functia + "','" + adresa + "','" + telefon + "','" + email + "','"
				+ cont_iban + "','" + nrContract + "','" + salariu + "','" + ore + "','" + dataAngajarii + "');";
		// ////System.out.println(sql);

		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void updateUser(String CNP1, String CNP2, String nume, String prenume, String username, String password,
			String tip, String functia, String adresa, String telefon, String email, String cont_iban,
			String nrContract, String salariu, String ore, String dataAngajarii) throws SQLException {

		if (salariu.length() == 0)
			salariu = "0";
		if (ore.length() == 0)
			ore = "0";
		if (tip == null)
			tip = "";
		if (functia == null)
			functia = "";

		String sql = "call update_utilizator('" + CNP1 + "','" + CNP2 + "','" + nume + "','" + prenume + "','"
				+ username + "','" + password + "','" + tip + "','" + functia + "','" + adresa + "','" + telefon + "','"
				+ email + "','" + cont_iban + "','" + nrContract + "','" + salariu + "','" + ore + "','" + dataAngajarii
				+ "');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);

		if (!functia.equals("Asistent medical") && !functia.equals("Medic")) {
			sql = "delete from asistent_medical where CNP_asistent='" + CNP1 + "';";
			stmt.executeUpdate(sql);
			sql = "delete from medic_specialitate where CNP_medic='" + CNP1 + "';";
			stmt.executeUpdate(sql);
			sql = "delete from medic_competente where CNP_medic='" + CNP1 + "';";
			stmt.executeUpdate(sql);
			sql = "delete from medic where CNP_medic='" + CNP1 + "';";
			stmt.executeUpdate(sql);

		}

	}

	public void insertMedicData(String CNP_medic, String grad, String cod_parafa, String titlu_stiintific,
			String post_didactic, String comision) throws SQLException {
		if (comision.length() == 0)
			comision = "0";

		String sql = "call insert_dateMedic('" + CNP_medic + "','" + grad + "','" + cod_parafa + "','"
				+ titlu_stiintific + "','" + post_didactic + "','" + comision + "');";
		// ////System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void updateMedicData(String CNP_medic1, String CNP_medic2, String grad, String cod_parafa,
			String titlu_stiintific, String post_didactic, String comision) throws SQLException {
		if (comision.length() == 0)
			comision = "0";

		String sql = "call update_dateMedic('" + CNP_medic1 + "','" + CNP_medic2 + "','" + grad + "','" + cod_parafa
				+ "','" + titlu_stiintific + "','" + post_didactic + "','" + comision + "');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void insertMedicSpecialties(String CNP_medic, int idspecialty) throws SQLException {

		String sql = "call insert_medic_specialitate('" + CNP_medic + "'," + idspecialty + ");";
		// ////System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void insertMedicSkills(String CNP_medic, int idspecialty) throws SQLException {

		String sql = "call insert_medic_competente('" + CNP_medic + "'," + idspecialty + ");";
		// ////System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void insertAsistent(String CNP_asistent, String tip, String grad) throws SQLException {
		String sql = "call insert_asistent_medical('" + CNP_asistent + "','" + tip + "','" + grad + "');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public void updateAsistent(String CNP_asistent1, String CNP_asistent2, String tip, String grad)
			throws SQLException {
		String sql = "call update_asistent_medical('" + CNP_asistent1 + "','" + CNP_asistent2 + "','" + tip + "','"
				+ grad + "');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeQuery(sql);
	}

	public boolean checkCNP(String CNP) throws SQLException {
		String sql = "select * from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		if (!rst.next())
			return false;

		return true;
	}

	public boolean checUserName(String userName) throws SQLException {
		String sql = "select * from utilizator where username='" + userName + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		if (!rst.next())
			return false;

		return true;
	}

	public ObservableList<String> getAllMedicalUnitIds() throws SQLException {
		String sql = "select id from unitati_medicale";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		ObservableList<String> idList = FXCollections.observableArrayList();
		while (rst.next()) {
			if (rst.getInt(1) != 0) {
				idList.add(rst.getString(1));
			}
		}
		return idList;
	}

	public String getTypeUser(String userName) throws SQLException {

		String sql = "select tip from utilizator where username='" + userName + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		rst.next();

		return rst.getString(1);

	}

	public String getFunctionUser(String CNP) throws SQLException {

		String sql = "select functia from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		rst.next();

		return rst.getString(1);

	}

	public String getCNPUser(String userName) throws SQLException {

		String sql = "select CNP from utilizator where username='" + userName + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		rst.next();

		return rst.getString(1);

	}

	public void deleteUser(String CNP) throws SQLException {

		String sql = "call sterge_Utilizator('" + CNP + "');";

		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

	}

	public boolean checkMedicalUnit(String medicalUnit) throws SQLException {
		String sql = "select * from unitati_medicale where id='" + medicalUnit + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		if (!rst.next())
			return false;
		return true;
	}

	public List<ResultSet> getAllUserData(String CNP, String functie) throws SQLException {

		List<ResultSet> data = new ArrayList<ResultSet>();

		String sql = "select * from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		if (rst.next())
			data.add(rst);

		if (functie.equals("Medic")) {
			sql = "select * from medic where CNP_medic='" + CNP + "';";
			stmt = con.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next())
				data.add(rst);

		} else if (functie.equals("Asistent medical")) {
			sql = "select * from asistent_medical where CNP_asistent='" + CNP + "';";
			stmt = con.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next())
				data.add(rst);
		}

		return data;
	}

	public String getHiringDate(String CNP) throws SQLException {
		String sql = " select data_angajarii from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		if (!rst.next())
			return null;

		return rst.getString(1);
	}

	public ResultSet getSalaryAndHoursPerMonth(String CNP) throws SQLException {
		String sql = " select salariu,ore from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		return rst;

	}

	public boolean checkHistoryOfTheSalary(String CNP, int month, int year) throws SQLException {
		String sql = "select * from istoric_salarii where CNP_angajat='" + CNP + "' and  luna='" + month + "' and an='"
				+ year + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		if (!rst.next()) {
			return false;
		}
		return true;

	}

	public void insertHistoryOfTheSalary(String CNP, int month, int year, double salary) throws SQLException {
		String sql = "insert into istoric_salarii (CNP_angajat,luna,an,salariu) values('" + CNP + "','" + month + "','"
				+ year + "','" + salary + "');";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		//// System.out.println(sql);
	}

	public List<Integer> getMedicSkills(String CNP) throws SQLException {

		List<Integer> medicSkills = new ArrayList<Integer>();
		String sql = "call  medic_competentee('" + CNP + "');";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		while (rst.next()) {
			medicSkills.add(rst.getInt(1));
		}
		return medicSkills;

	}

	public List<Integer> getMedicSpecialties(String CNP) throws SQLException {

		List<Integer> medicSpecialties = new ArrayList<Integer>();
		String sql = "call medic_specialitati('" + CNP + "');";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);

		while (rst.next()) {
			medicSpecialties.add(rst.getInt(1));
		}
		return medicSpecialties;

	}

	public void setForeignKeyChecks(Integer x) throws SQLException {
		String sql = "set foreign_key_checks=" + x + ";";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
	}

	public ResultSet getUserDataForSearch(String nume, String prenume, String functia) throws SQLException {

		String sql = "";
		ObservableList<String> userData = FXCollections.observableArrayList();

		sql = "select CNP,nume,prenume,functia,tip from utilizator where nume like'" + nume + "%' and prenume like'"
				+ prenume + "%' and functia like'" + functia + "%';";

		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
//		////System.out.println(sql);

		return rst;

	}

	public String getUserName(String CNP) throws SQLException {

		String sql = "select username from utilizator where CNP='" + CNP + "';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		if (rst.next())
			return rst.getString(1);
		return "";
	}

	public ResultSet getGenericSchedule(String CNP, String day) throws SQLException {
		String sql = "select zi,perioada_inceput,perioada_sfarsit,id_unitate_medicala from orar_angajat where CNP_angajat= '"
				+ CNP + "'and  zi like '" + day + "%';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

		return rst;
	}

	public ResultSet getAllMedicsCNP() throws SQLException {

		String sql = "select CNP_medic from medic";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

		return rst;
	}

	public ResultSet getSpecificTimeTable(String CNP, String date) throws SQLException {
		String sql = "select data,perioada_inceput,perioada_sfarsit,id_unitate_medicala from orar_angajat where CNP_angajat= '"
				+ CNP + "'and  data like '" + date + "%';";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

		return rst;
	}

	public ResultSet getVacation(String CNP) throws SQLException {
		String sql = "select perioada_inceput,perioada_sfarsit from concediu_angajat where CNP_angajat= '" + CNP
				+ "' order by  perioada_inceput;";
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
		//// System.out.println(sql);

		return rst;

	}

	public void insertVacation(String CNP, String start, String end) throws SQLException {
		String sql = "insert into concediu_angajat(CNP_angajat,perioada_inceput,perioada_sfarsit) values('" + CNP
				+ "','" + start + "','" + end + "');";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		//// System.out.println(sql);
	}

	public void deleteVacation(String CNP, String start) throws SQLException {
		String sql = "delete from concediu_angajat where CNP_angajat='" + CNP + "' and perioada_inceput='" + start
				+ "';";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		//// System.out.println(sql);
	}

	public void deleteSpecificTimeTable(String CNP, String date) throws SQLException {
		String sql = "delete from orar_angajat where CNP_angajat='" + CNP + "' and data='" + date + "';";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		//// System.out.println(sql);
	}

	public void updateSpecificTimeTable(String CNP, String date, String start, String end, String medicalUnit)
			throws SQLException {

		if (start.length() == 0)
			start = "";
		if (end.length() == 0)
			end = "";
		if (medicalUnit.length() == 0)
			medicalUnit = "0";

		String sql = "update orar_angajat set perioada_inceput='" + start + "',perioada_sfarsit='" + end
				+ "',id_unitate_medicala='" + medicalUnit + "' where CNP_angajat='" + CNP + "' and data='" + date
				+ "';";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}

	public void updateGenericTimeTable(String CNP, String day, String start, String end, String medicalUnit)
			throws SQLException {

		if (start.equals("-") || end.equals("-") || medicalUnit.equals("-")) {
			start = "-";
			end = "-";
			medicalUnit = "0";
		}

		String sql = "update orar_angajat set perioada_inceput='" + start + "',perioada_sfarsit='" + end
				+ "',id_unitate_medicala='" + medicalUnit + "' where CNP_angajat='" + CNP + "' and zi='" + day + "';";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}

	public void insertSpecificTimeTable(String CNP, String date, String start, String end, String medicalUnit)
			throws SQLException {
		String sql = "call insert_OrarSpecific('" + CNP + "','" + medicalUnit + "','" + date + "','" + start + "','"
				+ end + "');";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery(sql);
	}

	public ResultSet getMedicalUnitTimeTable(String medicalUnit, String day) throws SQLException {
		String sql = "select ora_deschidere,ora_inchidere from orar_policlinica where id_unitate_medicala='"
				+ medicalUnit + "' and zi='" + day + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		rst.next();

		return rst;
	}

	public void updateAfterSignUp(String CNP, String userName, String password) throws SQLException {

		String sql = "update utilizator set username='" + userName + "' ,password=aes_encrypt('" + password
				+ "','key1234') where CNP='" + CNP + "';";
		//// System.out.println(sql);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}

	public ObservableList<String> getAllServices() throws SQLException {

		ObservableList<String> services = FXCollections.observableArrayList();

		String sql = "select nume_investigatie from servicii_medicale order by nume_investigatie;";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		while (rst.next())
			services.add(rst.getString(1));

		return services;
	}

	public ObservableList<String> getServicesFilteredBySpeciality(String speciality) throws SQLException {

		ObservableList<String> services = FXCollections.observableArrayList();

		String sql = "select nume_investigatie from servicii_medicale where specialitate='" + speciality + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		while (rst.next())
			services.add(rst.getString(1));

		return services;
	}

	public ResultSet getMedicSpecialitiesNames(String CNP) throws SQLException {
		String sql = "call medic_specialitatiDenumire('" + CNP + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);

		return rst;
	}

	public ObservableList<String> getFilteredServices(String skill) throws SQLException {

		ObservableList<String> services = FXCollections.observableArrayList();

		String sql = "select nume_investigatie from servicii_medicale  where competenta_necesara like '" + skill
				+ "%' order by nume_investigatie;";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		while (rst.next())
			services.add(rst.getString(1));

		return services;
	}

	public ResultSet getAllApointments(String CNP_medic, String date) throws SQLException {
		String sql = "select perioada_inceput,perioada_final from programare where CNP_medic='" + CNP_medic
				+ "' and data_calendaristica='" + date + "';";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);

		return rst;

	}

	public String getMedicalUnitForSpecificDay(String CNP_medic, String date, String day) throws SQLException {
		String sql = "select id_unitate_medicala from orar_angajat where CNP_angajat='" + CNP_medic + "' and (data = '"
				+ date + "' or zi = '" + day + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);

		rst.next();

		return rst.getString(1);
	}

	public Integer getServiceTime(String service) throws SQLException {
		String sql = "select durata from servicii_medicale where nume_investigatie='" + service + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		rst.next();

		return rst.getInt(1);
	}

	public String getSpecificService(String text) throws SQLException {

		String sql = "select nume_investigatie from servicii_medicale where nume_investigatie like '" + text
				+ "%' order by nume_investigatie;";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		if (!rst.next())
			return "";

		return rst.getString(1);
	}

	public ResultSet getMedicForSpecificService(String service) throws SQLException {
		String sql = "call gaseste_medic('" + service + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);

		return rst;
	}

	public void insertAppointment(String lastName, String firstName, String start, String end, String date,
			String CNP_medic, String serviceId, String medicalUnit) throws SQLException {
		String sql = "call insert_programare('" + lastName + "','" + firstName + "','" + start + "','" + end + "','"
				+ date + "','" + CNP_medic + "','" + serviceId + "','" + medicalUnit + "','nu');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);
	}

	public String getServiceId(String service) throws SQLException {

		String sql = "select id from servicii_medicale where nume_investigatie = '" + service + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		if (!rst.next())
			return "";

		return rst.getString(1);
	}

	public void insertPatient(String CNP, String lastName, String firstName, String address, String phone)
			throws SQLException {
		String sql = "insert into pacient(CNP,nume,prenume,adresa,telefon) values('" + CNP + "','" + lastName + "','"
				+ firstName + "','" + address + "','" + phone + "');";
		Statement stm = con.createStatement();
		stm.execute(sql);
		//// System.out.println(sql);
	}

	public void updateStateAppointment(String patientCNP, String doctorCNP, String date, String time)
			throws SQLException {
		String sql = "update programare set prezentare='da',CNP_pacient='" + patientCNP + "' where CNP_medic='"
				+ doctorCNP + "' and data_calendaristica='" + date + "' and perioada_inceput='" + time + "';";
		Statement stm = con.createStatement();
		stm.execute(sql);
		//// System.out.println(sql);
	}

	public ResultSet getAllAppointmentsData(String doctorLastName, String doctorFirstName, String doctorCNP,
			String patientLastName, String patientFirstName, String date, String service, String state)
			throws SQLException

	{
		String sql = "call cautare_programare('" + doctorLastName + "','" + doctorFirstName + "','" + doctorCNP + "','"
				+ patientLastName + "','" + patientFirstName + "','" + date + "','" + service + "','" + state + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public void deleteAppointment(String doctorCNP, String date, String time) throws SQLException {
		String sql = "delete from programare  where CNP_medic='" + doctorCNP + "' and data_calendaristica='" + date
				+ "' and perioada_inceput='" + time + "';";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);
	}

	public boolean checkPatientCNP(String CNP) throws SQLException {
		String sql = "select CNP from pacient where CNP='" + CNP + "'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		//// System.out.println(sql);
		if (!rst.next())
			return false;
		return true;

	}

	public ResultSet getBillSpecificDate(Integer year, Integer mounth) throws SQLException {

		String sql = "select * from bon_fiscal where YEAR(data) =" + year + " and MONTH(data)=" + mounth + ";";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		//// System.out.println(sql);
		return rst;

	}

	public ResultSet getReportDates(String CNP) throws SQLException {

		ObservableList<String> repotsDateList = FXCollections.observableArrayList();

		String sql = "select  id,data_consultatiei,tip_raport from raport_pacient where CNP_pacient ='" + CNP
				+ "' and terminat is NOT NULL order by data_consultatiei;";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		//// System.out.println(sql);

		return rst;

	}

	public ResultSet getUnfinishedRaports(String medicCNP, String typeRaport, String patientCNP, String patientLastName,
			String patientFirstName) throws SQLException {

		ObservableList<String> raports = FXCollections.observableArrayList();
		String sql = "call cauta_raportNeterminat('" + medicCNP + "','" + typeRaport + "','" + patientCNP + "','"
				+ patientLastName + "','" + patientFirstName + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		ResultSet rst = stm.executeQuery(sql);

		return rst;

	}

	public void deleteServicesRaport(Integer id) throws SQLException {

		String sql = "delete from investigatie_raport where id_raport='" + id + "';";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);
	}

	public void deleteUnfinishedRaport(Integer id) throws SQLException {

		deleteServicesRaport(id);

		String sql = "delete from raport_pacient where id='" + id + "';";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);

	}

	public void addRaport(String patientCNP, String patientFirstName, String patientLastName, String cnp_medic,
			String recDoctorLastName, String recDoctorFirstName, String nurseLastName, String nurseFirstName,
			String data, String diagnostic, String recommendations, String simptoms) throws SQLException {
		ResultSet rst = getCurrentUserName(cnp_medic);
		if (rst.next()) {
			String sql = "call adauga_raport('" + patientCNP + "','" + patientLastName + "','" + patientFirstName
					+ "','" + rst.getString(1) + "','" + rst.getString(2) + "','" + recDoctorLastName + "','"
					+ recDoctorFirstName + "','" + nurseLastName + "','" + nurseFirstName + "','" + data + "','"
					+ simptoms + "','" + diagnostic + "','" + recommendations + "');";
		}
	}

	public void insertRaport(String patientCNP, String medicLastName, String medicFirstName, String medicCNP,
			String medicRecommendedLastName, String medicRecommendedFirstName, String asistentLastName,
			String asistentFirstName, String date, String symptoms, String diagnostic, String recommendations,
			String seal, String rapotType, String speciality) throws SQLException {
		String sql = "call insert_raport('" + patientCNP + "','" + medicLastName + "','" + medicFirstName + "','"
				+ medicCNP + "','" + medicRecommendedLastName + "','" + medicRecommendedFirstName + "','"
				+ asistentLastName + "','" + asistentFirstName + "','" + date + "','" + symptoms + "','" + diagnostic
				+ "','" + recommendations + "','" + seal + "','" + rapotType + "','" + speciality + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);

	}

	public void updateRaport(Integer idRaport, String patientCNP, String medicLastName, String medicFirstName,
			String medicCNP, String medicRecommendedLastName, String medicRecommendedFirstName, String asistentLastName,
			String asistentFirstName, String symptoms, String diagnostic, String recommendations, String seal,
			String rapotType) throws SQLException {
		String sql = "call update_raport('" + idRaport + "','" + patientCNP + "','" + medicLastName + "','"
				+ medicFirstName + "','" + medicCNP + "','" + medicRecommendedLastName + "','"
				+ medicRecommendedFirstName + "','" + asistentLastName + "','" + asistentFirstName + "','" + symptoms
				+ "','" + diagnostic + "','" + recommendations + "','" + seal + "','" + rapotType + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);

	}

	public String getMedicSeal(String CNP) throws SQLException {

		String sql = "select cod_parafa from medic where CNP_medic='" + CNP + "';";
		//// System.out.println(sql);
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		if (!rst.next())
			return null;

		//// System.out.println(rst.getString(1));
		return rst.getString(1);

	}

	public void setRaportFinished(Integer idRaport) throws SQLException {
		String sql = "update raport_pacient set terminat='da' where id='" + idRaport + "';";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);
	}

	public int getLastidRaport() throws SQLException {
		String sql = "SELECT id FROM raport_pacient ORDER BY id DESC LIMIT 1;";
		//// System.out.println(sql);
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		rst.next();
		return rst.getInt(1);

	}

	public ResultSet getPatientName(String CNP) throws SQLException {
		String sql = "select nume, prenume from pacient where CNP='" + CNP + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public void insertServiceRaport(Integer idRaport, String idService, String result) throws SQLException {
		String sql = "insert into investigatie_raport(id_raport,id_serviciu_medical,rezultat) values('" + idRaport
				+ "','" + idService + "','" + result + "');";
		Statement stm = con.createStatement();
		//// System.out.println(sql);
		stm.executeUpdate(sql);
	}

	public ResultSet getCurrentUserName(String cnp) throws SQLException {
		String sql = "select nume, prenume from utilizator where CNP='" + cnp + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public ResultSet getAllRaportsData(Integer id) throws SQLException {
		String sql = "call date_raport('" + id + "');";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public ResultSet getReportServices(Integer id) throws SQLException {
		String sql = "call servicii_raport('" + id + "');";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public void deleteServiceFromRaport(Integer idRaport, String idService) throws SQLException {
		String sql = "delete from investigatie_raport where id_raport='" + idRaport + "' and id_serviciu_medical='"
				+ idService + "';";
		Statement stm = con.createStatement();
		stm.executeUpdate(sql);
		//// System.out.print(sql);
	}

	public void updateResultServiceFromRaport(Integer idRaport, String idService, String result) throws SQLException {
		String sql = "update investigatie_raport set rezultat='" + result + "' where id_raport='" + idRaport
				+ "' and id_serviciu_medical='" + idService + "';";
		Statement stm = con.createStatement();
		stm.executeUpdate(sql);
		//// System.out.print(sql);
	}

	public void payService(Integer idRaport, String idService) throws SQLException {
		String sql = "call serviciu_platit('" + idRaport + "','" + idService + "');";
		Statement stm = con.createStatement();
		stm.executeQuery(sql);
	}

	public void processBill(String CNP, Double value, String data, String unitate, String specialitate)
			throws SQLException {
		String sql = "insert into bon_fiscal (cnp_medic,total,specialitate,unitate_medicala,data) values ('" + CNP
				+ "','" + value + "','" + specialitate + "','" + unitate + "','" + data + "');";
		Statement stm = con.createStatement();
		//// System.out.print(sql);
		stm.execute(sql);
	}

	public ResultSet getPacientRaport(String patientCNP, String patientLastName, String patientFirstName)
			throws SQLException {
		String sql = "call cauta_raport_pacient('" + patientCNP + "','" + patientFirstName + "','" + patientLastName
				+ "');";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

	public boolean checkGenericSchedule(String CNP) throws SQLException {
		String sql = "select * from orar_angajat where id_unitate_medicala is NULL and zi is not NULL and CNP_angajat='"
				+ CNP + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);

		if (!rst.next()) {
			return false;
		}

		return true;

	}

	public String getSalary(String CNP, int year, int month) throws SQLException {
		String sql = "select	salariu from istoric_salarii where CNP_angajat='" + CNP + "' and luna='" + month
				+ "' and an='" + year + "';";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		if (!rst.next()) {
			return "Date inexistente";
		}
		return rst.getString(1);

	}

	public ResultSet getAllUsersCNP() throws SQLException {
		String sql = " select CNP from utilizator;";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(sql);
		return rst;
	}

}
