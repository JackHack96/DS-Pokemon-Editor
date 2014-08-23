package gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import narc.Narc;

import org.apache.commons.io.EndianUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import poketext.PokeText;

public class MainWindow {
	public static Narc msgNarc, personalNarc, evoNarc, wotblNarc;
	public static ArrayList<String> pkmnList, attackList;
	public static ArrayList<EvData> tmpEvList;
	private static boolean unsaved = false;
	private static String prefix;
	private static Text txtHP;
	private static Text txtAttack;
	private static Text txtDefense;
	private static Text txtSpeed;
	private static Text txtSpAttack;
	private static Text txtSpDefense;
	private static Text txtBaseXP;
	private static Text txtCatchRate;
	private static Text txtBaseHappiness;
	private static Text txtRunChance;
	private static List lstPkmn;
	private static Combo cmbType1;
	private static Combo cmbType2;
	private static Combo cmbAbility1;
	private static Combo cmbAbility2;
	private static Combo cmbEggType1;
	private static Combo cmbEggType2;
	private static Combo cmbItem1;
	private static Combo cmbItem2;
	private static Combo cmbGenderRatio;
	private static Combo cmbSteps;
	private static Button rdEv1;
	private static Button rdEv2;
	private static Button rdEv3;
	private static Button rdEv4;
	private static Button rdEv5;
	private static Button rdEv6;
	private static Button rdEv7;
	private static Tree trEv;
	private static Group grpEvolutionData;
	private static Label lblEvolutionType;
	private static Combo cmbEvType;
	private static Group grpEvolutionEditor;
	private static Label lblEvolutionLevel;
	private static Combo cmbEvLvl;
	private static Label lblEvolutionItem;
	private static Combo cmbEvItem;
	private static Label lblEvolutionHappiness;
	private static Text txtEvHappiness;
	private static Label lblEvolutionPokemon;
	private static Combo cmbEvPkmn;
	private static Label lblEvolutionAttack;
	private static Combo cmbEvAttack;
	private static Label lblEvolvesIn;
	private static Combo cmbEvIn;
	private static Composite composite_2;
	private static Group grpTmCompatibility;
	private static Table tblTM;
	private static Button btnSelectAll;
	private static Button btnDeselectAll;
	private static Group grpMoveset;
	private static Table tblMoveset;
	private static Label lblLevel;
	private static Spinner spnLevel;
	private static Label lblAttack_1;
	private static Combo cmbAttack;
	private static Button btnSave;
	private static Group grpHmCompatibility;
	private static Table tblHM;
	private static Button btnSelectAll_1;
	private static Button btnDeselectAll_1;
	private static TableColumn tblclmnAttack;
	private static TableColumn tblclmnLevel;
	private static Group grpEvolutionGivenValues;
	private static Label label;
	private static Text txtEvHP;
	private static Label label_1;
	private static Text txtEvSpAttack;
	private static Label label_2;
	private static Text txtEvAttack;
	private static Label label_3;
	private static Text txtEvSpDefense;
	private static Label label_4;
	private static Text txtEvDefense;
	private static Label label_6;
	private static Text txtEvSpeed;
	private static Label lblMaxXp;
	private static Combo cmbMaxXP;
	private static Group grpColor;
	private static Spinner spnColor;
	private static Button btnSavePokmon;
	private static Button btnSaveEvolution;
	private static Button btnAdd;
	private static Button btnDelete;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		final Shell shlDsPokmonEditor = new Shell(SWT.CLOSE | SWT.TITLE
				| SWT.MIN);
		shlDsPokmonEditor.setImage(SWTResourceManager.getImage(
				MainWindow.class, "/res/icon.png"));
		shlDsPokmonEditor.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent arg0) {
				if (unsaved == true) {
					MessageBox msg = new MessageBox(shlDsPokmonEditor
							.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					msg.setMessage(Messages.getString("Unsaved"));
					msg.setText("DS Pokémon Editor");
					if (msg.open() == SWT.YES) {
						try {
							personalNarc.writeNarc(personalNarc.getNarcPath()
									.toString());
							evoNarc.writeNarc(evoNarc.getNarcPath().toString());
							wotblNarc.writeNarc(wotblNarc.getNarcPath()
									.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		shlDsPokmonEditor.setText(Messages
				.getString("MainWindow.shlDsPokmonEditor.text")); //$NON-NLS-1$
		shlDsPokmonEditor.setSize(775, 553);
		shlDsPokmonEditor.setLayout(new GridLayout(2, false));

		Menu menu = new Menu(shlDsPokmonEditor, SWT.BAR);
		shlDsPokmonEditor.setMenuBar(menu);

		MenuItem mntmfile = new MenuItem(menu, SWT.CASCADE);
		mntmfile.setText(Messages.getString("MainWindow.mntmfile.text")); //$NON-NLS-1$

		Menu menu_1 = new Menu(mntmfile);
		mntmfile.setMenu(menu_1);

		MenuItem mntmopenNarc = new MenuItem(menu_1, SWT.NONE);
		mntmopenNarc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dlgOpen = new FileDialog(shlDsPokmonEditor
						.getShell(), SWT.OPEN);
				dlgOpen.setText("Select msg.narc...");
				String[] filter_ext = { "*msg.narc" };
				String[] filter_name = { "NARC Archive (*.narc)" };
				dlgOpen.setFilterNames(filter_name);
				dlgOpen.setFilterExtensions(filter_ext);
				if (dlgOpen.open() != null) {
					try {
						msgNarc = new Narc(new File(dlgOpen.getFilterPath(),
								dlgOpen.getFileName()).getAbsolutePath());
						if (dlgOpen.getFileName().equals("msg.narc"))
							prefix = "DP";
						else if (dlgOpen.getFileName().equals("pl_msg.narc"))
							prefix = "P";
						else if (dlgOpen.getFileName().equals("hgss_msg.narc"))
							prefix = "HGSS";
						else
							;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dlgOpen.setText("Select personal.narc...");
				filter_ext = new String[] { "*personal.narc" };
				filter_name = new String[] { "NARC Archive (*.narc)" };
				dlgOpen.setFilterNames(filter_name);
				dlgOpen.setFilterExtensions(filter_ext);
				if (dlgOpen.open() != null) {
					try {
						personalNarc = new Narc(new File(dlgOpen
								.getFilterPath(), dlgOpen.getFileName())
								.getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dlgOpen.setText("Select evo.narc...");
				filter_ext = new String[] { "*evo.narc" };
				filter_name = new String[] { "NARC Archive (*.narc)" };
				dlgOpen.setFilterNames(filter_name);
				dlgOpen.setFilterExtensions(filter_ext);
				if (dlgOpen.open() != null) {
					try {
						evoNarc = new Narc(new File(dlgOpen.getFilterPath(),
								dlgOpen.getFileName()).getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dlgOpen.setText("Select wotbl.narc...");
				filter_ext = new String[] { "*wotbl.narc" };
				filter_name = new String[] { "NARC Archive (*.narc)" };
				dlgOpen.setFilterNames(filter_name);
				dlgOpen.setFilterExtensions(filter_ext);
				if (dlgOpen.open() != null) {
					try {
						wotblNarc = new Narc(new File(dlgOpen.getFilterPath(),
								dlgOpen.getFileName()).getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					loadLists();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mntmopenNarc
				.setText(Messages.getString("MainWindow.mntmopenNarc.text")); //$NON-NLS-1$
		mntmopenNarc.setAccelerator(SWT.CTRL + 'O');

		MenuItem mntmsaveNarc = new MenuItem(menu_1, SWT.NONE);
		mntmsaveNarc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					personalNarc.writeNarc(personalNarc.getNarcPath()
							.toString());
					evoNarc.writeNarc(evoNarc.getNarcPath().toString());
					wotblNarc.writeNarc(wotblNarc.getNarcPath().toString());
					unsaved = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mntmsaveNarc
				.setText(Messages.getString("MainWindow.mntmsaveNarc.text")); //$NON-NLS-1$

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmexit = new MenuItem(menu_1, SWT.NONE);
		mntmexit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlDsPokmonEditor.close();
			}
		});
		mntmexit.setText(Messages.getString("MainWindow.mntmexit.text")); //$NON-NLS-1$

		MenuItem mntmhelp = new MenuItem(menu, SWT.CASCADE);
		mntmhelp.setText(Messages.getString("MainWindow.mntmhelp.text")); //$NON-NLS-1$

		Menu menu_2 = new Menu(mntmhelp);
		mntmhelp.setMenu(menu_2);

		MenuItem mntmabout = new MenuItem(menu_2, SWT.NONE);
		mntmabout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AboutDialog ab = new AboutDialog(shlDsPokmonEditor, 0);
				ab.open();
			}
		});
		mntmabout.setText(Messages.getString("MainWindow.mntmabout.text")); //$NON-NLS-1$

		Group grpPokmonList = new Group(shlDsPokmonEditor, SWT.NONE);
		grpPokmonList.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpPokmonList = new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 2);
		gd_grpPokmonList.widthHint = 176;
		grpPokmonList.setLayoutData(gd_grpPokmonList);
		grpPokmonList.setText(Messages
				.getString("MainWindow.grpPokmonList.text")); //$NON-NLS-1$

		lstPkmn = new List(grpPokmonList, SWT.BORDER | SWT.V_SCROLL);
		lstPkmn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					loadStats();
					loadEvolutions();
					rdEv1.setSelection(true);
					getEvs();
					loadMoveset();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		TabFolder tabFolder = new TabFolder(shlDsPokmonEditor, SWT.NONE);
		tabFolder.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));

		TabItem tbtmStats = new TabItem(tabFolder, SWT.NONE);
		tbtmStats.setText(Messages.getString("MainWindow.tbtmStats.text")); //$NON-NLS-1$

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmStats.setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		Group grpBaseStats = new Group(composite, SWT.NONE);
		grpBaseStats.setLayout(new GridLayout(4, false));
		GridData gd_grpBaseStats = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_grpBaseStats.widthHint = 275;
		grpBaseStats.setLayoutData(gd_grpBaseStats);
		grpBaseStats
				.setText(Messages.getString("MainWindow.grpBaseStats.text")); //$NON-NLS-1$

		Label lblBaseHp = new Label(grpBaseStats, SWT.NONE);
		lblBaseHp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblBaseHp.setText(Messages.getString("MainWindow.lblBaseHp.text")); //$NON-NLS-1$

		txtHP = new Text(grpBaseStats, SWT.BORDER);
		txtHP.setText(Messages.getString("MainWindow.text.text_1")); //$NON-NLS-1$
		txtHP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Label lblSpAttack = new Label(grpBaseStats, SWT.NONE);
		lblSpAttack.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSpAttack.setText(Messages.getString("MainWindow.lblAttackSp.text")); //$NON-NLS-1$

		txtSpAttack = new Text(grpBaseStats, SWT.BORDER);
		txtSpAttack.setText(Messages.getString("MainWindow.text_4.text")); //$NON-NLS-1$
		txtSpAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAttack = new Label(grpBaseStats, SWT.NONE);
		lblAttack.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblAttack.setText(Messages.getString("MainWindow.lblAttack.text")); //$NON-NLS-1$

		txtAttack = new Text(grpBaseStats, SWT.BORDER);
		txtAttack.setText(Messages.getString("MainWindow.text_1.text")); //$NON-NLS-1$
		txtAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblSpDefense = new Label(grpBaseStats, SWT.NONE);
		lblSpDefense.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSpDefense
				.setText(Messages.getString("MainWindow.lblSpDefense.text")); //$NON-NLS-1$

		txtSpDefense = new Text(grpBaseStats, SWT.BORDER);
		txtSpDefense.setText(Messages.getString("MainWindow.text_5.text")); //$NON-NLS-1$
		txtSpDefense.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblDefense = new Label(grpBaseStats, SWT.NONE);
		lblDefense.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDefense.setText(Messages.getString("MainWindow.lblDefense.text")); //$NON-NLS-1$

		txtDefense = new Text(grpBaseStats, SWT.BORDER);
		txtDefense.setText(Messages.getString("MainWindow.text_2.text")); //$NON-NLS-1$
		txtDefense.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblBaseXp = new Label(grpBaseStats, SWT.NONE);
		lblBaseXp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblBaseXp.setText(Messages.getString("MainWindow.lblBaseXp.text")); //$NON-NLS-1$

		txtBaseXP = new Text(grpBaseStats, SWT.BORDER);
		txtBaseXP.setText(Messages.getString("MainWindow.text_6.text")); //$NON-NLS-1$
		txtBaseXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblSpeed = new Label(grpBaseStats, SWT.NONE);
		lblSpeed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSpeed.setText(Messages.getString("MainWindow.lblSpeed.text")); //$NON-NLS-1$

		txtSpeed = new Text(grpBaseStats, SWT.BORDER);
		txtSpeed.setText(Messages.getString("MainWindow.text_3.text")); //$NON-NLS-1$
		txtSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblCatchRate = new Label(grpBaseStats, SWT.NONE);
		lblCatchRate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCatchRate
				.setText(Messages.getString("MainWindow.lblCatchRate.text")); //$NON-NLS-1$

		txtCatchRate = new Text(grpBaseStats, SWT.BORDER);
		txtCatchRate.setText(Messages.getString("MainWindow.text_7.text")); //$NON-NLS-1$
		txtCatchRate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Group grpAllevamento = new Group(composite, SWT.NONE);
		grpAllevamento.setLayout(new GridLayout(2, false));
		GridData gd_grpAllevamento = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		gd_grpAllevamento.widthHint = 226;
		grpAllevamento.setLayoutData(gd_grpAllevamento);
		grpAllevamento.setText(Messages
				.getString("MainWindow.grpAllevamento.text")); //$NON-NLS-1$

		Label lblEggType1 = new Label(grpAllevamento, SWT.NONE);
		lblEggType1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEggType1.setText(Messages.getString("MainWindow.lblEggGroup.text")); //$NON-NLS-1$

		cmbEggType1 = new Combo(grpAllevamento, SWT.NONE);
		cmbEggType1.setItems(Messages.getStrings("EggList"));
		cmbEggType1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblEggType = new Label(grpAllevamento, SWT.NONE);
		lblEggType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEggType.setText(Messages.getString("MainWindow.lblEggType.text")); //$NON-NLS-1$

		cmbEggType2 = new Combo(grpAllevamento, SWT.NONE);
		cmbEggType2.setItems(Messages.getStrings("EggList"));
		cmbEggType2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblStepsToHatch = new Label(grpAllevamento, SWT.NONE);
		lblStepsToHatch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		lblStepsToHatch.setText(Messages
				.getString("MainWindow.lblStepsToHatch.text"));

		cmbSteps = new Combo(grpAllevamento, SWT.NONE);
		cmbSteps.setItems(new String[] { "0", "256", "512", "768", "1024",
				"1280", "1536", "1792", "2048", "2304", "2560", "2816", "3072",
				"3328", "3584", "3840", "4096", "4352", "4608", "4864", "5120",
				"5376", "5632", "5888", "6144", "6400", "6656", "6912", "7168",
				"7424", "7680", "7936", "8192", "8448", "8704", "8960", "9216",
				"9472", "9728", "9984", "10240", "10496", "10752", "11008",
				"11264", "11520", "11776", "12032", "12288", "12544", "12800",
				"13056", "13312", "13568", "13824", "14080", "14336", "14592",
				"14848", "15104", "15360", "15616", "15872", "16128", "16384",
				"16640", "16896", "17152", "17408", "17664", "17920", "18176",
				"18432", "18688", "18944", "19200", "19456", "19712", "19968",
				"20224", "20480", "20736", "20992", "21248", "21504", "21760",
				"22016", "22272", "22528", "22784", "23040", "23296", "23552",
				"23808", "24064", "24320", "24576", "24832", "25088", "25344",
				"25600", "25856", "26112", "26368", "26624", "26880", "27136",
				"27392", "27648", "27904", "28160", "28416", "28672", "28928",
				"29184", "29440", "29696", "29952", "30208", "30464", "30720",
				"30976", "31232", "31488", "31744", "32000", "32256", "32512",
				"32768", "33024", "33280", "33536", "33792", "34048", "34304",
				"34560", "34816", "35072", "35328", "35584", "35840", "36096",
				"36352", "36608", "36864", "37120", "37376", "37632", "37888",
				"38144", "38400", "38656", "38912", "39168", "39424", "39680",
				"39936", "40192", "40448", "40704", "40960", "41216", "41472",
				"41728", "41984", "42240", "42496", "42752", "43008", "43264",
				"43520", "43776", "44032", "44288", "44544", "44800", "45056",
				"45312", "45568", "45824", "46080", "46336", "46592", "46848",
				"47104", "47360", "47616", "47872", "48128", "48384", "48640",
				"48896", "49152", "49408", "49664", "49920", "50176", "50432",
				"50688", "50944", "51200", "51456", "51712", "51968", "52224",
				"52480", "52736", "52992", "53248", "53504", "53760", "54016",
				"54272", "54528", "54784", "55040", "55296", "55552", "55808",
				"56064", "56320", "56576", "56832", "57088", "57344", "57600",
				"57856", "58112", "58368", "58624", "58880", "59136", "59392",
				"59648", "59904", "60160", "60416", "60672", "60928", "61184",
				"61440", "61696", "61952", "62208", "62464", "62720", "62976",
				"63232", "63488", "63744", "64000", "64256", "64512", "64768",
				"65024", "65280" });
		cmbSteps.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		Group grpTypes = new Group(composite, SWT.NONE);
		grpTypes.setLayout(new GridLayout(2, false));
		grpTypes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		grpTypes.setText(Messages.getString("MainWindow.grpTypes.text")); //$NON-NLS-1$

		Label lblFirstType = new Label(grpTypes, SWT.NONE);
		lblFirstType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblFirstType
				.setText(Messages.getString("MainWindow.lblFirstType.text")); //$NON-NLS-1$

		cmbType1 = new Combo(grpTypes, SWT.NONE);
		cmbType1.setItems(Messages.getStrings("TypeList"));
		cmbType1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblSecondType = new Label(grpTypes, SWT.NONE);
		lblSecondType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSecondType.setText(Messages
				.getString("MainWindow.lblSecondType.text")); //$NON-NLS-1$

		cmbType2 = new Combo(grpTypes, SWT.NONE);
		cmbType2.setItems(Messages.getStrings("TypeList"));
		cmbType2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Group grpHoldItems = new Group(composite, SWT.NONE);
		grpHoldItems.setLayout(new GridLayout(2, false));
		grpHoldItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		grpHoldItems
				.setText(Messages.getString("MainWindow.grpHoldItems.text")); //$NON-NLS-1$

		Label lblFirstItem = new Label(grpHoldItems, SWT.NONE);
		lblFirstItem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblFirstItem
				.setText(Messages.getString("MainWindow.lblFirstItem.text")); //$NON-NLS-1$

		cmbItem1 = new Combo(grpHoldItems, SWT.NONE);
		cmbItem1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblSecondItem = new Label(grpHoldItems, SWT.NONE);
		lblSecondItem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSecondItem.setText(Messages
				.getString("MainWindow.lblSecondItem.text")); //$NON-NLS-1$

		cmbItem2 = new Combo(grpHoldItems, SWT.NONE);
		cmbItem2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Group grpOtherStats = new Group(composite, SWT.NONE);
		grpOtherStats.setLayout(new GridLayout(2, false));
		grpOtherStats.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 2));
		grpOtherStats.setText(Messages
				.getString("MainWindow.grpOtherStats.text")); //$NON-NLS-1$

		Label lblGenderRatio = new Label(grpOtherStats, SWT.NONE);
		lblGenderRatio.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblGenderRatio.setText(Messages
				.getString("MainWindow.lblGenderRatio.text")); //$NON-NLS-1$

		cmbGenderRatio = new Combo(grpOtherStats, SWT.NONE);
		cmbGenderRatio.setItems(new String[] { "100%M 0%F", "99,607%M 0,393%F",
				"99,214%M 0,786%F", "98,821%M 1,179%F", "98,428%M 1,572%F",
				"98,035%M 1,965%F", "97,642%M 2,358%F", "97,249%M 2,751%F",
				"96,856%M 3,144%F", "96,463%M 3,537%F", "96,07%M 3,93%F",
				"95,677%M 4,323%F", "95,284%M 4,716%F", "94,891%M 5,109%F",
				"94,498%M 5,502%F", "94,105%M 5,895%F", "93,712%M 6,288%F",
				"93,319%M 6,681%F", "92,926%M 7,074%F", "92,533%M 7,467%F",
				"92,14%M 7,86%F", "91,747%M 8,253%F", "91,354%M 8,646%F",
				"90,961%M 9,039%F", "90,568%M 9,432%F", "90,175%M 9,825%F",
				"89,782%M 10,218%F", "89,389%M 10,611%F", "88,996%M 11,004%F",
				"88,603%M 11,397%F", "88,21%M 11,79%F", "87,817%M 12,183%F",
				"87,424%M 12,576%F", "87,031%M 12,969%F", "86,638%M 13,362%F",
				"86,245%M 13,755%F", "85,852%M 14,148%F", "85,459%M 14,541%F",
				"85,066%M 14,934%F", "84,673%M 15,327%F", "84,28%M 15,72%F",
				"83,887%M 16,113%F", "83,494%M 16,506%F", "83,101%M 16,899%F",
				"82,708%M 17,292%F", "82,315%M 17,685%F", "81,922%M 18,078%F",
				"81,529%M 18,471%F", "81,136%M 18,864%F", "80,743%M 19,257%F",
				"80,35%M 19,65%F", "79,957%M 20,043%F", "79,564%M 20,436%F",
				"79,171%M 20,829%F", "78,778%M 21,222%F", "78,385%M 21,615%F",
				"77,992%M 22,008%F", "77,599%M 22,401%F", "77,206%M 22,794%F",
				"76,813%M 23,187%F", "76,42%M 23,58%F", "76,027%M 23,973%F",
				"75,634%M 24,366%F", "75,241%M 24,759%F", "74,848%M 25,152%F",
				"74,455%M 25,545%F", "74,062%M 25,938%F", "73,669%M 26,331%F",
				"73,276%M 26,724%F", "72,883%M 27,117%F", "72,49%M 27,51%F",
				"72,097%M 27,903%F", "71,704%M 28,296%F", "71,311%M 28,689%F",
				"70,918%M 29,082%F", "70,525%M 29,475%F", "70,132%M 29,868%F",
				"69,739%M 30,261%F", "69,346%M 30,654%F", "68,953%M 31,047%F",
				"68,56%M 31,44%F", "68,167%M 31,833%F", "67,774%M 32,226%F",
				"67,381%M 32,619%F", "66,988%M 33,012%F", "66,595%M 33,405%F",
				"66,202%M 33,798%F", "65,809%M 34,191%F", "65,416%M 34,584%F",
				"65,023%M 34,977%F", "64,63%M 35,37%F", "64,237%M 35,763%F",
				"63,844%M 36,156%F", "63,451%M 36,549%F", "63,058%M 36,942%F",
				"62,665%M 37,335%F", "62,272%M 37,728%F", "61,879%M 38,121%F",
				"61,486%M 38,514%F", "61,093%M 38,907%F", "60,7%M 39,3%F",
				"60,307%M 39,693%F", "59,914%M 40,086%F", "59,521%M 40,479%F",
				"59,128%M 40,872%F", "58,735%M 41,265%F", "58,342%M 41,658%F",
				"57,949%M 42,051%F", "57,556%M 42,444%F", "57,163%M 42,837%F",
				"56,77%M 43,23%F", "56,377%M 43,623%F", "55,984%M 44,016%F",
				"55,591%M 44,409%F", "55,198%M 44,802%F", "54,805%M 45,195%F",
				"54,412%M 45,588%F", "54,019%M 45,981%F", "53,626%M 46,374%F",
				"53,233%M 46,767%F", "52,84%M 47,16%F", "52,447%M 47,553%F",
				"52,054%M 47,946%F", "51,661%M 48,339%F", "51,268%M 48,732%F",
				"50,875%M 49,125%F", "50,482%M 49,518%F", "50,089%M 49,911%F",
				"49,696%M 50,304%F", "49,303%M 50,697%F", "48,91%M 51,09%F",
				"48,517%M 51,483%F", "48,124%M 51,876%F", "47,731%M 52,269%F",
				"47,338%M 52,662%F", "46,945%M 53,055%F", "46,552%M 53,448%F",
				"46,159%M 53,841%F", "45,766%M 54,234%F", "45,373%M 54,627%F",
				"44,98%M 55,02%F", "44,587%M 55,413%F", "44,194%M 55,806%F",
				"43,801%M 56,199%F", "43,408%M 56,592%F", "43,015%M 56,985%F",
				"42,622%M 57,378%F", "42,229%M 57,771%F", "41,836%M 58,164%F",
				"41,443%M 58,557%F", "41,05%M 58,95%F", "40,657%M 59,343%F",
				"40,264%M 59,736%F", "39,871%M 60,129%F", "39,478%M 60,522%F",
				"39,085%M 60,915%F", "38,692%M 61,308%F", "38,299%M 61,701%F",
				"37,906%M 62,094%F", "37,513%M 62,487%F", "37,12%M 62,88%F",
				"36,727%M 63,273%F", "36,334%M 63,666%F", "35,941%M 64,059%F",
				"35,548%M 64,452%F", "35,155%M 64,845%F", "34,762%M 65,238%F",
				"34,369%M 65,631%F", "33,976%M 66,024%F", "33,583%M 66,417%F",
				"33,19%M 66,81%F", "32,797%M 67,203%F", "32,404%M 67,596%F",
				"32,011%M 67,989%F", "31,618%M 68,382%F", "31,225%M 68,775%F",
				"30,832%M 69,168%F", "30,439%M 69,561%F", "30,046%M 69,954%F",
				"29,653%M 70,347%F", "29,26%M 70,74%F", "28,867%M 71,133%F",
				"28,474%M 71,526%F", "28,081%M 71,919%F", "27,688%M 72,312%F",
				"27,295%M 72,705%F", "26,902%M 73,098%F", "26,509%M 73,491%F",
				"26,116%M 73,884%F", "25,723%M 74,277%F", "25,33%M 74,67%F",
				"24,937%M 75,063%F", "24,544%M 75,456%F", "24,151%M 75,849%F",
				"23,758%M 76,242%F", "23,365%M 76,635%F", "22,972%M 77,028%F",
				"22,579%M 77,421%F", "22,186%M 77,814%F", "21,793%M 78,207%F",
				"21,4%M 78,6%F", "21,007%M 78,993%F", "20,614%M 79,386%F",
				"20,221%M 79,779%F", "19,828%M 80,172%F", "19,435%M 80,565%F",
				"19,042%M 80,958%F", "18,649%M 81,351%F", "18,256%M 81,744%F",
				"17,863%M 82,137%F", "17,47%M 82,53%F", "17,077%M 82,923%F",
				"16,684%M 83,316%F", "16,291%M 83,709%F", "15,898%M 84,102%F",
				"15,505%M 84,495%F", "15,112%M 84,888%F", "14,719%M 85,281%F",
				"14,326%M 85,674%F", "13,933%M 86,067%F", "13,54%M 86,46%F",
				"13,147%M 86,853%F", "12,754%M 87,246%F", "12,361%M 87,639%F",
				"11,968%M 88,032%F", "11,575%M 88,425%F", "11,182%M 88,818%F",
				"10,789%M 89,211%F", "10,396%M 89,604%F", "10,003%M 89,997%F",
				"9,61%M 90,39%F", "9,217%M 90,783%F", "8,824%M 91,176%F",
				"8,431%M 91,569%F", "8,038%M 91,962%F", "7,645%M 92,355%F",
				"7,252%M 92,748%F", "6,859%M 93,141%F", "6,466%M 93,534%F",
				"6,073%M 93,927%F", "5,68%M 94,32%F", "5,287%M 94,713%F",
				"4,894%M 95,106%F", "4,501%M 95,499%F", "4,108%M 95,892%F",
				"3,715%M 96,285%F", "3,322%M 96,678%F", "2,929%M 97,071%F",
				"2,536%M 97,464%F", "2,143%M 97,857%F", "1,75%M 98,25%F",
				"1,357%M 98,643%F", "0,964%M 99,036%F", "0,571%M 99,429%F",
				"0,178%M 99,822%F", "0%M 100%F" });
		cmbGenderRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblBaseHappiness = new Label(grpOtherStats, SWT.NONE);
		lblBaseHappiness.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblBaseHappiness.setText(Messages
				.getString("MainWindow.lblBaseHappiness.text")); //$NON-NLS-1$

		txtBaseHappiness = new Text(grpOtherStats, SWT.BORDER);
		txtBaseHappiness.setText(Messages.getString("MainWindow.text.text_2")); //$NON-NLS-1$
		txtBaseHappiness.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblGreatMarshRun = new Label(grpOtherStats, SWT.NONE);
		lblGreatMarshRun.setText(Messages
				.getString("MainWindow.lblGreatMarshRun.text"));

		txtRunChance = new Text(grpOtherStats, SWT.BORDER);
		txtRunChance.setText(Messages.getString("MainWindow.text.text_3")); //$NON-NLS-1$
		txtRunChance.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblMaxXp = new Label(grpOtherStats, SWT.NONE);
		lblMaxXp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblMaxXp.setText(Messages.getString("MainWindow.lblMaxXp.text")); //$NON-NLS-1$

		cmbMaxXP = new Combo(grpOtherStats, SWT.NONE);
		cmbMaxXP.setItems(new String[] { "1,000,000", "600,000", "1,640,000",
				"1,059,860", "800,000", "1,250,000" });
		cmbMaxXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Group grpSpecialAbilities = new Group(composite, SWT.NONE);
		grpSpecialAbilities.setLayout(new GridLayout(2, false));
		grpSpecialAbilities.setLayoutData(new GridData(SWT.FILL, SWT.TOP,
				false, false, 1, 1));
		grpSpecialAbilities
				.setText(Messages.getString("MainWindow.group.text")); //$NON-NLS-1$

		Label lblFirstAbility = new Label(grpSpecialAbilities, SWT.NONE);
		lblFirstAbility.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblFirstAbility.setText(Messages
				.getString("MainWindow.lblFirstAbility.text")); //$NON-NLS-1$

		cmbAbility1 = new Combo(grpSpecialAbilities, SWT.NONE);
		cmbAbility1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblSecondAbility = new Label(grpSpecialAbilities, SWT.NONE);
		lblSecondAbility.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblSecondAbility.setText(Messages
				.getString("MainWindow.lblSecondAbility.text")); //$NON-NLS-1$

		cmbAbility2 = new Combo(grpSpecialAbilities, SWT.NONE);
		cmbAbility2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		grpColor = new Group(composite, SWT.NONE);
		grpColor.setLayout(new GridLayout(1, false));
		grpColor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		grpColor.setText(Messages.getString("MainWindow.grpColor.text")); //$NON-NLS-1$

		spnColor = new Spinner(grpColor, SWT.BORDER);
		spnColor.setMaximum(255);
		spnColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		grpEvolutionGivenValues = new Group(composite, SWT.NONE);
		grpEvolutionGivenValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 2, 1));
		grpEvolutionGivenValues.setText(Messages
				.getString("MainWindow.grpEvolutionGivenValues.text")); //$NON-NLS-1$
		grpEvolutionGivenValues.setLayout(new GridLayout(6, false));

		label = new Label(grpEvolutionGivenValues, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label.setText(Messages.getString("MainWindow.label.text")); //$NON-NLS-1$

		txtEvHP = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvHP.setText(Messages.getString("MainWindow.txtEvHP.text")); //$NON-NLS-1$
		txtEvHP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		label_4 = new Label(grpEvolutionGivenValues, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_4.setText(Messages.getString("MainWindow.label_4.text")); //$NON-NLS-1$

		txtEvDefense = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvDefense
				.setText(Messages.getString("MainWindow.txtEvDefense.text")); //$NON-NLS-1$
		txtEvDefense.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		label_1 = new Label(grpEvolutionGivenValues, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_1.setText(Messages.getString("MainWindow.label_1.text")); //$NON-NLS-1$

		txtEvSpAttack = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvSpAttack.setText(Messages
				.getString("MainWindow.txtEvSpAttack.text")); //$NON-NLS-1$
		txtEvSpAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		label_2 = new Label(grpEvolutionGivenValues, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_2.setText(Messages.getString("MainWindow.label_2.text")); //$NON-NLS-1$

		txtEvAttack = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvAttack.setText(Messages.getString("MainWindow.txtEvAttack.text")); //$NON-NLS-1$
		txtEvAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		label_6 = new Label(grpEvolutionGivenValues, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_6.setText(Messages.getString("MainWindow.label_6.text")); //$NON-NLS-1$

		txtEvSpeed = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvSpeed.setText(Messages.getString("MainWindow.txtEvSpeed.text")); //$NON-NLS-1$
		txtEvSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		label_3 = new Label(grpEvolutionGivenValues, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_3.setText(Messages.getString("MainWindow.label_3.text")); //$NON-NLS-1$

		txtEvSpDefense = new Text(grpEvolutionGivenValues, SWT.BORDER);
		txtEvSpDefense.setText(Messages
				.getString("MainWindow.txtEvSpDefense.text")); //$NON-NLS-1$
		txtEvSpDefense.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		TabItem tbtmEvolutions = new TabItem(tabFolder, SWT.NONE);
		tbtmEvolutions.setText(Messages
				.getString("MainWindow.tbtmEvolutions.text")); //$NON-NLS-1$

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmEvolutions.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, false));

		Group grpEvolutions = new Group(composite_1, SWT.NONE);
		GridLayout gl_grpEvolutions = new GridLayout(7, true);
		gl_grpEvolutions.horizontalSpacing = 35;
		grpEvolutions.setLayout(gl_grpEvolutions);
		grpEvolutions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		grpEvolutions.setText(Messages
				.getString("MainWindow.grpEvolutions.text")); //$NON-NLS-1$

		rdEv1 = new Button(grpEvolutions, SWT.RADIO);
		rdEv1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv1.setText(Messages.getString("MainWindow.button.text")); //$NON-NLS-1$

		rdEv2 = new Button(grpEvolutions, SWT.RADIO);
		rdEv2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv2.setText(Messages.getString("MainWindow.button_1.text")); //$NON-NLS-1$

		rdEv3 = new Button(grpEvolutions, SWT.RADIO);
		rdEv3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv3.setText(Messages.getString("MainWindow.button_2.text")); //$NON-NLS-1$

		rdEv4 = new Button(grpEvolutions, SWT.RADIO);
		rdEv4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv4.setText(Messages.getString("MainWindow.button_3.text")); //$NON-NLS-1$

		rdEv5 = new Button(grpEvolutions, SWT.RADIO);
		rdEv5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv5.setText(Messages.getString("MainWindow.button_4.text")); //$NON-NLS-1$

		rdEv6 = new Button(grpEvolutions, SWT.RADIO);
		rdEv6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv6.setText(Messages.getString("MainWindow.button_5.text")); //$NON-NLS-1$

		rdEv7 = new Button(grpEvolutions, SWT.RADIO);
		rdEv7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getEvs();
			}
		});
		rdEv7.setText(Messages.getString("MainWindow.button_6.text"));

		grpEvolutionData = new Group(composite_1, SWT.NONE);
		grpEvolutionData.setLayout(new GridLayout(2, false));
		GridData gd_grpEvolutionData = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		gd_grpEvolutionData.heightHint = 172;
		grpEvolutionData.setLayoutData(gd_grpEvolutionData);
		grpEvolutionData.setText(Messages
				.getString("MainWindow.grpEvolutionData.text")); //$NON-NLS-1$

		lblEvolutionType = new Label(grpEvolutionData, SWT.NONE);
		lblEvolutionType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionType.setText(Messages
				.getString("MainWindow.lblEvolutionType.text")); //$NON-NLS-1$

		cmbEvType = new Combo(grpEvolutionData, SWT.NONE);
		cmbEvType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (cmbEvType.getSelectionIndex() == 4
						|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
								.getSelectionIndex() <= 14)
						|| cmbEvType.getSelectionIndex() == 22
						|| cmbEvType.getSelectionIndex() == 23) {
					cmbEvLvl.setEnabled(true);
					cmbEvAttack.setEnabled(false);
					cmbEvItem.setEnabled(false);
					cmbEvPkmn.setEnabled(false);
					txtEvHappiness.setEnabled(false);
				} else if (cmbEvType.getSelectionIndex() == 15) {
					cmbEvLvl.setEnabled(false);
					cmbEvAttack.setEnabled(false);
					cmbEvItem.setEnabled(false);
					cmbEvPkmn.setEnabled(false);
					txtEvHappiness.setEnabled(true);
				} else if (cmbEvType.getSelectionIndex() == 20) {
					cmbEvLvl.setEnabled(false);
					cmbEvAttack.setEnabled(true);
					cmbEvItem.setEnabled(false);
					cmbEvPkmn.setEnabled(false);
					txtEvHappiness.setEnabled(false);
				} else if (cmbEvType.getSelectionIndex() == 21) {
					cmbEvLvl.setEnabled(false);
					cmbEvAttack.setEnabled(false);
					cmbEvItem.setEnabled(false);
					cmbEvPkmn.setEnabled(true);
					txtEvHappiness.setEnabled(false);
				} else if (cmbEvType.getSelectionIndex() == 6
						|| cmbEvType.getSelectionIndex() == 7
						|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
								.getSelectionIndex() <= 19)) {
					cmbEvLvl.setEnabled(false);
					cmbEvAttack.setEnabled(false);
					cmbEvItem.setEnabled(true);
					cmbEvPkmn.setEnabled(false);
					txtEvHappiness.setEnabled(false);
				} else {
					cmbEvLvl.setEnabled(false);
					cmbEvAttack.setEnabled(false);
					cmbEvItem.setEnabled(false);
					cmbEvPkmn.setEnabled(false);
					txtEvHappiness.setEnabled(false);
				}
			}
		});
		cmbEvType.setItems(Messages.getStrings("EvolutionList"));
		cmbEvType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		trEv = new Tree(grpEvolutionData, SWT.BORDER);
		GridData gd_trEv = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_trEv.heightHint = 123;
		trEv.setLayoutData(gd_trEv);

		grpEvolutionEditor = new Group(composite_1, SWT.NONE);
		grpEvolutionEditor.setLayout(new GridLayout(4, false));
		grpEvolutionEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 1, 1));
		grpEvolutionEditor.setText(Messages
				.getString("MainWindow.grpEvolutionEditor.text"));

		lblEvolutionLevel = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolutionLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionLevel.setText(Messages
				.getString("MainWindow.lblEvolutionLevel.text")); //$NON-NLS-1$

		cmbEvLvl = new Combo(grpEvolutionEditor, SWT.NONE);
		cmbEvLvl.setItems(new String[] { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50", "51", "52", "53", "54", "55", "56", "57",
				"58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
				"68", "69", "70", "71", "72", "73", "74", "75", "76", "77",
				"78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
				"88", "89", "90", "91", "92", "93", "94", "95", "96", "97",
				"98", "99", "100" });
		cmbEvLvl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		lblEvolutionPokemon = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolutionPokemon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionPokemon.setText(Messages
				.getString("MainWindow.lblEvolutionPokemon.text")); //$NON-NLS-1$

		cmbEvPkmn = new Combo(grpEvolutionEditor, SWT.NONE);
		cmbEvPkmn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		lblEvolutionItem = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolutionItem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionItem.setText(Messages
				.getString("MainWindow.lblEvolutionItem.text")); //$NON-NLS-1$

		cmbEvItem = new Combo(grpEvolutionEditor, SWT.NONE);
		cmbEvItem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		lblEvolutionAttack = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolutionAttack.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionAttack.setText(Messages
				.getString("MainWindow.lblEvolutionAttack.text")); //$NON-NLS-1$

		cmbEvAttack = new Combo(grpEvolutionEditor, SWT.NONE);
		cmbEvAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblEvolutionHappiness = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolutionHappiness.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvolutionHappiness.setText(Messages
				.getString("MainWindow.lblEvolutionHappiness.text")); //$NON-NLS-1$

		txtEvHappiness = new Text(grpEvolutionEditor, SWT.BORDER);
		txtEvHappiness.setText(Messages
				.getString("MainWindow.txtEvolutionhappiness.text")); //$NON-NLS-1$
		txtEvHappiness.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblEvolvesIn = new Label(grpEvolutionEditor, SWT.NONE);
		lblEvolvesIn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEvolvesIn
				.setText(Messages.getString("MainWindow.lblEvolvesIn.text")); //$NON-NLS-1$

		cmbEvIn = new Combo(grpEvolutionEditor, SWT.NONE);
		cmbEvIn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		btnSaveEvolution = new Button(composite_1, SWT.NONE);
		btnSaveEvolution.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setEvs();
			}
		});
		btnSaveEvolution.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM,
				false, false, 1, 1));
		btnSaveEvolution.setText(Messages
				.getString("MainWindow.btnSaveEvolution.text")); //$NON-NLS-1$

		TabItem tbtmMoves = new TabItem(tabFolder, SWT.NONE);
		tbtmMoves.setText(Messages.getString("MainWindow.tbtmMoves.text")); //$NON-NLS-1$

		composite_2 = new Composite(tabFolder, SWT.NONE);
		composite_2.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmMoves.setControl(composite_2);
		composite_2.setLayout(new GridLayout(2, false));

		grpTmCompatibility = new Group(composite_2, SWT.NONE);
		GridData gd_grpTmCompatibility = new GridData(SWT.LEFT, SWT.FILL,
				false, true, 1, 1);
		gd_grpTmCompatibility.heightHint = 313;
		grpTmCompatibility.setLayoutData(gd_grpTmCompatibility);
		grpTmCompatibility.setText(Messages
				.getString("MainWindow.grpTmCompatibility.text_1"));
		grpTmCompatibility.setLayout(new GridLayout(2, true));

		tblTM = new Table(grpTmCompatibility, SWT.BORDER | SWT.CHECK
				| SWT.FULL_SELECTION);
		GridData gd_tblTM = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tblTM.widthHint = 181;
		gd_tblTM.heightHint = 171;
		tblTM.setLayoutData(gd_tblTM);
		for (int i = 1; i <= 92; i++) {
			TableItem t = new TableItem(tblTM, SWT.NONE);
			t.setText(Messages.getString("TM") + i);
		}

		btnSelectAll = new Button(grpTmCompatibility, SWT.NONE);
		btnSelectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (int i = 0; i < tblTM.getItemCount(); i++)
					tblTM.getItem(i).setChecked(true);
			}
		});
		btnSelectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnSelectAll
				.setText(Messages.getString("MainWindow.btnSelectAll.text")); //$NON-NLS-1$

		btnDeselectAll = new Button(grpTmCompatibility, SWT.NONE);
		btnDeselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (int i = 0; i < tblTM.getItemCount(); i++)
					tblTM.getItem(i).setChecked(false);
			}
		});
		btnDeselectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnDeselectAll.setText(Messages
				.getString("MainWindow.btnDeselectAll.text")); //$NON-NLS-1$

		grpMoveset = new Group(composite_2, SWT.NONE);
		grpMoveset.setLayout(new GridLayout(4, false));
		grpMoveset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		grpMoveset.setText(Messages.getString("MainWindow.grpMoveset.text_1")); //$NON-NLS-1$

		tblMoveset = new Table(grpMoveset, SWT.BORDER | SWT.FULL_SELECTION);
		tblMoveset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				spnLevel.setSelection(Integer.parseInt(tblMoveset.getItem(
						tblMoveset.getSelectionIndex()).getText(1)));
				cmbAttack.select(attackList.indexOf(tblMoveset.getItem(
						tblMoveset.getSelectionIndex()).getText(0)));
			}
		});
		tblMoveset.setLinesVisible(true);
		tblMoveset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				4, 1));

		tblclmnAttack = new TableColumn(tblMoveset, SWT.NONE);
		tblclmnAttack.setResizable(false);
		tblclmnAttack.setWidth(200);
		tblclmnAttack.setText(Messages
				.getString("MainWindow.tblclmnAttack.text")); //$NON-NLS-1$

		tblclmnLevel = new TableColumn(tblMoveset, SWT.NONE);
		tblclmnLevel.setResizable(false);
		tblclmnLevel.setWidth(105);
		tblclmnLevel
				.setText(Messages.getString("MainWindow.tblclmnLevel.text"));

		lblLevel = new Label(grpMoveset, SWT.NONE);
		lblLevel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false,
				1, 1));
		lblLevel.setText(Messages.getString("MainWindow.lblLevel.text_1")); //$NON-NLS-1$

		lblAttack_1 = new Label(grpMoveset, SWT.NONE);
		lblAttack_1.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false,
				false, 1, 1));
		lblAttack_1
				.setText(Messages.getString("MainWindow.lblAttack_1.text_1")); //$NON-NLS-1$
		new Label(grpMoveset, SWT.NONE);

		btnAdd = new Button(grpMoveset, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (tblMoveset.getItemCount() < 20) {
					TableItem tmp = new TableItem(tblMoveset, SWT.NONE);
					tmp.setText(0,
							attackList.get(cmbAttack.getSelectionIndex()));
					tmp.setText(1, String.valueOf(spnLevel.getSelection()));
				}
			}
		});
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd.setText(Messages.getString("MainWindow.btnAdd.text"));

		spnLevel = new Spinner(grpMoveset, SWT.BORDER);

		cmbAttack = new Combo(grpMoveset, SWT.NONE);
		cmbAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		btnSave = new Button(grpMoveset, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tblMoveset.getItem(tblMoveset.getSelectionIndex()).setText(1,
						String.valueOf(spnLevel.getSelection()));
				tblMoveset.getItem(tblMoveset.getSelectionIndex()).setText(0,
						attackList.get(cmbAttack.getSelectionIndex()));
			}
		});
		GridData gd_btnSave = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_btnSave.widthHint = 61;
		btnSave.setLayoutData(gd_btnSave);
		btnSave.setText(Messages.getString("MainWindow.btnSave.text")); //$NON-NLS-1$

		btnDelete = new Button(grpMoveset, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (tblMoveset.getItemCount() > 1
						&& tblMoveset.getSelectionIndex() != -1)
					tblMoveset.remove(tblMoveset.getSelectionIndex());
			}
		});
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnDelete.setText(Messages.getString("MainWindow.btnDelete.text")); //$NON-NLS-1$

		grpHmCompatibility = new Group(composite_2, SWT.NONE);
		grpHmCompatibility.setLayout(new GridLayout(2, false));
		GridData gd_grpHmCompatibility = new GridData(SWT.FILL, SWT.FILL,
				false, true, 2, 1);
		gd_grpHmCompatibility.heightHint = 139;
		grpHmCompatibility.setLayoutData(gd_grpHmCompatibility);
		grpHmCompatibility.setText(Messages
				.getString("MainWindow.grpHmCompatibility.text"));

		tblHM = new Table(grpHmCompatibility, SWT.BORDER | SWT.CHECK
				| SWT.FULL_SELECTION);
		tblHM.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		for (int i = 1; i <= 8; i++) {
			TableItem t = new TableItem(tblHM, SWT.NONE);
			t.setText(Messages.getString("HM") + i);
		}

		btnSelectAll_1 = new Button(grpHmCompatibility, SWT.NONE);
		btnSelectAll_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (int i = 0; i < tblHM.getItemCount(); i++)
					tblHM.getItem(i).setChecked(true);
			}
		});
		btnSelectAll_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		btnSelectAll_1.setText(Messages.getString("SelectAll"));

		btnDeselectAll_1 = new Button(grpHmCompatibility, SWT.NONE);
		btnDeselectAll_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (int i = 0; i < tblHM.getItemCount(); i++)
					tblHM.getItem(i).setChecked(false);
			}
		});
		btnDeselectAll_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		btnDeselectAll_1.setText(Messages.getString("DeselectAll"));

		btnSavePokmon = new Button(shlDsPokmonEditor, SWT.NONE);
		btnSavePokmon.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					if (lstPkmn.getSelectionIndex() != -1) {
						writeStats();
						writeEvolutions();
						writeMoveset();
						unsaved = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnSavePokmon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnSavePokmon.setText(Messages
				.getString("MainWindow.btnSavePokmon.text")); //$NON-NLS-1$

		shlDsPokmonEditor.open();
		shlDsPokmonEditor.layout();
		while (!shlDsPokmonEditor.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Load routines
	private static void loadMoveset() throws IOException {
		ByteArrayInputStream inp = new ByteArrayInputStream(wotblNarc
				.getFimgEntry().get(lstPkmn.getSelectionIndex()).getEntryData());
		tblMoveset.removeAll();
		for (int i = 0; i < 20; i++) {
			int t = inp.read() | (inp.read() << 8);
			if (t != 0xFFFF) {
				TableItem itm = new TableItem(tblMoveset, SWT.NONE);
				itm.setText(0, attackList.get(t & 0x1ff));
				itm.setText(1, String.valueOf(t >> 9));
			} else
				i = 20;
		}
		inp.close();
		System.gc();
	}

	private static void loadEvolutions() throws IOException {
		ByteArrayInputStream inp = new ByteArrayInputStream(evoNarc
				.getFimgEntry().get(lstPkmn.getSelectionIndex()).getEntryData());
		tmpEvList = new ArrayList<EvData>();
		tmpEvList.clear();
		for (int i = 0; i < 7; i++) {
			EvData tmp = new EvData();
			tmp.setType(EndianUtils.readSwappedUnsignedShort(inp));
			tmp.setSpecific(EndianUtils.readSwappedUnsignedShort(inp));
			tmp.setTarget(EndianUtils.readSwappedUnsignedShort(inp));
			tmpEvList.add(tmp);
		}
		inp.close();

		// Fill the evolution tree
		trEv.removeAll();
		inp.reset();
		inp.skip(4);
		TreeItem root = new TreeItem(trEv, SWT.NONE);
		root.setText(lstPkmn.getItem(lstPkmn.getSelectionIndex()));
		for (int i = 0; i < 7; i++) {
			int n = EndianUtils.readSwappedUnsignedShort(inp);
			if (n != 0) {
				TreeItem it = new TreeItem(root, SWT.NONE);
				it.setText(pkmnList.get(n));
			}
			inp.skip(4);
		}
		root.setExpanded(true);
		System.gc();
	}

	private static void getEvs() {
		if (rdEv1.getSelection()) {
			cmbEvType.select(tmpEvList.get(0).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(0).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(0)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(0).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(0).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(0).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(0).getTarget());
		} else if (rdEv2.getSelection()) {
			cmbEvType.select(tmpEvList.get(1).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(1).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(1)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(1).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(1).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(1).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(1).getTarget());
		} else if (rdEv3.getSelection()) {
			cmbEvType.select(tmpEvList.get(2).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(2).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(2)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(2).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(2).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(2).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(2).getTarget());
		} else if (rdEv4.getSelection()) {
			cmbEvType.select(tmpEvList.get(3).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(3).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(3)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(3).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(3).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(3).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(3).getTarget());
		} else if (rdEv5.getSelection()) {
			cmbEvType.select(tmpEvList.get(4).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(4).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(4)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(4).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(4).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(4).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(4).getTarget());
		} else if (rdEv6.getSelection()) {
			cmbEvType.select(tmpEvList.get(5).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(5).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(5)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(5).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(5).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(5).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(5).getTarget());
		} else if (rdEv7.getSelection()) {
			cmbEvType.select(tmpEvList.get(6).getType());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvLvl.select(tmpEvList.get(6).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				txtEvHappiness.setText(String.valueOf(tmpEvList.get(6)
						.getSpecific()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvAttack.select(tmpEvList.get(6).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				cmbEvPkmn.select(tmpEvList.get(6).getSpecific());
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				cmbEvItem.select(tmpEvList.get(6).getSpecific());
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			cmbEvIn.select(tmpEvList.get(6).getTarget());
		}
	}

	private static void loadStats() throws IOException {
		ByteArrayInputStream inp = new ByteArrayInputStream(personalNarc
				.getFimgEntry().get(lstPkmn.getSelectionIndex()).getEntryData());

		// Base Stats
		txtHP.setText(String.valueOf(inp.read()));
		txtAttack.setText(String.valueOf(inp.read()));
		txtDefense.setText(String.valueOf(inp.read()));
		txtSpeed.setText(String.valueOf(inp.read()));
		txtSpAttack.setText(String.valueOf(inp.read()));
		txtSpDefense.setText(String.valueOf(inp.read()));
		cmbType1.select(inp.read());
		cmbType2.select(inp.read());
		txtCatchRate.setText(String.valueOf(inp.read()));
		txtBaseXP.setText(String.valueOf(inp.read()));
		int t = inp.read();
		txtEvHP.setText(String.valueOf(t & 3));
		txtEvAttack.setText(String.valueOf((t & 0xC) >> 2));
		txtEvDefense.setText(String.valueOf((t & 0x30) >> 4));
		txtEvSpeed.setText(String.valueOf((t & 0xC0) >> 6));
		t = inp.read();
		txtEvSpAttack.setText(String.valueOf(t & 0x3));
		txtEvSpDefense.setText(String.valueOf((t & 0xC) >> 2));
		cmbItem1.select(EndianUtils.readSwappedUnsignedShort(inp));
		cmbItem2.select(EndianUtils.readSwappedUnsignedShort(inp));
		cmbGenderRatio.select(inp.read());
		cmbSteps.select(inp.read());
		txtBaseHappiness.setText(String.valueOf(inp.read()));
		cmbMaxXP.select(inp.read());
		cmbEggType1.select(inp.read());
		cmbEggType2.select(inp.read());
		cmbAbility1.select(inp.read());
		cmbAbility2.select(inp.read());
		txtRunChance.setText(String.valueOf(inp.read()));
		spnColor.setSelection(inp.read());

		// TMs
		inp.reset();
		inp.skip(28);
		byte buf[] = new byte[12];
		inp.read(buf);
		for (int i = 0; i < 92; i++) {
			if (((buf[i / 8] >> (i % 8)) & 1) == 1)
				tblTM.getItem(i).setChecked(true);
			else
				tblTM.getItem(i).setChecked(false);
		}

		// HMs
		inp.reset();
		inp.skip(39);
		t = inp.read();
		if (((t >> 4) & 1) == 1)
			tblHM.getItem(0).setChecked(true);
		if (((t >> 5) & 1) == 1)
			tblHM.getItem(1).setChecked(true);
		if (((t >> 6) & 1) == 1)
			tblHM.getItem(2).setChecked(true);
		if (((t >> 7) & 1) == 1)
			tblHM.getItem(3).setChecked(true);
		t = inp.read();
		if ((t & 1) == 1)
			tblHM.getItem(4).setChecked(true);
		if (((t >> 1) & 1) == 1)
			tblHM.getItem(5).setChecked(true);
		if (((t >> 2) & 1) == 1)
			tblHM.getItem(6).setChecked(true);
		if (((t >> 3) & 1) == 1)
			tblHM.getItem(7).setChecked(true);
		inp.close();
		System.gc();
	}

	private static void loadLists() throws Exception {
		pkmnList = new ArrayList<String>();
		attackList = new ArrayList<String>();
		ArrayList<String> itemList = new ArrayList<String>(), abilityList = new ArrayList<String>();
		if (prefix == "DP") {
			PokeText.readText(msgNarc.getFimgEntry().get(362), pkmnList);
			PokeText.readText(msgNarc.getFimgEntry().get(345), itemList);
			PokeText.readText(msgNarc.getFimgEntry().get(589), attackList);
			PokeText.readText(msgNarc.getFimgEntry().get(552), abilityList);
		} else if (prefix == "P") {
			PokeText.readText(msgNarc.getFimgEntry().get(412), pkmnList);
			PokeText.readText(msgNarc.getFimgEntry().get(392), itemList);
			PokeText.readText(msgNarc.getFimgEntry().get(648), attackList);
			PokeText.readText(msgNarc.getFimgEntry().get(610), abilityList);
		} else {
			PokeText.readText(msgNarc.getFimgEntry().get(237), pkmnList);
			PokeText.readText(msgNarc.getFimgEntry().get(222), itemList);
			PokeText.readText(msgNarc.getFimgEntry().get(751), attackList);
			PokeText.readText(msgNarc.getFimgEntry().get(720), abilityList);
		}
		for (int i = 0; i < pkmnList.size(); i++) {
			lstPkmn.add(pkmnList.get(i));
			cmbEvIn.add(pkmnList.get(i));
			cmbEvPkmn.add(pkmnList.get(i));
		}
		for (int i = 0; i < itemList.size(); i++) {
			cmbItem1.add(itemList.get(i));
			cmbItem2.add(itemList.get(i));
			cmbEvItem.add(itemList.get(i));
		}
		for (int i = 0; i < attackList.size(); i++) {
			cmbEvAttack.add(attackList.get(i));
			cmbAttack.add(attackList.get(i));
		}
		for (int i = 0; i < abilityList.size(); i++) {
			cmbAbility1.add(abilityList.get(i));
			cmbAbility2.add(abilityList.get(i));
		}
		System.gc();
	}

	// Write routines
	private static void writeStats() throws IOException {
		// Base Stats
		ByteArrayOutputStream out = new ByteArrayOutputStream(44);
		out.write(Integer.parseInt(txtHP.getText()));
		out.write(Integer.parseInt(txtAttack.getText()));
		out.write(Integer.parseInt(txtDefense.getText()));
		out.write(Integer.parseInt(txtSpeed.getText()));
		out.write(Integer.parseInt(txtSpAttack.getText()));
		out.write(Integer.parseInt(txtSpDefense.getText()));
		out.write(cmbType1.getSelectionIndex());
		out.write(cmbType2.getSelectionIndex());
		out.write(Integer.parseInt(txtCatchRate.getText()));
		out.write(Integer.parseInt(txtBaseXP.getText()));
		int ev = 0, e = 0;
		ev += (Integer.parseInt(txtEvHP.getText()) & 3) << e;
		e += 2;
		ev += (Integer.parseInt(txtEvAttack.getText()) & 3) << e;
		e += 2;
		ev += (Integer.parseInt(txtEvDefense.getText()) & 3) << e;
		e += 2;
		ev += (Integer.parseInt(txtEvSpeed.getText()) & 3) << e;
		e += 2;
		ev += (Integer.parseInt(txtEvSpAttack.getText()) & 3) << e;
		e += 2;
		ev += (Integer.parseInt(txtEvSpDefense.getText()) & 3) << e;
		e += 2;
		EndianUtils.writeSwappedShort(out, (short) ev);
		EndianUtils
				.writeSwappedShort(out, (short) cmbItem1.getSelectionIndex());
		EndianUtils
				.writeSwappedShort(out, (short) cmbItem2.getSelectionIndex());
		out.write(cmbGenderRatio.getSelectionIndex());
		out.write(cmbSteps.getSelectionIndex());
		out.write(Integer.parseInt(txtBaseHappiness.getText()));
		out.write(cmbMaxXP.getSelectionIndex());
		out.write(cmbEggType1.getSelectionIndex());
		out.write(cmbEggType2.getSelectionIndex());
		out.write(cmbAbility1.getSelectionIndex());
		out.write(cmbAbility2.getSelectionIndex());
		out.write(Integer.parseInt(txtRunChance.getText()));
		out.write(spnColor.getSelection());
		EndianUtils.writeSwappedShort(out, (short) 0x0);

		// TMs
		byte buf[] = new byte[12];
		for (int i = 0; i < 92; i++) {
			if (tblTM.getItem(i).getChecked() == true)
				buf[i / 8] += (1 << (i % 8));
			else
				buf[i / 8] += (0 << (i % 8));
		}
		out.write(buf, 0, 11);

		// HMs
		byte t = buf[11], t1 = 0;
		if (tblHM.getItem(0).getChecked() == true)
			t += 1 << 4;
		else
			t += 0 << 4;
		if (tblHM.getItem(1).getChecked() == true)
			t += 1 << 5;
		else
			t += 0 << 5;
		if (tblHM.getItem(2).getChecked() == true)
			t += 1 << 6;
		else
			t += 0 << 6;
		if (tblHM.getItem(3).getChecked() == true)
			t += 1 << 7;
		else
			t += 0 << 7;
		if (tblHM.getItem(4).getChecked() == true)
			t1 += 1;
		else
			t1 += 0;
		if (tblHM.getItem(5).getChecked() == true)
			t1 += 1 << 1;
		else
			t1 += 0 << 1;
		if (tblHM.getItem(6).getChecked() == true)
			t1 += 1 << 2;
		else
			t1 += 0 << 2;
		if (tblHM.getItem(7).getChecked() == true)
			t1 += 1 << 3;
		else
			t1 += 0 << 3;
		out.write(t);
		out.write(t1);
		while (out.size() < 44)
			out.write(0);

		personalNarc.getFimgEntry().get(lstPkmn.getSelectionIndex())
				.setEntryData(out.toByteArray());
	}

	private static void writeEvolutions() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(44);
		for (int i = 0; i < 7; i++) {
			EndianUtils.writeSwappedShort(out, (short) tmpEvList.get(i)
					.getType());
			EndianUtils.writeSwappedShort(out, (short) tmpEvList.get(i)
					.getSpecific());
			EndianUtils.writeSwappedShort(out, (short) tmpEvList.get(i)
					.getTarget());
		}
		EndianUtils.writeSwappedShort(out, (short) 0x0);
		evoNarc.getFimgEntry().get(lstPkmn.getSelectionIndex())
				.setEntryData(out.toByteArray());
	}

	private static void setEvs() {
		if (rdEv1.getSelection()) {
			tmpEvList.get(0).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(0).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(0).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(0).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(0).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(0).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(0).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv2.getSelection()) {
			tmpEvList.get(1).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(1).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(1).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(1).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(1).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(1).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(1).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv3.getSelection()) {
			tmpEvList.get(2).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(2).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(2).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(2).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(2).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(2).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(2).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv4.getSelection()) {
			tmpEvList.get(3).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(3).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(3).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(3).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(3).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(3).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(3).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv5.getSelection()) {
			tmpEvList.get(4).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(4).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(4).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(4).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(4).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(4).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(4).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv6.getSelection()) {
			tmpEvList.get(5).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(5).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(5).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(5).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(5).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(5).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(5).setTarget(cmbEvIn.getSelectionIndex());
		} else if (rdEv7.getSelection()) {
			tmpEvList.get(6).setType(cmbEvType.getSelectionIndex());
			if (cmbEvType.getSelectionIndex() == 4
					|| (cmbEvType.getSelectionIndex() >= 8 && cmbEvType
							.getSelectionIndex() <= 14)
					|| cmbEvType.getSelectionIndex() == 22
					|| cmbEvType.getSelectionIndex() == 23) {
				cmbEvLvl.setEnabled(true);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(6).setSpecific(cmbEvLvl.getSelectionIndex());
			} else if (cmbEvType.getSelectionIndex() == 15) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(true);
				tmpEvList.get(6).setSpecific(
						Integer.valueOf(txtEvHappiness.getText()));
			} else if (cmbEvType.getSelectionIndex() == 20) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(true);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(6).setSpecific(
						Integer.valueOf(cmbEvAttack.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 21) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(true);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(6).setSpecific(
						Integer.valueOf(cmbEvPkmn.getSelectionIndex()));
			} else if (cmbEvType.getSelectionIndex() == 6
					|| cmbEvType.getSelectionIndex() == 7
					|| (cmbEvType.getSelectionIndex() >= 16 && cmbEvType
							.getSelectionIndex() <= 19)) {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(true);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
				tmpEvList.get(6).setSpecific(
						Integer.valueOf(cmbEvItem.getSelectionIndex()));
			} else {
				cmbEvLvl.setEnabled(false);
				cmbEvAttack.setEnabled(false);
				cmbEvItem.setEnabled(false);
				cmbEvPkmn.setEnabled(false);
				txtEvHappiness.setEnabled(false);
			}
			tmpEvList.get(6).setTarget(cmbEvIn.getSelectionIndex());
		}
	}

	private static void writeMoveset() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < tblMoveset.getItemCount(); i++)
			EndianUtils
					.writeSwappedShort(out,
							(short) ((Integer.parseInt(tblMoveset.getItem(i)
									.getText(1)) << 9) | attackList
									.indexOf(tblMoveset.getItem(i).getText(0))));
		EndianUtils.writeSwappedShort(out, (short) 0xFFFF);
		wotblNarc.getFimgEntry().get(lstPkmn.getSelectionIndex())
				.setEntryData(out.toByteArray());
	}
}
