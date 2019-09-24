package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class Bill.
 */
public class Bill {

	/**
	 * Generate.
	 *
	 * @param order the order
	 * @param client the client
	 */
	public static void generate(Orders order, Client client) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Bill.pdf"));
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();
		PdfPTable table = new PdfPTable(5);
		table.getDefaultCell().setBorder(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		addTableHader(table);
		blankRow(table);
		blankRow(table);
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.add(new Paragraph("CLIENT"));
			document.add(new Paragraph("ID: " + client.getId()));
			document.add(new Paragraph("Name: " + client.getLastName() + " " + client.getFirstName()));
			document.add(new Paragraph("Address: " + client.getAddress()));
			document.add(new Paragraph("Phone: " + client.getPhone()));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		table = new PdfPTable(5);
		table.getDefaultCell().setBorder(0);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		blankRow(table);
		addRows(table, "PRODUCT", "PRODUCER", "QUANTITY", "AMOUNT", "");
		addRows(table, order.getProductName(), order.getProducer(), order.getQuantity().toString(),
				order.getTotal().toString(), "");
		blankRow(table);
		blankRow(table);
		addRows(table, "", "", "", "TOTAL", "");
		addRows(table, "", "", "", order.getTotal().toString(), "");
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.close();

	}

	/**
	 * Adds the table hader.
	 *
	 * @param table the table
	 */
	private static void addTableHader(PdfPTable table) {
		table.addCell("");
		table.addCell("");
		PdfPCell cell = new PdfPCell(new Paragraph("BILL", new Font(FontFamily.COURIER, 20, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		table.addCell("");
		table.addCell("");

	}

	/**
	 * Adds the rows.
	 *
	 * @param table the table
	 * @param str1 the str 1
	 * @param str2 the str 2
	 * @param str3 the str 3
	 * @param str4 the str 4
	 * @param str5 the str 5
	 */
	private static void addRows(PdfPTable table, String str1, String str2, String str3, String str4, String str5) {
		table.addCell(str1);
		table.addCell(str2);
		table.addCell(str3);
		table.addCell(str4);
		table.addCell(str5);
	}

	/**
	 * Blank row.
	 *
	 * @param table the table
	 */
	private static void blankRow(PdfPTable table) {
		PdfPCell cellBlankRow = new PdfPCell(new Phrase(" "));
		cellBlankRow.setBorder(0);
		table.addCell(cellBlankRow);
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
	}

}
