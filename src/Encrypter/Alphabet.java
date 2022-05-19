package Encrypter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alphabet {
    private static final Character[] symbols = {'a', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э',
            'ю', 'я', '.', ',', '"', ':', '-', '!', '?', ' '};

    private List<Character> alphabet = new ArrayList<>();
    private int cryptoKey;

    public Alphabet(int cryptoKey) {
        if (cryptoKey >= 0 && cryptoKey <= symbols.length) {
            this.cryptoKey = cryptoKey;
        } else {
            throw new IllegalArgumentException();
        }
        this.alphabet.addAll(Arrays.asList(symbols));
    }

    @Override
    public String toString() {
        return "Alphabet {" +
                "alphabet=" + alphabet +
                ", cryptoKey=" + cryptoKey +
                '}';
    }

    public Character getEncryptedSymbol(Character a) {
        int symbolTargetPosition = 0;
        int symbolInitialPosition = alphabet.indexOf(a);

        if (!alphabet.contains(a))
            return a;

        if ((symbolInitialPosition + this.cryptoKey) < (symbols.length - 1)) {
            symbolTargetPosition = symbolInitialPosition + cryptoKey;
        } else {
            symbolTargetPosition = symbolInitialPosition + cryptoKey - symbols.length;
        }

        return alphabet.get(symbolTargetPosition);
    }

    public Character getDecryptedSymbol(Character a) {
        int symbolTargetPosition = 0;
        int symbolInitialPosition = alphabet.indexOf(a);

        if (!alphabet.contains(a))
            return a;

        if ((symbolInitialPosition - cryptoKey) >= 0) {
            symbolTargetPosition = symbolInitialPosition - cryptoKey;
        } else {
            symbolTargetPosition = symbolInitialPosition - cryptoKey + symbols.length;
        }

        return alphabet.get(symbolTargetPosition);
    }
}
