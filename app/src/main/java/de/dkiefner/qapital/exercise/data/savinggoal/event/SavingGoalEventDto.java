package de.dkiefner.qapital.exercise.data.savinggoal.event;

public class SavingGoalEventDto {

	private String id;

	private String type;

	private String timestamp;

	private String message;

	private float amount;

	private Integer savingsRuleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Integer getSavingsRuleId() {
		return savingsRuleId;
	}

	public void setSavingsRuleId(Integer savingsRuleId) {
		this.savingsRuleId = savingsRuleId;
	}
}
