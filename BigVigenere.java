import java.util.Scanner;

public class BigVigenere {
    int[] key;
    char[][] alphabet;

    public BigVigenere() {
        this.key = new int[0];
        alphabet = new char[64][64];

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese una key: ");
        String userkey = scanner.nextLine();
        System.out.println("La llaver es: " + userkey);

        this.key = new int[userkey.length()];

        for (int i = 0; i < userkey.length(); i++) {
            char letra = userkey.charAt(i);
            this.key[i] = charToInt(letra);
        }
        for (int i= 0; i < 64;i++) {
            for(int j=0;j < 64;j++) {
                alphabet[i][j] = intToChar((i + j) % 64);
            }
        }

        imprimirAlphabet(); //  Porque lo imprimo, Solo lo imprimo para verificar si la matriz se construyo bien
    }
        public BigVigenere(String numericKey) { // clave numerica como string
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
            imprimirAlphabet();
    }
    private int charToInt(char letra) {
        if(letra >='a' && letra <= 'z') return letra - 'a';
        if(letra >= 'A' && letra <= 'Z') return letra - 'A' + 26;
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
        return ' '; // Caracter no válido
    }
    public void imprimirAlphabet() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                System.out.print(alphabet[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String encrypt(String message) {
        String encryptedMessage = "";
        int keyIndex = 0;

        for(int i =0;i < message.length(); i++) {
            char messageChar = message.charAt(i);

            int keyValue = key[keyIndex % key.length];
            int messageValue = charToInt(messageChar); // convertir letra a numero 0 -> 63

            char encryptedChar = alphabet[messageValue][keyValue];

            encryptedMessage += encryptedChar;

            keyIndex++;
        }
        return encryptedMessage;
    }
    public String decrypt(String encryptedMessage) {
        String decryptedMessage = "";
        int keyIndex = 0;

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char encryptedChar = encryptedMessage.charAt(i);

            int KeyValue = key[keyIndex % key.length];
            int encryptedValue = charToInt(encryptedChar);

            int decryptedValue = (encryptedValue - KeyValue + 64) % 64;
            char decryptedChar = intToChar(decryptedValue);

            decryptedMessage += decryptedChar;
            keyIndex++;
    }
    return decryptedMessage;
}
     public void reEncrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa el mensaje encriptado: ");
        String encryptedMessage = scanner.nextLine();

        String decryptedMessage = decrypt(encryptedMessage);
        System.out.println("Mensaje descifrado con la clave actual: " + decryptedMessage);
        System.out.println("Ingresa la nueva clave: ");
        String nuevaKey = scanner.nextLine();

        this.key = new int[nuevaKey.length()];

        for (int i = 0; i < nuevaKey.length(); i++){
            char letra = nuevaKey.charAt(i);
            this.key[i] = charToInt(letra);
        }

        String newEncryptedMessage = encrypt(decryptedMessage);
        System.out.println("Nuevo mensaje cifrado: " + newEncryptedMessage);
     }

     public char search(int position) {
        int fila = position/64;
        int col = position % 64;
        return alphabet[fila][col];
     }

       public char optimalSearch(int position) {
        return alphabet[position / 64][position % 64];
    }

     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         BigVigenere cipher = new BigVigenere();

         boolean continuar = true;

         while(continuar) {
            System.out.println("Ingresa mensaje a cifrar: ");
            String mensaje = scanner.nextLine();

            String mensajeCifrado = cipher.encrypt(mensaje);
            System.out.println("Mensaje cifrado: " + mensajeCifrado);

            String mensajeDescifrado = cipher.decrypt(mensajeCifrado);
            System.out.println("Mensaje descifrado: " + mensajeDescifrado);

            System.out.println("¿Desea cambiar la clave y reencriptar el mensaje? (si/no)");
            String respuesta = scanner.nextLine();

            if(respuesta.equals("SI") || respuesta.equals("s")) {
                cipher.reEncrypt();
            }else{
                continuar = false;
                System.out.println("Programa Finalizado");
            }
         }
     }

}
