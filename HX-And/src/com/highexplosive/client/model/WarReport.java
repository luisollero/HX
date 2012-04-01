package com.highexplosive.client.model;

import java.util.List;

public class WarReport {

	private int reportId;
	private List<String> ownRegimentNames;
	private List<String> rivalRegimentNames;

	private String result;
	private String resume;

	public WarReport(int id, String result, String resume) {
		this.reportId = id;
		this.result = result;
		this.resume = resume;
	}
	
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public List<String> getOwnRegimentNames() {
		return ownRegimentNames;
	}

	public void setOwnRegimentNames(List<String> ownRegimentNames) {
		this.ownRegimentNames = ownRegimentNames;
	}

	public List<String> getRivalRegimentNames() {
		return rivalRegimentNames;
	}

	public void setRivalRegimentNames(List<String> rivalRegimentNames) {
		this.rivalRegimentNames = rivalRegimentNames;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

}
