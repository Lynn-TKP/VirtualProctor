package org.teachingkidsprogramming.virtualproctor;

import org.jrack.RackServlet;
import org.jrack.utils.InvokerRack;

@SuppressWarnings("serial")
public class VirtualProctorServlet extends RackServlet
{
  public VirtualProctorServlet()
  {
    super(new RackRoute(new InvokerRack("org.teachingkidsprogramming.virtualproctor.*")).addRoutesFor(
        VirtualProctorViewerRack.class, ScreenShots.class));
  }
}
