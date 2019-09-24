package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchUserForDeleteController {

	@FXML
	private TextField txtNume;

	@FXML
	private TextField txtPrenume;

	@FXML
	private TextField txtFunctia;

	@FXML
	private Button modify;

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

	private ObservableList<String> results;


	@FXML
	private void initialize() throws SQLException {

		showSeaerchRezult();

		modify.setText("Sterge");
	}

	@FXML
	private void cancelButton() {
		Main.exitDeleteUserStage();
	}

	@FXML
	private void showSeaerchRezult() throws SQLException {

		ObservableList<UserSearch> userSearchList = FXCollections.observableArrayList();

		ResultSet rst = DataBase.app.getUserDataForSearch(txtNume.getText(), txtPrenume.getText(), txtFunctia.getText());

	while (rst.next()) {
		if (DataBase.app.getTypeUser(Main.getUserName()).equals("Super-administrator")) {
				if (!rst.getString(1).equals(DataBase.app.getCNPUser(Main.getUserName())))
			userSearchList
					.add(new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));

		} else if (DataBase.app.getTypeUser(Main.getUserName()).equals("Administrator")) {
			if (rst.getString(5).equals("Angajat")) {
				userSearchList.add(
						new UserSearch(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
			}
		}

	}
		userCNP.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userCNP"));
		userLastName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userLastName"));
		userFirstName.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFirstName"));
		userFunction.setCellValueFactory(new PropertyValueFactory<UserSearch, String>("userFunction"));

		userSearchTableView.setItems(userSearchList);

	}

	@FXML
	private void deleteButton() throws SQLException {

		String CNP = userSearchTableView.getSelectionModel().getSelectedItem().getUserCNP();

		if (DataBase.app.getTypeUser(Main.getUserName()).equals("Super-administrator")) {
			DataBase.app.deleteUser(CNP);
		} else if (DataBase.app.getTypeUser(DataBase.app.getUserName(CNP)).equals("Angajat")) {
			DataBase.app.deleteUser(CNP);
		}

		showSeaerchRezult();

	}

}