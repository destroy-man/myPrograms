package K2;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.program.*;
import org.eclipse.swt.printing.*;
import java.util.*;
import java.io.*;

public class MainProg {
	static Display display; // �������

	static Shell shell; // ������� ����

	static Text nameOrg; // ��� �����������

	static Text INN; // ���

	static long INNTest;

	static Text Deyatelnost; // ��� ������������

	static int DeyatelnostTest;

	static Text Address; // ����� �����������

	static Text Contact; // ���������� ����

	static int ContactTest;

	static Text Tel; // �������

	static String TelTest;

	static Text Data; // ���� ������

	static String DataTest;

	static Text errText; // ��� ���������

	static boolean isError = false;

	static boolean isVerify = false;

	static boolean isTable = false;

	static boolean isGraph = false;

	static Label errLabel;

	static Table table; // ������� �������� ��������

	static TableColumn columnOrg; // ������� �������

	static TableColumn columnINN;

	static TableColumn columnDeyatelnost;

	static TableColumn columnAddress;

	static TableColumn columnContact;

	static TableColumn columnTel;

	static TableColumn columnData;

	static String fileName; // ��� ����� ��� ����������

	static FileWriter fOut; // ���� ��� ������

	static FileReader fIn; // ���� ��� ������

	static Group grpGr; // ������ ��������� ��� ���������� �������

	static int penW = 1; // ��������� ���� ��� ���������

	static int penG = 2;

	static Listener onHelp = new Listener() {
		public void handleEvent(Event e) {
			// ��������� ���� ������� ����������� �� ���������� html
			// Program.launch("C:\\eclipse\\karjer.hlp");
			Program.launch("HelpKursovaya2.chm");
		}
	};

	static Listener onPrint = new Listener() {
		// ��������� ������� ���� "������ �������"
		public void handleEvent(Event e) {
			PrinterData data = Printer.getDefaultPrinterData();
			if (data == null) {
				MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION
						| SWT.OK);
				msg
						.setMessage("��������������: �� ��������� ������� �� ���������");
				msg.open();
				return;
			}
			// ������� ������ - �������
			Printer printer = new Printer(data);
			if (printer.startJob("Graphic_1")) {
				Color black = printer.getSystemColor(SWT.COLOR_BLACK);
				Color white = printer.getSystemColor(SWT.COLOR_WHITE);
				Color red = printer.getSystemColor(SWT.COLOR_RED);
				Rectangle bnd = printer.getBounds();
				Point dpi = printer.getDPI();
				int leftMargin = dpi.x / 2 + bnd.x; // 1/2 ����� ����� ��
				// ������� ������
				int topMargin = dpi.y / 2 + bnd.y; // 1/2 ����� ������ ��
				// ������� ������
				GC gc = new GC(printer); // �������� ����������� �������� 130
				if (printer.startPage()) { // ������� ����� �������� ��������
					gc.setBackground(white); // 136
					gc.setForeground(black);

					// ����������� ���������� ��� � ���������� ������� ��
					// ���������� ����
					int k = 0;
					String[] S = new String[table.getItemCount()];
					boolean pravda = false;
					int[] Kolichestvo = new int[table.getItemCount()];
					for (int i = 0; i < table.getItemCount(); i++) {
						for (int j = 0; j < S.length; j++) {
							if (table.getItem(i).getText(0).equals(S[j])) {
								pravda = true;
								break;
							} else
								pravda = false;
						}
						if (pravda == false) {
							S[k] = table.getItem(i).getText(0);
							k++;
						}
					}
					for (int j = 0; S[j] != null; j++)
						for (int i = 0; i < table.getItemCount(); i++) {
							if (table.getItem(i).getText(0).equals(S[j]))
								Kolichestvo[j] += 1;
						}
					double minY = 0;
					double maxY = Kolichestvo.length;
					double minX = 1;
					double maxX = S.length;

					int w = bnd.width - 10 - dpi.x;
					int h = bnd.height - 15 - dpi.y;
					double scaleX = w / ((maxX - minX) * 1.10); // ������� �� �
					double scaleY = h / ((maxY - minY) * 1.10); // ������� �� Y
					int x0 = (int) Math.floor((0 - minX) * scaleX) + leftMargin; // ���������
					// ���
					// Y
					int y0 = (int) Math.floor(h - (0 - minY) * scaleY)
							+ topMargin; // ��������� ��� �
					gc.setLineWidth(penW);
					gc
							.drawText("����", w - 20 + leftMargin, h - 10
									+ topMargin); // ��� ��� �
					gc.drawText("���������� �������", 5 + leftMargin,
							15 + topMargin); // ��� ��� Y
					double dx = (maxX - minX) / 5.0; // ��� ����� �� �
					double dy = (maxY - minY) / 5.0; // ��� ����� �� Y
					for (int i = 0; i < k + 1; i++) { // ������ ����� � �����
						double x = Math.floor(minX + dx * i); // ���������� X
						// ��������
						int x1 = (int) Math.floor((x - minX) * scaleX)
								+ leftMargin; // ���������� X �� ������
						if (i == 0)
							gc.drawText("0", x1, h + 1 + topMargin); // �����
						// �� �
						else
							gc.drawText(S[i - 1], x1, h + 1 + topMargin);
						gc.drawLine(x1, topMargin, x1, h + topMargin); // �����
						// �� �
						double y = Math.floor(minY + dy * i); // ���������� Y
						// ��������
						int y1 = (int) Math.floor(h - (y - minY) * scaleY)
								+ topMargin; // ���������� Y �� ������
						if (i == 0)
							gc.drawText(" ", 3 + leftMargin, y1 - 20); // �����
						// �� Y
						gc.drawText(Integer.toString(i), 3 + leftMargin,
								y1 - 20);
						gc.drawLine(leftMargin, y1, leftMargin + w, y1); // �����
						// �� Y
					}
					;
					// ������ ������
					double x2 = Math.floor(minX + dx * 0);
					x0 = (int) Math.floor((x2 - minX) * scaleX) + leftMargin;// ������
					// ���������
					// �����
					double y2 = Math.floor(minY + dy * 0);
					y0 = (int) Math.floor(h - (y2 - minY) * scaleY) + topMargin;
					gc.setLineWidth(penG); // ���� ��� �������
					gc.setForeground(red); // ���� ��� �������
					for (int i = 1; i < k + 1; i++) { // �� ������ �������
						double x = Math.floor(minX + dx * (i));
						int x1 = (int) Math.floor((x - minX) * scaleX)
								+ leftMargin;
						double y = Math.floor(minY + dy * Kolichestvo[i - 1]); // ����������
						// ��������
						int y1 = (int) Math.floor(h - (y - minY) * scaleY)
								+ topMargin;
						gc.drawLine(x0, y0, x1, y1);
						x0 = x1;
						y0 = y1; // ������ ��������� �����
					}
					;
					printer.endPage();
				}
				gc.dispose(); // ���������� ����������� ��������
				printer.endJob(); // ������� ��������� �� ������
			}
			printer.dispose();
		}
	};

	static Listener onExit = new Listener() {
		// ��������� ������� ���� "�����"
		public void handleEvent(Event e) {
			shell.close();
		}
	};

	static Listener onOpen = new Listener() {
		// ��������� ������� ���� "������� ����"
		public void handleEvent(Event e) {
			FileDialog dialog = new FileDialog(shell, SWT.OPEN); // 85
			dialog.setFilterNames(new String[] { "�������� ������",
					"��� ����� (*.*)" });
			dialog.setFilterExtensions(new String[] { "*.fun", "*.*" }); // Windows
			// wild
			// cards
			dialog.setFilterPath("c:\\user\\"); // Windows path
			dialog.setFileName("test.fun");
			fileName = dialog.open();
			try {
				fIn = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fIn);
				nameOrg.setText(br.readLine());
				INN.setText(br.readLine());
				Deyatelnost.setText(br.readLine());
				Address.setText(br.readLine());
				Contact.setText(br.readLine());
				Tel.setText(br.readLine());
				Data.setText(br.readLine());
				fIn.close();
			} catch (IOException eio) {
				errText.append("������ ��� ������ �� ����� " + fileName + "\n");
				isError = true;
			}
		}
	};

	static Listener onSave = new Listener() {
		// ��������� ������� ���� "��������� ����"
		public void handleEvent(Event e) {
			FileDialog dialog = new FileDialog(shell, SWT.SAVE); // 85
			dialog.setFilterNames(new String[] { "�������� ������",
					"��� ����� (*.*)" });
			dialog.setFilterExtensions(new String[] { "*.fun", "*.*" }); // Windows
			// wild
			// cards
			dialog.setFilterPath("c:\\user\\"); // Windows path
			dialog.setFileName("test.fun");
			fileName = dialog.open();
			try {
				fOut = new FileWriter(fileName);
				fOut.write(nameOrg.getText() + "\n");
				fOut.write(INN.getText() + "\n");
				fOut.write(Deyatelnost.getText() + "\n");
				fOut.write(Address.getText() + "\n");
				fOut.write(Contact.getText() + "\n");
				fOut.write(Tel.getText() + "\n");
				fOut.write(Data.getText() + "\n");
				fOut.close();
			} catch (IOException eio) {
				errText.append("������ ��� ������ � ���� " + fileName + "\n");
				isError = true;
			}
		}
	};

	static Listener onRevise = new Listener() {
		// ��������� ������� ���� "��������� ������"
		public void handleEvent(Event e) {
			errText.setText(""); // 45
			isError = false;
			if (nameOrg.getText().length() == 0) {
				errText
						.append("������ � ���������� ������������ ����������� \n"); // 42
				isError = true;
			}
			try {
				INNTest = Long.parseLong(INN.getText());
				if (INN.getText().length() != 10) {
					errText.append("������ � ���������� ���\n");
					isError = true;
				}
			} catch (RuntimeException e1) {
				errText.append("������ � ���������� ���\n"); // 42
				isError = true;
			}
			;

			try {
				if (Deyatelnost.getText().length() == 0) {
					errText.append("������ � ���������� ���� ������������ \n"); // 42
					isError = true;
				}
				DeyatelnostTest = Integer.parseInt(Deyatelnost.getText());
				errText.append("������ � ���������� ���� ������������ \n"); // 42
				isError = true;
			} catch (RuntimeException e1) {
			}
			;
			if (Address.getText().length() == 0) {
				errText.append("������ � ���������� ������ \n"); // 42
				isError = true;
			}
			try {
				if (Contact.getText().length() == 0) {
					errText.append("������ � ���������� ����������� ���� \n"); // 42
					isError = true;
				}
				ContactTest = Integer.parseInt(Contact.getText());
				errText.append("������ � ���������� ����������� ���� \n"); // 42
				isError = true;
			} catch (RuntimeException e1) {
			}
			;

			try {
				TelTest = Tel.getText();
				if (TelTest.charAt(3) != '-' | TelTest.charAt(6) != '-'
						| TelTest.length() > 9) {
					errText.append("������ � ���������� �������� \n"); // 42
					isError = true;
				}
				String a = TelTest.substring(0, 3);
				String b = TelTest.substring(4, 6);
				String c = TelTest.substring(7, 9);
				Integer.parseInt(a);
				Integer.parseInt(b);
				Integer.parseInt(c);
			} catch (RuntimeException e1) {
				errText.append("������ � ���������� �������� \n"); // 42
				isError = true;
			}
			;

			try {
				DataTest = Data.getText();
				if (DataTest.charAt(2) != '.' | DataTest.charAt(5) != '.'
						| DataTest.length() > 10) {
					errText.append("������ � ���������� ���� \n"); // 42
					isError = true;
				}
				String a = DataTest.substring(0, 2);
				String b = DataTest.substring(3, 5);
				String c = DataTest.substring(6, 9);
				Integer.parseInt(a);
				Integer.parseInt(b);
				Integer.parseInt(c);
			} catch (RuntimeException e1) {
				errText.append("������ � ���������� ���� \n"); // 42
				isError = true;
			}
			if (!isError) {
				errLabel.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
				errLabel.setText("��� �����!");
				isVerify = true;
			} else {
				errLabel.setForeground(display.getSystemColor(SWT.COLOR_RED));
				errLabel.setText("���� ������!");
				isVerify = false;
			}
			;
			isTable = false;
			isGraph = false;
		}
	};

	static Listener onGraph = new Listener() {
		// ��������� ������� �� ����������� �������
		public void handleEvent(Event e) {
			if ((isGraph) & (e.type == 9)) {// ��� �� ������ ��������
				GC gc;
				if (e.type != 9) {
					gc = new GC(grpGr);
				}// ��� ������� ����������� ������ ��������
				else {
					gc = (GC) e.gc;
				}
				;// ��� ������� �����������
				Color black = display.getSystemColor(SWT.COLOR_BLACK);
				Color white = display.getSystemColor(SWT.COLOR_WHITE);
				Color red = display.getSystemColor(SWT.COLOR_RED);
				gc.setBackground(white);
				gc.setForeground(black);
				gc.fillRectangle(grpGr.getBounds());
				int k = 0;
				String[] S = new String[table.getItemCount()];
				boolean pravda = false;
				int[] Kolichestvo = new int[table.getItemCount()];
				for (int i = 0; i < table.getItemCount(); i++) {
					for (int j = 0; j < S.length; j++) {
						if (table.getItem(i).getText(0).equals(S[j])) {
							pravda = true;
							break;
						} else
							pravda = false;
					}
					if (pravda == false) {
						S[k] = table.getItem(i).getText(0);
						k++;
					}
				}
				for (int j = 0; S[j] != null; j++)
					for (int i = 0; i < table.getItemCount(); i++) {
						if (table.getItem(i).getText(0).equals(S[j]))
							Kolichestvo[j] += 1;
					}
				double minY = 0;
				double maxY = Kolichestvo.length;
				double minX = 0;
				double maxX = S.length;
				int w = grpGr.getBounds().width - 10;
				int h = grpGr.getBounds().height - 15;
				double scaleX = w / ((maxX - minX) * 1.10); // ������� �� �
				double scaleY = h / ((maxY - minY) * 1.10); // ������� �� Y
				int x0 = (int) Math.floor((0 - minX) * scaleX); // ��������� ���
				// Y
				int y0 = (int) Math.floor(h - (0 - minY) * scaleY); // ���������
				// ��� �

				gc.setLineWidth(penW);
				gc.drawText("����", w - 20, h);// ��� ��� �
				gc.drawText("���������� �������", 5, 15); // ��� ��� Y
				double dx = (maxX - minX) / 5.0; // ��� ����� �� �
				double dy = (maxY - minY) / 5.0; // ��� ����� �� Y
				for (int i = 0; i < k + 1; i++) {// ������ ����� � �����
					double x = Math.floor(minX + dx * i); // ����������
					// ��������
					int x1 = (int) Math.floor((x - minX) * scaleX); // ����������
					// �� ������
					if (i == 0)
						gc.drawText("0", x1, h + 1); // ����� �� �
					else
						gc.drawText(S[i - 1], x1, h + 1);
					gc.drawLine(x1, 30, x1, h); // ����� �� �
					double y = Math.floor(minY + dy * i); // ����������
					// ��������
					int y1 = (int) Math.floor(h - (y - minY) * scaleY);// ����������
					// ��
					// ������
					if (i == 0)
						gc.drawText(" ", 3, y1 - 20); // ����� �� Y
					else
						gc.drawText(Integer.toString(i), 3, y1 - 20);
					gc.drawLine(0, y1, w, y1); // ����� �� Y
				}
				;
				// ������ ������
				double x2 = Math.floor(minX + dx * 0);
				x0 = (int) Math.floor((x2 - minX) * scaleX);// ������ ���������
				// �����
				double y2 = Math.floor(minY + dy * 0);
				y0 = (int) Math.floor(h - (y2 - minY) * scaleY);
				gc.setLineWidth(penG); // ���� ��� �������
				gc.setForeground(red); // ���� ��� �������
				for (int i = 1; i < k + 1; i++) {// �� ������ �������
					double x = Math.floor(minX + dx * (i));
					int x1 = (int) Math.floor((x - minX) * scaleX);
					double y = Math.floor(minY + dy * Kolichestvo[i - 1]); // ����������
					// ��������
					int y1 = (int) Math.floor(h - (y - minY) * scaleY);
					gc.drawLine(x0, y0, x1, y1);
					x0 = x1;
					y0 = y1;// ������ ��������� �����
				}
				;
				if (e.type == 13) {
					gc.dispose();
				}
				;
			}
			;
		}
	};

	public static void main(String[] args) {
		// ������� ����� ����������
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("���� ����� �� ���������� ������");
		// ������� ���� ����������
		shell.setSize(700, 600);

		// ������� ���� ����������
		Menu bar = new Menu(shell, SWT.BAR); // ������� �������� ����
		shell.setMenuBar(bar);

		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("�����"); // ������ ����

		Menu subFile = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(subFile);

		MenuItem itemOpen = new MenuItem(subFile, SWT.PUSH);
		itemOpen.setText("�������");
		itemOpen.addListener(SWT.Selection, onOpen);

		MenuItem itemSave = new MenuItem(subFile, SWT.PUSH);
		itemSave.setText("���������");
		itemSave.addListener(SWT.Selection, onSave);

		MenuItem itemExit = new MenuItem(subFile, SWT.PUSH);
		itemExit.setText("�����");
		itemExit.addListener(SWT.Selection, onExit);

		MenuItem funItem = new MenuItem(bar, SWT.CASCADE);
		funItem.setText("�������"); // ������

		Menu subFun = new Menu(shell, SWT.DROP_DOWN);
		funItem.setMenu(subFun);

		MenuItem itemRevise = new MenuItem(subFun, SWT.PUSH);
		itemRevise.setText("��������� ��������� ������");
		itemRevise.addListener(SWT.Selection, onRevise);

		MenuItem itemGraph = new MenuItem(subFun, SWT.PUSH);
		itemGraph.setText("���������� ������");
		itemGraph.addListener(SWT.Selection, onPrint);

		MenuItem itemHelp = new MenuItem(bar, SWT.PUSH);
		itemHelp.setText("�������");
		itemHelp.addListener(SWT.Selection, onHelp);

		// ������� � ����������
		final TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		// �������� "�������� ������"
		TabItem tabInp = new TabItem(tabFolder, SWT.NONE);
		tabInp.setText("���� ������");
		// ������� ������ ��� ��������� �� ��������
		Group grpInp = new Group(tabFolder, SWT.SHADOW_IN);
		// ��������� ��� ���������� ��������� ����� �������� ������
		final int inp_leftLabel = 50;
		final int inp_wLabel = 160;
		final int inp_leftText = 210;
		final int inp_wText = 200;
		final int inp_topLabel = 50;
		final int inp_hText = 20;
		final int inp_vertStep = 40;
		// ���� ����� �������� �����������
		Label lName = new Label(grpInp, SWT.LEFT);
		lName.setText("������������ �����������");
		lName.setLocation(inp_leftLabel, inp_topLabel);
		lName.setSize(inp_wLabel, inp_hText);
		nameOrg = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		nameOrg.setLocation(inp_leftText, inp_topLabel);
		nameOrg.setSize(inp_wText, inp_hText);

		// ���� ����� ���
		Label lINN = new Label(grpInp, SWT.LEFT);
		lINN.setText("���");
		lINN.setLocation(inp_leftLabel, inp_topLabel + 1 * inp_vertStep);
		lINN.setSize(inp_wLabel, inp_hText);
		INN = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		INN.setLocation(inp_leftText, inp_topLabel + 1 * inp_vertStep);
		INN.setSize(inp_wText, inp_hText);

		// ���� ����� ���� ������������
		Label lDeyatelnost = new Label(grpInp, SWT.LEFT);
		lDeyatelnost.setText("��� ������������");
		lDeyatelnost
				.setLocation(inp_leftLabel, inp_topLabel + 2 * inp_vertStep);
		lDeyatelnost.setSize(inp_wLabel, inp_hText);
		Deyatelnost = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		Deyatelnost.setLocation(inp_leftText, inp_topLabel + 2 * inp_vertStep);
		Deyatelnost.setSize(inp_wText, inp_hText);

		// ���� ����� ������
		Label lAddress = new Label(grpInp, SWT.LEFT);
		lAddress.setText("�����");
		lAddress.setLocation(inp_leftLabel, inp_topLabel + 3 * inp_vertStep);
		lAddress.setSize(inp_wLabel, inp_hText);
		Address = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		Address.setLocation(inp_leftText, inp_topLabel + 3 * inp_vertStep);
		Address.setSize(inp_wText, inp_hText);

		// ���� ����� ����������� ����
		Label lContact = new Label(grpInp, SWT.LEFT);
		lContact.setText("���������� ����");
		lContact.setLocation(inp_leftLabel, inp_topLabel + 4 * inp_vertStep);
		lContact.setSize(inp_wLabel, inp_hText);
		Contact = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		Contact.setLocation(inp_leftText, inp_topLabel + 4 * inp_vertStep);
		Contact.setSize(inp_wText, inp_hText);

		// ���� ����� ��������
		Label lTel = new Label(grpInp, SWT.LEFT);
		lTel.setText("�������");
		lTel.setLocation(inp_leftLabel, inp_topLabel + 5 * inp_vertStep);
		lTel.setSize(inp_wLabel, inp_hText);
		Tel = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		Tel.setLocation(inp_leftText, inp_topLabel + 5 * inp_vertStep);
		Tel.setSize(inp_wText, inp_hText);

		// ���� ����� ����
		Label lData = new Label(grpInp, SWT.LEFT);
		lData.setText("����");
		lData.setLocation(inp_leftLabel, inp_topLabel + 6 * inp_vertStep);
		lData.setSize(inp_wLabel, inp_hText);
		Data = new Text(grpInp, SWT.BORDER | SWT.SINGLE);
		Data.setLocation(inp_leftText, inp_topLabel + 6 * inp_vertStep);
		Data.setSize(inp_wText, inp_hText);

		// �������� ������ ��������
		Button button = new Button(grpInp, SWT.LEFT);
		button.setText("��������");
		button.setAlignment(SWT.CENTER);
		button.setLocation(inp_leftLabel, inp_topLabel + 7 * inp_vertStep + 20);
		button.setSize(355, 30);
		button
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(0, Data.getText());
						item.setText(1, nameOrg.getText());
						item.setText(2, INN.getText());
						item.setText(3, Deyatelnost.getText());
						item.setText(4, Address.getText());
						item.setText(5, Contact.getText());
						item.setText(6, Tel.getText());
						isTable = true;
						isGraph = true;
					}
				});

		errLabel = new Label(grpInp, SWT.LEFT);
		errLabel.setLocation(inp_leftLabel, inp_topLabel + 8 * inp_vertStep
				+ 20);
		errLabel.setSize(inp_wLabel, inp_hText);

		// ���������� ������ �� ��������

		tabInp.setControl(grpInp);
		// ============= �������� ��� ������� =================
		TabItem tabTab = new TabItem(tabFolder, SWT.NONE);
		tabTab.setText("������� �����");
		Group grpTbl = new Group(tabFolder, SWT.SHADOW_IN);

		Label l2 = new Label(grpTbl, SWT.LEFT);
		l2.setText("������� ��������� �����");
		l2.setLocation(5, 15);
		l2.setSize(145, 20);
		table = new Table(grpTbl, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION
				| SWT.H_SCROLL | SWT.V_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		columnData = new TableColumn(table, SWT.NONE);
		columnData.setText("����"); // ��������� ������� 1
		columnData.setWidth(80);

		columnOrg = new TableColumn(table, SWT.NONE);
		columnOrg.setText("������������ �����������"); // ��������� ������� 2
		columnOrg.setWidth(150);

		columnINN = new TableColumn(table, SWT.NONE);
		columnINN.setText("���"); // ��������� ������� 3
		columnINN.setWidth(80);

		columnDeyatelnost = new TableColumn(table, SWT.NONE);
		columnDeyatelnost.setText("��� ������������"); // ��������� ������� 4
		columnDeyatelnost.setWidth(150);

		columnAddress = new TableColumn(table, SWT.NONE);
		columnAddress.setText("�����"); // ��������� ������� 5
		columnAddress.setWidth(150);

		columnContact = new TableColumn(table, SWT.NONE);
		columnContact.setText("���������� ����"); // ��������� ������� 6
		columnContact.setWidth(150);

		columnTel = new TableColumn(table, SWT.NONE);
		columnTel.setText("�������"); // ��������� ������� 7
		columnTel.setWidth(150);

		table.setSize(table.computeSize(SWT.DEFAULT, 400));
		table.setLocation(5, 40);

		Button button1 = new Button(grpTbl, SWT.LEFT);
		button1.setText("�������� ������� � CSV ����");
		button1.setAlignment(SWT.CENTER);
		button1.setLocation(0, 480);
		button1.pack();
		button1
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						FileDialog dialog = new FileDialog(shell, SWT.SAVE); // 85
						dialog.setFilterNames(new String[] { "���� CSV",
								"��� ����� (*.*)" });
						dialog.setFilterExtensions(new String[] { "*.csv",
								"*.*" }); // Windows wild cards
						dialog.setFilterPath("c:\\user\\"); // Windows path
						dialog.setFileName("test.csv");
						fileName = dialog.open();
						try {
							fOut = new FileWriter(fileName);
							for (int i = 0; i < table.getItemCount(); i++) {
								fOut.write(table.getItem(i).getText(0) + ";");
								fOut.write(table.getItem(i).getText(1) + ";");
								fOut.write(table.getItem(i).getText(2) + ";");
								fOut.write(table.getItem(i).getText(3) + ";");
								fOut.write(table.getItem(i).getText(4) + ";");
								fOut.write(table.getItem(i).getText(5) + ";");
								fOut.write(table.getItem(i).getText(6) + "\n");
							}
							fOut.close();
						} catch (IOException eio) {
							errText.append("������ ��� ������ � ���� "
									+ fileName + "\n");
							isError = true;
						}
					}
				});

		Button button2 = new Button(grpTbl, SWT.LEFT);
		button2.setText("�������� � ������� �� CSV �����");
		button2.setAlignment(SWT.CENTER);
		button2.setLocation(180, 480);
		button2.pack();
		button2
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						FileDialog dialog = new FileDialog(shell, SWT.OPEN); // 85
						dialog.setFilterNames(new String[] { "���� CSV",
								"��� ����� (*.*)" });
						dialog.setFilterExtensions(new String[] { "*.csv",
								"*.*" }); // Windows wild cards
						dialog.setFilterPath("c:\\user\\"); // Windows path
						dialog.setFileName("test.csv");
						fileName = dialog.open();
						try {
							String line;
							fIn = new FileReader(fileName);
							BufferedReader br = new BufferedReader(fIn);
							while ((line = br.readLine()) != null) {
								TableItem item = new TableItem(table, SWT.NONE);
								int i = 0;
								StringTokenizer st = new StringTokenizer(line,
										"\n;");
								while (st.hasMoreTokens()) {
									item.setText(i, st.nextToken());
									i++;
								}
							}
							fIn.close();
							isTable = true;
							isGraph = true;
						} catch (IOException eio) {
							errText.append("������ ��� ������ �� ����� "
									+ fileName + "\n");
							isError = true;
						}
					}
				});

		tabTab.setControl(grpTbl);
		// ============== �������� ��� ������� ================
		TabItem tabGr = new TabItem(tabFolder, SWT.NONE);
		tabGr.setText("������");
		grpGr = new Group(tabFolder, SWT.SHADOW_IN);

		tabGr.setControl(grpGr);
		grpGr.addListener(SWT.Paint, onGraph); // ���������� ���������� ���
		// ����������� �������

		// ============= �������� ��� ������� =================
		TabItem tabErr = new TabItem(tabFolder, SWT.NONE);
		tabErr.setText("���������");
		Group grpErr = new Group(tabFolder, SWT.SHADOW_IN);

		Label lErr = new Label(grpErr, SWT.LEFT);
		lErr.setText("��������� �� �������");
		lErr.setLocation(5, 15);
		lErr.setSize(145, 20);
		errText = new Text(grpErr, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		errText.setLocation(5, 35);
		errText.setSize(550, 470);
		tabErr.setControl(grpErr);
		// ��������� ������������ ��������� ����� � ��������� ��
		shell.open();

		// ���� ��������� ��������� �� ������� ������������ �������
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
