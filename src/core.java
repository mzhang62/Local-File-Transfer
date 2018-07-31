package copyLog;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class core {
	public static ArrayList<File> myArrayList = new ArrayList<File>();
	
	/**
	 * copy files from src to dest
	 * 
	 * @param from source file's path
	 * @param to   destination path
	 * @throws Exception unable to copy/delete files
	 */
	public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {
		try {
			// opens source directory
			File dir = new File(from);
			// copy src name
			to += File.separator + dir.getName();
			// create dest dir
			File moveDir = new File(to);
			if (dir.isDirectory()) {
				if (!moveDir.exists()) {
					moveDir.mkdirs();
				}
				// if there is only one single file under from
			} else {
				File tofile = new File(to);
				dir.renameTo(tofile);
				return;
			}

			// view all files
			File[] files = dir.listFiles();
			if (files == null)
				return;

			// move files
			for (int i = 0; i < files.length; i++) {
				System.out.println("File name: " + files[i].getName());
				if (files[i].isDirectory()) {
					// recursively copy inner directories
					MoveFolderAndFileWithSelf(files[i].getPath(), to);
					// delete inner directories
					files[i].delete();
				}
				// copy files into destination
				File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());

				// delete original files if copy is completed
				if (moveFile.exists()) {
					moveFile.delete();
				}
				files[i].renameTo(moveFile);
			}
			// delete original directory/file
			dir.delete();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * print from and all the files within it
	 * 
	 * @param from source file's path
	 */
	public static void printFiles(String from) {
		File directory = new File(from);
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files == null || files.length == 0) {
				System.out.println("There are no file under this directory.");
				return;
			}
			System.out.println("Files under " + directory.getPath() + ":");
			for (int i = 0; i < files.length; i++) {
				System.out.println("\t" + (i + 1) + ": " + files[i].getName() + ", size: "
						+ core.getSize(files[i].getPath()) + "MB.");
			}
		} else {
			System.out.println(directory.getPath());
		}
	}

	/**
	 * 
	 * @param from source file's path
	 * @return from's size in MB
	 */
	public static double getSize(String from) {
		File src = new File(from);
		double size = src.length() / 1024.0; // convert to MB
		return size;
	}

	/**
	 *
	 * @param from source file's path
	 * @return created days of the parameter file
	 */
	public static long getDifference(String from) throws Exception {
		File src = new File(from);
		if (!src.exists()) {
			throw new Exception("cannot find file");
		}
		long srcTime = src.lastModified(); // get src date
		Date now = new Date();
		long current = now.getTime(); // get current date
		long days = (current - srcTime) / (1000 * 3600 * 24);
		System.out.println("\"" + src.getName() + "\" has existed for " + (days) + " day(s).");
		return days;
	}

	/**
	 * Move all the files which exceeds the date limit
	 * 
	 * @param from      source file path
	 * @param to        destination path
	 * @param dateLimit the date limit set by te user
	 * @throws Exception
	 */
	public static void moveWithDate(String from, String to, int dateLimit) throws Exception {
		File directory = new File(from);
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files == null) {
				System.out.println("There are no file under " + directory.getPath());
				return;
			}
			System.out.println("Files under " + directory.getPath() + ":");
			for (int i = 0; i < files.length; i++) {
				System.out.println("--> " + (i + 1) + ": " + files[i].getName() + "; size: "
						+ core.getSize(files[i].getPath()) + " MB.");
				if (core.getDifference(files[i].getPath()) > dateLimit) {
					core.MoveFolderAndFileWithSelf(files[i].getPath(), to);
					System.out.println("Moved: " + files[i].getName());
					myArrayList.add(files[i]);
					
					continue;
				}
				System.out.println("Did not move: " + files[i].getName());
			}
		} else {
			if (core.getDifference(directory.getPath()) > dateLimit) {
				core.MoveFolderAndFileWithSelf(directory.getPath(), to);
				System.out.println("Have moved: " + directory.getName());
				myArrayList.add(directory);
				
			}
			System.out.println("Did not move: " + directory.getName());
		}
	}

	
	public boolean copyBegin(String from, String to, long space, int date) throws Exception {
		// FINAL CONSTANTS
//		final String source = "C:\\Users\\Administrator\\Desktop\\dest";
//		final String destination = "C:\\Users\\Administrator\\Desktop\\src";
//		final long spaceLimit = -1; // SET STORAGE LIMIT
//		final int dateLimit = 2; // SET EXPIRATION DATE
		// ------------------------------------------------------------------
		// core.printFiles(destination);
		if (core.getSize(from) > space) {
			System.out.println("***Storage overflow! Begin to transfer files***");
			core.moveWithDate(from, to, date);
			System.out.println("***Transfer DONE***");
			return true;

		} else {
			System.out.println("***Enough storage. DONE***");
			return false;
		}
	}

}
