package com.itechtopus.sshgenerator.test;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;

public class SSHReader {

  private String user;
  private String host;
  private String folder;

  private String RSA_keyPath = "/home/yerlan/.ssh/id_rsa";
  private int port = 22;

  public SSHReader(String user, String host, String folder) {
    this.user = user;
    this.host = host;
    this.folder = folder;
  }

  public void readToConsole() {
    JSch jsch = new JSch();
    Session session = null;
    FileReader reader = null;
    BufferedReader buffer = null;
    InputStream inputStream = null;
    try {
      jsch.addIdentity(RSA_keyPath);
      session = jsch.getSession(user, host);

      /*---- Unsafe part of connection. Allows Man-in-the-middle manupulations ----*/
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      /*---- end of the unsafe operation ----*/

      session.connect();
      Channel channel = session.openChannel("sftp");
      channel.connect();
      ChannelSftp sftpChannel = (ChannelSftp) channel;
      System.out.println("Is connected to IP:" + channel.isConnected());
      Vector ls = sftpChannel.ls(folder);
      String fileName = "";
      for (int i = 0; i < ls.size(); i++) {
        String fl = ls.get(i).toString();
        fl = fl.substring(fl.lastIndexOf(" ") + 1);
        System.out.println("Ls:" + fl);
        fileName = !fl.endsWith(".xml") ? fl : fileName;
      }
      sftpChannel.cd(folder);
      File file = new File(folder + fileName);
      inputStream = sftpChannel.get(fileName);
      buffer = new BufferedReader(new InputStreamReader(inputStream));
      String getLine = "";
      while ((getLine = buffer.readLine()) != null) {
        System.out.println("Line: " + Arrays.stream(getLine.split("}"))
            .map(line -> Arrays.stream(line.replace("{", "{\n").split("\"\"")).collect(Collectors.joining("\"\n\"")))
            .map(line -> line.replaceAll("\n\"", "\n\t\""))
            .collect(Collectors.joining("\n}\n")));
      }
      sftpChannel.exit();
      session.disconnect();
    } catch (JSchException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null)
          reader.close();
        if (buffer != null)
          buffer.close();
        if (inputStream != null)
          inputStream.close();
        if (session != null)
          session.disconnect();
      } catch (Exception e) {
        System.out.println("Exception:" + e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    SSHReader reader = new SSHReader("root", "5.189.186.79", "/transfer/");
    reader.readToConsole();
  }
}
