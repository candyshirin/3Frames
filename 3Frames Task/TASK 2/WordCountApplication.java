
// Word Count Application
// ## Overview :
// This Java application performs word counting and fuzzy search using a Trie data structure. It reads text from a file, populates the Trie, and provides functionality to get sorted word counts and perform fuzzy searches.

// ## Features :
// Word Counting: Counts the occurrences of each word in the input text file.
// Fuzzy Search: Finds words similar to a given word based on Levenshtein distance.
// Efficient Data Structure: Utilizes a Trie (prefix tree) for efficient word storage and retrieval.


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordCountApplication {
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;
        int count;

        TrieNode() {
            isEndOfWord = false;
            count = 0;
        }
    }

    static class WordCount {
        String word;
        int count;

        WordCount(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }

    static TrieNode root = new TrieNode();

    // Function to insert a word into the trie
    static void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
        current.count++;
    }

    // Function to perform fuzzy search using Levenshtein distance
    static List<String> fuzzySearch(String word, int maxDistance) {
        List<String> results = new ArrayList<>();
        fuzzySearchDFS(root, "", word, maxDistance, results);
        return results;
    }

    static void fuzzySearchDFS(TrieNode node, String currentWord, String targetWord, int maxDistance, List<String> results) {
        if (node == null || currentWord.length() > targetWord.length() + maxDistance)
            return;

        if (node.isEndOfWord && Math.abs(currentWord.length() - targetWord.length()) <= maxDistance)
            results.add(currentWord);

        for (char c : node.children.keySet()) {
            fuzzySearchDFS(node.children.get(c), currentWord + c, targetWord, maxDistance, results);
            fuzzySearchDFS(node.children.get(c), currentWord, targetWord, maxDistance, results);
            fuzzySearchDFS(node.children.get(c), currentWord + c, targetWord.substring(1), maxDistance - 1, results);
        }
    }

    // Function to read the text file and populate the trie
    static void buildTrieFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remove non-alphabetic characters and convert to lowercase
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty())
                        insert(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to get sorted word count
    static List<WordCount> getSortedWordCount() {
        List<WordCount> wordCounts = new ArrayList<>();
        getWordCountDFS(root, "", wordCounts);
        wordCounts.sort(Comparator.comparing((WordCount wc) -> wc.word));
        return wordCounts;
    }

    static void getWordCountDFS(TrieNode node, String currentWord, List<WordCount> wordCounts) {
        if (node == null)
            return;

        // if(node.next == null) wordCounts.add(new WordCount(currentWord, node.count));

        if (node.isEndOfWord)
            wordCounts.add(new WordCount(currentWord, node.count));

        for (char c : node.children.keySet()) {
            getWordCountDFS(node.children.get(c), currentWord + c, wordCounts);
        }
    }

    public static void main(String[] args) {
        String filename = "C:\\Users\\shirin\\OneDrive\\Desktop\\3Frames Task\\TASK 2\\large_text_file.txt"; //Update with file location
        buildTrieFromFile(filename);
    
        // Get sorted word count
        List<WordCount> wordCounts = getSortedWordCount();
        // Collections.sort(wordCounts, Comparator.comparing(wc -> wc.word)); // Sort the word counts alphabetically
        for (WordCount wc : wordCounts) {
            System.out.println(wc.word + ": " + wc.count);
        }
    
        // Fuzzy search example
        List<String> fuzzyResults = fuzzySearch("hello", 1);
        System.out.println("Fuzzy search results for 'hello': " + fuzzyResults);
    }
    
}
