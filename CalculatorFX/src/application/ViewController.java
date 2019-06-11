package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.math.BigInteger;

public class ViewController {
	@FXML
	Button dotAction;
	@FXML
	Button clear;
	@FXML
	Button number0;
	@FXML
	Button number7;
	@FXML
	Button number6;
	@FXML
	Button number5;
	@FXML
	Button number4;
	@FXML
	Button number3;
	@FXML
	Button number2;
	@FXML
	Button number1;
	@FXML
	Button number8;
	@FXML
	Button number9;
	@FXML
	Button szorzas;
	@FXML
	Button osztas;
	@FXML
	Button egyenlo;
	@FXML
	Button plus;
	@FXML
	Button minus;
	@FXML
	Label resultLabel;
	@FXML
	Label historyLabel;
	
	String zeroOsztasMsg = "divide by zero";
		
	boolean lastActionIsOperationSign = false;
	
	public void clearAction(ActionEvent event) {
		historyLabel.setText("");
		resultLabel.setText("0");
	}
	
	public void number1Action(ActionEvent event) {
		showNumberInResult("1");
	}
	
	public void number2Action(ActionEvent event) {
		showNumberInResult("2");
	}

	public void number3Action(ActionEvent event) {
		showNumberInResult("3");		
	}
	
	public void number4Action(ActionEvent event) {
		showNumberInResult("4");
	}
	
	public void number5Action(ActionEvent event) {
		showNumberInResult("5");
	}
	
	public void number6Action(ActionEvent event) {
		showNumberInResult("6");
	}
	
	public void number7Action(ActionEvent event) {
		showNumberInResult("7");
	}
	
	public void number8Action(ActionEvent event) {
		showNumberInResult("8");
	}
	
	public void number9Action(ActionEvent event) {
		showNumberInResult("9");
	}
	
	public void dotAction(ActionEvent event) {
		boolean isTizedesSzam = false;
		for(int i=0;i<resultLabel.getText().length();i++) {
			if(resultLabel.getText().charAt(i)=='.') {
				isTizedesSzam = true;
			}
		}
		if(!isTizedesSzam) {
			if(resultLabel.getText().equals("0") || resultLabel.getText().equals(zeroOsztasMsg)) {
				showNumberInResult("0.");
			}else {
				showNumberInResult(".");
			}
		}
	}
	
	public void number0Action(ActionEvent event) {
		if(!resultLabel.getText().equals("0")) {
			showNumberInResult("0");
		}		
	}
	
	public void moveResultToHistory(String muveletjel) {
		historyLabel.setText(resultLabel.getText()+muveletjel);
	}
	
	public void setLastCharInHistory(String muvelet) {
		historyLabel.setText(historyLabel.getText().substring(0,historyLabel.getText().length()-1)+muvelet);
	}
	
	public void checkZeroDivMsg(ActionEvent event) {
		if(resultLabel.getText().equals(zeroOsztasMsg)) {
			clearAction(event);
		}
	}
	
	public void checkIsDotLastChar() {
		if(resultLabel.getText().charAt(resultLabel.getText().length()-1)=='.'){
			resultLabel.setText(resultLabel.getText().substring(0,resultLabel.getText().length()-1));
		}
	}
		
	public void clickPlusButton(ActionEvent event) {
		checkZeroDivMsg(event);
		checkIsDotLastChar();
		
		if(historyLabel.getText().equals("")) {
			moveResultToHistory("+");	
			lastActionIsOperationSign = true;
		}else if(!lastActionIsOperationSign) {
				
			egyenlo();
			moveResultToHistory("+");
			lastActionIsOperationSign = true;
		}else {
			setLastCharInHistory("+");
		}	
	}
	
	public void clickMinusButton(ActionEvent event) {
		checkZeroDivMsg(event);
		checkIsDotLastChar();
				
		if(historyLabel.getText().equals("")) {
			moveResultToHistory("-");	
			lastActionIsOperationSign = true;
		}else
		
		if(!lastActionIsOperationSign) {
				
			egyenlo();
			moveResultToHistory("-");
			lastActionIsOperationSign = true;
		}else {
			setLastCharInHistory("-");
		}
	}
	
	public void clickDivideButton(ActionEvent event) {
		checkZeroDivMsg(event);
		checkIsDotLastChar();
				
		if(historyLabel.getText().equals("")) {
			historyLabel.setText(resultLabel.getText()+"/");	
			lastActionIsOperationSign = true;
		}else
		
		if(!lastActionIsOperationSign) {				
			egyenlo();
			historyLabel.setText(resultLabel.getText()+"/");
			lastActionIsOperationSign = true;
		}else {
			setLastCharInHistory("/");
		}
	}
	
	public void clickMultiplyButton(ActionEvent event) {
		checkZeroDivMsg(event);
		checkIsDotLastChar();
				
		if(historyLabel.getText().equals("")) {
			historyLabel.setText(resultLabel.getText()+"*");	
			lastActionIsOperationSign = true;
		}else
		
		if(!lastActionIsOperationSign) {
				
			egyenlo();
			historyLabel.setText(resultLabel.getText()+"*");
			lastActionIsOperationSign = true;
		}else {
			setLastCharInHistory("*");
		}
	}
	
	public void clickEqualButton(ActionEvent event) {
		if(resultLabel.getText().equals(zeroOsztasMsg)) {
			clearAction(event);
		}
		
		if(historyLabel.getText().length()<1) {
			return;
		}
		
		egyenlo();
		historyLabel.setText("");
		lastActionIsOperationSign = true;
	}
		
	public void egyenlo() {
		
		char lastOperationSignalInHistory = historyLabel.getText().charAt(historyLabel.getText().length()-1);
	
		
		if(lastOperationSignalInHistory=='+') {
			resultLabel.setText(addition());
		}
		if(lastOperationSignalInHistory=='-') {
			resultLabel.setText(substraction());
		}
		if(lastOperationSignalInHistory=='/') {
			resultLabel.setText(division());
		}
		if(lastOperationSignalInHistory=='*') {
			resultLabel.setText(multiplication());
		}
		
		return;
	}
	
	public String addition() {
		
		double firstNumber = Double.parseDouble(historyLabel.getText().substring(0, historyLabel.getText().length()-1));
		double secondNumber = Double.parseDouble(resultLabel.getText());
		double result = firstNumber + secondNumber;
		
		return result+"";
	}
	
	public String substraction() {
		
		double firstNumber = Double.parseDouble(historyLabel.getText().substring(0, historyLabel.getText().length()-1));
		double secondNumber = Double.parseDouble(resultLabel.getText());
		double result = firstNumber - secondNumber;
		
		return result+"";
	}
	
	public String multiplication() {

		double firstNumber = Double.parseDouble(historyLabel.getText().substring(0, historyLabel.getText().length()-1));
		double secondNumber = Double.parseDouble(resultLabel.getText());
		double result = firstNumber * secondNumber;
		
		return result+"";
	}
	
	public String division() {

		double firstNumber = Double.parseDouble(historyLabel.getText().substring(0, historyLabel.getText().length()-1));
		
		if(resultLabel.getText().equals("0")) {
			return zeroOsztasMsg;
		}
		
		double secondNumber = Double.parseDouble(resultLabel.getText());
		double result = firstNumber / secondNumber;
		
		return result+"";			
	}
	
	public void showNumberInResult(String pushedButton) {
		
		if(lastActionIsOperationSign) {
			resultLabel.setText("");
			resultLabel.setText(resultLabel.getText()+pushedButton);
			lastActionIsOperationSign = false;
		}else {
			if(resultLabel.getText().equals("0")) {
				resultLabel.setText("");
			}
			resultLabel.setText(resultLabel.getText()+pushedButton);
		}
	}
}
