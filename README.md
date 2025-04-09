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
