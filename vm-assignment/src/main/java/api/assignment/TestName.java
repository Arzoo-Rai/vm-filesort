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

public class TestName {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Path fullPath = new File("./", "commands.txt").toPath();
		List<File> files;
		try {
			files = splitAndSortTempFiles(fullPath.toAbsolutePath().toString(), "C:/temp", 4,
					new StringComparator());
			mergeSortedFiles(files, "./LargeFile.txt", new StringComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<File> splitAndSortTempFiles(final String fileName, final String tempDirectory,
			final int noOfSplits, final StringComparator cmp) throws IOException {
		List<File> files = new ArrayList<>();
		RandomAccessFile raf = new RandomAccessFile(fileName, "r");
		long sourceSize = raf.length();
		long bytesPerSplit = sourceSize / noOfSplits;
		long remainingBytes = sourceSize % noOfSplits;
		int maxReadBufferSize =  8*1024; // 8KB
		int fileCounter = 1;
		for (int i = 1; i <= noOfSplits; i++) {
			File dir = new File(tempDirectory);
			if (dir.exists()) {
				dir.delete();
			}
			dir.mkdir();
			File file = new File(tempDirectory + "/temp-file-" + fileCounter + ".txt");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			if (bytesPerSplit > maxReadBufferSize) {
				long numReads = bytesPerSplit / maxReadBufferSize;
				long numRemainingRead = bytesPerSplit % maxReadBufferSize;
				for (int j = 0; j < numReads; j++) {
					readWrite(raf, bos, maxReadBufferSize);
				}
				if (numRemainingRead > 0) {
					readWrite(raf, bos, numRemainingRead);
				}
			} else {
				readWrite(raf, bos, bytesPerSplit);
			}
			file = sortFileContent(file, cmp);
			files.add(file);
			fileCounter++;
			bos.close();
		}
		if (remainingBytes > 0) {
			File file = new File(tempDirectory + "/temp-file-" + fileCounter + ".txt");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			readWrite(raf, bos, remainingBytes);
			file = sortFileContent(file, cmp);
			files.add(file);
			bos.close();
		}
		return files;
	}
	private static void readWrite(RandomAccessFile raf, BufferedOutputStream bos, long numBytes) throws IOException {
		byte[] buf = new byte[(int) numBytes];
		int val = raf.read(buf);
		if (val != -1) {
			bos.write(buf);
			bos.flush();
		}
	}
	/**
	 * Sort file content

	 */
	private static File sortFileContent(File file, StringComparator cmp) throws IOException {
		List<String> lines = new ArrayList<>();
		List<Person> persons = new ArrayList<>();

		try (Stream<String> ln = Files.lines(file.toPath())) {
			lines = ln.collect(Collectors.toList());

		}
		for(String line:lines) {
			System.out.println(line);
			String []ls= line.split(" ");

			Person obj;
		//	System.out.println(line);
			if(ls.length==3) {	
				obj = new Person(ls[0],Integer.parseInt(ls[1]),Integer.parseInt(ls[2]));
				persons.add(obj);
			} else if (ls.length==2){
				obj = new Person(ls[0],Integer.parseInt(ls[1]),0);

			}
			else {
				obj = new Person(ls[0],0,0);
			}



		}
		Collections.sort(persons,cmp);
		try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
			for (Person line : persons) {
				bw.write(line.toString());
				bw.write("\r\n");
			}
		}




		return file;
	}

	public static void mergeSortedFiles(final List<File> files, final String outputFile, final StringComparator cmp)
			throws IOException {
		List<BufferedReader> brReaders = new ArrayList<>();
		TreeMap<String, BufferedReader> map = new TreeMap();
		List<Person> lst = new ArrayList();
		List<String> ls= new ArrayList();
		File f = new File(outputFile);
		if (f.exists()) {
			f.delete();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));
		try {
			
			for (File file : files) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				brReaders.add(br);
				String line = br.readLine();
				
				ls.add(line);
				 String s = "";
			       while (br.ready()) {
			          s += br.readLine() + "\n";
			       }
			       System.out.println(s);
				//if(null!=line)
				map.put(s, br);
			}
			System.out.println(ls.size());
			while (!map.isEmpty()) {
				Map.Entry<String, BufferedReader> nextToGo = map.pollFirstEntry();
				//System.out.println(nextToGo.getKey())	;
				bw.write(nextToGo.getKey());
				bw.write("\r\n");
//				String line = nextToGo.getValue().readLine();
//				if (line != null) {
//					map.put(line, nextToGo.getValue());
//				}
			}
		} finally {
			if (brReaders != null) {
				for (BufferedReader br : brReaders) {
					br.close();
				}
				File dir = files.get(0).getParentFile();
				for (File file : files) {
					file.delete();
				}
				if (dir.exists()) {
					dir.delete();
				}
			}
			if (bw != null) {
				bw.close();
			}
		}
	}

}
