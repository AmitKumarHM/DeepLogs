package edu.nitdelhi.deeplogs.bean;

import java.util.Date;

public class Covid19StatewiseTestingDetails {
	private Date ST_Date;
	private String ST_State;
	private Double ST_TotalSamples;
	private Double ST_Negative;
	private Double ST_Positive;
	public Date getST_Date() {
		return ST_Date;
	}
	public void setST_Date(Date sT_Date) {
		ST_Date = sT_Date;
	}
	public String getST_State() {
		return ST_State;
	}
	public void setST_State(String sT_State) {
		ST_State = sT_State;
	}
	public Double getST_TotalSamples() {
		return ST_TotalSamples;
	}
	public void setST_TotalSamples(Double sT_TotalSamples) {
		ST_TotalSamples = sT_TotalSamples;
	}
	public Double getST_Negative() {
		return ST_Negative;
	}
	public void setST_Negative(Double sT_Negative) {
		ST_Negative = sT_Negative;
	}
	public Double getST_Positive() {
		return ST_Positive;
	}
	public void setST_Positive(Double sT_Positive) {
		ST_Positive = sT_Positive;
	}
	
}
