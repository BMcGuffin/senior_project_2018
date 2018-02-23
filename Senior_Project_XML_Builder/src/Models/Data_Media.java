/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

/**
 * Represents a single piece of media data. Includes metadata relevant to the
 * playback of audio or video.
 *
 * @author Bryan McGuffin
 * @version Feb 20, 2018
 */
class Data_Media implements Buildable
{
    public Data_Media()
    {
        
    }
    
    /**
     * The name of the data file.
     */
    public String fileName;
    
    /**
     * Type of media: Still image, audio only, or video.
     */
    public boolean isImage, isAudio, isVideo;
    
    /**
     * How many seconds into the media file should playback begin. Defaults to 0.
     */
    public int startTime;
    
    /**
     * How long should playback last. Defaults to length of file.
     */
    public int length;
}
