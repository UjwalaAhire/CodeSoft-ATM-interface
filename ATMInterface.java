import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Class representing the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}

// Class representing the ATM machine
public class ATMInterface extends JFrame {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JButton withdrawButton, depositButton, checkBalanceButton;

    public ATMInterface(BankAccount account) {
        this.account = account;
        setTitle("ATM Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set up the layout
        setLayout(new GridLayout(5, 1));

        balanceLabel = new JLabel("Current Balance: $0.00");
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        JLabel enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        add(amountField);

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        checkBalanceButton = new JButton("Check Balance");

        withdrawButton.addActionListener(new WithdrawListener());
        depositButton.addActionListener(new DepositListener());
        checkBalanceButton.addActionListener(new CheckBalanceListener());

        add(withdrawButton);
        add(depositButton);
        add(checkBalanceButton);

        updateBalanceDisplay();
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", account.getBalance()));
    }

    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (account.withdraw(amount)) {
                    JOptionPane.showMessageDialog(null, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance or invalid amount.");
                }
                updateBalanceDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
            amountField.setText("");
        }
    }

    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(null, "Deposit Successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
                }
                updateBalanceDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
            amountField.setText("");
        }
    }

    private class CheckBalanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateBalanceDisplay();
            JOptionPane.showMessageDialog(null, "Your current balance is: $" + String.format("%.2f", account.getBalance()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAccount account = new BankAccount(500.00); // Initial balance of $500
            ATMInterface atm = new ATMInterface(account);
            atm.setVisible(true);
        });
    }
}
