package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class SecondModuleController {

	@FXML
	private TableView<UserSearch> userSearchTableView;
	@FXML
	private TableColumn<UserSearch, String> userCNP;
	@FXML
	private TableColumn<UserSearch, String> userLastName;
	@FXML
	private TableColumn<UserSearch, String> userFirstName;
	@FXML
	private TableColumn<UserSearch, String> userFunction;

	@FXML
	private TextField txtNume;

	@FXML
	private TextField txtPrenume;

	@FXML
	private TextField txtFunction;

	@FXML
	private ComboBox<Integer> monthsClinicProfitComboBox;
	@FXML
	private ComboBox<Integer> yearsClinicProfitComboBox;
	@FXML
	private ComboBox<Integer> monthsMedicProfit;
	@FXML
	private ComboBox<Integer> yearsMedicProfit;
	@FXML
	private ComboBox<Integer> monthSalaryComboBox;
	@FXML
	private ComboBox<Integer> yearSalaryComboBox;
	@FXML
	private TextField txtSalary;
	@FXML
	private ComboBox<String> specialitiesComboBox;
	@FXML
	private ComboBox<String> UMComboBox;
	@FXML
	private TextField txtProfit;
	@FXML
	private TextField txtClinicProfit;
	@FXML
	private VBox economicVbox;
	@FXML
	private VBox medicProfitVbox;

	

	ObservableList<Integer> monthsClinicProfitList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			11, 12);
	ObservableList<Integer> yearsClinicProfitList = FXCollections.observableArrayList();

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	private String logInCNP = new String();

	@FXML
	private void initialize() throws SQLException {

		Integer intialyear = Main.getOpeningyear();

		while (intialyear <= LocalDate.now().getYear()) {
			yearsClinicProfitList.add(intialyear++);
		}

		initializeUserSearchTableView();
		monthsClinicProfitComboBox.setItems(monthsClinicProfitList);
		yearsClinicProfitComboBox.setItems(yearsClinicProfitList);
		monthSalaryComboBox.setItems(monthsClinicProfitList);
		yearSalaryComboBox.setItems(yearsClinicProfitList);
		monthsMedicProfit.setItems(monthsClinicProfitList);
		yearsMedicProfit.setItems(yearsClinicProfitList);
		UMComboBox.setItems(DataBase.app.getAllMedicalUnitIds());

		logInCNP = DataBase.app.getCNPUser(Main.getUserName());
		initializeSalary();
		setSpecialitiesComboBox(logInCNP);

		economicVbox.managedProperty().bind(economicVbox.visibleProperty());
		medicProfitVbox.managedProperty().bind(medicProfitVbox.visibleProperty());
		
		String currentFunction = DataBase.app.getFunctionUser(logInCNP);
		String currentUserType = DataBase.app.getTypeUser(Main.getUserName());
		boolean type = (currentUserType.equals("Super-administrator") || currentUserType.equals("Administrator"));

		if (currentFunction.equals("Medic") && !type) {
			economicVbox.setVisible(false);
		} else if (!currentFunction.equals("Expert financiar") && !type) {
			economicVbox.setVisible(false);		
			medicProfitVbox.setVisible(false);;
		}

	}

	private void setSpecialitiesComboBox(String CNP) throws SQLException {

		if (DataBase.app.getFunctionUser(CNP).equals("Medic")) {
			ObservableList<String> specialitiesList = FXCollections.observableArrayList();
			ResultSet rst = DataBase.app.getMedicSpecialitiesNames(CNP);
			while (rst.next()) {
				specialitiesList.add(rst.getString(1));
			}
			specialitiesComboBox.setItems(specialitiesList);
		} else {
			specialitiesComboBox.setItems(null);
		}

	}

	@FXML
	private void initilizeSpecialities() throws SQLException {

		if (!userSearchTableView.getSelectionModel().isEmpty()) {
			setSpecialitiesComboBox(userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP());
			txtProfit.setText("");
			setSalaryText();
		}

	}

	@FXML
	private void setTotalProfit() throws SQLException {

		String currentCNP = logInCNP;
		if (!userSearchTableView.getSelectionModel().isEmpty()) {
			currentCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
		}

		if (DataBase.app.getFunctionUser(currentCNP).equals("Medic") && !monthsMedicProfit.getSelectionModel().isEmpty()
				&& !yearsMedicProfit.getSelectionModel().isEmpty()) {

			int year = yearsMedicProfit.getSelectionModel().getSelectedItem();
			int month = monthsMedicProfit.getSelectionModel().getSelectedItem();
			//System.out.println(year + " " + month);
			//System.out.println(LocalDate.now().getYear() + " " + LocalDate.now().getMonthValue());
			if (year <= LocalDate.now().getYear()) {
				int ok = 1;
				if (year == LocalDate.now().getYear() && month >= LocalDate.now().getMonthValue())
					ok = 0;
				if (DataBase.app.getSalary(currentCNP, year, month).equals("Date inexistente"))
					ok = 0;

				if (ok == 1) {
					double total = 0;
					double income = 0;
					double commission = 0;
					double salary = new Double(DataBase.app.getSalary(currentCNP, year, month));
					ResultSet rst = DataBase.app.getBillSpecificDate(year, month);
					while (rst.next()) {
						income += rst.getDouble(3);
					}

					List<ResultSet> data = DataBase.app.getAllUserData(currentCNP, "Medic");

					commission = data.get(1).getDouble(6) / 100;
					commission *= income;
					total = income - salary - commission;
					txtProfit.setText(String.valueOf(total));
					specialitiesComboBox.getSelectionModel().clearSelection();
					UMComboBox.getSelectionModel().clearSelection();
				}
			}
		}
	}

	@FXML
	private void setMedicalUnitProfit() throws SQLException {

		String currentCNP = logInCNP;
		if (!userSearchTableView.getSelectionModel().isEmpty()) {
			currentCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
		}

		if (DataBase.app.getFunctionUser(currentCNP).equals("Medic") && !monthsMedicProfit.getSelectionModel().isEmpty()
				&& !yearsMedicProfit.getSelectionModel().isEmpty() && !UMComboBox.getSelectionModel().isEmpty()) {

			int year = yearsMedicProfit.getSelectionModel().getSelectedItem();
			int month = monthsMedicProfit.getSelectionModel().getSelectedItem();
			String medicalUnit = UMComboBox.getSelectionModel().getSelectedItem();
			//System.out.println(year + " " + month);
			//System.out.println(LocalDate.now().getYear() + " " + LocalDate.now().getMonthValue());
			if (year <= LocalDate.now().getYear()) {
				int ok = 1;
				if (year == LocalDate.now().getYear() && month >= LocalDate.now().getMonthValue())
					ok = 0;
				if (DataBase.app.getSalary(currentCNP, year, month).equals("Date inexistente"))
					ok = 0;

				if (ok == 1) {
					double total = 0;
					double income = 0;
					double commission = 0;
					double salary = new Double(DataBase.app.getSalary(currentCNP, year, month));
					ResultSet rst = DataBase.app.getBillSpecificDate(year, month);
					double index1 = 0;
					double index2 = 0;
					while (rst.next()) {
						index1++;
						if (rst.getString(5).equals(medicalUnit)) {
							income += rst.getDouble(3);
							index2++;
						}
					}

					List<ResultSet> data = DataBase.app.getAllUserData(currentCNP, "Medic");

					commission = data.get(1).getDouble(6) / 100;
					commission *= income;
					salary = salary / index1 * index2;
					total = income - salary - commission;
					//System.out.println(income + "\t" + salary + "\t" + commission);
					txtProfit.setText(String.valueOf(total));
					specialitiesComboBox.getSelectionModel().clearSelection();

				}
			}
		}
	}

	@FXML
	private void setSpecialityProfit() throws SQLException {

		String currentCNP = logInCNP;
		if (!userSearchTableView.getSelectionModel().isEmpty()) {
			currentCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
		}

		if (DataBase.app.getFunctionUser(currentCNP).equals("Medic") && !monthsMedicProfit.getSelectionModel().isEmpty()
				&& !yearsMedicProfit.getSelectionModel().isEmpty()
				&& !specialitiesComboBox.getSelectionModel().isEmpty()) {

			int year = yearsMedicProfit.getSelectionModel().getSelectedItem();
			int month = monthsMedicProfit.getSelectionModel().getSelectedItem();
			String speciality = specialitiesComboBox.getSelectionModel().getSelectedItem();
			//System.out.println(year + " " + month);
			//System.out.println(LocalDate.now().getYear() + " " + LocalDate.now().getMonthValue());
			if (year <= LocalDate.now().getYear()) {
				int ok = 1;
				if (year == LocalDate.now().getYear() && month >= LocalDate.now().getMonthValue())
					ok = 0;
				if (DataBase.app.getSalary(currentCNP, year, month).equals("Date inexistente"))
					ok = 0;

				if (ok == 1) {
					double total = 0;
					double income = 0;
					double commission = 0;
					double salary = new Double(DataBase.app.getSalary(currentCNP, year, month));
					ResultSet rst = DataBase.app.getBillSpecificDate(year, month);
					double index1 = 0;
					double index2 = 0;
					while (rst.next()) {
						index1++;
						if (rst.getString(4).equals(speciality)) {
							income += rst.getDouble(3);
							index2++;
						}
					}

					List<ResultSet> data = DataBase.app.getAllUserData(currentCNP, "Medic");

					commission = data.get(1).getDouble(6) / 100;
					commission *= income;
					//System.out.println(index1+"\t"+index2);
					salary = salary / index1 * index2;
					total = income - salary - commission;
					//System.out.println(income + "\t" + salary + "\t" + commission);
					txtProfit.setText(String.valueOf(total));
					UMComboBox.getSelectionModel().clearSelection();

				}
			}
		}
	}

	@FXML
	private void setClinicProfit() throws SQLException {

		if (!monthsClinicProfitComboBox.getSelectionModel().isEmpty()
				&& !yearsClinicProfitComboBox.getSelectionModel().isEmpty()) {
			Integer month = monthsClinicProfitComboBox.getSelectionModel().getSelectedItem();
			Integer year = yearsClinicProfitComboBox.getSelectionModel().getSelectedItem();

			ResultSet rstCNP = DataBase.app.getAllUsersCNP();
			double total = 0;
			double medicTotal = 0;
			double salaryTotal = 0;

			while (rstCNP.next()) {
				String currentCNP = rstCNP.getString(1);

				if (!DataBase.app.getSalary(currentCNP, year, month).equals("Date inexistente")) {
					if (!DataBase.app.getFunctionUser(currentCNP).equals("Medic")) {
						salaryTotal += new Double(DataBase.app.getSalary(currentCNP, year, month));
					} else {

						double income = 0;
						double commission = 0;
						double salary = new Double(DataBase.app.getSalary(currentCNP, year, month));
						ResultSet rst = DataBase.app.getBillSpecificDate(year, month);
						while (rst.next()) {
							income += rst.getDouble(3);
						}

						List<ResultSet> data = DataBase.app.getAllUserData(currentCNP, "Medic");

						commission = data.get(1).getDouble(6) / 100;
						commission *= income;
						medicTotal = income - salary - commission;

					}

				}

			}
			total = medicTotal - salaryTotal;
			txtClinicProfit.setText(String.valueOf(total));
		}
	}

	@FXML
	private void initializeSalary() throws SQLException {

		String currentCNP = new String();
		ResultSet rstCNP = DataBase.app.getAllUsersCNP();
		while (rstCNP.next()) {

			currentCNP = rstCNP.getString(1);

			ResultSet rst = DataBase.app.getSalaryAndHoursPerMonth(currentCNP);
			ResultSet rstGenericSchedule = null;
			Double salary = null;
			Double hoursPerMonth = null;

			if (rst.next()) {
				salary = rst.getDouble(1);
				hoursPerMonth = rst.getDouble(2);
			}

			LocalDate date = LocalDate.parse(DataBase.app.getHiringDate(currentCNP));
			int indexMonth = date.getMonthValue();
			int indexYear = date.getYear();
			LocalDate currentDate = LocalDate.now();
			int days = 0;

			rstGenericSchedule = DataBase.app.getGenericSchedule(currentCNP, RO.convertToRO(date.getDayOfWeek().toString()));

			if (rstGenericSchedule.next() && (rstGenericSchedule.getString(2) != null)) {
				while (date.getMonthValue() < currentDate.getMonthValue() || date.getYear() < currentDate.getYear()) {

					if (!DataBase.app.checkHistoryOfTheSalary(currentCNP, date.getMonthValue(), date.getYear())) {
						rstGenericSchedule = DataBase.app.getGenericSchedule(currentCNP,
								RO.convertToRO(date.getDayOfWeek().toString()));
						if (rstGenericSchedule.next()) {

							if (!rstGenericSchedule.getString(2).equals("-")
									&& checkVacation(currentCNP, date.toString())) {
								days++;
								date = date.plusDays(1);

							} else {

								date = date.plusDays(1);

							}
						}
						if (indexMonth != date.getMonthValue()) {

							//System.out.println(salary + "   " + days + "   " + hoursPerMonth);
							Double salaryPerMounth = (salary * (double) days * 10.0) / hoursPerMonth;

							int currentYear = 0;
							if (indexYear != date.getYear()) {
								currentYear = indexYear;
								indexYear = date.getYear();
							} else {
								currentYear = date.getYear();
							}

							if (DataBase.app.getFunctionUser(currentCNP).equals("Medic")) {

								double income = 0;
								double commission = 0;

								ResultSet rstBill = DataBase.app.getBillSpecificDate(currentYear, indexMonth);
								while (rstBill.next()) {
									income += rstBill.getDouble(3);
								}

								List<ResultSet> data = DataBase.app.getAllUserData(currentCNP, "Medic");
								commission = data.get(1).getDouble(6) / 100;
								commission *= income;
								salaryPerMounth += commission;
							}

							DataBase.app.insertHistoryOfTheSalary(currentCNP, indexMonth, currentYear, salaryPerMounth);
							days = 0;
							indexMonth = date.getMonthValue();
						}
					} else {
						date = date.plusMonths(1);
						date = YearMonth.of(date.getYear(), date.getMonth()).atDay(1);
					}
				}
			}
		}
	}

	@FXML
	private void setSalaryText() throws SQLException {

		if (!userSearchTableView.getSelectionModel().isEmpty()) {
			logInCNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();
		}

		if (!yearSalaryComboBox.getSelectionModel().isEmpty() && !monthSalaryComboBox.getSelectionModel().isEmpty()) {
			txtSalary.setText(DataBase.app.getSalary(logInCNP, yearSalaryComboBox.getSelectionModel().getSelectedItem(),
					monthSalaryComboBox.getSelectionModel().getSelectedItem()));
		}
	}

	private boolean checkVacation(String CNP, String dateCheck) throws SQLException {

		ResultSet rst = DataBase.app.getVacation(CNP);
		LocalDate date = LocalDate.parse(dateCheck);

		while (rst.next()) {
			LocalDate start = LocalDate.parse(rst.getString(1));
			LocalDate end = LocalDate.parse(rst.getString(2));

			if (((date.isAfter(start) && date.isBefore(end)) || date.equals(start) || date.equals(end)))
				return false;
		}
		return true;
	}

	@FXML
	private void initializeUserSearchTableView() throws SQLException {

		ObservableList<UserSearch> userSearchList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getUserDataForSearch(txtNume.getText(), txtPrenume.getText(), txtFunction.getText());

		while (rst.next()) {
			if (!rst.getString(1).equals(DataBase.app.getCNPUser(Main.getUserName())))
				userSearchList
						.add(new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
		}

		userCNP.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userCNP"));
		userLastName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userLastName"));
		userFirstName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFirstName"));
		userFunction.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFunction"));

		userSearchTableView.setItems(userSearchList);

	}

	@FXML
	private void backButton() throws IOException {
		Main.showMenu(Main.getUserName());
		Main.exitSecondModule();
	}

}
