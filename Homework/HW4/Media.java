import tester.Tester;

// a piece of media
interface IMedia {

  // is this media really old?
  boolean isReallyOld();

  // are captions available in this language?
  boolean isCaptionAvailable(String language);

  // a string showing the proper display of the media
  String format();
}

abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions; // available captions

  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.title ...             -- String
   *  ... this.captionOptions ...    -- ILoString
   *  
   *  Methods:
   *  ... this.isReallyOld() ...                -- boolean
   *  ... this.isCaptionAvailable(String) ...   -- boolean
   *  ... this.format() ...                     -- String
   *  
   *  Methods of parameters:
   *  ... this.captionOptions.isCaptionAvailable(String) ... -- boolean
   */

  // is this media really old?
  // defaults to false, overridden by other classes as necessary
  public boolean isReallyOld() {
    return false;
  }

  // are captions available in this language?
  public boolean isCaptionAvailable(String language) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.language ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.language.concat(String) ...    -- String 
     * ... this.language.compareTo(String) ... -- int 
     */
    return this.captionOptions.isCaptionAvaliable(language);
  }

  // a string showing the proper display of the media
  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;

  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.title ...             -- String
   *  ... this.captionOptions ...    -- ILoString
   *  ... this.year ...              -- int
   *  
   *  Methods:
   *  ... this.isReallyOld() ...                -- boolean
   *  ... this.isCaptionAvailable(String) ...   -- boolean
   *  ... this.format() ...                     -- String
   *  
   *  Methods of parameters:
   *  ... this.captionOptions.isCaptionAvailable(String) ... -- boolean
   */

  // is this media really old?
  // i.e. did this movie come out before 1930
  public boolean isReallyOld() {
    return this.year < 1930;
  }

  // a string showing the proper display of the media
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.title ...             -- String
   *  ... this.captionOptions ...    -- ILoString
   *  ... this.showName ...          -- String
   *  ... this.seasonNumber ...      -- int
   *  ... this.episodeOfSeason ...   -- int
   *  
   *  Methods:
   *  ... this.isReallyOld() ...                -- boolean
   *  ... this.isCaptionAvailable(String) ...   -- boolean
   *  ... this.format() ...                     -- String
   *  
   *  Methods of parameters:
   *  ... this.captionOptions.isCaptionAvailable(String) ... -- boolean
   */

  // a string showing the proper display of the media
  public String format() {
    return this.showName + " " + this.seasonNumber + "." + this.episodeOfSeason + " - "
        + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.title ...             -- String
   *  ... this.captionOptions ...    -- ILoString
   *  ... this.channelName ...       -- String
   *  
   *  Methods:
   *  ... this.isReallyOld() ...                -- boolean
   *  ... this.isCaptionAvailable(String) ...   -- boolean
   *  ... this.format() ...                     -- String
   *  
   *  Methods of parameters:
   *  ... this.captionOptions.isCaptionAvailable(String) ... -- boolean
   */

  // a string showing the proper display of the media
  public String format() {
    return this.title + " by " + this.channelName;
  }
}

// lists of strings
interface ILoString {
  boolean isCaptionAvaliable(String str);
}

// an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {

  }

  // are captions available in a given language?
  public boolean isCaptionAvaliable(String language) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // are captions available in a given language?
  public boolean isCaptionAvaliable(String language) {
    return this.first.equals(language) || this.rest.isCaptionAvaliable(language);
  }
}

class ExamplesMedia {
  IMedia movie1 = new Movie("American Psycho", 1900,
      new ConsLoString("English", new ConsLoString("Spanish", new MtLoString())));
  IMedia movie2 = new Movie("The Wolf of Wall Street", 2013, new ConsLoString("English",
      new ConsLoString("Spanish", new ConsLoString("German", new MtLoString()))));

  IMedia tvEpisode1 = new TVEpisode("Pilot", "Breaking Bad", 1, 1,
      new ConsLoString("English", new ConsLoString("Spanish", new MtLoString())));
  IMedia tvEpisode2 = new TVEpisode("The Iron Throne", "Game of Thrones", 8, 6, new MtLoString());

  IMedia ytVideo1 = new YTVideo("Me at the zoo", "jawed",
      new ConsLoString("English", new ConsLoString("Spanish",
          new ConsLoString("German", new ConsLoString("Turkish", new MtLoString())))));
  IMedia ytVideo2 = new YTVideo("history of the entire world, i guess", "bill wurtz",
      new ConsLoString("English", new ConsLoString("Spanish",
          new ConsLoString("German", new ConsLoString("Turkish", new MtLoString())))));

  // tests the method isReallyOld for the classes that implement IMedia
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(movie1.isReallyOld(), true) && t.checkExpect(movie2.isReallyOld(), false)
        && t.checkExpect(tvEpisode1.isReallyOld(), false)
        && t.checkExpect(tvEpisode2.isReallyOld(), false)
        && t.checkExpect(ytVideo1.isReallyOld(), false)
        && t.checkExpect(ytVideo2.isReallyOld(), false);
  }

  // tests the method isReallyOld for the classes that implement IMedia
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(movie1.isCaptionAvailable("German"), false)
        && t.checkExpect(movie2.isCaptionAvailable("German"), true)
        && t.checkExpect(tvEpisode1.isCaptionAvailable("Spanish"), true)
        && t.checkExpect(tvEpisode2.isCaptionAvailable("English"), false)
        && t.checkExpect(ytVideo1.isCaptionAvailable("English"), true)
        && t.checkExpect(ytVideo2.isCaptionAvailable("Turkish"), true);
  }

  // tests the method format for the classes that implement IMedia
  boolean testFormat(Tester t) {
    return t.checkExpect(movie1.format(), "American Psycho (1900)")
        && t.checkExpect(movie2.format(), "The Wolf of Wall Street (2013)")
        && t.checkExpect(tvEpisode1.format(), "Breaking Bad 1.1 - Pilot")
        && t.checkExpect(tvEpisode2.format(), "Game of Thrones 8.6 - The Iron Throne")
        && t.checkExpect(ytVideo1.format(), "Me at the zoo by jawed")
        && t.checkExpect(ytVideo2.format(), "history of the entire world, i guess by bill wurtz");
  }

}