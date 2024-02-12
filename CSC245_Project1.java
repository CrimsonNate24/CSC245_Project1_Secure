// CSC 2045
// Reid Mercer, Nathan Lopez, and Dean Lewis
package src;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.io.File;

public class CSC245_Project1 {

    public static void main(String[] args) {
        // Handle situation where arguments aren't provided
        if (args.length == 0) {
            System.out.println("Please provide the program with runtime arguments.");
            return;
        }

        // Read the filename from the command line argument
        String dirtyFilename = args[0];
        String filename = sanitizeArgs(dirtyFilename);
        BufferedReader inputStream = null;

        String fileLine;
        try {
            //Canonicalize path names before validation process
            File file = new File(filename);
            filename = file.getCanonicalPath();
            //This switch statement keeps the user from accessing files they are not supposed to reach.
            switch (String.valueOf(args[0])){
                //If the argument equals the Email Addresses, they can continue through the code like normal.
                case "EmailAddresses20210205.txt":

                    inputStream = new BufferedReader(new FileReader(filename));

                    System.out.println("Email Addresses:");
                    // Read one Line using BufferedReader
                    while ((fileLine = inputStream.readLine()) != null) {
                        //normalizes the line before sanitizing it
                        String normalizedLine = normalize(fileLine);
                        // Sanitize the line
                        String sanitizedLine = sanitize(normalizedLine);
                        System.out.println(sanitizedLine);
                    }
                    break;
                //If the user does not enter the case in correctly, they get a default option that says the option is not available to them.
                //This will keep the user from being able to access files that are not available to them.
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        } catch (IOException io) {
            //Invalid filenames will give this information back to the user and then wraps.
            System.out.println("Invalid File: " + filename);
            return;
        } finally {
            // Need another catch for closing
            // the streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException io) {
                System.out.println("Cannot Complete Action");
            }

        }
    }

    // Method to normalize the input string
    private static String normalize(String input) {
        // Normalize the string using normalization form
        return Normalizer.normalize(input, Normalizer.Form.NFKC);
    }

    // Method to sanitize the input string
    private static String sanitize(String input) {
        // Sanitize search string
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);
            // Allow alphanumeric characters, whitespace, '@', and '.'
            if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch) || ch == '@' || ch == '.') {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    private static String sanitizeArgs(String input) {
        // Sanitize arguments
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);
            // Allow alphanumeric characters, whitespace, '_', and '.'
            if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch) || ch == '_' || ch == '.') {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

}

