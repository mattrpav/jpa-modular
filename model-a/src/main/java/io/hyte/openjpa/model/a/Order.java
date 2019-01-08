package io.hyte.openjpa.model.a;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Order {

	private long orderId;
	private String createdBy;
	private Calendar createdDateTime;
	private String updatedBy;
	private Calendar updatedDateTime;
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Calendar getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}
	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	
}
