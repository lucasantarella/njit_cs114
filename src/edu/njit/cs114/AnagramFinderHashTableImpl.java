package edu.njit.cs114;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Author: Ravi Varadarajan
 * Date created: 3/22/20
 */
public class AnagramFinderHashTableImpl extends AbstractAnagramFinder {

    private static final int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19,
            23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79,
            83, 89, 97, 101};

    private Map<Character, Integer> letterMap;

    private Map<Long, ArrayList<String>> anagramTable = new HashMap<>();


    private void buildLetterMap() {
        this.letterMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            this.letterMap.put((char) (i + 97), AnagramFinderHashTableImpl.primes[i]); // 97 offset because lowercase 'a' is ascii value 97
        }
    }

    public AnagramFinderHashTableImpl() {
        buildLetterMap();
    }

    /**
     * Finds hash code for a word using prime number factors
     *
     * @param word
     * @return
     */
    public Long myHashCode(String word) {
        long product = 1;
        for (char letter : word.toCharArray()) {
            product *= this.letterMap.get(letter);
        }
        return product;
    }

    /**
     * Add the word to the anagram table using hash code
     *
     * @param word
     */
    @Override
    public void addWord(String word) {
        long key = this.myHashCode(word);
        if (this.anagramTable.containsKey(key)) {
            this.anagramTable.get(key).add(word);
        } else {
            ArrayList<String> newList = new ArrayList<>();
            newList.add(word);
            this.anagramTable.put(key, newList);
        }
    }

    @Override
    public void clear() {
        anagramTable.clear();
    }


    /**
     * Return list of groups of anagram words which have most anagrams
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<List<String>> getMostAnagrams() {
        int hightestCount = 0;
        HashMap<Integer, List<List<String>>> groups = new HashMap<>();

        for (ArrayList<String> anagrams : this.anagramTable.values()) {
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
        AnagramFinderHashTableImpl finder = new AnagramFinderHashTableImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords = 0;
        try {
            nWords = finder.processDictionary("words.txt");
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
