package UI.ruleSet;

import UI.sharedUI.checkOut.CheckOutUI;
import javax.swing.*;
import java.awt.*;

public class CheckOutRuleSet implements RuleSet{

    private CheckOutUI checkOutGui;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkOutGui = (CheckOutUI) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        for(JTextField jTextField : checkOutGui.getCheckOutFields()){
            if(jTextField.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }
    }
}
