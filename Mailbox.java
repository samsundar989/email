package hw5;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * The Mailbox class implements the methods and variables to represent an email box which contains all email folders
 * and controls folder operations. 
 * 
 * 
 * @author Samuel Sundararaman 
 * 		e-mail: samuel.sundararaman@stonybrook.edu 
 * 		Stony Brook ID: 111352739
 */
public class Mailbox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Folder inbox; // Folder which stores the inbox, all new emails go here
	private Folder trash; // Folder which stores deleted emails
	private ArrayList<Folder> folders; // List which contains all folders
	public static Mailbox mailbox = new Mailbox(); // Main mailbox that will be used by the application

	public void addFolder(Folder folder) {
		folders.add(folder);
	}

	public void deleteFolder(String name) {
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getName().equals(name)) {
				folders.remove(i);
			}
		}
	}

	/**
	 * Gets user input on the contents of the email and adds it to the inbox.
	 */
	public void composeEmail(String to, String c, String b, String sub, String body, GregorianCalendar time) {
		Email toAdd = new Email(to, c, b, sub, body, time);
		inbox.addEmail(toAdd);
	}

	/**
	 * Adds email to the trash folder.
	 * 
	 * @param email
	 *            Email to be moved to trash folder.
	 */
	public void deleteEmail(Email email) {
		trash.addEmail(email);
	}

	/**
	 * Empties the trash folder of all emails.
	 */
	public void clearTrash() {
		for (int i = 0; i < trash.getSize(); i++) {
			trash.removeEmail(i);
		}
	}

	/**
	 * Takes the given email and moves it to the target folder.
	 * 
	 * @param email
	 * @param target
	 */
	public void moveEmail(Email email, Folder target) {
		if (folders.contains(target)) {
			for (int i = 0; i < folders.size(); i++) {
				if (folders.get(i).getName().equals(target.getName())) {
					folders.get(i).addEmail(email);
				}
			}
		} else {
			inbox.addEmail(email);
		}
	}

	public Folder getFolder(String name) {
		Folder toRet = null;
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getName().equals(name)) {
				toRet = folders.get(i);
			}
		}
		return toRet;
	}

	public static void main(String[] args) {

		try {
			FileInputStream file = new FileInputStream("mailbox.obj");
			ObjectInputStream box = new ObjectInputStream(file);
			mailbox = (Mailbox) box.readObject();
			file.close();
		} catch (IOException a) {
			System.out.println("Previous save not found, starting with an empty mailbox.");
			mailbox = new Mailbox();
			mailbox.inbox = new Folder("Inbox");
			mailbox.trash = new Folder("Trash");
			mailbox.folders = new ArrayList<Folder>();
			mailbox.addFolder(mailbox.inbox);
			mailbox.addFolder(mailbox.trash);
		} catch (ClassNotFoundException c) {
			System.out.println("ClassNotFound");
		}

		Scanner scanner = new Scanner(System.in);

		String input = "Z";

		while (!input.equals("Q")) {
			System.out.println("Mailbox:");
			System.out.println("--------");
			for (int i = 0; i < mailbox.folders.size(); i++) {
				System.out.println(mailbox.folders.get(i).getName());
			}
			System.out.println("");
			System.out.println("A - Add Folder \n" + "R - Remove Folder \n" + "C - Compose email \n"
					+ "F - Open Folder \n" + "I - Open Inbox \n" + "T - Open Trash \n" + "Q - Quit");
			System.out.println(" ");
			System.out.print("Enter a user option: ");
			input = scanner.nextLine().toUpperCase();
			System.out.println(" ");

			if (input.equals("A")) {
				String name;
				System.out.print("Enter folder name: ");
				name = scanner.nextLine();
				Folder toAdd = new Folder(name);
				mailbox.addFolder(toAdd);
			}

			if (input.equals("R")) {
				System.out.print("Enter the name of the folder to remove: ");
				String f = scanner.nextLine();
				mailbox.folders.remove(mailbox.getFolder(f));
			}

			if (input.equals("C")) {
				String to;
				String cc;
				String bcc;
				String sub;
				String bod;
				System.out.print("Enter recipient (To): ");
				to = scanner.nextLine();
				System.out.println("");
				System.out.print("Enter carbon copy recipients (CC): ");
				cc = scanner.nextLine();
				System.out.println("");
				System.out.print("Enter blind carbon copy recipients (BCC): ");
				bcc = scanner.nextLine();
				System.out.println("");
				System.out.println("Enter subject line: ");
				sub = scanner.nextLine();
				System.out.println("");
				System.out.print("Enter body: ");
				bod = scanner.next();
				System.out.println("");
				GregorianCalendar time = new GregorianCalendar();
				mailbox.composeEmail(to, cc, bcc, sub, bod, time);
			}

			if (input.equals("F")) {
				System.out.print("Enter folder name: ");
				String f = scanner.nextLine();
				System.out.println("");
				System.out.println(f);
				System.out.println("");
				if (mailbox.getFolder(f).getSize() == 0) {
					System.out.println(f + " is empty");
				} else {
					System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
					System.out.println("");
					System.out.println("---------------------------------------------------------");
					for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
						System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
								mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
								mailbox.getFolder(f).getEmail(x).getSubject());
						System.out.println("");
					}
				}
				String i = "Z";
				System.out.println("");

				while (!i.equals("R")) {
					System.out.println("M - Move email \n" + "V - View email contents \n"
							+ "SA - Sort by subject line in ascending order \n"
							+ "SD - Sort by subject line in descending order \n"
							+ "DA - Sort by date in ascending order \n" + "DD - Sort by date in descending order \n"
							+ "R - Return to mailbox");
					System.out.println("");
					System.out.print("Enter a user option: ");
					i = scanner.nextLine().toUpperCase();
					System.out.println("");

					if (i.equals("M")) {
						System.out.println("Enter the index of the email to move: ");
						int index = scanner.nextInt();
						System.out.println("Folders");
						for (int z = 0; z < mailbox.folders.size(); z++) {
							System.out.println(mailbox.folders.get(z).getName());
						}
						System.out.println("");
						System.out.print("Select a folder to move \"" + mailbox.trash.getEmail(index - 1).getSubject()
								+ "\" to: ");
						String fold = scanner.nextLine();
						System.out.println("");
						mailbox.moveEmail(mailbox.getFolder(f).getEmail(index - 1), mailbox.getFolder(fold));
						System.out.println("");
						System.out.println("\"" + mailbox.getFolder(f).getEmail(index - 1).getSubject()
								+ "\" successfully moved to \"" + fold + "\"");
						System.out.println("");
						System.out.println(f);
						System.out.println("");

						if (mailbox.getFolder(f).getSize() == 0) {
							System.out.println(f + "  is empty");
						} else {
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
										mailbox.getFolder(f).getEmail(x).getSubject());
								System.out.println("");
							}
						}
					}

					if (i.equals("V")) {
						System.out.println("Enter email index: ");
						int index = scanner.nextInt();
						System.out.println("");
						System.out.println("To: " + mailbox.getFolder(f).getEmail(index - 1).getTo());
						System.out.println("CC: " + mailbox.getFolder(f).getEmail(index - 1).getCC());
						System.out.println("BCC: " + mailbox.getFolder(f).getEmail(index - 1).getBCC());
						System.out.println("Subject: " + mailbox.getFolder(f).getEmail(index - 1).getSubject());
						System.out.println(mailbox.getFolder(f).getEmail(index - 1).getBody());
						System.out.println(f);
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
									mailbox.getFolder(f).getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (i.equals("SA")) {
						mailbox.getFolder(f).sortBySubjectAscending();
						System.out.println(f + " has been sorted by subject line in ascending order.");
						System.out.println(f);
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
									mailbox.getFolder(f).getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (i.equals("SD")) {
						mailbox.getFolder(f).sortBySubjectDescending();
						System.out.println(f + " has been sorted by subject line in descending order.");
						System.out.println(f);
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
									mailbox.getFolder(f).getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (i.equals("DA")) {
						mailbox.getFolder(f).sortByDateAscending();
						System.out.println(f + " has been sorted by date in ascending order.");
						System.out.println(f);
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
									mailbox.getFolder(f).getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (i.equals("DD")) {
						mailbox.getFolder(f).sortByDateAscending();
						System.out.println(f + " has been sorted by date in descending order.");
						System.out.println(f);
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.getFolder(f).getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.getFolder(f).getEmail(x).getTime().getTime(), "|",
									mailbox.getFolder(f).getEmail(x).getSubject());
							System.out.println("");
						}
					}
				}
			}

			if (input.equals("I")) {
				System.out.println("Inbox");
				System.out.println("");
				if (mailbox.inbox.getSize() == 0) {
					System.out.println("Inbox is empty");
				} else {
					System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
					System.out.println("");
					System.out.println("---------------------------------------------------------");
					for (int x = 0; x < mailbox.inbox.getSize(); x++) {
						System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
								mailbox.inbox.getEmail(x).getTime().getTime(), "|",
								mailbox.inbox.getEmail(x).getSubject());
						System.out.println("");
					}
				}
				String in = "Z";
				System.out.println("");

				while (!in.equals("R")) {
					System.out.println("M - Move email \n" + "D - Delete email \n" + "V - View email contents \n"
							+ "SA - Sort by subject line in ascending order \n"
							+ "SD - Sort by subject line in descending order \n"
							+ "DA - Sort by date in ascending order \n" + "DD - Sort by date in descending order \n"
							+ "R - Return to mailbox");
					System.out.println("");
					System.out.print("Enter a user option: ");
					in = scanner.nextLine().toUpperCase();
					System.out.println("");

					if (in.equals("M")) {
						System.out.println("Enter the index of the email to move: ");
						int index = scanner.nextInt();
						System.out.println("Folders");
						for (int z = 0; z < mailbox.folders.size(); z++) {
							System.out.println(mailbox.folders.get(z).getName());
						}
						System.out.println("");
						System.out.print("Select a folder to move \"" + mailbox.inbox.getEmail(index - 1).getSubject()
								+ "\" to: ");
						String fold = scanner.nextLine();
						System.out.println("");
						mailbox.moveEmail(mailbox.inbox.getEmail(index - 1), mailbox.getFolder(fold));
						System.out.println("");
						System.out.println("\"" + mailbox.inbox.getEmail(index - 1).getSubject()
								+ "\" successfully moved to \"" + fold + "\"");
						System.out.println("Inbox");
						System.out.println("");

						if (mailbox.inbox.getSize() == 0) {
							System.out.println("Inbox is empty");
						} else {
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.inbox.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.inbox.getEmail(x).getTime().getTime(), "|",
										mailbox.inbox.getEmail(x).getSubject());
								System.out.println("");
							}
						}
					}

					if (in.equals("D")) {
						System.out.println("Enter email index: ");
						int index = scanner.nextInt();
						System.out.println("");
						Email move = mailbox.inbox.removeEmail(index - 1);
						mailbox.trash.addEmail(move);
						System.out.println("\"" + move.getSubject() + "\" has successfully been moved to the trash.");
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}

					}

					if (in.equals("V")) {
						System.out.println("Enter email index: ");
						int index = scanner.nextInt();
						System.out.println("");
						System.out.println("To: " + mailbox.inbox.getEmail(index - 1).getTo());
						System.out.println("CC: " + mailbox.inbox.getEmail(index - 1).getCC());
						System.out.println("BCC: " + mailbox.inbox.getEmail(index - 1).getBCC());
						System.out.println("Subject: " + mailbox.inbox.getEmail(index - 1).getSubject());
						System.out.println(mailbox.inbox.getEmail(index - 1).getBody());
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (in.equals("SA")) {
						mailbox.inbox.sortBySubjectAscending();
						System.out.println("Inbox has been sorted by subject line in ascending order.");
						System.out.println("");
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (in.equals("SD")) {
						mailbox.inbox.sortBySubjectDescending();
						System.out.println("Inbox has been sorted by subject line in descending order.");
						System.out.println("");
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (in.equals("DA")) {
						mailbox.inbox.sortByDateAscending();
						System.out.println("Inbox has been sorted by date in ascending order.");
						System.out.println("");
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}
					}

					if (in.equals("DD")) {
						mailbox.inbox.sortByDateAscending();
						System.out.println("Inbox has been sorted by date in descending order.");
						System.out.println("");
						System.out.println("Inbox");
						System.out.println("");
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.inbox.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.inbox.getEmail(x).getTime().getTime(), "|",
									mailbox.inbox.getEmail(x).getSubject());
							System.out.println("");
						}
					}

				}

				if (input.equals("T")) {
					System.out.println("Trash");
					System.out.println("");
					if (mailbox.trash.getSize() == 0) {
						System.out.println("Trash is empty");
					} else {
						System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
						System.out.println("");
						System.out.println("---------------------------------------------------------");
						for (int x = 0; x < mailbox.trash.getSize(); x++) {
							System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
									mailbox.trash.getEmail(x).getTime().getTime(), "|",
									mailbox.trash.getEmail(x).getSubject());
							System.out.println("");
						}
					}
					String i = "Z";
					System.out.println("");

					while (!i.equals("R")) {
						System.out.println("M - Move email \n" + "V - View email contents \n"
								+ "SA - Sort by subject line in ascending order \n"
								+ "SD - Sort by subject line in descending order \n"
								+ "DA - Sort by date in ascending order \n" + "DD - Sort by date in descending order \n"
								+ "R - Return to mailbox");
						System.out.println("");
						System.out.print("Enter a user option: ");
						i = scanner.nextLine().toUpperCase();
						System.out.println("");

						if (i.equals("M")) {
							System.out.println("Enter the index of the email to move: ");
							int index = scanner.nextInt();
							System.out.println("Folders");
							for (int z = 0; z < mailbox.folders.size(); z++) {
								System.out.println(mailbox.folders.get(z).getName());
							}
							System.out.println("");
							System.out.print("Select a folder to move \""
									+ mailbox.trash.getEmail(index - 1).getSubject() + "\" to: ");
							String fold = scanner.nextLine();
							System.out.println("");
							mailbox.moveEmail(mailbox.trash.getEmail(index - 1), mailbox.getFolder(fold));
							System.out.println("");
							System.out.println("\"" + mailbox.trash.getEmail(index - 1).getSubject()
									+ "\" successfully moved to \"" + fold + "\"");
							System.out.println("Trash");
							System.out.println("");

							if (mailbox.trash.getSize() == 0) {
								System.out.println("Trash is empty");
							} else {
								System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
								System.out.println("");
								System.out.println("---------------------------------------------------------");
								for (int x = 0; x < mailbox.trash.getSize(); x++) {
									System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
											mailbox.trash.getEmail(x).getTime().getTime(), "|",
											mailbox.trash.getEmail(x).getSubject());
									System.out.println("");
								}
							}
						}

						if (i.equals("V")) {
							System.out.println("Enter email index: ");
							int index = scanner.nextInt();
							System.out.println("");
							System.out.println("To: " + mailbox.trash.getEmail(index - 1).getTo());
							System.out.println("CC: " + mailbox.trash.getEmail(index - 1).getCC());
							System.out.println("BCC: " + mailbox.trash.getEmail(index - 1).getBCC());
							System.out.println("Subject: " + mailbox.trash.getEmail(index - 1).getSubject());
							System.out.println(mailbox.trash.getEmail(index - 1).getBody());
							System.out.println("Trash");
							System.out.println("");
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.trash.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.trash.getEmail(x).getTime().getTime(), "|",
										mailbox.trash.getEmail(x).getSubject());
								System.out.println("");
							}
						}

						if (i.equals("SA")) {
							mailbox.trash.sortBySubjectAscending();
							System.out.println("Trash has been sorted by subject line in ascending order.");
							System.out.println("");
							System.out.println("Trash");
							System.out.println("");
							System.out.printf("%4d %4d %4d", "Index", "Time", "Subject");
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.trash.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.trash.getEmail(x).getTime().getTime(), "|",
										mailbox.trash.getEmail(x).getSubject());
								System.out.println("");
							}
						}

						if (i.equals("SD")) {
							mailbox.trash.sortBySubjectDescending();
							System.out.println("Trash has been sorted by subject line in descending order.");
							System.out.println("");
							System.out.println("Trash");
							System.out.println("");
							System.out.printf("%4d %4d %4d", "Index", "Time", "Subject");
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.trash.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.trash.getEmail(x).getTime().getTime(), "|",
										mailbox.trash.getEmail(x).getSubject());
								System.out.println("");
							}
						}

						if (i.equals("DA")) {
							mailbox.trash.sortByDateAscending();
							System.out.println("Trash has been sorted by date in ascending order.");
							System.out.println("");
							System.out.println("Trash");
							System.out.println("");
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.trash.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.trash.getEmail(x).getTime().getTime(), "|",
										mailbox.trash.getEmail(x).getSubject());
								System.out.println("");
							}
						}

						if (i.equals("DD")) {
							mailbox.trash.sortByDateAscending();
							System.out.println("Trash has been sorted by date in descending order.");
							System.out.println("");
							System.out.println("Trash");
							System.out.println("");
							System.out.printf("%4s %4s %20s %15s %4s", "Index", "|", "Time", "|", "Subject");
							System.out.println("");
							System.out.println("---------------------------------------------------------");
							for (int x = 0; x < mailbox.trash.getSize(); x++) {
								System.out.printf("%5s %4s %20s %7s %4s", x + 1, "|",
										mailbox.trash.getEmail(x).getTime().getTime(), "|",
										mailbox.trash.getEmail(x).getSubject());
								System.out.println("");
							}
						}
					}
				}
				

				try {
					FileOutputStream file = new FileOutputStream("mailbox.obj");
					ObjectOutputStream fout = new ObjectOutputStream(file);
					fout.writeObject(mailbox);
					fout.close();
				} catch (IOException a) {
					System.out.println("IOException");
				}
			}

		}
	}
}