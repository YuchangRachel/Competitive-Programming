
import java.io.*;
import java.util.*;

public class MainImage {
  public static void main(String[] args) throws Exception {
    boolean start = true;

    while (start) {
      // main menu
      System.out.print(
          "\nWhat would you like to do ? \n a.) Hide a message \n b.) Recover a message \n c.) Exit \n Enter your selection : ");
      Scanner sc = new Scanner(System.in);
      String selection = sc.nextLine(); // read user input

      switch (selection.toLowerCase()) {
      case "a":
        // read, hide, and write to file
        System.out.print("\nPlease specify the source PPM filename: ");
        String inputfile = sc.nextLine();
        System.out.print("Please specify the output PPM filename: ");
        String outfile = sc.nextLine();
        System.out.print("\nPlease enter a phrase to hide: ");
        String hideMsg = sc.nextLine();

        // handle inputfile.extension exception
        File inputFile = new File(inputfile);
        try {
          PPMImage ppm = new PPMImage(inputFile);
          ppm.checkFileExist(inputFile);
          ppm.hideData(hideMsg);
          File outputFile = new File(outfile);
          try {
            // cannot remove need throw exception in constructor
            PPMImage ppmOutput = new PPMImage(outputFile);
            ppm.writeImage(outputFile);
            System.out.println("\nYour message " + "\"" + hideMsg + "\"" + " has been hidden in file: " + outfile);
          } catch (InvalidFileExtension ee) {
            System.out.println("Output file->" + ee.getMessage());
          }
        } catch (InvalidFileExtension ee) {
          System.out.println("Input file->" + ee.getMessage());
        } catch (FileNotFoundException exception) {
          System.out.println("The file " + inputFile.getPath() + " was not found.");
        }

        break;

      case "b":
        // reads file and displays message
        System.out.print("\nPlease specify the source PPM filename: ");
        String recover = sc.nextLine();
        File recoverFile = new File(recover);
        try {
          PPMImage ppm2 = new PPMImage(recoverFile);
          ppm2.checkFileExist(recoverFile);
          hideMsg = ppm2.recoverData();
          System.out
              .println("\nThe following message has been recovered from file " + recoverFile + ": \"" + hideMsg + "\"");
        } catch (InvalidFileExtension ee) {
          System.out.println("Hide file->" + ee.getMessage());
        } catch (FileNotFoundException fe) {
          System.out.println("The file " + recoverFile.getPath() + " was not found.");
        }
        break;

      case "c":
        // exit program
        start = false;
        sc.close();
        System.exit(0);
        break;

      default:
        // default message
        System.out.println("Invalid input. Try again!");
        break;
      }
    }
  }
}

class PPMImage {
  String magicNumber;
  int width;
  int height;
  int maxColorValue;
  char[][][] raster;
  File image;

  // constructor
  public PPMImage(File image) throws Exception {
    // if file not exist throw exception
    if (!image.exists()) {
      throw new FileNotFoundException();
    }
    // if not ppm image throw exception
    String name = image.getName();
    if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
      name = name.substring(name.lastIndexOf(".") + 1);
    }
    // checks if it equals ppm
    if (name.equals("ppm")) {
      this.image = image;
    } else {
      throw new InvalidFileExtension(name);// else error message
    }
    // reads file
    readImage();
  }

  // test file exist or not
  public void checkFileExist(File image) throws Exception {
    // if file not exist throw exception
    if (!image.exists()) {
      throw new FileNotFoundException();
    }
  }

  // method
  public void writeImage(File filename) {
    try {
      // if not ppm image throw exception
      String name = filename.getName();
      if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
        name = name.substring(name.lastIndexOf(".") + 1);
      }
      // checks if it equals ppm
      if (name.equals("ppm")) {

        DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
        // writes first 3 lines
        out.writeBytes(magicNumber + "\n");
        out.writeBytes(width + " " + height + "\n");
        out.writeBytes(maxColorValue + "\n");

        // writes pixels
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            out.write(raster[i][j][0]);// R
            out.write(raster[i][j][1]);// G
            out.write(raster[i][j][2]);// B
          }
        }
        out.close();// close data output stream
      } else {
        throw new InvalidFileExtension(name);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readImage() throws Exception {
    // when reading the first header is P6
    // PPM are in bytes, every 3 bytes (R, G, B)
    // read Bytes

    try {
      DataInputStream in = new DataInputStream(new FileInputStream(image));
      // 1st line P6
      magicNumber = (char) in.read() + "" + (char) in.read();
      in.read();// ignore \n

      // 2nd line gets width and height
      char curr = (char) in.read();
      boolean space = false;
      String w = "";// for width
      String h = "";// for height

      // splits the string to width and height
      while (curr != '\n') {
        if (curr != ' ') {
          if (!space) {
            w += (char) curr + "";// width
          } else {
            h += (char) curr + "";// height
          }
        } else {
          space = true;
        }
        curr = (char) in.read();
      }
      width = Integer.parseInt(w);// result for width
      height = Integer.parseInt(h);// result for height

      curr = (char) in.read();
      // gets the 3rd line max color value
      String m = "";
      while (curr != '\n') {
        m += curr + "";
        curr = (char) in.read();
      }
      maxColorValue = Integer.parseInt(m);// result for max

      raster = new char[height][width][3];// reads pixels RGB
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          for (int k = 0; k < 3; k++) {
            raster[i][j][k] = (char) in.read();
          }
        }
      }
      in.close();// close data input stream
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void hideData(String message) {
    // adds 0000 0000 at the end to know when to stop
    message += '\0';
    // sends in message and returns a string of the bits
    String bin = getBits(message);
    int index = 0;
    char mask = (char) (1 << 0);// 0000 0001 (1 << (1-1)) start with 1 not need move
    char unmask = (char) ~(1 << 0);/// 1111 1110
    char ras;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          ras = raster[i][j][k];// current pixel
          if (index < bin.length()) {
            // if the LSB is off
            if ((bin.charAt(index) & mask) == 0) { // Use mask check bit status of message, no need care about original
                                                   // pixel
              // turns off bit
              raster[i][j][k] = (char) ((char) ras & unmask);
            }
            // if its on
            else if ((bin.charAt(index) & mask) != 0) { // Use mask check bit status
              // turns on bit
              raster[i][j][k] = (char) ((char) ras | mask);
            }
          } else {
            break;
          }
          index++;
        }
      }
    } // 00000000 //target least significant bit
  }

  public String recoverData() {
    String result = "";// the result string

    // current bits as string, later converted to a char
    String temp = "";
    char mask = (char) (1 << 0);// 0000 0001

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          // adds the 0 or 1 to temp
          temp += (raster[i][j][k] & mask);
          // reads 8 bits at a time
          if (temp.length() == 8) {
            // if its equal to \0 then return current result
            if (Integer.parseInt(temp, 2) == 0) {
              return result;
            }
            // converts temp to a int then to char
            result += (char) Integer.parseInt(temp, 2);
            temp = "";// resets temp for the next 8
          }
        }
      }
    }
    return result;
  }

  private String getBits(String message) {
    // result string of 0's and 1's
    String result = "";
    char mask;
    // loops through every char in string
    for (int i = 0; i < message.length(); i++) {
      // loops through every bit in that char
      for (int j = 8; j > 0; j--) {
        mask = (char) (1 << (j - 1));// gets the bit
        // if the bit is 0
        if (((char) message.charAt(i) & mask) == 0) {
          result += 0; // adds 0 to result
        }
        // if the bit is 1
        else {
          result += 1;// adds 1 to result
        }
      }
    }
    return result;
    // checking satus of a bit //off //on
  }
}

// Custom exception for file extension: ppm
class InvalidFileExtension extends Exception {
  private static final long serialVersionUID = -8460356990632230194L;
  private String extension;

  // error message to display
  public InvalidFileExtension(String extension) {
    super("extension of file incorrect ! " + extension);
    this.extension = extension;
  }

  // send back message
  public String getMessage() {
    return "File extension must be .ppm!";
  }
}
