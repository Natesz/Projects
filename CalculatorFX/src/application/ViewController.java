package application;

import java.awt.event.ActionListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViewController {
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
	Label calculationHistoryLabel;
	
	String zeroOsztasMsg = "nobnob nullaval osztani...";
	String tooBigNumberMsg = "nobnob ekkora szamot nem ismerek";
	
	boolean lastActionIsOperationSignal = false;
	
	public void showNumberInResult(String pushedButton) {
		
		if(lastActionIsOperationSignal) {
			resultLabel.setText("");
			resultLabel.setText(resultLabel.getText()+pushedButton);
			lastActionIsOperationSignal = false;
		}else {
			if(resultLabel.getText().equals("0")) {
				resultLabel.setText("");
			}
			resultLabel.setText(resultLabel.getText()+pushedButton);
		}
	}
	
	public void clearAction(ActionEvent event) {
		calculationHistoryLabel.setText("");
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
	
	public void number0Action(ActionEvent event) {
		if(!resultLabel.getText().equals("0")) {
			showNumberInResult("0");
		}
		
	}
	
	public void checkZeroDivMsg(ActionEvent event) {
		if(resultLabel.getText().equals(zeroOsztasMsg)) {
			clearAction(event);
		}
	}
	
	public void checkTooBigNumberMsg(ActionEvent event) {
		if(resultLabel.getText().equals(tooBigNumberMsg)) {
			clearAction(event);
		}
	}
	
	public void clickPlusButton(ActionEvent event) {
		checkZeroDivMsg(event);
		checkTooBigNumberMsg(event);
		
		if(calculationHistoryLabel.getText().equals("")) {
			moveResultToHistory("+");	
			lastActionIsOperationSignal = true;
		}else if(!lastActionIsOperationSignal) {
				
			egyenlo();
			moveResultToHistory("+");
			lastActionIsOperationSignal = true;
		}else {
			setLastCharInHistory("+");
		}
		
	}
	
	public void moveResultToHistory(String muveletjel) {
		calculationHistoryLabel.setText(resultLabel.getText()+muveletjel);
	}
	
	public void setLastCharInHistory(String muvelet) {
		calculationHistoryLabel.setText(calculationHistoryLabel.getText().substring(0,calculationHistoryLabel.getText().length()-1)+muvelet);
	}
	
	public void minusAction(ActionEvent event) {
		checkZeroDivMsg(event);
		checkTooBigNumberMsg(event);
		
		if(calculationHistoryLabel.getText().equals("")) {
			moveResultToHistory("-");	
			lastActionIsOperationSignal = true;
		}else
		
		if(!lastActionIsOperationSignal) {
				
			egyenlo();
			moveResultToHistory("-");
			lastActionIsOperationSignal = true;
		}else {
			setLastCharInHistory("-");
		}
	}
	
	public void osztasAction(ActionEvent event) {
		checkZeroDivMsg(event);
		checkTooBigNumberMsg(event);
		
		if(calculationHistoryLabel.getText().equals("")) {
			calculationHistoryLabel.setText(resultLabel.getText()+"/");	
			lastActionIsOperationSignal = true;
		}else
		
		if(!lastActionIsOperationSignal) {
				
			egyenlo();
			calculationHistoryLabel.setText(resultLabel.getText()+"/");
			lastActionIsOperationSignal = true;
		}else {
			setLastCharInHistory("/");
		}
	}
	
	public void szorzasAction(ActionEvent event) {
		checkZeroDivMsg(event);
		checkTooBigNumberMsg(event);
		
		if(calculationHistoryLabel.getText().equals("")) {
			calculationHistoryLabel.setText(resultLabel.getText()+"*");	
			lastActionIsOperationSignal = true;
		}else
		
		if(!lastActionIsOperationSignal /*&& calculationHistoryLabel.getText().substring(calculationHistoryLabel.getText().length()-1).equals("*")*/) {
				
			egyenlo();
			calculationHistoryLabel.setText(resultLabel.getText()+"*");
			lastActionIsOperationSignal = true;
		}else {
			setLastCharInHistory("*");
		}
	}
	
	public void egyenloAction(ActionEvent event) {
		if(resultLabel.getText().equals(zeroOsztasMsg)) {
			clearAction(event);
		}
		
		if(calculationHistoryLabel.getText().length()<1) {
			return;
		}
		
		egyenlo();
		calculationHistoryLabel.setText("");
		lastActionIsOperationSignal = true;
	}
		
	public void egyenlo() {
		
		char lastOperationSignalInHistory = calculationHistoryLabel.getText().charAt(calculationHistoryLabel.getText().length()-1);
		
		if(lastOperationSignalInHistory=='+') {
			resultLabel.setText(addition(calculationHistoryLabel.getText()));
		}
		if(lastOperationSignalInHistory=='-') {
			resultLabel.setText(kivonas(calculationHistoryLabel.getText()));
		}
		if(lastOperationSignalInHistory=='/') {
			resultLabel.setText(osztas(calculationHistoryLabel.getText()));
		}
		if(lastOperationSignalInHistory=='*') {
			resultLabel.setText(szorzas(calculationHistoryLabel.getText()));
		}
		
		return;
	}
	
	public String addition(String osszeadandok) {
		/*String[] darabok = osszeadandok.split("\\+");
		int result = Integer.parseInt(darabok[0]) + Integer.parseInt(darabok[1]); */
		try {
			int historyNumber = Integer.parseInt(osszeadandok.substring(0, osszeadandok.length()-1));
			int resultNumber = Integer.parseInt(resultLabel.getText());
			int result = historyNumber + resultNumber;
			if(result>Integer.MAX_VALUE) {
				return tooBigNumberMsg;
			}
			return result+"";
		}catch(Exception e) {
			return tooBigNumberMsg;
		}
		
	}
	
	public String kivonas(String kivonas) {
		try {
			int historyNumber = Integer.parseInt(kivonas.substring(0, kivonas.length()-1));
			int resultNumber = Integer.parseInt(resultLabel.getText());
			int result = historyNumber - resultNumber;
			if(result>Integer.MAX_VALUE) {
				return tooBigNumberMsg;
			}
			return result+"";
		} catch (Exception e) {
			return tooBigNumberMsg;
		}
	}
	
	public String szorzas(String szorzandok) {
		try {
			int historyNumber = Integer.parseInt(szorzandok.substring(0, szorzandok.length()-1));
			int resultNumber = Integer.parseInt(resultLabel.getText());
			int result = historyNumber * resultNumber;
			if(result>2147483647) {
				return tooBigNumberMsg;
			}
			return result+"";
		} catch (NumberFormatException e) {
			return tooBigNumberMsg;
		}
	}
	
	public String osztas(String osztandok) {
		try {
			int historyNumber = Integer.parseInt(osztandok.substring(0, osztandok.length()-1));
			int resultNumber = Integer.parseInt(resultLabel.getText());
			if(resultNumber == 0) {
				return zeroOsztasMsg;
			}
			int result = historyNumber / resultNumber;
			if(result>2147483647) {
				return tooBigNumberMsg;
			}
			return result+"";
		} catch (NumberFormatException e) {
			return tooBigNumberMsg;
		}
	}
	
}
