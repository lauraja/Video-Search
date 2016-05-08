/*  
 *   This file is part of the computer assignment for the
 *   Information Retrieval course at KTH.
 * 
 *   First version: Johan Boye, 2012
 *   Additions: Hedvig Kjellström, 2012-14
 *   Second version: Laura Jacquemod, 2015
 */  


package ir;

import java.io.File;
import java.util.LinkedList;

/**
 *   Class linking the web interface to the search engine
 */
public class SearchWeb {
	//VARIABLES TO CHOOSE:
	static final double weightPopularity = 0.5;
	static final double alpha = 1;
	static final double beta = 0.5;
	static final double thresholdProbability = -10;
	static final double[] popularityScores ={0.3, 0.3, 0.3};
	static final int distanceFrames = 3;
	//For ranked retrieval
	static final boolean optimization = false;    
	static final double idf_threshold = 0.0001;
	//For additional info (title, description,...)
	static final boolean addition = false;
	static final double weight_addition = 0.2;
	
    static final String LOGOPIC = "Videoquery.png";
    static final String BLANKPIC = "blank.jpg";

    static final String DIRNAME = "myFiles";

    Indexer indexer = new Indexer();
    private Query query; 
    private PostingsList results; 
    LinkedList<String> dirNames = new LinkedList<String>();
    int queryType = Index.INTERSECTION_QUERY;
    int rankingType = Index.TF_IDF;
    int structureType = Index.UNIGRAM;
    int frameType = Index.ALLFRAMES;
    Object indexLock = new Object();

    public SearchWeb(String query) {
        this.query = new Query(SimpleTokenizer.normalize(query));
    }

    public void index() {
        synchronized ( indexLock ) {
            indexer.processFiles(new File(SearchWeb.DIRNAME), thresholdProbability);
        }
    }
    
    public PostingsList search() {
        return indexer.index.search(query, queryType, rankingType, frameType, weightPopularity, 
                popularityScores, distanceFrames, optimization, idf_threshold, addition, weight_addition);
    }
}

