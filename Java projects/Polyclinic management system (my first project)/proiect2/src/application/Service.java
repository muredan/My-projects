package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Service {
	private String investigation;
	@FXML
	private TextArea result;

	public Service(String investigation, TextArea result) {
		super();
		this.investigation = investigation;
		this.result = result;
	}

	public String getInvestigation() {
		return investigation;
	}

	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	public TextArea getResult() {
		return result;
	}

	public void setResult(TextArea result) {
		this.result = result;
	}

}
