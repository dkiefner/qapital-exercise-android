package de.dkiefner.qapital.exercise.data.savinggoal;

public class SavingGoalDto {

	private int id;

	private String goalImageURL;

	private Float targetAmount;

	private float currentBalance;

	private String status;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoalImageURL() {
		return goalImageURL;
	}

	public void setGoalImageURL(String goalImageURL) {
		this.goalImageURL = goalImageURL;
	}

	public Float getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Float targetAmount) {
		this.targetAmount = targetAmount;
	}

	public float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
