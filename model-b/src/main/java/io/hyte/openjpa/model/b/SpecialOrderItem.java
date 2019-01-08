package io.hyte.openjpa.model.b;

import io.hyte.openjpa.model.a.OrderItem;

public class SpecialOrderItem extends OrderItem {

	private String special;

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}
	
}
