package UI.ruleSet;

import UI.sharedUI.checkOut.SearchCheckOut;

import java.awt.*;

public class SearchCheckoutRuleSet implements RuleSet{
    private SearchCheckOut searchMemberPanel;
    @Override
    public void applyRules(Component ob) throws RuleException {
        searchMemberPanel = (SearchCheckOut) ob;
        nonemptyRule();
    }
    private void nonemptyRule() throws RuleException {
        if(searchMemberPanel.getMemberFields()[0].getText().trim().isEmpty())
            throw new RuleException("Search field should be non-empty");
    }
}
