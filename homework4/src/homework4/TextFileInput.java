package homework4;
// TextFileInput.java
// This Class is obtained from an open-source Github project

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Simplified buffered character input
 * stream from an input text file.
 * Manages an input text file,
 * handling all IOExceptions by generating
 * RuntimeExcpetions (run-time error
 * messages).
 *
 * If the text file cannot be created,
 * a RuntimeException is thrown,
 * which by default results an an
 * error message being printed to
 * the standard error stream.
 *
 * 
 */
public class TextFileInput  {

   /**  Name of text file  */
   private String filename;

   /**  Buffered character stream from file  */
   private BufferedReader br;  

   /**  Count of lines read so far.  */
   private int lineCount = 0;

   public TextFileInput(String filename)
   {
      this.filename = filename;
      try  {
         br = new BufferedReader(
                  new InputStreamReader(
                      new FileInputStream(filename)));
      } catch ( IOException ioe )  {
         throw new RuntimeException(ioe);
      }  // catch
   }  // constructor


   public void close()
   {
      try  {
         br.close();
         br = null;
      } catch ( NullPointerException npe )  {
         throw new NullPointerException(
                        filename + "already closed.");
      } catch ( IOException ioe )  {
         throw new RuntimeException(ioe);
      }  // catch
   }  // method close

   public String readLine()
   {
      return readLineOriginal();
   }  // method readLine()

   /**
    * Returns a count of lines
    * read from the file so far.
    */
   public int getLineCount()  { return lineCount; }

  
   public static boolean isOneOf(char toBeChecked,
                                 char[] options)
   {
      boolean oneOf = false;
      for ( int i = 0; i < options.length && !oneOf; i++ )
         if ( Character.toUpperCase(toBeChecked)
                   == Character.toUpperCase(options[i]) )
            oneOf = true;
      return oneOf;
   }  // method isOneOf(char, char[])

   public static boolean isOneOf(String toBeChecked,
                                 String[] options)
   {
      boolean oneOf = false;
      for ( int i = 0; i < options.length && !oneOf; i++ )
         if ( toBeChecked.equalsIgnoreCase(options[i]) )
            oneOf = true;
      return oneOf;
   }  // method isOneOf(String, String[])
 
   public String readSelection(String[] options)
   {
      if ( options == null || options.length == 0 )
         throw new NullPointerException(
                            "No options provided for "
                            + " selection to be read in file "
                            + filename + ", line " 
                            + (lineCount + 1) + ".");

      String answer = readLine();

      if ( answer == null )
         throw new NullPointerException(
                            "End of file "
                            + filename + "has been reached.");

      if ( !TextFileInput.isOneOf(answer, options) )  {
         String optionString = options[0];
         for ( int i = 1; i < options.length; i++ )
            optionString += ", " + options[i];
         throw new RuntimeException("File " + filename
                            + ", line " + lineCount
                            + ": \"" + answer
                            + "\" not one of "
                            + optionString + ".");
      }  // if
      return answer;
  }  // method readSelection

   public boolean readBooleanSelection()
   {
      String[] options = {"Y", "N", "yes", "no", "1", "0",
                          "T", "F", "true", "false"};
      String answer = readSelection(options);
      return isOneOf(answer,
                     new String[] {"Y", "yes", "1", "T", "true"} );
   }  // method askUserYesNo

   protected final String readLineOriginal()
   {
       try  {
          if ( br == null )
             throw new RuntimeException(
                                "Cannot read from closed file "
                                + filename + ".");
          String line = br.readLine();
          if ( line != null )
             lineCount++;
          return line;
       } catch (IOException ioe)  {
          throw new RuntimeException(ioe);
       }  // catch
   }  // method readLineOriginal
}  // class TextFileInput