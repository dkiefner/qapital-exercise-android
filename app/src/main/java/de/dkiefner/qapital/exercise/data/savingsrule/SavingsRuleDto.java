package de.dkiefner.qapital.exercise.data.savingsrule;

public class SavingsRuleDto {

	private int id;

	private String type;

	private float amount;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
