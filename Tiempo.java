import java.util.Random;
import java.util.Scanner;

public class BigVigenere {
    int[] key;
    char[][] alphabet;

    public BigVigenere(String numericKey) { // Constructor para clave numérica como String
        this.key = new int[numericKey.length()];
        alphabet = new char[64][64];

        for (int i = 0; i < numericKey.length(); i++) {
            char letra = numericKey.charAt(i);
            this.key[i]= charToInt(letra);
        }

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                alphabet[i][j] = intToChar((i + j) % 64);
            }
        }
    }

    private int charToInt(char letra) {
        if (letra >= 'a' && letra <= 'z') return letra - 'a';
        if (letra >= 'A' && letra <= 'Z') return letra - 'A' + 26;
        if (letra >= '0' && letra <= '9') return letra - '0' + 52;
        if (letra == 'Ñ') return 62;
        if (letra == 'ñ') return 63;
        return -1;
    }

    private char intToChar(int value) {
        if (value >= 0 && value < 26) return (char) ('a' + value);
        if (value >= 26 && value < 52) return (char) ('A' + value - 26);
        if (value >= 52 && value < 62) return (char) ('0' + value - 52);
        if (value == 62) return 'Ñ';
        if (value == 63) return 'ñ';
        return ' ';
    }

    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            int messageValue = charToInt(messageChar);
            int keyValue = key[keyIndex % key.length];
            encryptedMessage.append(alphabet[messageValue][keyValue]);
            keyIndex++;
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String encryptedMessage) {
        StringBuilder decryptedMessage = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char encryptedChar = encryptedMessage.charAt(i);
            int keyValue = key[keyIndex % key.length];

            int decryptedValue = (charToInt(encryptedChar) - keyValue + 64) % 64;
            decryptedMessage.append(intToChar(decryptedValue));
            keyIndex++;
        }
        return decryptedMessage.toString();
    }

    // Genera un mensaje aleatorio de longitud 'length'
    public static String generateRandomMessage(int length) {
        Random random = new Random();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int value = random.nextInt(64);
            message.append(intToCharStatic(value));
        }
        return message.toString();
    }

    // Genera una key aleatoria de longitud 'length'
    public static String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int value = random.nextInt(64);
            key.append(intToCharStatic(value));
        }
        return key.toString();
    }

    // Función estática auxiliar para generación aleatoria
    private static char intToCharStatic(int value) {
        if (value >= 0 && value < 26) return (char) ('a' + value);
        if (value >= 26 && value < 52) return (char) ('A' + value - 26);
        if (value >= 52 && value < 62) return (char) ('0' + value - 52);
        if (value == 62) return 'Ñ';
        if (value == 63) return 'ñ';
        return ' ';
    }

    public static void main(String[] args) {
        String message = generateRandomMessage(10000); // 10,000 caracteres
        System.out.println("Mensaje de prueba generado de longitud: " + message.length());

        int[] keySizes = {5000};

        for (int keySize : keySizes) {
            String key = generateRandomKey(keySize);

            BigVigenere vigenere = new BigVigenere(key);

            // Medir tiempo de cifrado
            long startEncrypt = System.nanoTime();
            String encrypted = vigenere.encrypt(message);
            long endEncrypt = System.nanoTime();
            long encryptTime = (endEncrypt - startEncrypt) / 1_000_000; // a milisegundos

            // Medir tiempo de descifrado
            long startDecrypt = System.nanoTime();
            String decrypted = vigenere.decrypt(encrypted);
            long endDecrypt = System.nanoTime();
            long decryptTime = (endDecrypt - startDecrypt) / 1_000_000; // a milisegundos

            // Verificar que decryption sea correcto
            boolean correct = message.equals(decrypted);

            System.out.println("Key size: " + keySize);
            System.out.println("Encrypt time: " + encryptTime + " ms");
            System.out.println("Decrypt time: " + decryptTime + " ms");
            System.out.println("Correct decryption: " + correct);
            System.out.println("---------------------------");
        }
    }
}
