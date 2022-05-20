package Encrypter;

public class Text {
    String initialText;
    Alphabet alphabet;
    String targetText;

    public Text(String initialText) {
        this.initialText = initialText;
    }
    public Text(String initialText, int cryptoKey) {
        this.initialText = initialText;
        this.alphabet = new Alphabet(cryptoKey);
    }

    public String getInitialText() {
        return initialText;
    }

    public String getTargetText() {
        return targetText;
    }

    public String encryptText() {
        StringBuilder result = new StringBuilder();

        for (char a : this.initialText.toCharArray()) {
            result.append(this.alphabet.encryptSymbol(a));
        }

        this.targetText = result.toString();

        return this.targetText;
    }

    public String decryptText() {
        StringBuilder result = new StringBuilder();

        for (char a : this.initialText.toCharArray()) {
            result.append(this.alphabet.decryptSymbol(a));
        }

        this.targetText = result.toString();

        return this.targetText;
    }

    public void determineCryptoKey() {
        if (this.alphabet == null)
            this.alphabet = new Alphabet(0);

        double minShareOfSpaces = 0.1;
        double maxShareOfSpaces = 0.25;

        for (int i = 0; i < this.alphabet.getAlphabet().size(); i++) {
            this.alphabet.setCryptoKey(i);
            this.targetText = this.decryptText();
            if (this.determineTextPattern() > minShareOfSpaces && this.determineTextPattern() < maxShareOfSpaces)
                break;
        }
    }

    private double determineTextPattern() {
        int numberOfSpaces = 0;

        for (char a : this.targetText.toCharArray()) {
            if (a == ' ') numberOfSpaces++;
        }

        return (double) numberOfSpaces / this.targetText.length();
    }
}
