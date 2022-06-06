package UI.ruleSet;

import UI.LoginWindow;

import java.awt.*;

public class LoginRuleSet implements RuleSet{
    private LoginWindow loginScreen;
    @Override
    public void applyRules(Component ob) throws RuleException {
        loginScreen = (LoginWindow) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(loginScreen.getPassword().getPassword().length ==0 || loginScreen.getUserName().getText().trim().isEmpty())
            throw new RuleException("All fields must be non-empty");
    }
}
