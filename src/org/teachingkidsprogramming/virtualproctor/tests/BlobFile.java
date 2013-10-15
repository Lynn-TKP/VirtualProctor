package org.teachingkidsprogramming.virtualproctor.tests;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.Query;

import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.datastore.Entity;

public class BlobFile
{
  private String key;
  private Date   creation;
  private String creationHandle;
  private String fileName;
  private String contentType;
  private long   size;
  public String toString()
  {
    return MessageFormat.format("BlobFile [\r\n  key={0}\r\n" + "  creation={1}\r\n" + "  creationHandle={2}\r\n"
        + "  fileName={3}\r\n" + "  contentType={4}\r\n" + "  size={5}\r\n" + "]", key, creation, creationHandle,
        fileName, contentType, size);
  }
  public String getKey()
  {
    return key;
  }
  public void setKey(String key)
  {
    this.key = key;
  }
  public void setCreation(Date creation)
  {
    this.creation = creation;
  }
  public void setCreationHandle(String creationHandle)
  {
    this.creationHandle = creationHandle;
  }
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }
  public void setSize(long size)
  {
    this.size = size;
  }
  public Date getCreation()
  {
    return creation;
  }
  public String getCreationHandle()
  {
    return creationHandle;
  }
  public String getFileName()
  {
    return fileName;
  }
  public String getContentType()
  {
    return contentType;
  }
  public long getSize()
  {
    return size;
  }
  public static BlobFile wrap(Entity entity)
  {
    BlobFile blob = new BlobFile();
    blob.key = entity.getKey().getName();
    blob.creation = (Date) entity.getProperty(BlobInfoFactory.CREATION);
    blob.creationHandle = (String) entity.getProperty("creation_handle");
    blob.fileName = (String) entity.getProperty(BlobInfoFactory.FILENAME);
    blob.contentType = (String) entity.getProperty(BlobInfoFactory.CONTENT_TYPE);
    blob.size = (Long) entity.getProperty(BlobInfoFactory.SIZE);
    return blob;
  }
  public static List<BlobFile> wrapAll(List<Entity> asList)
  {
    return Query.select(asList, new Function1<Entity, BlobFile>()
    {
      public BlobFile call(Entity e)
      {
        return BlobFile.wrap(e);
      }
    });
  }
}
