package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Day7 extends Day {

    public static void main(String[] args) throws IOException {
        Day7 day = new Day7();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;

        List<Hand> hands = new ArrayList<>();
        for (String s : list) {
            String[] hand = s.split(" ");
            hands.add(new Hand(hand[0], hand[1]));
        }

        Collections.sort(hands);
        for (int i = 0; i < hands.size(); ++i) {
            sum += (i+1)*hands.get(i).bid;
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {

        long sum = 0;
        List<Hand> hands = new ArrayList<>();
        for (String s : list) {
            String[] hand = s.split(" ");
            hands.add(new Hand(hand[0].replaceAll("J", "j"), hand[1]));
        }

        Collections.sort(hands);
        for (int i = 0; i < hands.size(); ++i) {
            sum += (i+1)*hands.get(i).bid;
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    public class Hand implements Comparable<Hand> {
        private List<Integer> cards;
        private int bid;
        private int strength;

        public Hand(String cards, String bid) {
            this.bid = Integer.parseInt(bid);
            this.cards = new ArrayList<>();
            for (char label : cards.toCharArray()) {
                this.cards.add(getCardWorth(label));
            }
            calculateHandStrength(this.cards);
        }

        List<Integer> getCards() {
            return this.cards;
        }

        int getBit() {
            return this.bid;
        }

        int getStrength() {
            return this.strength;
        }

        private void calculateHandStrength(List<Integer> cards) {
            int[] freq = new int[15];
            int jokers = 0;
            for (int i = 0; i < cards.size(); ++i) {
                if (cards.get(i) > 0) {
                    ++freq[cards.get(i)];
                } else ++jokers;
            }

            Arrays.sort(freq);
            int numOfPairs = 0;
            boolean threeSame = false;
            for (int i = freq.length - 1; i >= 0; --i) {
                if (freq[i] + jokers == 5) {
                    strength = 7;
                    break;
                } else if (freq[i] + jokers == 4) {
                    strength = 6;
                    break;
                } else if (freq[i] + jokers == 3) {
                    if (jokers > 0) jokers -= (3 - freq[i]);
                    threeSame = true;
                } else if (freq[i] + jokers == 2) {
                    ++numOfPairs;
                    if (jokers > 0) --jokers;
                }
            }

            if (strength == 0) {
                if (threeSame && numOfPairs > 0) {
                    strength = 5;
                } else if (threeSame) {
                    strength = 4;
                } else if (numOfPairs > 1) {
                    strength = 3;
                } else if (numOfPairs > 0) {
                    strength = 2;
                } else strength = 1;
            }

        }

        private int getCardWorth(char label) {
            if (label == 'A') {
                return 14;
            } else if (label == 'K') {
                return 13;
            } else if (label == 'Q') {
                return 12;
            } else if (label == 'J') {
                return 11;
            } else if (label == 'T') {
                return 10;
            } else if (label == 'j') {
                return 0;
            } else {
                return label - 48;
            }
        }

        @Override
        public int compareTo(Hand second) {
            if (this.strength != second.getStrength()) {
                return this.getStrength() - second.getStrength();
            } else {
                for (int i = 0; i < Math.min(this.getCards().size(), second.getCards().size()); ++i) {
                    if (this.getCards().get(i) != second.getCards().get(i)) {
                        return this.getCards().get(i) - second.getCards().get(i);
                    }
                }
                return this.cards.size() - second.getCards().size();
            }
        }

    }
}