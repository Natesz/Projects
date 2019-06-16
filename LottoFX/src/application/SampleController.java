package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class SampleController {

	@FXML
	Button button1;
	@FXML
	Button okButton;
	@FXML
	Label label1;
	@FXML
	Label label2;
	@FXML
	Label label3;
	@FXML
	Label label4;
	@FXML
	Label label5;
	@FXML
	TextField textfield1;
	@FXML
	TextField textfield2;
	@FXML
	TextField textfield3;
	@FXML
	TextField textfield4;
	@FXML
	TextField textfield5;
	@FXML
	Label result;
	@FXML
	Pane alertPane;
	@FXML
	Pane basePane;
	@FXML
	Label alertText;
	
	final int MIN = 1;
	final int MAX = 90;
	boolean isAlerted = false;
		
	public int getRandomNumber() {
		
		int number = (int) (Math.random() * MAX) + MIN;
		return number;
	}
	
	@FXML
	public void okButtonHandler(ActionEvent event) {
		alertPane.setVisible(false);
		basePane.setOpacity(1);
		return;
	}
	
	@FXML
	public void handleButtonAction(ActionEvent event) {
		isAlerted = false;
		Set<Integer> selectedNumbers = new HashSet<Integer>();
		int count = 0;
		int generatedNum1 = 0;
		int generatedNum2 = 0;
		int generatedNum3 = 0;
		int generatedNum4 = 0;
		int generatedNum5 = 0;
				
		generatedNum1 = getRandomNumber();
		generatedNum2 = getRandomNumber();
		generatedNum3 = getRandomNumber();
		generatedNum4 = getRandomNumber();
		generatedNum5 = getRandomNumber();
		
		label1.setText("" + generatedNum1);
		label2.setText("" + generatedNum2);
		label3.setText("" + generatedNum3);
		label4.setText("" + generatedNum4);
		label5.setText("" + generatedNum5);
		
		uploadSelectedNumbers(selectedNumbers);
		
		for(Integer selectedNumber : selectedNumbers) {
			if(selectedNumber == generatedNum1 || selectedNumber == generatedNum2 || selectedNumber == generatedNum3 || 
					selectedNumber == generatedNum4 || selectedNumber == generatedNum5) {
				count++;
			}
		}
		
		if(!isAlerted) {
			String res = resultText(count);
			result.setText(res);
		}
	}
	
	public String resultText(int numberOfHits) {
		switch (numberOfHits) {
			case 0 : return "Sajnos nincs találatod.";
			case 1 : return "Egyesed van a lottón!";
			case 2 : return "Kettõ találatod van!";
			case 3 : return "Három talalatod van!";
			case 4 : return "Négy találatod van!";
			case 5 : return "Telitalálat, milliomos lettél!";
			
		}
		return "";
	}
	
	private void alert(String text) {
		isAlerted = true;
		label1.setText("");
		label2.setText("");
		label3.setText("");
		label4.setText("");
		label5.setText("");
		result.setText("");
		basePane.setOpacity(0.3);
		alertPane.setVisible(true);
		alertText.setText(text);
	}
	
	public void uploadSelectedNumbers(Set<Integer> selectedNumbers) {
		
		try {
			int selNum1 = Integer.parseInt(textfield1.getText());
			int selNum2 = Integer.parseInt(textfield2.getText());
			int selNum3 = Integer.parseInt(textfield3.getText());
			int selNum4 = Integer.parseInt(textfield4.getText());
			int selNum5 = Integer.parseInt(textfield5.getText());
			
			selectedNumbers.add(selNum1);
			selectedNumbers.add(selNum2);
			selectedNumbers.add(selNum3);
			selectedNumbers.add(selNum4);
			selectedNumbers.add(selNum5);
						
		}catch(Exception e){
			alert("Hiányos / rossz adatokat adtál meg!");
			return;
		}
				
		if(selectedNumbers.size()<5) {
			alert("Különbözõ számokat adj meg!");
			return;
		}
				
		for(Integer number : selectedNumbers) {
			if((number>MAX) || (number<MIN)) {
				alertPane.setVisible(true);
				alert("1 és 90 közötti számokat adj meg!");
				return;
			}
		}
	}
}
