package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial {

	private Map<Integer, Monomial> polynomialMap = new TreeMap<Integer, Monomial>(new Comparator<Integer>() {

		public int compare(Integer o1, Integer o2) {
			return o2.compareTo(o1);
		}

	});

	public void addMonomial(Monomial monomial) {	// adaugarea unui polinom in polynomialMap

		Integer exponent = monomial.getExponent();

		if (polynomialMap.containsKey(exponent)) {	//se verifica daca exista un monom cu acelasi exponent
			Double constant = monomial.getConstant();
			constant += polynomialMap.get(exponent).getConstant();
			if (constant == 0) {
				polynomialMap.remove(exponent);
				monomial.setConstant((double) 0);
				monomial.setExponent(0);
				if (!polynomialMap.containsKey(0)) {	//se verifica daca merita introdusa constanta 0
					polynomialMap.put(0, monomial);
				}
			} else if (constant != 0) {
				monomial.setConstant(constant);
				polynomialMap.put(exponent, monomial);
			}
		} else {
			if (monomial.getConstant() == 0 && !polynomialMap.containsKey(0)) {
				monomial.setExponent(0);
				polynomialMap.put(0, monomial);
			} else if (monomial.getConstant() != 0) {
				polynomialMap.put(exponent, monomial);
			}
		}
	}

	public Map<Integer, Monomial> getPolynomialMap() {
		return polynomialMap;
	}

	public void setPolynomialMap(Map<Integer, Monomial> polynomialMap) {
		this.polynomialMap = polynomialMap;
	}

	public String getPolynomialString() { // se coverteste un polinom in string pentru afisare
		String str = new String();
		boolean first = true;
		for (Map.Entry<Integer, Monomial> entry : polynomialMap.entrySet()) {
			String constant = new String();
			String exponent = entry.getKey().toString();
			Double constantDouble = entry.getValue().getConstant();
			if (constantDouble == Math.floor(constantDouble)) {  //se verivca daca coeficientul e double sau intreg
				constant = String.valueOf(constantDouble.intValue());
			} else
				constant = String.format("%.2f", constantDouble);
			List<String> list = conerterConstantAndExponent(constant, exponent);
			constant = list.get(0);
			exponent = list.get(1);
			if (constant.equals("0") && str.length() == 0) {
				str += "0";
			} else if (!constant.equals("0")) {
				if (entry.getValue().getConstant() > 0 && first == false) {
					str += "+" + constant + exponent;
				} else {
					str += constant + exponent;
					first = false;
				}
			}
		}
		return str;
	}

	private List<String> conerterConstantAndExponent(String constant, String exponent) {  // se converteste constanta si exponentul in String sub un format specific
		List<String> list = new ArrayList<String>();

		if (constant.equals("1")) {
			constant = "";
		} else if (constant.equals("-1")) {
			constant = "-";
		}
		if (exponent.equals("1")) {
			exponent = "x";
		} else if (exponent.equals("0") && (constant.length() == 0 || constant.equals("-"))) {
			exponent = "";
			constant += "1";
		} else if (exponent.equals("0")) {
			exponent = "";
		} else {
			exponent = "x^" + exponent;
		}
		list.add(constant);
		list.add(exponent);

		return list;
	}

	public boolean polynomialConverter(String text) { 	//se coverteste un string intr-un polinom si se verifica daca formatul introdus de la tastaura e corect
		this.polynomialMap.clear();
		int textLenght = text.length();
		if (textLenght == 0) {
			return false;
		}
		String[] parts = text.split("(?=\\+)|(?=\\-)");
		for (String part : parts) {
			if (part.matches("([+-]?[0-9]+x\\^[0-9]+)|([+-]?x\\^[0-9]+)|([+-]?[0-9]+x)|([+-]?[0-9]+)|([+-]?x)")) {
				if (!part.contains("-") && !part.contains("+")) {
					part = "+" + part;
				}
				String[] str = part.split("x(\\^)?");
				String constantString = str[0].toString();
				String exponentString = new String();
				if (part.contains("x^")) {
					exponentString = str[1].toString();
				} else if (part.contains("x")) {
					exponentString = "1";
				} else {
					exponentString = "0";
				}
				if (constantString.length() == 1) {
					constantString += "1";
				}
				this.addMonomial(new Monomial(Double.valueOf(constantString), Integer.valueOf(exponentString)));
			} else {
				return false;
			}
		}
		return true;
	}

	public Monomial getFirstMonomial() {
		return this.polynomialMap.values().stream().findFirst().get();
	}
}