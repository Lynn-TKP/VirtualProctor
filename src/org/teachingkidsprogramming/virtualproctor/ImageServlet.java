package org.teachingkidsprogramming.virtualproctor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.spun.util.servlets.BasicServlet;

public class ImageServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
    BlobKey blobKey = new BlobKey(BasicServlet.loadNullableString(req, "fileName"));
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, res);
  }
}
