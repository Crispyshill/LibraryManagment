package UI.ruleSet;

import UI.LoginWindow;
import UI.sharedUI.book.AddBookCopyPanel;
import UI.sharedUI.book.BookUI;
import UI.sharedUI.book.SearchBookPanel;
import UI.sharedUI.checkOut.CheckOutUI;
import UI.sharedUI.checkOut.SearchCheckOut;
import UI.sharedUI.member.MemberUI;

import java.awt.*;
import java.util.HashMap;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(BookUI.class, new BookRuleSet());
		map.put(MemberUI.class, new MemberRuleSet());
		map.put(LoginWindow.class, new LoginRuleSet());
		map.put(CheckOutUI.class, new CheckOutRuleSet());
		map.put(SearchCheckOut.class, new SearchCheckoutRuleSet());
		map.put(SearchBookPanel.class, new SearchBookRuleSet());
		map.put(AddBookCopyPanel.class, new BookCopyRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
