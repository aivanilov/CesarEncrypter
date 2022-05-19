package Encrypter;

public class EncryptedText {
    String initialText;
    Alphabet alphabet;

    public EncryptedText(String initialText, int cryptoKey) {
        this.initialText = initialText;
        this.alphabet = new Alphabet(cryptoKey);
    }

    public String getEncryptedText() {
        StringBuilder result = new StringBuilder();

        for (char a : this.initialText.toCharArray()) {
            result.append(this.alphabet.getEncryptedSymbol(a));
        }

        return result.toString();
    }

    public String getDecryptedText() {
        StringBuilder result = new StringBuilder();

        for (char a : this.initialText.toCharArray()) {
            result.append(this.alphabet.getDecryptedSymbol(a));
        }

        return result.toString();
    }
}
