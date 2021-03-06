/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.sound.sampled.*;

/**
 * Represents a single piece of media data. Includes metadata relevant to the
 * playback of audio or video.
 *
 * @author Bryan McGuffin
 * @version Feb 20, 2018
 */
public class Data_Media implements Buildable
{
	/**
	 * The name of this particular data field.
	 */
	public String elementName;
	/**
	 *
	 */
	private File mediaFile;
	/**
	 * The name of the data file.
	 */
	private String fileName;
	/**
	 * Type of media: Still image, audio only, or video.
	 */
	private boolean isImage, isAudio, isVideo;
	/**
	 * How many seconds into the media file playback should begin. Defaults to 0.
	 */
	private int startTime;
	/**
	 * How long playback should last. Defaults to length of file.
	 */
	private int playbackLength;
	private int fileLength;

	/**
	 * Constructor.
	 */
	public Data_Media(String eName)
	{
		elementName = eName;
		mediaFile = null;
		fileName = "";
		isImage = isAudio = isVideo = false;
		startTime = 0;
		playbackLength = 0;
	}

	/**
	 * Discern the type of media file; if acceptable, set it as the current file.
	 *
	 * @param f
	 *            the file in question.
	 * @return true if the file is of an acceptable format.
	 */
	public boolean setMediaFile(File f)
	{
		if (f != null)
		{
			String fileType = "";
			try
			{
				fileType = Files.probeContentType(f.toPath());
				isImage = (fileType.equals("image"));
				isAudio = (fileType.equals("audio"));
				isVideo = (fileType.equals("video"));
				parseValidMediaFile(f);
			}
			catch (Exception ex)
			{
				mediaFile = null;
				fileName = "";
				isImage = isAudio = isVideo = false;
				startTime = 0;
				playbackLength = 0;
			}
		}
		else
		{
			mediaFile = null;
			fileName = "";
			isImage = isAudio = isVideo = false;
			startTime = 0;
			playbackLength = 0;
		}
		return isImage || isAudio || isVideo;
	}

	/**
	 * Set the playback length of the file.
	 *
	 * @param newLength
	 *            the length, in seconds, of playback.
	 * @return true if the proposed new playback length was valid.
	 */
	public boolean setPlayLength(int newLength)
	{
		if (newLength >= 1 && (startTime + newLength) <= fileLength)
		{
			playbackLength = newLength;
			return true;
		}
		return false;
	}

	/**
	 * Set the point in the file at which playback begins.
	 *
	 * @param newStart
	 *            the time, in seconds, where playback should start.
	 * @return true if the new start time was valid.
	 */
	public boolean setStartTime(int newStart)
	{
		if (newStart >= 0 && newStart < fileLength)
		{
			startTime = newStart;
			if (startTime + playbackLength > fileLength)
			{
				playbackLength = fileLength - startTime;
			}
			return true;
		}
		return false;
	}

	/**
	 * Get the number of seconds of playback for this file.
	 *
	 * @return the length of playback time.
	 */
	public int getPlayLength()
	{
		return playbackLength;
	}

	/**
	 * Get the number of seconds into the file to begin playback.
	 *
	 * @return the start time of file playback.
	 */
	public int getStartTime()
	{
		return startTime;
	}

	/**
	 * Get the name of this file.
	 *
	 * @return the name of the media file
	 */
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public Buildable duplicate()
	{
		Data_Media dup = new Data_Media(this.elementName);
		dup.setMediaFile(this.mediaFile);
		dup.setPlayLength(this.fileLength);
		dup.setStartTime(this.startTime);
		return dup;
	}

	@Override
	public String elementName()
	{
		return elementName;
	}

	/**
	 * Parse the media file. Assign values based on the file's characteristics.
	 *
	 * @param f
	 *            the media file to be parsed.
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private void parseValidMediaFile(File f) throws UnsupportedAudioFileException, IOException
	{
		mediaFile = f;
		fileName = f.getName();
		if (isVideo)
		{
			// FUTURE Add code for determining duration of a video file.
			throw new UnsupportedOperationException("Not supported yet.");
		}
		if (isAudio)
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = f.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			fileLength = (int) Math.ceil(durationInSeconds);
			playbackLength = fileLength;
			startTime = 0;
		}
		if (isImage)
		{
			fileLength = 1;
			playbackLength = fileLength;
			startTime = 0;
		}
	}

	/**
	 * {@inheritDoc} This class uses the following tags: SRC_FILE (Holds the file
	 * path), PLAYBACK (holds length of playback in seconds), START (holds start
	 * time in seconds).
	 */
	@Override
	public String toXML()
	{
		String output = "";
		ArrayList<XML_Writer.attributes> attrs = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		attrs.add(XML_Writer.attributes.Data_Type);
		attrs.add(XML_Writer.attributes.Src_File);
		attrs.add(XML_Writer.attributes.Playback);
		attrs.add(XML_Writer.attributes.Start);
		values.add("Media");
		values.add(fileName);
		values.add("" + playbackLength);
		values.add("" + startTime);
		output += XML_Writer.emptyTag(XML_Writer.tagWithAttributes(elementName, attrs, values));
		return output;
	}

	@Override
	public void reset()
	{
		mediaFile = null;
		fileName = "";
		isImage = isAudio = isVideo = false;
		startTime = 0;
		playbackLength = 0;
	}
}
