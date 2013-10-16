package org.teachingkidsprogramming.virtualproctor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;

public class ScreenShots implements JRack
{
  @Override
  @Route("/ScreenShots")
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    String date = "\"" + new Date().getTime() + "\"";
    String fake = "{\"images\":{\"0\":{\"fileName\":\"llewellynscomputer\",\"imageUrl\":\"http://docs.python.org/3/_images/turtle-star.png\"},\"1\":{\"fileName\":\"lynnscomputer\",\"imageUrl\":\"http://nebomusic.net/jesRainbowExample.png\"},\"length\":2}}";
    return standardJson(fake);
  }
  public static RackResponse standardJson(String html)
  {
    return new RackResponse(RackResponseUtils.ReturnCode.OK, getStandardJsonHeader(), html);
  }
  public static Map<String, String> getStandardJsonHeader()
  {
    Map<String, String> header = new HashMap<String, String>();
    header.put(RackResponseUtils.CONTENT_TYPE, "application/json");
    return header;
  }
}
