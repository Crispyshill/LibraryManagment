package UI.ruleSet;

import UI.sharedUI.book.AddBookCopyPanel;

import javax.swing.*;
import java.awt.*;

public class BookCopyRuleSet implements RuleSet {

    private AddBookCopyPanel bookCopyPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {

        bookCopyPanel = (AddBookCopyPanel) ob;
        nonemptyRule();
        //maxDays();
    }

    private void nonemptyRule() throws RuleException {
        for(JTextField field : bookCopyPanel.getBookFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }
    }
}
