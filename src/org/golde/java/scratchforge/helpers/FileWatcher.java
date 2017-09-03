package org.golde.java.scratchforge.helpers;

import java.io.File;
import java.util.TimerTask;

public abstract class FileWatcher extends TimerTask { //http://www.rgagnon.com/javadetails/java-0490.html
	  private long timeStamp;
	  private File file;

	  public FileWatcher( File file ) {
	    this.file = file;
	    this.timeStamp = file.lastModified();
	  }

	  public final void run() {
	    long timeStamp = file.lastModified();

	    if( this.timeStamp != timeStamp ) {
	      this.timeStamp = timeStamp;
	      onChange(file);
	    }
	  }

	  protected abstract void onChange( File file );
	}
