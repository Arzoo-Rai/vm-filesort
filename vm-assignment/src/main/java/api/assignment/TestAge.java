package api.assignment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestAge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Path fullPath = new File("./", "commands.txt").toPath();
		List<File> files;
		try {
			files = Util.splitAndSortTempFiles(fullPath.toAbsolutePath().toString(), "C:/temp", 4,
					new AgeComparator());
			Util.mergeSortedFiles(files, "./LargeFile.txt", new AgeComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
