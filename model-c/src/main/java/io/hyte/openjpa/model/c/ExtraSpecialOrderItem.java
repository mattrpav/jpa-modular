package io.hyte.openjpa.model.c;

import io.hyte.openjpa.model.a.OrderItem;

public class ExtraSpecialOrderItem extends OrderItem {

	private String extraSpecial;

	public String getExtraSpecial() {
		return extraSpecial;
	}

	public void setExtraSpecial(String extraSpecial) {
		this.extraSpecial = extraSpecial;
	}
	
}
