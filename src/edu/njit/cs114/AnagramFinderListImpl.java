package edu.njit.cs114;

import java.io.IOException;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/22/20
 */
public class AnagramFinderListImpl extends AbstractAnagramFinder {

    private List<WordArrPair> wordArrList = new ArrayList<>();

    private class WordArrPair implements Comparable<WordArrPair> {
        public final String word;
        public final char[] charArr;

        public WordArrPair(String word) {
            this.word = word;
            charArr = word.toCharArray();
            Arrays.sort(charArr);
        }

        public boolean isAnagram(WordArrPair wordArrPair) {
            /**
             * To be completed
             * Compare charArr already sorted
             */
            return Arrays.equals(this.charArr, wordArrPair.charArr);
        }

        @Override
        public int compareTo(WordArrPair wordArrPair) {
            return this.word.compareTo(wordArrPair.word);
        }


    }


    @Override
    public void clear() {
        wordArrList.clear();
    }

    @Override
    public void addWord(String word) {
        /**
         * Create a word pair object and add it to list
         */
        wordArrList.add(new WordArrPair(word));
    }

    @Override
    public List<List<String>> getMostAnagrams() {
        Collections.sort(wordArrList); // Why is this necessary?
        ArrayList<List<String>> allAnagramsList = new ArrayList<>();

        /**
         * To be completed
         * Repeatedly do this:
         * (a)Each wordPair in list is compared to others to identify all its anagrams;
         * (b) add the anagram words to the same group;
         *      if there are no anagrams, single word forms a group
         * (c) remove these words from wordArrList
         */

        for (WordArrPair pair : this.wordArrList) {
            List<String> anagramList = null;
            for (List<String> list : allAnagramsList) {
                if (list.size() > 0 &&
                        (new WordArrPair(list.get(0))).isAnagram(pair)
                ) { // Maybe a redundant check, but will prevent index out of bounds in all case
                    anagramList = list;
                    break;
                }
            }

            if (anagramList != null) {
                anagramList.add(pair.word);
            } else {
                anagramList = new ArrayList<>();
                anagramList.add(pair.word);
                allAnagramsList.add(anagramList);
            }

            // wordArrList.remove(pair); // Not sure why this is necessary?
        }

        // Determine the most
        int hightestCount = 0;
        HashMap<Integer, List<List<String>>> groups = new HashMap<>();

        for (List<String> anagrams : allAnagramsList) {
            if (anagrams.size() > hightestCount)
                hightestCount = anagrams.size();

            if(groups.containsKey(anagrams.size()))
                groups.get(anagrams.size()).add(anagrams);
            else {
                List<List<String>> group = new ArrayList<>();
                group.add(anagrams);
                groups.put(anagrams.size(), group);
            }
        }

        return groups.get(hightestCount);
    }

    public static void main(String[] args) {
        AnagramFinderListImpl finder = new AnagramFinderListImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords = 0;
        try {
            nWords = finder.processDictionary("/home/lucasantarella/git/njit_cs114/src/edu/njit/cs114/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> anagramGroups = finder.getMostAnagrams();
        long estimatedTime = System.nanoTime() - startTime;
        double seconds = ((double) estimatedTime / 1000000000);
        System.out.println("NUmber of words : " + nWords);
        System.out.println("Number of groups of words with maximum anagrams : "
                + anagramGroups.size());
        if (!anagramGroups.isEmpty()) {
            System.out.println("Length of list of max anagrams : " + anagramGroups.get(0).size());
            for (List<String> anagramGroup : anagramGroups) {
                System.out.println("Anagram Group : " + anagramGroup);
            }
        }
        System.out.println("Time : " + seconds);

    }
}
