package com.techiethoughts;

import jcifs.CIFSContext;
import jcifs.Credentials;
import jcifs.context.SingletonContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** @author Rajesh Bandarupalli */
public class SmbUtil {

  public static final String SMB_PROTOCOL = "smb://";
  public static final String DOMAIN = "techiethoughts.com";
  public static final String USER_NAME = "TechieThoughts";
  public static final String PASSWORD = "Techie123";
  public static final String FILE_LOCATION = "techiethoughts.com/FILES/";
  public static final String FILE_NAME = "notes.txt";
  public static final String FILE_PATH = FILE_LOCATION + FILE_NAME;

  public static void readFile() {

    //  Domain name is optional. Pass null if domain name doesn't exist.
    Credentials credentials = new NtlmPasswordAuthenticator(DOMAIN, USER_NAME, PASSWORD);
    CIFSContext context = SingletonContext.getInstance().withCredentials(credentials);
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                new SmbFileInputStream(new SmbFile(SMB_PROTOCOL + FILE_PATH, context))))) {

      String line = reader.readLine();
      while (line != null) {
        System.out.println(line);
        line = reader.readLine();
      }
    } catch (Exception e) {
      //      log error
      e.printStackTrace();
    }
  }
}
