package edu.handong.bonus;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

public class Commandls {
	
	String input;
	boolean optionA;
	boolean optionT;
	boolean optionF;
	boolean optionCapF;
	boolean optionD;
	boolean optionH;
	boolean optionR;
	
	public void run(String[] args) {
		
		Options options = new Options(); 
		createOptions(options);
		
		//System.out.println("your input: " + input);
		
		/*try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}*/
		if(args.length < 2) {
			System.out.println("need to input arguments");
		}
		
		
		if (parseOption(options, args)) {
			
			if(input == null) {
				input = System.getProperty("user.dir");
			}
			
			System.out.println("your input: " + input);
			File file = new File(input);
			File dir = new File(input);
			File[] fileList = file.listFiles();
			File[] dirFile = dir.listFiles((FileFilter) FileFileFilter.FILE);
			//String[] pathFile = file.list();
			//String[] nameFile = file.list();
			ArrayList<String> pathOfFile = new ArrayList<String>();
			ArrayList<String> abPathOfFile = new ArrayList<String>();
			ArrayList<String> nameOfFile = new ArrayList<String>();
			
			for(File listFile: fileList) {
				String filePath = listFile.getParent();
				String fileAbPath = listFile.getAbsolutePath();
				String fileName = listFile.getName();
				
				pathOfFile.add(filePath);
				abPathOfFile.add(fileAbPath);
				nameOfFile.add(fileName);
			}
			
			
			if(optionA) {
				
				Collections.sort(nameOfFile);
				
				for(String resultA: nameOfFile) {
					System.out.println(resultA);
				}
			}
			
			if(optionT) { 
				
				//ArrayList<String> sortedByTime = new ArrayList<String>();
				Arrays.sort(dirFile, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				
				for(File sortedByTime: dirFile) {
					System.out.println(sortedByTime.getName());
				}
			}
			
			if(optionF) {
				
				for(String resultOptf: nameOfFile) {
					System.out.println(resultOptf);
				}
			}
			
			/*if(optionCapF) { 
				
				for(File f: fileList) {
					String str = f.getName();
					
					if(f.isDirectory()) {
						str = str + "/";
					}
					
					else if(f.isFile()) {
						str = str + "";
					}
					System.out.println(str);
				}
			}*/
			
			if(optionD) {
				
				for(File f: fileList) {
					String str = f.getName();
					if(f.isDirectory()) {
						System.out.println(str + "/");
					}
				}
			}
			
			if(optionH) {
				String[] resultOptH = file.list();
				String size = "";
				
				long fileSize = file.length();
				size = Long.toString(fileSize) + "bytes";
				
				System.out.println(size);
			}
			
			/*if(optionR) {
				
				
				//Collections.sort(nameOfFile);
				Collections.reverse(nameOfFile);
				
				for(String resultA: nameOfFile) {
					System.out.println(resultA);
				}
			}*/
		}	
		
	}
	
	private boolean parseOption(Options options, String[] args) {
		
		CommandLineParser parser = new DefaultParser();
		
		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			optionA = cmd.hasOption("a");
			optionT = cmd.hasOption("t");
			optionF = cmd.hasOption("f");
			optionCapF = cmd.hasOption("F");
			optionD = cmd.hasOption("d");
			optionH = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}

	private Options createOptions(Options options) {
		// TODO Auto-generated method stub
		//Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.argName("Input path").build());
		
		options.addOption(Option.builder("a").longOpt("listFile")
				.desc("list all files")
				.argName("list").build());
		
		options.addOption(Option.builder("t").longOpt("sortFile")
				.desc("sort file by modified time")
				.argName("sortFile").build());
		
		options.addOption(Option.builder("f").longOpt("notSort")
				.desc("do not sort")
				.argName("notSort").build());
		
		/*options.addOption(Option.builder("F").longOpt("char")
				.desc("append character")
				.argName("appendChar").build());*/
		
		options.addOption(Option.builder("d").longOpt("information")
				.desc("show information about link or directory")
				.argName("info").build());
		
		options.addOption(Option.builder("h").longOpt("size")
				.desc("print sizes")
				.argName("size").build());
		
		/*options.addOption(Option.builder("r").longOpt("reverse")
				.desc("list files by sort reversed")
				.argName("reverse").build());*/
		
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}
}
