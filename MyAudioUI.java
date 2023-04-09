import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

// Name : Jaihunbek Mohammadullah
// Student ID : 501180612
public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int index = 0;
				
				System.out.print("Store Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Content Not Found in Store");
				else if (!mylibrary.download(content))
						System.out.println(mylibrary.getErrorMessage());						
			}

			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// read the index of the song in library songs list 
				int index = 0;
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				if (!mylibrary.playSong(index)) // check if the index is valid for the songs list
				{
					System.out.println(mylibrary.getErrorMessage());
				}

				// Print error message if the song doesn't exist in the library
			}
			
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				// Read and validate the input for the bookIndex
				int index = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// At once print the TOC and check the boolean value 
				if (!mylibrary.printAudioBookTOC(index))
				{
					System.out.println(mylibrary.getErrorMessage());
				}

			// Print error message if the book doesn't exist in the library
			}

			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				// Read and validate bookIndex input 
				int bookIndex = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					bookIndex = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Read and validate chapterIndex input
				int chapterIndex = 0;
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapterIndex = scanner.nextInt(); 
					scanner.nextLine(); 
				}
				
				// Play the book while at once checking the boolean value 
				if (!mylibrary.playAudioBook(bookIndex, chapterIndex)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}
				
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				// Read and validate the input for the podcast index
				// this index-variable is 1-indexed -- which is why it is called "podcast", rather than "podIndex" 
				// this helps me keep track of when it is 1-indexed or 0-indexed
				int podcast = 0; 

				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					podcast = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				int season = 0;
				System.out.print("Season Number: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt(); 
					scanner.nextLine(); 
				}
				// At once print the TOC and check the boolean value 
				if (!mylibrary.printPodcastTOC(podcast, season)); 
				{
					System.out.println(mylibrary.getErrorMessage());
				}

			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				// Read and validate whichPodcast input 
				int whichPodcast = 0;
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					whichPodcast = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Read and validate whichSeason input
				int whichSeason = 0;
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					whichSeason = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Read and validate whichSeason input
				int whichEpisode = 0;
				System.out.print("Episode: ");
				if (scanner.hasNextInt())
				{
					whichEpisode = scanner.nextInt(); 
					scanner.nextLine(); 
				}
				
				// Play the book while at once checking the boolean value 
				if (!mylibrary.playPodcast(whichPodcast, whichSeason, whichEpisode)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}
				
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				// Read and validate title input 
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}
				
				// Play the book while at once checking the boolean value 
				if (!mylibrary.playPlaylist(title)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}				
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				// Read and validate title input 
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}
				
				int index = 0; 
				System.out.print("Content Number: "); 
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}
				// Play the book while at once checking the boolean value 
				if (!mylibrary.playPlaylist(title, index)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}	
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				
				int index = 0; 
				System.out.print("Library Song #: "); 
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Play the book while at once checking the boolean value 
				if (!mylibrary.deleteSong(index)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}	
					
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}
				
				// Play the book while at once checking the boolean value 
				if (!mylibrary.makePlaylist(title)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}	
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}
				
				// Play the book while at once checking the boolean value 
				if (!mylibrary.printPlaylist(title)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}	
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}
				
				String type  = "";
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNext())
				{
					type = scanner.next(); 
					scanner.nextLine(); 
				}

				int index = 0; 
				System.out.print("Library Content Number #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Play the book while at once checking the boolean value 
				if (!mylibrary.addContentToPlaylist(type, index, title)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}	
				
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next(); 
					scanner.nextLine(); 
				}

				int index = 0; 
				System.out.print("Playlist Content Number #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt(); 
					scanner.nextLine(); 
				}

				// Play the book while at once checking the boolean value 
				if (!mylibrary.delContentFromPlaylist(index, title)) //
				{
					System.out.println(mylibrary.getErrorMessage());
				}					
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
