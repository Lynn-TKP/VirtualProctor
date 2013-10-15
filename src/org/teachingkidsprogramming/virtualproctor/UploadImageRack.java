package org.teachingkidsprogramming.virtualproctor;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.jrack.JRack;
import org.jrack.RackEnvironment;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import org.teachingkidsprogramming.virtualproctor.tests.BlobFile;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.spun.util.io.FileUtils;

public class UploadImageRack implements JRack
{
  public static Hashtable<String, BlobFile> uploadedFiles = new Hashtable<String, BlobFile>();
  @Override
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    HttpServletRequest request = (HttpServletRequest) environment.get("HttpServletRequest");
    if (request.getContentLength() > 1)
    {
      ServletInputStream image = request.getInputStream();
      String query = (String) environment.get(RackEnvironment.QUERY_STRING);
      String fileName = query.split("=")[1];
      writeToFile(fileName, image);
    }
    return RackResponseUtils.standardHtml("uploaded");
  }
  public static void writeToFile(String fileName, InputStream in) throws Exception
  {
    FileService fileService = FileServiceFactory.getFileService();
    AppEngineFile file = fileService.createNewBlobFile("image/png", fileName);
    FileWriteChannel channel = fileService.openWriteChannel(file, true);
    FileUtils.copyStream(in, Channels.newOutputStream(channel));
    channel.closeFinally();
    BlobFile blobFile = new BlobFile();
    blobFile.setKey(fileService.getBlobKey(file).getKeyString());
    blobFile.setCreation(new Date());
    blobFile.setFileName(fileName);
    uploadedFiles.put(fileName, blobFile);
  }
}
