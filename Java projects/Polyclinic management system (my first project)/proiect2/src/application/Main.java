package application;

import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

import javax.security.auth.login.LoginContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage primaryStage;
	// private static BorderPane mainLayout;
	private static Stage addStage;
	private static Stage deleteStage;
	private static Stage modifyStage;
	private static String userName;
	private static Stage firstModuleStage;
	private static Stage appointmentStage;
	private static Stage registerStage;
	private static Stage HistoryAndAppointmentsStage;
	private static Stage raportStage;
	private static Stage raportViewStage;
	private static Stage billStage;
	private static Stage secondModuleStage;
	private static boolean accountDate;
	private static RaportSearch currentRaportSearch;
	private  final static Integer OPENINGYEAR=2018;
	
	
	

	public static Integer getOpeningyear() {
		return OPENINGYEAR;
	}

	public static RaportSearch getCurrentRaportSearch() {
		return currentRaportSearch;
	}

	public static void setCurrentRaportSearch(RaportSearch currentRaportSearch) {
		Main.currentRaportSearch = currentRaportSearch;
	}

	public static boolean isAccountDate() {
		return accountDate;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		Main.userName = userName;
	}

	@Override
	public void start(Stage primaryStage) throws IOException, SQLException {
		this.primaryStage = primaryStage;
		DataBase.init();
		 showMainView();
		//showMenu("dan");
		//userName = "dan";
		// showAppointments();

	}

	public static void showMainView() throws IOException {

		Parent mainLayout = FXMLLoader.load(Main.class.getResource("LogIn.fxml"));

		Scene scene = new Scene(mainLayout);

		primaryStage.setTitle("Lant de policlinici");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void showMenu(String userNameLogin) throws IOException {

		userName = userNameLogin;

		BorderPane menu = FXMLLoader.load(Main.class.getResource("Menu.fxml"));
		Scene scene = new Scene(menu);

		primaryStage.setTitle("Meniu");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void goFirstModule() throws IOException {

		BorderPane menu = FXMLLoader.load(Main.class.getResource("firstModule.fxml"));
		Scene scene = new Scene(menu);

		firstModuleStage = new Stage();
		firstModuleStage.setResizable(true);
		firstModuleStage.setScene(scene);
		firstModuleStage.show();
	}

	public static void goSignUp() throws IOException {

		BorderPane signUp = FXMLLoader.load(Main.class.getResource("SignUp.fxml"));
		Scene scene = new Scene(signUp);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showAddStage() throws IOException {

		Parent add = FXMLLoader.load(Main.class.getResource("AddUser.fxml"));

		addStage = new Stage();
		addStage.setResizable(false);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(primaryStage);

		Scene scene = new Scene(add);
		addStage.setScene(scene);
		addStage.setTitle("Adaugare utilizator");
		addStage.show();
	}

	public static void showNextAdd() throws IOException {
		Parent add = FXMLLoader.load(Main.class.getResource("AddUser.fxml"));
		Scene scene = new Scene(add);

		addStage.setScene(scene);
		addStage.show();

	}

	public static void exitAddStage() {
		addStage.close();
	}

	public static void showModifyUserDataStage(boolean bool) throws IOException {

		accountDate = bool;
		Parent search = FXMLLoader.load(Main.class.getResource("modifyData.fxml"));

		modifyStage = new Stage();
		modifyStage.setResizable(false);
		modifyStage.initModality(Modality.WINDOW_MODAL);
		modifyStage.initOwner(primaryStage);

		Scene scene = new Scene(search);
		modifyStage.setScene(scene);
		modifyStage.show();
	}

	public static void showDeleteUser() throws IOException {

		Parent search = FXMLLoader.load(Main.class.getResource("searchUserForDelete.fxml"));

		deleteStage = new Stage();
		deleteStage.setResizable(false);
		deleteStage.initModality(Modality.WINDOW_MODAL);
		deleteStage.initOwner(primaryStage);

		Scene scene = new Scene(search);
		deleteStage.setScene(scene);
		deleteStage.show();
	}

	public static void showThirdModule() throws IOException {

		BorderPane menu = FXMLLoader.load(Main.class.getResource("ThirdModule.fxml"));
		Scene scene = new Scene(menu);

		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showAppointments() throws IOException {

		Parent appointment = FXMLLoader.load(Main.class.getResource("appointment.fxml"));

		appointmentStage = new Stage();
		appointmentStage.setResizable(true);
		appointmentStage.initModality(Modality.WINDOW_MODAL);
		appointmentStage.initOwner(primaryStage);

		Scene scene = new Scene(appointment);
		appointmentStage.setScene(scene);
		appointmentStage.show();

	}

	public static void showRegister() throws IOException {

		Parent register = FXMLLoader.load(Main.class.getResource("register.fxml"));

		registerStage = new Stage();
		registerStage.setResizable(true);
		registerStage.initModality(Modality.WINDOW_MODAL);
		registerStage.initOwner(primaryStage);

		Scene scene = new Scene(register);
		registerStage.setScene(scene);
		registerStage.show();
	}

	public static void showHistoryAndAppointmentsStage() throws IOException {

		Parent history = FXMLLoader.load(Main.class.getResource("historyandappointments.fxml"));

		HistoryAndAppointmentsStage = new Stage();
		HistoryAndAppointmentsStage.setResizable(true);
		HistoryAndAppointmentsStage.initModality(Modality.WINDOW_MODAL);
		HistoryAndAppointmentsStage.initOwner(primaryStage);

		Scene scene = new Scene(history);
		HistoryAndAppointmentsStage.setScene(scene);
		HistoryAndAppointmentsStage.show();
	}

	public static void showRaportStage() throws IOException {
		Parent raport = FXMLLoader.load(Main.class.getResource("raport.fxml"));
		raportStage = new Stage();
		raportStage.setResizable(true);
		raportStage.initModality(Modality.WINDOW_MODAL);
		raportStage.initOwner(primaryStage);

		Scene scene = new Scene(raport);
		raportStage.setScene(scene);
		raportStage.show();
	}
	

	public static void showRaportViewStage() throws IOException {
		Parent raport = FXMLLoader.load(Main.class.getResource("raportview.fxml"));
		raportViewStage = new Stage();
		raportViewStage.setResizable(true);
		raportViewStage.initModality(Modality.WINDOW_MODAL);
		raportViewStage.initOwner(HistoryAndAppointmentsStage);

		Scene scene = new Scene(raport);
		raportViewStage.setScene(scene);
		raportViewStage.show();
	}
	public static void showBillStage() throws IOException {
		Parent raport = FXMLLoader.load(Main.class.getResource("bill.fxml"));
		billStage = new Stage();
		billStage.setResizable(true);
		billStage.initModality(Modality.WINDOW_MODAL);
		billStage.initOwner(primaryStage);

		Scene scene = new Scene(raport);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		billStage.setScene(scene);
		billStage.show();
	}

	public static void showSecondModule() throws IOException {

		BorderPane menu = FXMLLoader.load(Main.class.getResource("secondmodule.fxml"));
		Scene scene = new Scene(menu);

		secondModuleStage = new Stage();
		secondModuleStage.setResizable(true);
		secondModuleStage.setScene(scene);
		secondModuleStage.show();
	}
public static void exitBillStage() {
		billStage.close();
	}



	public static void exitModifyUserDataStage() {
		modifyStage.close();
	}

	public static void exitDeleteUserStage() {
		deleteStage.close();
	}

	public static void exitFirstModule() {
		firstModuleStage.close();
	}

	public static void exitPrimaryStage() {
		primaryStage.close();
	}

	public static void exitAppointmentStage() {
		appointmentStage.close();
	}

	public static void exitRegister() {
		registerStage.close();
	}

	public static void exitHistoryAndAppointmentsStage() {
		HistoryAndAppointmentsStage.close();
	}

	public static void exitRaportStage() {
		raportStage.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public static void exitRaportViewStage() {
		raportViewStage.close();
	}
	public static void exitSecondModule() {
		secondModuleStage.close();
	}

}
