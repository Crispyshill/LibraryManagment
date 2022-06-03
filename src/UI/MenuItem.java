package UI;

public class MenuItem {
	private String itemName;
	private boolean highlight = false;
	public MenuItem(String item, boolean visible) {
	   itemName = item;
	   highlight = visible;
    }

	@Override
	public boolean equals(Object ob) {
		if(ob.getClass() != MenuItem.class) return false;
		MenuItem item = (MenuItem)ob;
		return itemName.equals(item.itemName);
	}
	public String getItemName() {
		return itemName;
	}
	public boolean highlight() {
		return highlight;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
