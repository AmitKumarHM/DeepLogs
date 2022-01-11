package edu.nitdelhi.deeplogs.bean;

import java.io.Serializable;

public class SimpleLog implements Serializable{
	private static final long serialVersionUID = 1L;
	private String messageByLine;
	private String objectId;
	private double probability;
	


	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public SimpleLog(String messageByLine) {
		super();
		this.messageByLine = messageByLine;
	}

	public SimpleLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessageByLine() {
		return messageByLine;
	}

	public void setMessageByLine(String messageByLine) {
		this.messageByLine = messageByLine;
	}

}
