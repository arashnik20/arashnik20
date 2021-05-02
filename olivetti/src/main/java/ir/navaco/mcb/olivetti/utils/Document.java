package ir.navaco.mcb.olivetti.utils;


import ir.navaco.mcb.olivetti.model.WordParts;
import ir.navaco.mcb.olivetti.model.enums.document.Align;
import ir.navaco.mcb.olivetti.model.enums.document.Direction;
import ir.navaco.mcb.olivetti.model.enums.document.Emulation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ir.navaco.mcb.olivetti.model.dto.CharacterMap.*;
import static org.apache.commons.lang3.ArrayUtils.reverse;


public class Document {
    public Emulation emulation;

    public byte MAX_CHAR_EACH_LINE = 88;

    public final String spaceRegex = "\\s\\n\\f\\r\\t";

    public final String spaceRegexSet = "[" + spaceRegex + "]+";
    public final String sentenceWordsRegex = "[^" + spaceRegex + "]+";

    public final String prepositionRegex = "_\\.\\,\\'\\\"\\;\\:\\|\\?\\>\\<\\،\\؟\\]\\[\\}\\{\\+\\_\\=\\-\\*\\/\\~\\`\\!\\@\\#\\$\\%\\^\\&\\(\\\\)";

    public final String englishNumberRegex = "\\d";
    public final String persianNumberRegex = "\\u0660-\\u0669\\u06F0-\\u06F9";
    public final String numberRegex = persianNumberRegex + englishNumberRegex;

    public final String prepositionSentenceRegex = "[" + prepositionRegex + "]+";
    public final String numberSentenceRegex = "[" + numberRegex + "]+";

    public final String persianRegex = "\\u060C-\\uFEFC";
    public final String persianWordRegex = "[" + persianRegex + "]+";
    public final String persianSentenceRegex = persianWordRegex + "[" + prepositionRegex + persianRegex + spaceRegex + numberRegex + "]+[" + persianRegex + englishNumberRegex + "]+" + "|" + persianWordRegex;
    public final String persianSingleWordRegex = "[" + prepositionRegex + persianRegex + numberRegex + "]+";

    public final String englishRegex = "a-zA-Z";
    public final String englishWordRegex = "[" + englishRegex + "]+";
    public final String englishSentenceRegex = englishWordRegex + "[" + englishRegex + prepositionRegex + spaceRegex + numberRegex + "]+[" + englishRegex + numberRegex + "]+" + "|" + englishWordRegex;

    public final String sentenceRegex = persianSentenceRegex + "|" + englishSentenceRegex + "|" +
            prepositionSentenceRegex + "|" + numberSentenceRegex + "|" +
            spaceRegexSet;

    public final String persianAlphabetsRegex = "[\\u0621-\\u0652\\u067E-\\u06CC\\u200C-\\uFEFC]+[\\W]+|[\\u0621-\\u0652\\u067E-\\u06CC\\u200C-\\uFEFC]+";
    public final String withoutAlphabetsRegex = "[" + numberRegex + prepositionRegex + "]+|" + prepositionSentenceRegex;

    public final String persianWordsRegex = persianAlphabetsRegex + "|" + withoutAlphabetsRegex;

    public final String otherWordsRegex = "[\\w" + prepositionRegex + "]+";

    public Direction direction = Direction.RIGHT_TO_LEFT;
    public Align align = Align.RIGHT;

    public Boolean isFirstLetter(Integer index) {
        return index == 0;
    }

    public Boolean isNoAlphabet(char cIn) {
        return OTHER_LETTERS.get(cIn) != null;
    }

    public Boolean isMiddleLetter(String text, Integer index) {
        return index > 0 && index < text.length() - 1;
    }

    public Boolean isLastLetters(String text, Integer index) {
        return index == text.length() - 1;
    }

    public boolean isPersian(char c) {
        return (c >= 0x0600 && c <= 0x06FF) /*|| (c >= 0xFB50 && c <= 0xFDFF) || (c >= 0xFE70 && c <= 0xFEFF)*/;
    }

    public Boolean isPreposition(String text, Integer index) {//حرف اضافه
        return PREPOSITION.indexOf(text.charAt(index)) > -1;
    }

    public Boolean isTwoFacePreposition(Character character) {
        return TWO_FACE_PREPOSITION.indexOf(character) > -1;
    }

    public Boolean isSeparateLetter(String text, Integer index) {
        if (index < 0) {
            return true;
        }

        char c = text.charAt(index);
        return SEPARATE_LETTERS.indexOf(c) >= 0 || !isPersian(c) || isNoAlphabet(c);
    }

    public Boolean isTwoPhasedMiddleLetters(String text, Integer index) {
        char c = text.charAt(index);
        return TWO_PHASED_MIDDLE_LETTERS.indexOf(c) >= 0;
    }

    public Byte getMiddleLetter(String text, Integer index, char cIn) {
        if (isTwoPhasedMiddleLetters(text, index) && isSeparateLetter(text, index - 1)) {
            return FIRST_LETTERS.get(cIn);
        }
//            else if(isSeparateLetter(text, index + 1) &&
//                    (!isTwoPhasedMiddleLetters(text, index + 1)) &&
//                    (!isSeparateLetter(text, index - 1))){
//                c = LAST_LETTERS.get(cIn);
//            }
        else if (isPreposition(text, index + 1)) {
            return getLastLetter(text, index, cIn);
        } else {
            return MIDDLE_LETTERS.get(cIn);
        }
    }

    public Byte getLastLetter(String text, Integer index, char cIn) {
        if (isSeparateLetter(text, index - 1)) {
            return TWO_PHASED_LAST_LETTERS.get(cIn);
        }

        return LAST_LETTERS.get(cIn);
    }

    public Byte charAt(String text, Integer index) {
        Byte c = null;
        char cIn = text.charAt(index);

        if (isNoAlphabet(cIn)) {

            c = OTHER_LETTERS.get(cIn);

        } else if (isFirstLetter(index) && (text.length() > 1)) {

            c = FIRST_LETTERS.get(cIn);

        } else if (isMiddleLetter(text, index)) {

            c = getMiddleLetter(text, index, cIn);

        } else if (isLastLetters(text, index) || (text.length() == 1)) {

            c = getLastLetter(text, index, cIn);

        }

        return c;
    }

    public List<WordParts> wordPartsList(String word, String regex) {
        List<WordParts> irsysWordList = new ArrayList<>();

        final Pattern pattern = Pattern.compile(regex);
        Matcher matcherRequest = pattern.matcher(word);
        while (matcherRequest.find()) {
            WordParts wordParts = new WordParts(matcherRequest.group(), matcherRequest.start(), matcherRequest.end());
            irsysWordList.add(wordParts);
        }

        return irsysWordList;
    }

    public List<WordParts> sentenceSpaces(String line) {
        return wordPartsList(line, spaceRegexSet);
    }

    public List<WordParts> sentenceWords(String line) {
        return wordPartsList(line, sentenceWordsRegex);
    }

    public List<WordParts> sentence(String word) {
        return wordPartsList(word, sentenceRegex);
    }

    public char faceOff(Character character, Direction sentenceDirection) {
        if (isTwoFacePreposition(character) && Direction.RIGHT_TO_LEFT.equals(sentenceDirection)) {
            return TWO_FACE_PREPOSITION_LETTERS.get(character);
        }

        if (Direction.RIGHT_TO_LEFT.equals(direction) && Pattern.matches(englishNumberRegex, new String(new char[]{character}))) {
            return (char) ((byte) ENGLISH_NUMBER_LETTERS.get(character));
        }

        return character;
    }

    public byte[] unicodeToIrSys(String word, Direction sentenceDirection) {
        byte[] out = new byte[word.length()];

        for (int index = 0; index < word.length(); index++) {
            Byte irSYS = charAt(word, index);
            out[index] = irSYS != null ? irSYS : (byte) faceOff(word.charAt(index), sentenceDirection);
        }

        return out;
    }

    public List<WordParts> concat(List<WordParts>... wordPartsLists) {
        List<WordParts> allWord = new ArrayList<>();

        for (List<WordParts> wordPartsList : wordPartsLists) {
            allWord.addAll(wordPartsList);
        }

        return allWord;
    }

    public byte[] getBytes(Direction direction, List<WordParts>... wordPartsLists) {
        List<WordParts> allWords = concat(wordPartsLists);

        return allWords.size() > 0 ? allWords.stream().sorted(direction.comparator()).map(WordParts::getIrSysWord).reduce(ArrayUtils::addAll).get() : null;
    }

    public List<WordParts> conver(String word, String regex, Direction wordDirection) {
        return wordPartsList(word, regex).stream().map(wordParts -> {

            wordParts.setIrSysWord(converString(wordParts.getWord(), wordDirection));

            return wordParts;
        }).collect(Collectors.toList());
    }

    public byte[] converString(String word) {
        return converString(word, Direction.LEFT_TO_RIGHT);
    }

    public byte[] converString(String word, Direction direction) {
        byte[] out = unicodeToIrSys(word, direction);

        if (Direction.RIGHT_TO_LEFT.equals(direction)) {
            reverse(out);
        }

        return out;
    }

    public List<WordParts> converPersian(String word) {
        return wordPartsList(word, persianWordsRegex).stream().map(wordParts -> {
            byte[] out;
            String selectedWord = wordParts.getWord();
            if (Pattern.matches(persianAlphabetsRegex, selectedWord)) {
                out = converString(selectedWord, Direction.RIGHT_TO_LEFT);
            } else if (Pattern.matches(prepositionSentenceRegex, selectedWord)) {
                out = converString(selectedWord, Direction.RIGHT_TO_LEFT);
            } else {
                out = converString(selectedWord);
            }

            wordParts.setIrSysWord(out);

            return wordParts;
        }).collect(Collectors.toList());
    }

    public byte[] convertPersianWord(String word) {
        List<WordParts> persianWordList = converPersian(word);

        return getBytes(Direction.RIGHT_TO_LEFT, persianWordList);
    }

    public byte[] convertWord(String word, String regex, Direction wordDirection) {
        List<WordParts> wordList = conver(word, regex, wordDirection);

        return getBytes(wordDirection, wordList);
    }

        public List<WordParts> convertWordList(String line, Direction sentenceDirection) {
        return sentenceWords(line).stream().map(wordParts -> {

            String word = wordParts.getWord();
            if (Pattern.matches(prepositionSentenceRegex, word)) {
                wordParts.setIrSysWord(convertWord(wordParts.getWord(), prepositionSentenceRegex, sentenceDirection));
            } else if ((Pattern.matches(persianSingleWordRegex, word))) {
                wordParts.setIrSysWord(convertPersianWord(wordParts.getWord()));
            } else {
                wordParts.setIrSysWord(convertWord(wordParts.getWord(), otherWordsRegex, sentenceDirection));
            }

            return wordParts;

        }).collect(Collectors.toList());
    }

    public List<WordParts> convertSpaceList(String line) {
        return sentenceSpaces(line).stream().map(wordParts -> {
            byte[] out = unicodeToIrSys(wordParts.getWord(), direction);

            wordParts.setIrSysWord(out);

            return wordParts;

        }).collect(Collectors.toList());
    }

    public byte[] convertSentence(String sentence, Direction wordDirection) {
        List<WordParts> wordList = convertWordList(sentence, wordDirection);
        List<WordParts> spaceList = convertSpaceList(sentence);

        return getBytes(wordDirection, wordList, spaceList);
    }

    public List<WordParts> convertSentence(String line) {
        return sentence(line).stream().map(wordParts -> {

            String word = wordParts.getWord();
            if (Pattern.matches(persianSentenceRegex, word)) {
                wordParts.setIrSysWord(convertSentence(word, Direction.RIGHT_TO_LEFT));
            } else if (Pattern.matches(prepositionSentenceRegex, word)) {
                wordParts.setIrSysWord(convertSentence(word, direction));

            } else {
                wordParts.setIrSysWord(convertSentence(word, Direction.LEFT_TO_RIGHT));
            }

            return wordParts;

        }).collect(Collectors.toList());
    }

    public byte[] makeLines(byte[] bytes) throws IOException {
        if (bytes != null &&  Emulation.EpsonTMU590.equals(emulation)) {
            if (Direction.RIGHT_TO_LEFT.equals(direction)) {
                if (bytes.length > MAX_CHAR_EACH_LINE) {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    int count = (int) Math.ceil(bytes.length / MAX_CHAR_EACH_LINE);

                    int end = bytes.length;
                    for (int i = 0; i <= count; i++) {
                        int begin = end - MAX_CHAR_EACH_LINE;
                        begin = begin < 0 ? 0 : begin;

                        byte[] subarray = ArrayUtils.subarray(bytes, begin, end);
                        output.write(subarray);

                        end = begin;
                    }

                    return output.toByteArray();
                }
            }
        }

        return bytes;
    }

    public String alignLines(String line) {
        int len = Align.RIGHT.equals(align) ? MAX_CHAR_EACH_LINE : (MAX_CHAR_EACH_LINE / 2) + (line.length() / 2);

        if (Emulation.Pr4SL.equals(emulation)) {
            if (Align.LEFT.equals(align)) {
                return line;
            }

            return StringUtils.rightPad(line, len, " ");
        }

        return line;
    }

    public static Document initialize(Emulation emulation) {
        Document document = new Document();
        document.emulation = emulation;

        document.MAX_CHAR_EACH_LINE = Emulation.Pr4SL.equals(emulation) ? (byte) 95 : (byte) 88;

        return document;
    }

    public Document direction(String direction) {
        this.direction = Direction.valueOf(direction);
        return this;
    }

    public Document direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public Document align(String align) {
        this.align = Align.valueOf(align);
        return this;
    }

    public Document align(Align align) {
        this.align = align;
        return this;
    }

    public byte[] getBytes(String line) throws IOException {
        List<WordParts> lines = convertSentence(alignLines(line));

        return makeLines(getBytes(direction, lines));
    }

    public static byte[] stringToBytesASCII(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) str.charAt(i);
        }
        return b;
    }
}
