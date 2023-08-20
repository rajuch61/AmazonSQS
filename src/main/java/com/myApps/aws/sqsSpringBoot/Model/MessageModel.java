package com.myApps.aws.sqsSpringBoot.Model;

public class MessageModel {

	private String info;
	private String statusCode;
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public MessageModel(String info, String statusCode) {
		super();
		this.info = info;
		this.statusCode = statusCode;
	}
	public MessageModel() {
		super();
	}
	@Override
	public String toString() {
		return "Message [info=" + info + ", statusCode=" + statusCode + "]";
	}
	
//	{
//		"info": "Test Message",
//		"statusCode": "Success"
//	}
}
