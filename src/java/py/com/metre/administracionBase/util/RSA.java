package py.com.metre.administracionBase.util;

import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Adolfo
 */
public class RSA {

    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;
    
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
        generarPrimos(); 
        generarClaves();
    }

    public RSA(int tamPrimo, BigInteger d, BigInteger n){
        this.tamPrimo = tamPrimo;
        this.d = d;
        this.n = n;
    }

    /*
     * Genera los valores de p y q
     */
    private void generarPrimos()
    {
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0); // P y Q deben tener valores diferentes
    }

    /*
     * Genera los valores de e y d
     */
    private void generarClaves()
    {
        // n = p * q
        n = p.multiply(q);
        // totient = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        // e = coprimo de y menor que n
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(totient) != -1) ||
		 (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
        // d = e^1 mod totient
        d = e.modInverse(totient);
    }

    /**
     * Encripta mensaje usando la clave pública
     *
     * @param    mensaje   Texto a ser encriptado
     * @return   El mensaje encriptado como un array de BigIntegers
     */
    public BigInteger[] encriptar(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }

        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);

        return(encriptado);
    }

    /**
     * Desencripta el texto cifrado usando la clave privada
     *
     * @param   encriptado       Array de BigInteger con el texto que se
     *                           busca desencriptar
     * @return  El texto desencriptado
     */
    public String desencriptar(BigInteger[] encriptado) {
        BigInteger[] desencriptado = new BigInteger[encriptado.length];

        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(d,n);

        char[] charArray = new char[desencriptado.length];

        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());

        return(new String(charArray));
    }

    public BigInteger getP() {return(p);}
    public BigInteger getQ() {return(q);}
    public BigInteger getTotient() {return(totient);}
    public BigInteger getN() {return(n);}
    public BigInteger getE() {return(e);}
    public BigInteger getD() {return(d);}
}