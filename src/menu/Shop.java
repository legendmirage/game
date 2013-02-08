package menu;

public class Shop extends MenuInterface{

	public Shop(){

		tabs = new Tab[4];
		int count = 0;
		downPressed = upPressed = leftPressed = rightPressed = false;

		tabs[count] = new UseableItemTab(inventory);
		count++;
		tabs[count] = new CashStoreTab(inventory);
		count++;
		tabs[count] = new WillpathTab();
		count++;
		tabs[count] = new ExitShopTab();
		count++;
	}
}
