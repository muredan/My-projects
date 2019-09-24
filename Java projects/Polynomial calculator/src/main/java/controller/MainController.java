package controller;

import java.util.ArrayList;
import java.util.List;

import exception.DivisionException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.PolinomyalOperations;
import model.Polynomial;
import view.MainView;

public class MainController {

	private MainView mainView;
	private Polynomial polynomial1 = new Polynomial();
	private Polynomial polynomial2 = new Polynomial();

	public void start(Stage stage) {
		mainView = new MainView(stage);
		mainView.show();
		initializeButtonEvents();

	}

	public void initializeButtonEvents() {

		mainView.addAdditionButtonActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de adunare

				if (!polynomial1.polynomialConverter(mainView.getPol1())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					mainView.showResult(PolinomyalOperations.addition(polynomial1, polynomial2).getPolynomialString());
				}

			}
		});

		mainView.addSubtractionButtonActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de scadere

				if (!polynomial1.polynomialConverter(mainView.getPol1())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					mainView.showResult(
							PolinomyalOperations.subtraction(polynomial1, polynomial2).getPolynomialString());
				}

			}
		});

		mainView.addMultiplicationButtonActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de inmultire

				if (!polynomial1.polynomialConverter(mainView.getPol1())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					mainView.showResult(
							PolinomyalOperations.multiplication(polynomial1, polynomial2).getPolynomialString());
				}

			}
		});

		mainView.addDerivationButtonActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de derivare

				if (mainView.firstPolynomialRadioButtonIsSelected()) {
					if (!polynomial1.polynomialConverter(mainView.getPol1())) {
						mainView.displayMessageLabel("format incorect", Color.RED);
					} else {
						mainView.showResult(PolinomyalOperations.derivation(polynomial1).getPolynomialString());
					}
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					mainView.showResult(PolinomyalOperations.derivation(polynomial2).getPolynomialString());
				}

			}
		});

		mainView.addIntegrationButtonnActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de integrare

				if (mainView.firstPolynomialRadioButtonIsSelected()) {
					if (!polynomial1.polynomialConverter(mainView.getPol1())) {
						mainView.displayMessageLabel("format incorect", Color.RED);
					} else {
						mainView.showResult(PolinomyalOperations.integration(polynomial1).getPolynomialString());
					}
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					mainView.showResult(PolinomyalOperations.integration(polynomial2).getPolynomialString());
				}

			}
		});

		mainView.addDivisionButtonActionEvent(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) { // actiunea realizata de butonul de impartire
				List<Polynomial> resultList = new ArrayList<Polynomial>();
				if (!polynomial1.polynomialConverter(mainView.getPol1())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else if (!polynomial2.polynomialConverter(mainView.getPol2())) {
					mainView.displayMessageLabel("format incorect", Color.RED);
				} else {
					try {
						resultList = PolinomyalOperations.division(polynomial1, polynomial2);

						if (resultList.get(0).getPolynomialString().length() == 0) {
							mainView.showResult("(" + resultList.get(1).getPolynomialString() + ")/("
									+ polynomial2.getPolynomialString() + ")");
						} else if (resultList.get(1).getFirstMonomial().getConstant() == 0) {
							mainView.showResult(resultList.get(0).getPolynomialString());
						} else {
							mainView.showResult(resultList.get(0).getPolynomialString() + "+("
									+ resultList.get(1).getPolynomialString() + ")/("
									+ polynomial2.getPolynomialString() + ")");
						}
					} catch (DivisionException e) {
						mainView.displayMessageLabel(e.getMessage(), Color.RED);
					}
				}

			}
		});

	}
}
