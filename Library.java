import java.io.IOException;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */

// Name : Jaihunbek Mohammadullah
// Student ID : 501180612
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  	podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{ 
		if (content == null) throw new AudioContentNotFound("Content Not Found"); 
		// if the content is a song
		if (content.getType().equals(Song.TYPENAME) &&  !songs.contains(content)) 
		{	
			Song addedSong = (Song) content; // Down casting the AudioContent object to an instance of Song
			songs.add(addedSong);    
		}

		// if the content is an audiobook 
		else if (content.getType().equals(AudioBook.TYPENAME) &&  ! audiobooks.contains(content))
		{
			AudioBook addedAudioBook = (AudioBook) content; // Down casting the AudioContent object to an instance of AudioBook
			audiobooks.add(addedAudioBook);
		}

		// if the content is a podcast and it is not contained already
		else if (content.getType().equals(Podcast.TYPENAME) &&  !podcasts.contains(content) )
		{
			Podcast addedPodcast = (Podcast) content; 
			podcasts.add(addedPodcast);  
		}
		// if already downloaded throw exception 
		else throw new DownloadedAlready(content.getType() + " " + content.getTitle() + " Already Downloaded");
		System.out.println(content.getType() + " " + content.getTitle() + " Added to Library"); 
	}
	
	public void downloadArtist(String artist, AudioContentStore store)
	{
		// This try block is for the declaration of the ArrayList of indexes using the Artist string 
		// i.e., this try block sees if the artist that is inputted is a valid input
		try 
		{
			ArrayList<Integer> indexes = store.getArtistIndexes(artist); 
			for (int i = 0; i < indexes.size(); i++)
			{
				// This try-catch is inside the for loop in order that the "Already Downloaded Message" be printed for each item,
				// as opposed to only the firsts
				try 
				{
					int index = indexes.get(i) + 1; // add one because the method "Download" does a -1 on the index elsewhere in the program 
					AudioContent content = store.getContent(index); 
					this.download(content); 
				}
				catch (DownloadedAlready e)
				{
					System.out.println(e.getMessage());
				}
			}	
		}
		// Catch exception where artist input is invalid 
		catch (NullPointerException e)
		{
			System.out.println("No Matches for " + artist);
		}
	}

	public void downloadGenre(Song.Genre genre, AudioContentStore store)
	{

			ArrayList<Integer> indexes = store.getGenreIndexes(genre); 
			for (int i = 0; i < indexes.size(); i++)
			{
				try
				{
					int index = indexes.get(i) + 1; // index + 1 because it the index is being subtracted by 1 elsewhere
					AudioContent content = store.getContent(index); 
					this.download(content); 
				}
				catch (DownloadedAlready e) 
				{
					System.out.println(e.getMessage()); 
				}
			}

	}
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		// Iterate thru the ArrayList songs and printInfo
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// Iterate thru the ArrayList audiobooks and printInfo
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1; 
			System.out.print(index + ". "); 
			podcasts.get(i).printInfo();
			System.out.println();
		}
	}

	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		// Iterate thru the ArrayList playlists and printTitle
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle()); 
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		ArrayList<String> artistNames = new ArrayList<String>();
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		for (int i = 0; i < songs.size(); i++)
		{
			if (!artistNames.contains(songs.get(i).getArtist()))
			{
				artistNames.add(songs.get(i).getArtist());
			} 
		}

		for (int i = 0; i < artistNames.size(); i++)
		{
			System.out.println( (i+1) + ". " + artistNames.get(i) );
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist

	public void deleteSong(int index) // the argument is 1-indexed
	{
		if (index < 1 || index > songs.size())
		{
			throw new PlaylistNotFound("Song Not Found");
		}  

		String songTitle = songs.get(index - 1).getTitle(); 
		for (int i = 0; i < playlists.size(); i++)
		{
			// iterate through the ArrayList of Playlist objects 
			// then check if this song is contained in the Playlist object's ArrayList called contents
			if (playlists.get(i).getContent().contains(songs.get(index - 1))) 
			{
				// if this Playlist's content has this song, iterate through the contents ArrayList to find the index of it 
				// so that we can remove it from this playlist
				for (int j = 0; j < playlists.get(i).getContent().size(); j++)
				{
					int songToRemoveIndex = playlists.get(i).findSongInPlaylist(songTitle); 
					if (songToRemoveIndex != -1) 
					{
						// remove the song in the ArrayList contents of the Playlist object at the index it is found 
						playlists.get(i).getContent().remove(songToRemoveIndex); 
					}
				}

			}
		}
		songs.remove(index - 1); // remove the song from the ArrayList song
	}

  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator()); 
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{

		public int compare(Song song1, Song song2)
		{
			if (song1.getYear() < song2.getYear()) // if the first song is less than the other in terms of "year" 
			{
				return -1;
			} 
			else if (song1.getYear() == song2.getYear()) // if they are the from the same year 
			{
				return 0; 
			}
			else return 1; // if the second song's year is greater than the first 
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
		Collections.sort(songs, new SongLengthComparator()); 
	 // Use Collections.sort() 
	}

  	// Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2)
		{
			if (song1.getLength() < song2.getLength() ) // if the first song is less than the other in terms of length 
			{
				return -1;
			} 
			else if (song1.getLength()  == song2.getLength() ) // if they are the same length  
			{
				return 0; 
			}
			else return 1; // if the second song's length is greater than the first 
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
	  Collections.sort(songs);
		// class Song should implement the Comparable interface
		// see class Song code
	}


	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFound("Song Not Found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode)
	{	
		if (index <  1 || index > podcasts.size()) // check if there is such a podcast
		{
			errorMsg = "Podcast Not Found";
			throw new AudioContentNotFound(errorMsg);
		}

		// Validate the other inputted parameters as well 
		if (! podcasts.get(index - 1).hasSeason(season)) // Check if this podcast HAS this season, i.e., if the index is valid for the arraylist of podcasts
		{
			errorMsg = "Season Not Found"; 
			throw new AudioContentNotFound(errorMsg);
		}

		if (! podcasts.get(index - 1).seasons.get(season - 1).hasEpisode(episode)) // likewise, check if this season object HAS this episode, i.e., if episode index is valid for the arraylist of episodes
		{
			errorMsg = "Episode Not Found"; 
			throw new AudioContentNotFound(errorMsg);
		}
		 

		// if the all the inputs are valid, then go set the correct episode file in the place of AudioFile
		podcasts.get(index - 1).setEpisodeData(season, episode);
		// Then play it
		podcasts.get(index- 1).play();
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season)
	{
		if (index <  1 || index > podcasts.size()) // check if there is such a podcast
		{
			errorMsg = "Podcast Not Found";
			throw new AudioContentNotFound(errorMsg);
		}

		// Validate the other inputted parameters as well 
		if (! podcasts.get(index - 1).hasSeason(season)) // Check if this podcast HAS this season, i.e., if the index is valid for the arraylist of podcasts
		{
			errorMsg = "Season Not Found"; 
			throw new AudioContentNotFound(errorMsg);
		}
		podcasts.get(index - 1).printTOC(season);
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		// get audiobook from ArrayList
		// get the ArrayList of chapters associated with that audiobook object 
		// then get that chapter from that ArrayList 
		if (index < 1 || index > audiobooks.size()) // check if index is valid 
		{
			errorMsg = "Audiobook Not Found";
			throw new AudioContentNotFound(errorMsg);
		}
		else if (chapter < 1 || index > audiobooks.get(index-1).getChapters().size()) // check if chapter index is valid 
		{
			errorMsg = "Chapter Not Found";
			throw new AudioContentNotFound(errorMsg);
		}

		audiobooks.get(index-1).selectChapter(chapter); // select chapter 
		audiobooks.get(index-1).play(); // play the chapter
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())
		{
			errorMsg = "Audiobook Not Found";
			throw new AudioContentNotFound(errorMsg);
		}

		audiobooks.get(index - 1).printTOC();
	}
	
	// PRINT PDOCAST TOC 
	public void printPodcastTOC(int podcast, int season) 
	{ 

		// Validate the parameters and then print the TOC for that particular season in the podcast
		if (podcast < 1 || podcast > podcasts.size()) 
		{
			errorMsg = "Podcast Not Found"; 
			throw new AudioContentNotFound(errorMsg);
		}

		if (! podcasts.get(podcast- 1).hasSeason(season)) // 
		{
			errorMsg = "Season Not Found"; 
			throw new AudioContentNotFound(errorMsg);
		}

		podcasts.get(podcast - 1).printTOC(season);
	}
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title); 
		if (!playlists.contains(newPlaylist)) 
		{
			playlists.add(newPlaylist);
		}
		errorMsg = "Playlist " + title + " Already Exists"; 
		throw new PlaylistAlreadyExists(errorMsg);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			if (playlists.get(i).getTitle().equals(title))
			{
				playlists.get(i).printContents(); 
				System.out.println();
				return;
			}
		}

		throw new PlaylistNotFound("No such playlist");
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			if (playlists.get(i).getTitle().equals(playlistTitle))
			{
				playlists.get(i).playAll(); 
				return;
			}
		}
		throw new PlaylistNotFound("No such playlist");
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			if (playlists.get(i).getTitle().equals(playlistTitle))
			{
				playlists.get(i).play(indexInPL); 
				return; 
			}
		}
		throw new PlaylistNotFound("No such playlist");
	}
	

	// Self-made helper method for retrieving a Playlist object from ArrayList of Playlists given the Playlist Title

	public Playlist getPlaylist(String playlistTitle)
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			if (playlists.get(i).getTitle().equals(playlistTitle))
			{
				return playlists.get(i);
			}
		}
		return null; 
	}
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		if (type.equalsIgnoreCase(Song.TYPENAME))
		{
			// by means of the method above, retrieve the playlist object 
			// from the list and then add the Song object contained at the index given in the songs list 
			this.getPlaylist(playlistTitle).addContent(songs.get(index - 1));
			return;
		}
		else if (type.equalsIgnoreCase(AudioBook.TYPENAME))
		{
			// by means of the method above, retrieve the playlist object 
			// from the list and then add the Audiobook object contained at the index given in the audiobooks list 
			this.getPlaylist(playlistTitle).addContent(audiobooks.get(index-1));
			return;
		}
		
		else if (type.equalsIgnoreCase(Podcast.TYPENAME))
		{
			// by means of the method above, retrieve the playlist object 
			// from the list and then add the Podcast object contained at the index given in the podcasts list 
			this.getPlaylist(playlistTitle).addContent(podcasts.get(index));
			return;
		}
		
		throw new PlaylistNotFound("Could Not Find That Playlist");
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		if (! this.getPlaylist(title).contains(index)) 
		{
			errorMsg = "Index in playlist is not valid"; 
			throw new PlaylistNotFound(errorMsg);
		}
		this.getPlaylist(title).deleteContent(index); 
	}
	
}

class AudioContentNotFound extends RuntimeException 
{
	public AudioContentNotFound() {} 
	public AudioContentNotFound(String message)
	{
		super(message); 
	}
}

class DownloadedAlready extends RuntimeException
{
	public DownloadedAlready() {}
	public DownloadedAlready(String message)
	{
		super(message); 
	}
}

class PlaylistNotFound extends RuntimeException
{
	public PlaylistNotFound() {}
	public PlaylistNotFound(String message)
	{
		super(message); 
	}
}

class PlaylistAlreadyExists extends RuntimeException
{
	public PlaylistAlreadyExists() {}
	public PlaylistAlreadyExists(String message)
	{
		super(message);
	}
}
