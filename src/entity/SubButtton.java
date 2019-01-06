package entity;

import java.util.ArrayList;
import java.util.List;

public class SubButtton extends AbstractButton{
	
	private List<AbstractButton> sub_button = new ArrayList<>();

	public List<AbstractButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbstractButton> sub_button) {
		this.sub_button = sub_button;
	}

	public SubButtton(String name) {
		super(name);
	}
}
