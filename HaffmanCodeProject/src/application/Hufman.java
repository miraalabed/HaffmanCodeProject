package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class Hufman {// huffman class that will represents the operation that will done in the
						// encoding compressing or decoding decompressing
	public static String fname;// static that represent s the file name
	public static HuffmanTable[] array;// static public variable that represent the huffman table array that represents
										// the content of it in array
	public static int harrSize = 0;// static public variable that represents the huffman array size and give it
									// initialize value that is zero
	public static int compSize;
	public static int realSize;

	public static String getHeaderInfo(File file) throws Exception {
		StringBuilder headerInfo = new StringBuilder();
		headerInfo.append("File header size: ").append(getOriginalFileSize(file)).append(" bytes\n");
	

		return headerInfo.toString();
	}

	private static int getOriginalFileSize(File file) {
		@SuppressWarnings("unused")
		File originalFile = new File(fname.replace(".huff", ""));
		return (int) fname.length();
	}
////////////////////header/////////////////////

	// Method to generate header information for the compressed file
	public static String header(String path, String path2, int realFSize, int charFreq) throws IOException {
		StringBuilder headerInfo = new StringBuilder();

		// Extract the file name from the original file path
		String fileName = new File(path).getName();
		headerInfo.append("File name and extension:\t" + fileName + "\n").append("\n");

		// Extract the file name from the compressed file path
		String NewfileName = new File(path2).getName();
		headerInfo.append("Compressed File name and extension:\t" + NewfileName + "\n").append("\n");

		// Append the number of frequent characters to the header
		headerInfo.append("Number of frequented characters:\t" + charFreq + "\n").append("\n");

		// Calculate and append the header size
				int headerSize = headerInfo.length();
				
				headerInfo.append("The header size :\t " + headerSize + "\n").append("\n");
		// Start appending the Huffman code details
		headerInfo.append("Huffman code" + "\n");
		for (int i = 0; i < harrSize; i++) {
			// Append the Huffman code and calculate total frequency and code length
			headerInfo.append(array[i].getHuffCode());
		
		}

		
		
		return headerInfo.toString(); // Return the complete header information
	}

	// Method to generate statistics on the original and compressed file sizes
	public static String statistic(File originalFile, File compressedFile) {
		StringBuilder statInfo = new StringBuilder();

		long originalSize = originalFile.length(); // Get the size of the original file
		long compressedSize = compressedFile.length(); // Get the size of the compressed file

		// Calculate the compression ratio
		double compressionRatio = ((double) (originalSize - compressedSize) / originalSize) * 100;

		// Append file sizes and compression ratio to the statistics string
		statInfo.append("Original File Size:\t " + originalSize + " bytes");
		statInfo.append("\n\n");
		statInfo.append("Compressed File Size:\t " + compressedSize + " bytes");
		statInfo.append("\n\n");
		statInfo.append("Compression Ratio:\t " + String.format("%.2f", compressionRatio) + "%");

		return statInfo.toString(); // Return the file statistics
	}

	// Method to generate information about the decompressed file
	public static String DeComp(File path, File path1) throws IOException {
		StringBuilder DeCompInfo = new StringBuilder();

		// Extract the file name from the original compressed file
		String fileName = path.getName();
		DeCompInfo.append("File name and extension: \t" + fileName);
		DeCompInfo.append("\n\n");

		// Extract the file name from the decompressed file
		String decompfileName = path1.getName();
		DeCompInfo.append("Decompress File name and extension:\t" + decompfileName);
		DeCompInfo.append("\n\n");

		// Get and append the sizes of the original and decompressed files
		long originalSize = path.length();
		long compressedSize = path1.length();

		DeCompInfo.append("Original File Size:\t " + originalSize + " bytes");
		DeCompInfo.append("\n\n");
		DeCompInfo.append("Decompressed File Size:\t " + compressedSize + " bytes");

		return DeCompInfo.toString(); // Return the decompressed file information
	}

	public static File encoding(File file) throws Exception {
//////////////////////////////////////////////*******************read the file and save the frequencies************//////////////////////////////////////////////.

		String path = file.getPath();// local variable that is string and save the file path we enter on it
		FileInputStream scanner = new FileInputStream(path);// Opens the file for reading it used for reading binary
// data from a file.
		realSize = scanner.available();// real size to store size of file the real and original one to do compress
// or encoding to it
		byte[] buffer = new byte[512]; // buffer that will help to read the file data
		int[] frequency = new int[256];// creates an array to store the frequency of each byte value thats its size is
// 256
		int size = scanner.read(buffer, 0, 512);// reads a block of data from the file into the buffer and updates the
// size variable with the number of bytes read.
		int index = -1;// to ensures that the loop go over all elements in the buffer so we put
//initially -1
		do {
			for (int i = 0; i < size; i++) {// keep going through each byte in the buffer
				index = buffer[i];// make when i read buffer the value make it refer to index then increment the
//value on it in the frequency array
				if (index < 0) {// if its less than 0 the content of the index
					index += 256;// we added the 256 toindex to avoid the negative values
				}
				if (frequency[index] == 0) {// if the frecuency at the spicific index ==0 the n ill increase the huffman
// array size
					harrSize++; // Counting number of different characters in the file
				}
				frequency[index]++;// increments the element in the frequency array to count the frequency of each
//byte value and continue untill there is nth to read size
			}
			size = scanner.read(buffer, 0, 512);// keep reading
		} while (size > 0); // while size is greater than zero meaning there is data to be read then it will
//be continue doing this

//////////////////////////////////////************reset the buffer to accept other data******************//////////////////////////////////////////////////////////////////////////

		for (int i = 0; i < buffer.length; i++) {// make every element in the buffer equals to zero clears the buffer
// preparing it for storing other data in the coming steps
			buffer[i] = 0;
		}

/////////////////////////////////////**************Building the HuffmanTable array**********************///////////////////////////////////////////////////////////////////////////

		int numberCH = 0;// initializes a counter variable named nbChar to 0
		int stalker = 0;// initializes a counter variable named stalker to 0 thet will chase backward it
		array = new HuffmanTable[harrSize];// creates a new array named array of type HuffmanTable with a size equal to
// the number of distinct characters (harrSize) encountered in the file.
//shrinking the original data representation .
		for (int i = 0; i < 256; i++)// goes through all possible byte values 256 characters.
			if (frequency[i] != 0) {// checks if the frequency of the current byte value i in the frequency array is
//greater than 0.
				array[stalker++] = new HuffmanTable((char) i, frequency[i]);// assigns a new HuffmanTable object to the
// array at the index pointed to by stalker.
// This object is constructed with the
// current character i and its frequency
// frequency[i]
				numberCH += frequency[i]; // adds the frequency of the current character to the number of character
// counter.
				frequency[i] = 0;// here i reset the the frequency of spicific index to encure that i use it once
//and save the vulue in the array with stakler index.
			} // if true it meaning it appears in the file

////////////////////////////////////////////////*************build the minheap and build the huffman tree **************\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\.

		if (harrSize == 1) {// if there is only one character on the file.
			array[0].setHuffCode("1");// assigns the code 1 to the single character stored in the first element of the
// array.
			array[0].setCodeLength(1);// sets the code length for the single character to 1

		} else {
			Node[] node = new Node[harrSize];// the size of hufman array not equal to 1 then we create a new node with
// the huffman array size to represent the character and its frequency.
			MinHeap heap = new MinHeap(harrSize + 22);// create anew heap with capacity equals to huffman array size +22
// to make more size
			for (int i = 0; i < harrSize; i++) {// from 0 to size of huffman array size
				node[i] = new Node(array[i].getFrequency(), array[i].getCharacter());// create a new node and put for
// each the character and its
// freaquency
				heap.insert(node[i]);// insert the node to the heap
			} // Add the character and its frequency in tree node m,, then
//add the tree node to the heap
			for (int i = 1; i <= node.length - 1; i++) {// the length of node array that i created
				Node z = new Node();// create a new node
				Node x = heap.remove();// remove the min
				Node y = heap.remove();// remove the second min
				z.frequency = x.frequency + y.frequency;// let z = frequences of the x and y
				z.setLeft(x);// set x to left child
				z.setRight(y);// set y to right child
				heap.insert(z);// insert the z value to the heap
			} // delete from heap and add a new tree node
			getCodes(heap.get()[1], "");// traverses the Huffman tree starting from the first element in the heap array
// the root node and recursively assigns binary codes to each
// character based on their path in the tree, ultimately returning the complete
// set of Huffman codes
		}
////////////////////////////////////////////////////////*****************create Huffman Code Array*************************///////////////////////////////////////.
		String[] out = new String[256];// array to string is created with a size of 256 its enough to store codes for
// all possible byte values
		for (int i = 0; i < harrSize; i++) {
			out[(int) array[i].getCharacter()] = new String(array[i].getHuffCode());// like building map between the
// character and the huffmancode
		}
//////////////////////////////////////////////////////**********************Generate File Name and Open Output Stream****************///////////////////////////////////
		fname = new StringTokenizer(file.getAbsolutePath(), ".").nextToken() + ".huff";// original file name is parsed
// to form the new file name
// with ".huff" appended
		// Determine the name of the compressed file (add ".huff" extension)
	    fname = new StringTokenizer(file.getAbsolutePath(), ".").nextToken() + ".huff";
	    File f = new File(fname);
	    FileOutputStream output = new FileOutputStream(f); // Create output stream to write the compressed file
///////////////////////////////////////////*********************Prepare Header Information*****************////////////////////////////////////////////////////.
		byte[] outbuffer = new byte[512];// buffer array of bytes is created to store the header information
		stalker = 0;// counter variable keeps track of the current position in the buffer

//The Name of The Original File

		for (int i = 0; i < path.length(); i++) {// characters of the original file name are written to the buffer one
// by one
			outbuffer[stalker++] = (byte) path.charAt(i);
		}
		outbuffer[stalker++] = '\n';// each value is separated by a newline character \n for clarity

//Number of Characters in the Original File
		String nbchar = String.valueOf(numberCH);
		for (int i = 0; i < nbchar.length(); i++) {// writes it as a byte value to the outbuffer at the current stalker
// position
			outbuffer[stalker++] = (byte) nbchar.charAt(i);
		}
		outbuffer[stalker++] = '\n';// adds a newline \n character to separate the values

//Number of Distinct Characters
		for (int i = 0; i < String.valueOf(harrSize).length(); i++) {
			outbuffer[stalker++] = (byte) String.valueOf(harrSize).charAt(i);// writes it as a byte value to the
// outbuffer after stalker
		}
		outbuffer[stalker++] = '\n';//// adds a newline \n character to separate the values

		output.write(outbuffer, 0, stalker);// write it to new file
		stalker = 0;// reset it
		for (int i = 0; i < outbuffer.length; i++) {// walk through the out array containing Huffman codes
			outbuffer[i] = 0;
		}
////////////////////////////////////////***********************huffman code for each character*****************************//////////////////////////////////////////////////////.
//The HuffCode for Each Character
		for (int i = 0; i < harrSize; i++) {
			if (stalker == 512) {// checks if the stalker reaches the buffer size 512
				output.write(outbuffer);// writes the contents of the outbuffer to the compressed file
				stalker = 0;// Resets stalker to 0
			}
			outbuffer[stalker++] = (byte) array[i].getCharacter();
			if (stalker == 512) {
				output.write(outbuffer);
				stalker = 0;
			}
//Add the Counter
			outbuffer[stalker++] = (byte) array[i].getCodeLength();// writes the character as a byte value to the
// outbuffer.
			String res = "";
			Long x;
			if (array[i].getHuffCode().length() <= 15) {// hecks again if stalker reaches the buffer size and handles it
// similarly
				x = Long.parseLong(array[i].getHuffCode());

			} else {
				for (int z = 0; z < array[i].getHuffCode().length() / 2; z++) {
					res += array[i].getHuffCode().charAt(z) + "";//
				}
				x = Long.parseLong(res);
				res = "";
				for (int z = (array[i].getHuffCode().length() + 1) / 2; z < array[i].getHuffCode().length(); z++) {
					res += array[i].getHuffCode().charAt(z) + "";
				}
				x += Long.parseLong(res);
			}
			byte[] code = new byte[50];
			int l = 0;
			if (x == 0) {
				outbuffer[stalker++] = 0;
				if (stalker == 512) {
					output.write(outbuffer);
					stalker = 0;
				}

				outbuffer[stalker++] = 0;
				if (stalker == 512) {
					output.write(outbuffer);
					stalker = 0;
				}
			} else {
				while (x != 0) {
					if (stalker == 512) {
						output.write(outbuffer);
						stalker = 0;
					}
					code[l++] = (byte) (x % 256);
					x /= 256;
				}
				outbuffer[stalker++] = (byte) l;
				if (stalker == 512) {
					output.write(outbuffer);
					stalker = 0;
				}
				for (int j = 0; j < l; j++) {
					outbuffer[stalker++] = code[j];
					if (stalker == 512) {
						output.write(outbuffer);
						stalker = 0;
					}
				}
			}

			if (stalker == 512) {
				output.write(outbuffer);
				stalker = 0;
			}
			outbuffer[stalker++] = '\n';
		} // end for

//Print Out The Header
		output.write(outbuffer, 0, stalker);

//Reinitialize the Output Buffer
		for (int i = 0; i < outbuffer.length; i++)
			outbuffer[i] = 0;

		scanner.close();
		scanner = new FileInputStream(path);
		stalker = 0;
		size = scanner.read(buffer, 0, 512);
		do {
			for (int i = 0; i < size; i++) {
				index = buffer[i];
				if (index < 0)// If the Value was negative
					index += 256;
				for (int j = 0; j < out[index].length(); j++) {
					char ch = out[index].charAt(j);
					if (ch == '1')
						outbuffer[stalker / 8] = (byte) (outbuffer[stalker / 8] | 1 << 7 - stalker % 8);
					stalker++;
					if (stalker / 8 == 512) {
						output.write(outbuffer);
						for (int k = 0; k < outbuffer.length; k++)
							outbuffer[k] = 0;
						stalker = 0;
					}
				}
			}
			size = scanner.read(buffer, 0, 512);
		} while (size > 0);
		// Determine the name of the compressed file (add ".huff" extension)
	   
		scanner.close();
		output.write(outbuffer, 0, (stalker / 8) + 1);
		output.close();
		scanner = new FileInputStream(f);

		compSize = scanner.available();
		scanner.close();
		return f;

	}

	// This method generates Huffman codes for each character by traversing the
	// Huffman tree.
	public static void getCodes(Node t, String st) {
		// Base case: If we reach a leaf node, we assign the Huffman code for the
		// character at this node.
		if (t.getLeft() == t.getRight() && t.getRight() == null)
			for (int i = 0; i < harrSize; i++) {
				// If the character in the node matches the character in the array
				if (array[i].getCharacter() == t.getCh()) {
					// Assign the Huffman code
					array[i].setHuffCode(st);
					// Assign the length of the code
					array[i].setCodeLength(st.length());
				}
			}
		else {
			// Traverse left node, adding '0' to the Huffman code.
			getCodes(t.getLeft(), st + '0');
			// Traverse right node, adding '1' to the Huffman code.
			getCodes(t.getRight(), st + '1');
		}
	}

	// This method decompresses a file using the Huffman code.
	public static File deCompress(File infile) {
		try {
			int size = 0;
			String fileName = infile.getPath();
			int tracker = 0;
			int bufferTracker = 0;
			boolean flag = true;
			String originalFileName = "";
			File file = new File(fileName);
			// Open the file for reading
			@SuppressWarnings("resource")
			FileInputStream scan = new FileInputStream(file);
			byte[] buffer = new byte[512];

			// Extract the original file name from the first 512 bytes.
			size = scan.read(buffer, 0, 512);
			char[] tmp = new char[200];
			while (flag) {
				if (buffer[tracker] != '\n') // If the character is not a newline
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false; // If we reach the newline
			}
			bufferTracker++;
			originalFileName = String.valueOf(tmp, 0, tracker); // Convert characters to the original file name

			// Extract the number of characters in the original file
			long nbChar = 0;
			tracker = 0;
			flag = true;
			while (flag) {
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				if (buffer[bufferTracker] != '\n') // If the character is not a newline
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false; // If we reach the newline
			}
			nbChar = Integer.parseInt(String.valueOf(tmp, 0, tracker)); // Convert the number to a long value
			bufferTracker++;

			// Extract the number of distinct characters in the file
			int loopSize = 0;
			tracker = 0;
			flag = true;
			while (flag) {
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				if (buffer[bufferTracker] != '\n') // If the character is not a newline
					tmp[tracker++] = (char) buffer[bufferTracker++];
				else
					flag = false; // If we reach the newline
			}
			loopSize = Integer.parseInt(String.valueOf(tmp, 0, tracker)); // Get the number of distinct characters
			bufferTracker++;

			// Read Huffman codes for each character
			array = new HuffmanTable[loopSize];
			harrSize = loopSize;
			for (int i = 0; i < loopSize; i++) {
				array[i] = new HuffmanTable((char) Byte.toUnsignedInt(buffer[bufferTracker++]));
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				array[i].setCodeLength(buffer[bufferTracker++]);
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				int l = buffer[bufferTracker++];
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				if (l == 0)
					bufferTracker++;
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
				long x = 0;
				// Read the value that represents the Huffman code
				for (int j = 0; j < l; j++) {
					x += Byte.toUnsignedLong(buffer[bufferTracker++]) * (1 << 8 * j);
					if (bufferTracker == 512) {
						size = scan.read(buffer, 0, 512);
						bufferTracker = 0;
					}
				}
				array[i].setHuffCode(String.valueOf(x));
				// Ensure the Huffman code has the correct length
				if (array[i].getHuffCode().length() != array[i].getCodeLength()) {
					String s = "";
					for (int j = 0; j < array[i].getCodeLength() - array[i].getHuffCode().length(); j++)
						s += "0"; // Add zero-padding if necessary
					s += array[i].getHuffCode();
					array[i].setHuffCode(s);
				}
				bufferTracker++;
				if (bufferTracker == 512) {
					size = scan.read(buffer, 0, 512);
					bufferTracker = 0;
				}
			}

			// Build the Huffman tree from the read codes
			TreeNode tree = new TreeNode();
			for (int i = 0; i < harrSize; i++) {
				tree = TreeNode.insert(tree, array[i].getHuffCode(), 0, array[i].getCharacter());
			}

			// Prepare the output file buffer
			byte[] outputBuffer = new byte[512];
			int tracker2 = 0;

			// Generate the output file name ensuring no overwrite
			File outputFile = new File(originalFileName);
			int counter = 1;
			while (outputFile.exists()) {
				String newFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + "_" + counter
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				outputFile = new File(newFileName); // Ensure the file name is unique
				counter++;
			}

			// Create and write to the output file
			
			FileOutputStream output = new FileOutputStream(outputFile);
			TreeNode root = tree;
			long count = 0;
			flag = false;

			int index = bufferTracker;
			bufferTracker = 0;
			while (size != -1) {
				// Traverse the tree to decode the Huffman codes
				while (tree.getLeft() != null || tree.getRight() != null) {
					if ((buffer[index] & (1 << 7 - bufferTracker)) == 0)
						tree = tree.getRight();
					else
						tree = tree.getLeft();
					bufferTracker++;
					if (bufferTracker == 8) { // When 8 bits are read
						bufferTracker = 0;
						index++;
						if (index == 512) {
							size = scan.read(buffer, 0, 512);
							index = 0;
							if (size == -1)
								flag = true; // If the file is finished
						}
					}
				}

				if (flag)
					break;

				outputBuffer[tracker2++] = (byte) tree.getCh(); // Add the decoded character to the buffer
				if (tracker2 == 512) {
					output.write(outputBuffer); // Write to the output file
					tracker2 = 0;
				}

				count++;
				tree = root;
				if (count == nbChar) // If all characters are decoded
					break;
			}

			output.write(outputBuffer, 0, tracker2); // Write any remaining data
			output.close();

			// Return the decompressed output file
			return outputFile;
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Print the error message in case of an exception
		}
		return null; // Return null in case of an error
	}

}