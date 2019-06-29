package application;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.ObservableList;

public class PdfGeneration {
	
	public void pdfGeneration(String fajlnev, ObservableList<Statistics> data) {
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fajlnev + ".pdf"));
			document.open();
			//Image image = Image.getInstance(getClass().getResource("/got.jpg"));
			//document.add(image);
			
			float[] columnWidths = {2,2,2,2,2};
			PdfPTable table = new PdfPTable(columnWidths);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("Stats"));
			cell.setBackgroundColor(GrayColor.GRAYWHITE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(5);
			table.addCell(cell);

			table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell("Player 1");
			table.addCell("Player 2");
			table.addCell("Player 3");
			table.addCell("Player 4");
			table.addCell("Winner");
			table.setHeaderRows(1);
			
			table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			
			for(int i=0;i<data.size();i++) {
				Statistics currentStat = data.get(i);
					
				table.addCell(currentStat.getPlayer1());
				table.addCell(currentStat.getPlayer2());
				table.addCell(currentStat.getPlayer3());
				table.addCell(currentStat.getPlayer4());
				table.addCell(currentStat.getWinner());					
			}
			
			document.add(table);
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		document.close();
	}
}