package model;

public class Monomial {

	private Double constant;
	private Integer exponent;

	public Monomial(Double constant, Integer exponent) {
		super();
		this.constant = constant;
		this.exponent = exponent;
	}

	public Double getConstant() {
		return constant;
	}

	public void setConstant(Double constant) {
		this.constant = constant;
	}

	public Integer getExponent() {
		return exponent;
	}

	public void setExponent(Integer exponent) {
		this.exponent = exponent;
	}

}
