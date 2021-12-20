package org.bouncycastle.jce.provider;

import java.security.Provider;

import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;

/**
 * To add the provider at runtime use:
 * <pre>
 * import java.security.Security;
 * import org.bouncycastle.jce.provider.BouncyCastleProvider;
 *
 * Security.addProvider(new BouncyCastleProvider());
 * </pre>
 * The provider can also be configured as part of your environment via
 * static registration by adding an entry to the java.security properties
 * file (found in .JAVA_HOME/jre/lib/security/java.security, where
 * .JAVA_HOME is the location of your JDK/JRE distribution). You'll find
 * detailed instructions in the file but basically it comes down to adding
 * a line:
 * <pre>
 * <code>
 *    security.provider.&lt;n&gt;=org.bouncycastle.jce.provider.BouncyCastleProvider
 * </code>
 * </pre>
 * Where &lt;n&gt; is the preference you want the provider at (1 being the
 * most prefered).
 * <p>Note: JCE algorithm names should be uppercase only so the case insensitive
 * test for getInstance works.
 */
public final class BouncyCastleProvider extends Provider
{
    private static final String info = "BouncyCastle Security Provider v1.32";

    public static String PROVIDER_NAME = "BC";

    /**
     * Construct a new provider.  This should only be required when
     * using runtime registration of the provider using the
     * <code>Security.addProvider()</code> mechanism.
     */
    public BouncyCastleProvider()
    {
        super(PROVIDER_NAME, 1.32, info);

        //
        // KeyStore
        //
        put("KeyStore.BKS", JDKKeyStore.class.getName());
        put("KeyStore.BouncyCastle", JDKKeyStore.BouncyCastleStore.class.getName());
        put("KeyStore.PKCS12", JDKPKCS12KeyStore.BCPKCS12KeyStore.class.getName());
        put("KeyStore.BCPKCS12", JDKPKCS12KeyStore.BCPKCS12KeyStore.class.getName());
        put("KeyStore.PKCS12-DEF", JDKPKCS12KeyStore.DefPKCS12KeyStore.class.getName());
        put("Alg.Alias.KeyStore.UBER", "BouncyCastle");
        put("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
        put("Alg.Alias.KeyStore.bouncycastle", "BouncyCastle");

        //
        // certificate factories.
        //
        put("CertificateFactory.X.509", JDKX509CertificateFactory.class.getName());
        put("Alg.Alias.CertificateFactory.X509", "X.509");

        //
        // algorithm parameter generators
        //
        put("AlgorithmParameterGenerator.DH", JDKAlgorithmParameterGenerator.DH.class.getName());
        put("AlgorithmParameterGenerator.DSA", JDKAlgorithmParameterGenerator.DSA.class.getName());
        put("AlgorithmParameterGenerator.GOST3410", JDKAlgorithmParameterGenerator.GOST3410.class.getName());
        put("AlgorithmParameterGenerator.ELGAMAL", JDKAlgorithmParameterGenerator.ElGamal.class.getName());
        put("AlgorithmParameterGenerator.DES", JDKAlgorithmParameterGenerator.DES.class.getName());
        put("AlgorithmParameterGenerator.DESEDE", JDKAlgorithmParameterGenerator.DES.class.getName());
        put("AlgorithmParameterGenerator.1.2.840.113549.3.7", JDKAlgorithmParameterGenerator.DES.class.getName());
        put("AlgorithmParameterGenerator.1.3.14.3.2.7", JDKAlgorithmParameterGenerator.DES.class.getName());
        put("AlgorithmParameterGenerator.IDEA", JDKAlgorithmParameterGenerator.IDEA.class.getName());
        put("AlgorithmParameterGenerator.1.3.6.1.4.1.188.7.1.1.2", JDKAlgorithmParameterGenerator.IDEA.class.getName());
        put("AlgorithmParameterGenerator.RC2", JDKAlgorithmParameterGenerator.RC2.class.getName());
        put("AlgorithmParameterGenerator.1.2.840.113549.3.2", JDKAlgorithmParameterGenerator.RC2.class.getName());
        put("AlgorithmParameterGenerator.CAST5", JDKAlgorithmParameterGenerator.CAST5.class.getName());
        put("AlgorithmParameterGenerator.1.2.840.113533.7.66.10", JDKAlgorithmParameterGenerator.CAST5.class.getName());
        put("AlgorithmParameterGenerator.AES", JDKAlgorithmParameterGenerator.AES.class.getName());
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.2", "AES");  // these first 3 are wrong, but seem to have got around
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.22", "AES");
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.42", "AES");
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.1.2", "AES");
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.1.22", "AES");
        put("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.1.42", "AES");
        put("Alg.Alias.AlgorithmParameterGenerator.GOST-3410", "GOST3410");
        //
        // algorithm parameters
        //
        put("AlgorithmParameters.OAEP", JDKAlgorithmParameters.OAEP.class.getName());
        put("AlgorithmParameters.PSS", JDKAlgorithmParameters.PSS.class.getName());
        put("AlgorithmParameters.DH", JDKAlgorithmParameters.DH.class.getName());
        put("AlgorithmParameters.DSA", JDKAlgorithmParameters.DSA.class.getName());
        put("AlgorithmParameters.ELGAMAL", JDKAlgorithmParameters.ElGamal.class.getName());
        put("AlgorithmParameters.IES", JDKAlgorithmParameters.IES.class.getName());
        put("AlgorithmParameters.PKCS12PBE", JDKAlgorithmParameters.PKCS12PBE.class.getName());
        put("AlgorithmParameters.1.2.840.113549.3.7", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.IDEA", JDKAlgorithmParameters.IDEAAlgorithmParameters.class.getName());
        put("AlgorithmParameters.1.3.6.1.4.1.188.7.1.1.2", JDKAlgorithmParameters.IDEAAlgorithmParameters.class.getName());
        put("AlgorithmParameters.CAST5", JDKAlgorithmParameters.CAST5AlgorithmParameters.class.getName());
        put("AlgorithmParameters.1.2.840.113533.7.66.10", JDKAlgorithmParameters.CAST5AlgorithmParameters.class.getName());
        put("AlgorithmParameters.GOST3410", JDKAlgorithmParameters.GOST3410.class.getName());
        put("Alg.Alias.AlgorithmParameters.GOST-3410", "GOST3410");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC2", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC2-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC2-CBC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
        //put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
        //put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.1", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.2", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.5", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.6", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWithSHAAnd3KeyTripleDES", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.id_RSAES_OAEP, "OAEP");

        put("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.id_RSASSA_PSS, "PSS");
        put("Alg.Alias.AlgorithmParameters.SHA1WITHRSAANDMGF1", "PSS");
        put("Alg.Alias.AlgorithmParameters.SHA224WITHRSAANDMGF1", "PSS");
        put("Alg.Alias.AlgorithmParameters.SHA256WITHRSAANDMGF1", "PSS");
        put("Alg.Alias.AlgorithmParameters.SHA384WITHRSAANDMGF1", "PSS");
        put("Alg.Alias.AlgorithmParameters.SHA512WITHRSAANDMGF1", "PSS");

        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC","PKCS12PBE");
        put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC","PKCS12PBE");

        //
        // key agreement
        //
        put("KeyAgreement.DH", JCEDHKeyAgreement.class.getName());
        put("KeyAgreement.ECDH", JCEECDHKeyAgreement.DH.class.getName());
        put("KeyAgreement.ECDHC", JCEECDHKeyAgreement.DHC.class.getName());

        //
        // cipher engines
        //
        put("Cipher.DES", JCEBlockCipher.DES.class.getName());
        put("Cipher.DESEDE", JCEBlockCipher.DESede.class.getName());
        put("Cipher.1.2.840.113549.3.7", JCEBlockCipher.DESedeCBC.class.getName());
        put("Cipher.1.3.14.3.2.7", JCEBlockCipher.DESCBC.class.getName());
        put("Cipher.DESEDEWRAP", WrapCipherSpi.DESEDEWrap.class.getName());
        put("Cipher.1.2.840.113549.1.9.16.3.6", WrapCipherSpi.DESEDEWrap.class.getName());
        put("Cipher.SKIPJACK", JCEBlockCipher.Skipjack.class.getName());
        put("Cipher.BLOWFISH", JCEBlockCipher.Blowfish.class.getName());
        put("Cipher.TWOFISH", JCEBlockCipher.Twofish.class.getName());
        put("Cipher.RC2", JCEBlockCipher.RC2.class.getName());
        put("Cipher.RC2WRAP", WrapCipherSpi.RC2Wrap.class.getName());
        put("Cipher.1.2.840.113549.1.9.16.3.7", WrapCipherSpi.RC2Wrap.class.getName());
        put("Cipher.ARC4", JCEStreamCipher.RC4.class.getName());
        put("Alg.Alias.Cipher.1.2.840.113549.3.4", "ARC4");
        put("Alg.Alias.Cipher.ARCFOUR", "ARC4");
        put("Alg.Alias.Cipher.RC4", "ARC4");
        put("Cipher.RC5", JCEBlockCipher.RC5.class.getName());
        put("Cipher.1.2.840.113549.3.2", JCEBlockCipher.RC2CBC.class.getName());
        put("Alg.Alias.Cipher.RC5-32", "RC5");
        put("Cipher.RC5-64", JCEBlockCipher.RC564.class.getName());
        put("Cipher.RC6", JCEBlockCipher.RC6.class.getName());
        put("Cipher.RIJNDAEL", JCEBlockCipher.Rijndael.class.getName());
        put("Cipher.AES", JCEBlockCipher.AES.class.getName());
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.2", "AES");
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.22", "AES");
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.42", "AES");
        put("Cipher." + NISTObjectIdentifiers.id_aes128_ECB, JCEBlockCipher.AES.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes192_ECB, JCEBlockCipher.AES.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes256_ECB, JCEBlockCipher.AES.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes128_CBC, JCEBlockCipher.AESCBC.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes192_CBC, JCEBlockCipher.AESCBC.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes256_CBC, JCEBlockCipher.AESCBC.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes128_OFB, JCEBlockCipher.AESOFB.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes192_OFB, JCEBlockCipher.AESOFB.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes256_OFB, JCEBlockCipher.AESOFB.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes128_CFB, JCEBlockCipher.AESCFB.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes192_CFB, JCEBlockCipher.AESCFB.class.getName());
        put("Cipher." + NISTObjectIdentifiers.id_aes256_CFB, JCEBlockCipher.AESCFB.class.getName());
        put("Cipher.AESWRAP", WrapCipherSpi.AESWrap.class.getName());
        put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes128_wrap, "AESWRAP");
        put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes192_wrap, "AESWRAP");
        put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes256_wrap, "AESWRAP");

        put("Cipher.SERPENT", JCEBlockCipher.Serpent.class.getName());
        put("Cipher.CAMELLIA", JCEBlockCipher.Camellia.class.getName());
        put("Cipher.CAST5", JCEBlockCipher.CAST5.class.getName());
        put("Cipher.1.2.840.113533.7.66.10", JCEBlockCipher.CAST5CBC.class.getName());
        put("Cipher.CAST6", JCEBlockCipher.CAST6.class.getName());
        put("Cipher.IDEA", JCEBlockCipher.IDEA.class.getName());
        put("Cipher.1.3.6.1.4.1.188.7.1.1.2", JCEBlockCipher.IDEACBC.class.getName());
        put("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES",  "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");

        put("Cipher.GOST28147", JCEBlockCipher.GOST28147.class.getName());
        put("Alg.Alias.Cipher.GOST", "GOST28147");
        put("Alg.Alias.Cipher.GOST-28147", "GOST28147");

        put("Cipher." + CryptoProObjectIdentifiers.gostR28147_cbc, JCEBlockCipher.GOST28147cbc.class.getName());

/*
        put("Cipher.DES/CFB8", JCEStreamCipher.DES_CFB8.class.getName());
        put("Cipher.DESEDE/CFB8", JCEStreamCipher.DESede_CFB8.class.getName());
        put("Cipher.SKIPJACK/CFB8", JCEStreamCipher.Skipjack_CFB8.class.getName());
        put("Cipher.BLOWFISH/CFB8", JCEStreamCipher.Blowfish_CFB8.class.getName());
        put("Cipher.TWOFISH/CFB8", JCEStreamCipher.Twofish_CFB8.class.getName());
        put("Cipher.IDEA/CFB8", JCEStreamCipher.IDEA_CFB8.class.getName());

        put("Alg.Alias.Cipher.DES/CFB8/NOPADDING", "DES/CFB8");
        put("Alg.Alias.Cipher.DESEDE/CFB8/NOPADDING", "DESEDE/CFB8");
        put("Alg.Alias.Cipher.SKIPJACK/CFB8/NOPADDING", "SKIPJACK/CFB8");
        put("Alg.Alias.Cipher.BLOWFISH/CFB8/NOPADDING", "Blowfish/CFB8");
        put("Alg.Alias.Cipher.TWOFISH/CFB8/NOPADDING", "Twofish/CFB8");
        put("Alg.Alias.Cipher.IDEA/CFB8/NOPADDING", "IDEA/CFB8");

        put("Cipher.DES/OFB8", JCEStreamCipher.DES_OFB8.class.getName());
        put("Cipher.DESEDE/OFB8", JCEStreamCipher.DESede_OFB8.class.getName());
        put("Cipher.SKIPJACK/OFB8", JCEStreamCipher.Skipjack_OFB8.class.getName());
        put("Cipher.BLOWFISH/OFB8", JCEStreamCipher.Blowfish_OFB8.class.getName());
        put("Cipher.TWOFISH/OFB8", JCEStreamCipher.Twofish_OFB8.class.getName());
        put("Cipher.IDEA/OFB8", JCEStreamCipher.IDEA_OFB8.class.getName());

        put("Alg.Alias.Cipher.DES/OFB8/NOPADDING", "DES/OFB8");
        put("Alg.Alias.Cipher.DESEDE/OFB8/NOPADDING", "DESEDE/OFB8");
        put("Alg.Alias.Cipher.SKIPJACK/OFB8/NOPADDING", "SKIPJACK/OFB8");
        put("Alg.Alias.Cipher.BLOWFISH/OFB8/NOPADDING", "BLOWFISH/OFB8");
        put("Alg.Alias.Cipher.TWOFISH/OFB8/NOPADDING", "TWOFISH/OFB8");
        put("Alg.Alias.Cipher.IDEA/OFB8/NOPADDING", "IDEA/OFB8");
*/

        put("Cipher.RSA", JCERSACipher.NoPadding.class.getName());
        put("Cipher.RSA/RAW", JCERSACipher.NoPadding.class.getName());
        put("Cipher.RSA/PKCS1", JCERSACipher.PKCS1v1_5Padding.class.getName());
        put("Cipher.1.2.840.113549.1.1.1", JCERSACipher.PKCS1v1_5Padding.class.getName());
        put("Cipher.2.5.8.1.1", JCERSACipher.PKCS1v1_5Padding.class.getName());
        put("Cipher.RSA/1", JCERSACipher.PKCS1v1_5Padding_PrivateOnly.class.getName());
        put("Cipher.RSA/2", JCERSACipher.PKCS1v1_5Padding_PublicOnly.class.getName());
        put("Cipher.RSA/OAEP", JCERSACipher.OAEPPadding.class.getName());
        put("Cipher.1.2.840.113549.1.1.7", JCERSACipher.OAEPPadding.class.getName());
        put("Cipher.RSA/ISO9796-1", JCERSACipher.ISO9796d1Padding.class.getName());

        put("Cipher.ECIES", JCEIESCipher.ECIES.class.getName());
        put("Cipher.IES", JCEIESCipher.IES.class.getName());
        put("Cipher.ELGAMAL", JCEElGamalCipher.NoPadding.class.getName());
        put("Cipher.ELGAMAL/PKCS1", JCEElGamalCipher.PKCS1v1_5Padding.class.getName());

        put("Alg.Alias.Cipher.RSA//RAW", "RSA");
        put("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
        put("Alg.Alias.Cipher.RSA//PKCS1PADDING", "RSA/PKCS1");
        put("Alg.Alias.Cipher.RSA//OAEPPADDING", "RSA/OAEP");
        put("Alg.Alias.Cipher.RSA//ISO9796-1PADDING", "RSA/ISO9796-1");

        put("Alg.Alias.Cipher.ELGAMAL/ECB/PKCS1PADDING", "ELGAMAL/PKCS1");
        put("Alg.Alias.Cipher.ELGAMAL/NONE/PKCS1PADDING", "ELGAMAL/PKCS1");
        put("Alg.Alias.Cipher.ELGAMAL/NONE/NOPADDING", "ELGAMAL");

        put("Cipher.PBEWITHMD5ANDDES", JCEBlockCipher.PBEWithMD5AndDES.class.getName());
        put("Cipher.BROKENPBEWITHMD5ANDDES", BrokenJCEBlockCipher.BrokePBEWithMD5AndDES.class.getName());
        put("Cipher.PBEWITHMD5ANDRC2", JCEBlockCipher.PBEWithMD5AndRC2.class.getName());
        put("Cipher.PBEWITHSHA1ANDDES", JCEBlockCipher.PBEWithSHA1AndDES.class.getName());
        put("Cipher.BROKENPBEWITHSHA1ANDDES", BrokenJCEBlockCipher.BrokePBEWithSHA1AndDES.class.getName());
        put("Cipher.PBEWITHSHA1ANDRC2", JCEBlockCipher.PBEWithSHA1AndRC2.class.getName());
        put("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", JCEBlockCipher.PBEWithSHAAndDES3Key.class.getName());
        put("Cipher.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", BrokenJCEBlockCipher.BrokePBEWithSHAAndDES3Key.class.getName());
        put("Cipher.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", BrokenJCEBlockCipher.OldPBEWithSHAAndDES3Key.class.getName());
        put("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", JCEBlockCipher.PBEWithSHAAndDES2Key.class.getName());
        put("Cipher.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", BrokenJCEBlockCipher.BrokePBEWithSHAAndDES2Key.class.getName());
        put("Cipher.PBEWITHSHAAND128BITRC2-CBC", JCEBlockCipher.PBEWithSHAAnd128BitRC2.class.getName());
        put("Cipher.PBEWITHSHAAND40BITRC2-CBC", JCEBlockCipher.PBEWithSHAAnd40BitRC2.class.getName());
        put("Cipher.PBEWITHSHAAND128BITRC4", JCEStreamCipher.PBEWithSHAAnd128BitRC4.class.getName());
        put("Cipher.PBEWITHSHAAND40BITRC4", JCEStreamCipher.PBEWithSHAAnd40BitRC4.class.getName());

        put("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());

        put("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC2-CBC", "Cipher.PBEWITHSHAAND128BITRC2-CBC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC2-CBC", "Cipher.PBEWITHSHAAND40BITRC2-CBC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "Cipher.PBEWITHSHAAND128BITRC4");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "Cipher.PBEWITHSHAAND40BITRC4");

        //put("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        //put("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        //put("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC","PBEWITHSHAAND128BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC","PBEWITHSHAAND192BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC","PBEWITHSHAAND256BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC","PBEWITHSHAAND128BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC","PBEWITHSHAAND192BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC","PBEWITHSHAAND256BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC","PBEWITHSHA256AND128BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC","PBEWITHSHA256AND192BITAES-CBC-BC");
        put("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC","PBEWITHSHA256AND256BITAES-CBC-BC");

        put("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", JCEBlockCipher.PBEWithAESCBC.class.getName());
        put("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", JCEBlockCipher.PBEWithAESCBC.class.getName());

        put("Cipher.PBEWITHSHAANDTWOFISH-CBC", JCEBlockCipher.PBEWithSHAAndTwofish.class.getName());
        put("Cipher.OLDPBEWITHSHAANDTWOFISH-CBC", BrokenJCEBlockCipher.OldPBEWithSHAAndTwofish.class.getName());
        put("Cipher.PBEWITHSHAANDIDEA-CBC", JCEBlockCipher.PBEWithSHAAndIDEA.class.getName());

        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.1", "PBEWITHSHAAND128BITRC4");
        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.2", "PBEWITHSHAAND40BITRC4");
        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.5", "PBEWITHSHAAND128BITRC2-CBC");
        put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.6", "PBEWITHSHAAND40BITRC2-CBC");
        put("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        //
        // key generators.
        //
        put("KeyGenerator.DES", JCEKeyGenerator.DES.class.getName());
        put("Alg.Alias.KeyGenerator.1.3.14.3.2.7", "DES");
        put("KeyGenerator.DESEDE", JCEKeyGenerator.DESede.class.getName());
        put("KeyGenerator.1.2.840.113549.3.7", JCEKeyGenerator.DESede3.class.getName());
        put("KeyGenerator.DESEDEWRAP", JCEKeyGenerator.DESede.class.getName());
        put("KeyGenerator.SKIPJACK", JCEKeyGenerator.Skipjack.class.getName());
        put("KeyGenerator.BLOWFISH", JCEKeyGenerator.Blowfish.class.getName());
        put("KeyGenerator.TWOFISH", JCEKeyGenerator.Twofish.class.getName());
        put("KeyGenerator.RC2", JCEKeyGenerator.RC2.class.getName());
        put("KeyGenerator.1.2.840.113549.3.2", JCEKeyGenerator.RC2.class.getName());
        put("KeyGenerator.RC4", JCEKeyGenerator.RC4.class.getName());
        put("Alg.Alias.KeyGenerator.ARC4", "RC4");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "RC4");
        put("KeyGenerator.RC5", JCEKeyGenerator.RC5.class.getName());
        put("Alg.Alias.KeyGenerator.RC5-32", "RC5");
        put("KeyGenerator.RC5-64", JCEKeyGenerator.RC564.class.getName());
        put("KeyGenerator.RC6", JCEKeyGenerator.RC6.class.getName());
        put("KeyGenerator.RIJNDAEL", JCEKeyGenerator.Rijndael.class.getName());
        put("KeyGenerator.AES", JCEKeyGenerator.AES.class.getName());
        put("KeyGenerator.2.16.840.1.101.3.4.2", JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator.2.16.840.1.101.3.4.22", JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator.2.16.840.1.101.3.4.42", JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_ECB, JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_CBC, JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_OFB, JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_CFB, JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_ECB, JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_CBC, JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_OFB, JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_CFB, JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_ECB, JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_CBC, JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_OFB, JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_CFB, JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator.AESWRAP", JCEKeyGenerator.AES.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_wrap, JCEKeyGenerator.AES128.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_wrap, JCEKeyGenerator.AES192.class.getName());
        put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_wrap, JCEKeyGenerator.AES256.class.getName());
        put("KeyGenerator.SERPENT", JCEKeyGenerator.Serpent.class.getName());
        put("KeyGenerator.CAMELLIA", JCEKeyGenerator.Camellia.class.getName());
        put("KeyGenerator.CAST5", JCEKeyGenerator.CAST5.class.getName());
        put("KeyGenerator.1.2.840.113533.7.66.10", JCEKeyGenerator.CAST5.class.getName());
        put("KeyGenerator.CAST6", JCEKeyGenerator.CAST6.class.getName());
        put("KeyGenerator.IDEA", JCEKeyGenerator.IDEA.class.getName());
        put("KeyGenerator.1.3.6.1.4.1.188.7.1.1.2", JCEKeyGenerator.IDEA.class.getName());

        put("KeyGenerator.GOST28147", JCEKeyGenerator.GOST28147.class.getName());
        put("Alg.Alias.KeyGenerator.GOST", "GOST28147");
        put("Alg.Alias.KeyGenerator.GOST-28147", "GOST28147");
        put("Alg.Alias.KeyGenerator." + CryptoProObjectIdentifiers.gostR28147_cbc, "GOST28147");

        put("KeyGenerator.HMACMD2", JCEKeyGenerator.MD2HMAC.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-MD2", "HMACMD2");
        put("KeyGenerator.HMACMD4", JCEKeyGenerator.MD4HMAC.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-MD4", "HMACMD4");
        put("KeyGenerator.HMACMD5", JCEKeyGenerator.MD5HMAC.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-MD5", "HMACMD5");
        put("KeyGenerator.HMACRIPEMD128", JCEKeyGenerator.RIPEMD128HMAC.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-RIPEMD128", "HMACRIPEMD128");
        put("KeyGenerator.HMACRIPEMD160", JCEKeyGenerator.RIPEMD160HMAC.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-RIPEMD160", "HMACRIPEMD160");

        put("KeyGenerator.HMACSHA1", JCEKeyGenerator.HMACSHA1.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-SHA1", "HMACSHA1");
        put("Alg.Alias.KeyGenerator." + PKCSObjectIdentifiers.id_hmacWithSHA1, "HMACSHA1");

        put("KeyGenerator.HMACSHA224", JCEKeyGenerator.HMACSHA224.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-SHA224", "HMACSHA224");
        put("Alg.Alias.KeyGenerator." + PKCSObjectIdentifiers.id_hmacWithSHA224, "HMACSHA224");

        put("KeyGenerator.HMACSHA256", JCEKeyGenerator.HMACSHA256.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-SHA256", "HMACSHA256");
        put("Alg.Alias.KeyGenerator." + PKCSObjectIdentifiers.id_hmacWithSHA256, "HMACSHA256");

        put("KeyGenerator.HMACSHA384", JCEKeyGenerator.HMACSHA384.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-SHA384", "HMACSHA384");
        put("Alg.Alias.KeyGenerator." + PKCSObjectIdentifiers.id_hmacWithSHA384, "HMACSHA384");

        put("KeyGenerator.HMACSHA512", JCEKeyGenerator.HMACSHA512.class.getName());
        put("Alg.Alias.KeyGenerator.HMAC-SHA512", "HMACSHA512");
        put("Alg.Alias.KeyGenerator." + PKCSObjectIdentifiers.id_hmacWithSHA512, "HMACSHA512");

        put("KeyGenerator.HMACTIGER", JCEKeyGenerator.HMACTIGER.class.getName());

        //
        // key pair generators.
        //
        put("KeyPairGenerator.RSA", JDKKeyPairGenerator.RSA.class.getName());
        put("KeyPairGenerator.DH", JDKKeyPairGenerator.DH.class.getName());
        put("KeyPairGenerator.DSA", JDKKeyPairGenerator.DSA.class.getName());
        put("KeyPairGenerator.ELGAMAL", JDKKeyPairGenerator.ElGamal.class.getName());
        put("KeyPairGenerator.EC", JDKKeyPairGenerator.EC.class.getName());
        put("KeyPairGenerator.ECDSA", JDKKeyPairGenerator.ECDSA.class.getName());
        put("KeyPairGenerator.ECDH", JDKKeyPairGenerator.ECDH.class.getName());
        put("KeyPairGenerator.ECDHC", JDKKeyPairGenerator.ECDHC.class.getName());
        put("KeyPairGenerator.ECIES", JDKKeyPairGenerator.ECDH.class.getName());
        put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1.1", "RSA");

        put("KeyPairGenerator.GOST3410", JDKKeyPairGenerator.GOST3410.class.getName());
        put("Alg.Alias.KeyPairGenerator.GOST-3410", "GOST3410");
        put("Alg.Alias.KeyPairGenerator.GOST-3410-94", "GOST3410");

        put("KeyPairGenerator.ECGOST3410", JDKKeyPairGenerator.ECGOST3410.class.getName());
        put("Alg.Alias.KeyPairGenerator.ECGOST-3410", "ECGOST3410");
        put("Alg.Alias.KeyPairGenerator.GOST-3410-2001", "ECGOST3410");


        //
        // key factories
        //
        put("KeyFactory.RSA", JDKKeyFactory.RSA.class.getName());
        put("KeyFactory.DH", JDKKeyFactory.DH.class.getName());
        put("KeyFactory.DSA", JDKKeyFactory.DSA.class.getName());
        put("KeyFactory.ELGAMAL", JDKKeyFactory.ElGamal.class.getName());
        put("KeyFactory.ElGamal", JDKKeyFactory.ElGamal.class.getName());
        put("KeyFactory.EC", JDKKeyFactory.EC.class.getName());
        put("KeyFactory.ECDSA", JDKKeyFactory.ECDSA.class.getName());
        put("KeyFactory.ECDH", JDKKeyFactory.ECDH.class.getName());
        put("KeyFactory.ECDHC", JDKKeyFactory.ECDHC.class.getName());

        put("Alg.Alias.KeyFactory.1.2.840.113549.1.1.1", "RSA");
        put("Alg.Alias.KeyFactory.1.2.840.10040.4.1", "DSA");
        put("Alg.Alias.KeyFactory." + X9ObjectIdentifiers.id_ecPublicKey, "EC");

        put("KeyFactory.GOST3410", JDKKeyFactory.GOST3410.class.getName());
        put("Alg.Alias.KeyFactory.GOST-3410", "GOST3410");
        put("Alg.Alias.KeyFactory.GOST-3410-94", "GOST3410");
        put("Alg.Alias.KeyFactory." + CryptoProObjectIdentifiers.gostR3410_94, "GOST3410");
        put("KeyFactory.ECGOST3410", JDKKeyFactory.ECGOST3410.class.getName());
        put("Alg.Alias.KeyFactory.GOST-3410-2001", "ECGOST3410");
        put("Alg.Alias.KeyFactory.ECGOST-3410", "ECGOST3410");
        put("Alg.Alias.KeyFactory." + CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410");

        //
        // Algorithm parameters
        //
        put("AlgorithmParameters.DES", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("Alg.Alias.AlgorithmParameters.1.3.14.3.2.7", "DES");
        put("AlgorithmParameters.DESEDE", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        //put("AlgorithmParameters.1.2.840.113549.3.7", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.RC2", JDKAlgorithmParameters.RC2AlgorithmParameters.class.getName());
        put("AlgorithmParameters.1.2.840.113549.3.2", JDKAlgorithmParameters.RC2AlgorithmParameters.class.getName());
        put("AlgorithmParameters.RC5", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.RC6", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        //put("AlgorithmParameters.IDEA", JDKAlgorithmParameters.IDEAAlgorithmParameters.class.getName());
        put("AlgorithmParameters.BLOWFISH", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.TWOFISH", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.SKIPJACK", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.RIJNDAEL", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("AlgorithmParameters.AES", JDKAlgorithmParameters.IVAlgorithmParameters.class.getName());
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.2", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.22", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.42", "AES");

        //
        // secret key factories.
        //
        put("SecretKeyFactory.DES", JCESecretKeyFactory.DES.class.getName());
        //put("SecretKeyFactory.DESEDE", JCESecretKeyFactory.DESede.class.getName());
        put("SecretKeyFactory.DESEDE", JCESecretKeyFactory.DESede.class.getName());
        put("SecretKeyFactory.PBEWITHMD5ANDDES", JCESecretKeyFactory.PBEWithMD5AndDES.class.getName());
        put("SecretKeyFactory.PBEWITHMD5ANDRC2", JCESecretKeyFactory.PBEWithMD5AndRC2.class.getName());
        put("SecretKeyFactory.PBEWITHSHA1ANDDES", JCESecretKeyFactory.PBEWithSHA1AndDES.class.getName());
        put("SecretKeyFactory.PBEWITHSHA1ANDRC2", JCESecretKeyFactory.PBEWithSHA1AndRC2.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", JCESecretKeyFactory.PBEWithSHAAndDES3Key.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", JCESecretKeyFactory.PBEWithSHAAndDES2Key.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND128BITRC4", JCESecretKeyFactory.PBEWithSHAAnd128BitRC4.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND40BITRC4", JCESecretKeyFactory.PBEWithSHAAnd40BitRC4.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND128BITRC2-CBC", JCESecretKeyFactory.PBEWithSHAAnd128BitRC2.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND40BITRC2-CBC", JCESecretKeyFactory.PBEWithSHAAnd40BitRC2.class.getName());
        put("SecretKeyFactory.PBEWITHSHAANDTWOFISH-CBC", JCESecretKeyFactory.PBEWithSHAAndTwofish.class.getName());
        put("SecretKeyFactory.PBEWITHSHAANDIDEA-CBC", JCESecretKeyFactory.PBEWithSHAAndIDEA.class.getName());
        put("SecretKeyFactory.PBEWITHHMACRIPEMD160", JCESecretKeyFactory.PBEWithRIPEMD160.class.getName());
        put("SecretKeyFactory.PBEWITHHMACSHA1", JCESecretKeyFactory.PBEWithSHA.class.getName());
        put("SecretKeyFactory.PBEWITHHMACTIGER", JCESecretKeyFactory.PBEWithTiger.class.getName());

        put("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", JCESecretKeyFactory.PBEWithMD5And128BitAESCBCOpenSSL.class.getName());
        put("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", JCESecretKeyFactory.PBEWithMD5And192BitAESCBCOpenSSL.class.getName());
        put("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", JCESecretKeyFactory.PBEWithMD5And256BitAESCBCOpenSSL.class.getName());

        put("Alg.Alias.SecretKeyFactory.PBE", "PBE/PKCS5");

        put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHMD5ANDDES", "PBE/PKCS5");
        put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHA1ANDDES", "PBE/PKCS5");
        put("Alg.Alias.SecretKeyFactory.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        put("Alg.Alias.SecretKeyFactory.OLDPBEWITHSHAANDTWOFISH-CBC", "PBE/PKCS12");

        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.1", "PBEWITHSHAAND128BITRC4");
        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.2", "PBEWITHSHAAND40BITRC4");
        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.5", "PBEWITHSHAAND128BITRC2-CBC");
        put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.6", "PBEWITHSHAAND40BITRC2-CBC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
        put("Alg.Alias.SecretKeyFactory.1.3.14.3.2.26", "PBEWITHHMACSHA1");
        put("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");

        put("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHAAnd128BitAESBC.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHAAnd192BitAESBC.class.getName());
        put("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHAAnd256BitAESBC.class.getName());
        put("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHA256And128BitAESBC.class.getName());
        put("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHA256And192BitAESBC.class.getName());
        put("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", JCESecretKeyFactory.PBEWithSHA256And256BitAESBC.class.getName());
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC","PBEWITHSHAAND128BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC","PBEWITHSHAAND192BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC","PBEWITHSHAAND256BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC","PBEWITHSHAAND128BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC","PBEWITHSHAAND192BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC","PBEWITHSHAAND256BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC","PBEWITHSHA256AND128BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC","PBEWITHSHA256AND192BITAES-CBC-BC");
        put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC","PBEWITHSHA256AND256BITAES-CBC-BC");

        addMacAlgorithms();

        addMessageDigestAlgorithms();

        addSignatureAlgorithms();

        // Certification Path API
        put("CertPathValidator.PKIX", PKIXCertPathValidatorSpi.class.getName());
        put("CertPathValidator.PKIX ValidationAlgorithm", "RFC2459");
        put("CertPathBuilder.PKIX", PKIXCertPathBuilderSpi.class.getName());
        put("CertPathBuilder.PKIX ValidationAlgorithm", "RFC2459");
        put("CertStore.Collection", CertStoreCollectionSpi.class.getName());
    }

    //
    // macs
    //
    private void addMacAlgorithms()
    {
        put("Mac.DESMAC", JCEMac.DES.class.getName());
        put("Alg.Alias.Mac.DES", "DESMAC");
        put("Mac.DESMAC/CFB8", JCEMac.DESCFB8.class.getName());
        put("Alg.Alias.Mac.DES/CFB8", "DESMAC/CFB8");

        put("Mac.DESEDEMAC", JCEMac.DESede.class.getName());
        put("Alg.Alias.Mac.DESEDE", "DESEDEMAC");
        put("Mac.DESEDEMAC/CFB8", JCEMac.DESedeCFB8.class.getName());
        put("Alg.Alias.Mac.DESEDE/CFB8", "DESEDEMAC/CFB8");

        //put("Mac.DESWITHISO9797", JCEMac.ISO9797_DES.class.getName());
        put("Alg.Alias.Mac.DESISO9797MAC", "DESWITHISO9797");

        put("Mac.DESEDEMAC64", JCEMac.DESede64.class.getName());
        put("Alg.Alias.Mac.DESEDE64", "DESEDEMAC64");

        put("Mac.ISO9797ALG3MAC", JCEMac.DES9797Alg3.class.getName());
        put("Alg.Alias.Mac.ISO9797ALG3", "ISO9797ALG3MAC");

        put("Mac.SKIPJACKMAC", JCEMac.Skipjack.class.getName());
        put("Alg.Alias.Mac.SKIPJACK", "SKIPJACKMAC");
        put("Mac.SKIPJACKMAC/CFB8", JCEMac.SkipjackCFB8.class.getName());
        put("Alg.Alias.Mac.SKIPJACK/CFB8", "SKIPJACKMAC/CFB8");

        put("Mac.IDEAMAC", JCEMac.IDEA.class.getName());
        put("Alg.Alias.Mac.IDEA", "IDEAMAC");
        put("Mac.IDEAMAC/CFB8", JCEMac.IDEACFB8.class.getName());
        put("Alg.Alias.Mac.IDEA/CFB8", "IDEAMAC/CFB8");

        put("Mac.RC2MAC", JCEMac.RC2.class.getName());
        put("Alg.Alias.Mac.RC2", "RC2MAC");
        put("Mac.RC2MAC/CFB8", JCEMac.RC2CFB8.class.getName());
        put("Alg.Alias.Mac.RC2/CFB8", "RC2MAC/CFB8");

        put("Mac.RC5MAC", JCEMac.RC5.class.getName());
        put("Alg.Alias.Mac.RC5", "RC5MAC");
        put("Mac.RC5MAC/CFB8", JCEMac.RC5CFB8.class.getName());
        put("Alg.Alias.Mac.RC5/CFB8", "RC5MAC/CFB8");

        put("Mac.GOST28147MAC", JCEMac.GOST28147.class.getName());

        put("Mac.HMACMD2", JCEMac.MD2.class.getName());
        put("Alg.Alias.Mac.HMAC-MD2", "HMACMD2");
        put("Alg.Alias.Mac.HMAC/MD2", "HMACMD2");

        put("Mac.HMACMD4", JCEMac.MD4.class.getName());
        put("Alg.Alias.Mac.HMAC-MD4", "HMACMD4");
        put("Alg.Alias.Mac.HMAC/MD4", "HMACMD4");

        put("Mac.HMACMD5", JCEMac.MD5.class.getName());
        put("Alg.Alias.Mac.HMAC-MD5", "HMACMD5");
        put("Alg.Alias.Mac.HMAC/MD5", "HMACMD5");

        put("Mac.HMACRIPEMD128", JCEMac.RIPEMD128.class.getName());
        put("Alg.Alias.Mac.HMAC-RIPEMD128", "HMACRIPEMD128");
        put("Alg.Alias.Mac.HMAC/RIPEMD128", "HMACRIPEMD128");

        put("Mac.HMACRIPEMD160", JCEMac.RIPEMD160.class.getName());
        put("Alg.Alias.Mac.HMAC-RIPEMD160", "HMACRIPEMD160");
        put("Alg.Alias.Mac.HMAC/RIPEMD160", "HMACRIPEMD160");

        put("Mac.HMACSHA1", JCEMac.SHA1.class.getName());
        put("Alg.Alias.Mac.HMAC-SHA1", "HMACSHA1");
        put("Alg.Alias.Mac.HMAC/SHA1", "HMACSHA1");
        put("Alg.Alias.Mac." + PKCSObjectIdentifiers.id_hmacWithSHA1, "HMACSHA1");

        put("Mac.HMACSHA224", JCEMac.SHA224.class.getName());
        put("Alg.Alias.Mac.HMAC-SHA224", "HMACSHA224");
        put("Alg.Alias.Mac.HMAC/SHA224", "HMACSHA224");
        put("Alg.Alias.Mac." + PKCSObjectIdentifiers.id_hmacWithSHA224, "HMACSHA224");

        put("Mac.HMACSHA256", JCEMac.SHA256.class.getName());
        put("Alg.Alias.Mac.HMAC-SHA256", "HMACSHA256");
        put("Alg.Alias.Mac.HMAC/SHA256", "HMACSHA256");
        put("Alg.Alias.Mac." + PKCSObjectIdentifiers.id_hmacWithSHA256, "HMACSHA256");

        put("Mac.OLDHMACSHA384", JCEMac.OldSHA384.class.getName());

        put("Mac.OLDHMACSHA512", JCEMac.OldSHA512.class.getName());

        put("Mac.HMACSHA384", JCEMac.SHA384.class.getName());
        put("Alg.Alias.Mac.HMAC-SHA384", "HMACSHA384");
        put("Alg.Alias.Mac.HMAC/SHA384", "HMACSHA384");
        put("Alg.Alias.Mac." + PKCSObjectIdentifiers.id_hmacWithSHA384, "HMACSHA384");

        put("Mac.HMACSHA512", JCEMac.SHA512.class.getName());
        put("Alg.Alias.Mac.HMAC-SHA512", "HMACSHA512");
        put("Alg.Alias.Mac.HMAC/SHA512", "HMACSHA512");
        put("Alg.Alias.Mac." + PKCSObjectIdentifiers.id_hmacWithSHA512, "HMACSHA512");

        put("Mac.HMACTiger", JCEMac.Tiger.class.getName());
        put("Alg.Alias.Mac.HMAC-Tiger", "HMACTiger");
        put("Alg.Alias.Mac.HMAC/Tiger", "HMACTiger");

        put("Mac.PBEWITHHMACSHA", JCEMac.PBEWithSHA.class.getName());
        put("Mac.PBEWITHHMACSHA1", JCEMac.PBEWithSHA.class.getName());
        put("Mac.PBEWITHHMACRIPEMD160", JCEMac.PBEWithRIPEMD160.class.getName());
        put("Alg.Alias.Mac.1.3.14.3.2.26", "PBEWITHHMACSHA");
    }

    //
    // message digests
    //
    private void addMessageDigestAlgorithms()
    {
        put("MessageDigest.SHA-1", JDKMessageDigest.SHA1.class.getName());
        put("Alg.Alias.MessageDigest.SHA1", "SHA-1");
        put("Alg.Alias.MessageDigest.SHA", "SHA-1");
        put("Alg.Alias.MessageDigest." + OIWObjectIdentifiers.idSHA1, "SHA-1");
        put("MessageDigest.SHA-224", JDKMessageDigest.SHA224.class.getName());
        put("Alg.Alias.MessageDigest.SHA224", "SHA-224");
        put("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha224, "SHA-224");
        put("MessageDigest.SHA-256", JDKMessageDigest.SHA256.class.getName());
        put("Alg.Alias.MessageDigest.SHA256", "SHA-256");
        put("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha256, "SHA-256");
        put("MessageDigest.SHA-384", JDKMessageDigest.SHA384.class.getName());
        put("Alg.Alias.MessageDigest.SHA384", "SHA-384");
        put("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha384, "SHA-384");
        put("MessageDigest.SHA-512", JDKMessageDigest.SHA512.class.getName());
        put("Alg.Alias.MessageDigest.SHA512", "SHA-512");
        put("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha512, "SHA-512");

        put("MessageDigest.MD2", JDKMessageDigest.MD2.class.getName());
        put("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.md2, "MD2");
        put("MessageDigest.MD4", JDKMessageDigest.MD4.class.getName());
        put("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.md4, "MD4");
        put("MessageDigest.MD5", JDKMessageDigest.MD5.class.getName());
        put("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.md5, "MD5");
        put("MessageDigest.RIPEMD128", JDKMessageDigest.RIPEMD128.class.getName());
        put("Alg.Alias.MessageDigest." + TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
        put("MessageDigest.RIPEMD160", JDKMessageDigest.RIPEMD160.class.getName());
        put("Alg.Alias.MessageDigest." + TeleTrusTObjectIdentifiers.ripemd160, "RIPEMD160");
        put("MessageDigest.RIPEMD256", JDKMessageDigest.RIPEMD256.class.getName());
        put("Alg.Alias.MessageDigest." + TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
        put("MessageDigest.RIPEMD320", JDKMessageDigest.RIPEMD320.class.getName());
        put("MessageDigest.Tiger", JDKMessageDigest.Tiger.class.getName());

        put("MessageDigest.WHIRLPOOL", JDKMessageDigest.Whirlpool.class.getName());

        put("MessageDigest.GOST3411", JDKMessageDigest.GOST3411.class.getName());
        put("Alg.Alias.MessageDigest.GOST", "GOST3411");
        put("Alg.Alias.MessageDigest.GOST-3411", "GOST3411");
        put("Alg.Alias.MessageDigest." + CryptoProObjectIdentifiers.gostR3411, "GOST3411");
    }

    //
    // signature algorithms.
    //
    private void addSignatureAlgorithms()
    {
        put("Signature.MD2WithRSAEncryption", JDKDigestSignature.MD2WithRSAEncryption.class.getName());
        put("Signature.MD4WithRSAEncryption", JDKDigestSignature.MD4WithRSAEncryption.class.getName());
        put("Signature.MD5WithRSAEncryption", JDKDigestSignature.MD5WithRSAEncryption.class.getName());
        put("Signature.SHA1WithRSAEncryption", JDKDigestSignature.SHA1WithRSAEncryption.class.getName());
        put("Signature.SHA224WithRSAEncryption", JDKDigestSignature.SHA224WithRSAEncryption.class.getName());
        put("Signature.SHA256WithRSAEncryption", JDKDigestSignature.SHA256WithRSAEncryption.class.getName());
        put("Signature.SHA384WithRSAEncryption", JDKDigestSignature.SHA384WithRSAEncryption.class.getName());
        put("Signature.SHA512WithRSAEncryption", JDKDigestSignature.SHA512WithRSAEncryption.class.getName());
        put("Signature.RIPEMD160WithRSAEncryption", JDKDigestSignature.RIPEMD160WithRSAEncryption.class.getName());
        put("Signature.RIPEMD128WithRSAEncryption", JDKDigestSignature.RIPEMD128WithRSAEncryption.class.getName());
        put("Signature.RIPEMD256WithRSAEncryption", JDKDigestSignature.RIPEMD256WithRSAEncryption.class.getName());
        put("Signature.DSA", JDKDSASigner.stdDSA.class.getName());
        put("Signature.NONEWITHDSA", JDKDSASigner.noneDSA.class.getName());
        put("Signature.ECDSA", JDKDSASigner.ecDSA.class.getName());
        put("Signature.SHA224WITHECDSA", JDKDSASigner.ecDSA224.class.getName());
        put("Signature.SHA256WITHECDSA", JDKDSASigner.ecDSA256.class.getName());
        put("Signature.SHA384WITHECDSA", JDKDSASigner.ecDSA384.class.getName());
        put("Signature.SHA512WITHECDSA", JDKDSASigner.ecDSA512.class.getName());
        put("Signature.SHA1WITHECNR", JDKDSASigner.ecNR.class.getName());
        put("Signature.SHA224WITHECNR", JDKDSASigner.ecNR224.class.getName());
        put("Signature.SHA256WITHECNR", JDKDSASigner.ecNR256.class.getName());
        put("Signature.SHA384WITHECNR", JDKDSASigner.ecNR384.class.getName());
        put("Signature.SHA512WITHECNR", JDKDSASigner.ecNR512.class.getName());
        put("Signature.SHA1withRSA/ISO9796-2", JDKISOSignature.SHA1WithRSAEncryption.class.getName());
        put("Signature.MD5withRSA/ISO9796-2", JDKISOSignature.MD5WithRSAEncryption.class.getName());
        put("Signature.RIPEMD160withRSA/ISO9796-2", JDKISOSignature.RIPEMD160WithRSAEncryption.class.getName());

        put("Signature.RSASSA-PSS", JDKPSSSigner.PSSwithRSA.class.getName());
        put("Signature." + PKCSObjectIdentifiers.id_RSASSA_PSS, JDKPSSSigner.PSSwithRSA.class.getName());
        put("Signature.SHA1withRSA/PSS", JDKPSSSigner.SHA1withRSA.class.getName());
        put("Signature.SHA224withRSA/PSS", JDKPSSSigner.SHA224withRSA.class.getName());
        put("Signature.SHA256withRSA/PSS", JDKPSSSigner.SHA256withRSA.class.getName());
        put("Signature.SHA384withRSA/PSS", JDKPSSSigner.SHA384withRSA.class.getName());
        put("Signature.SHA512withRSA/PSS", JDKPSSSigner.SHA512withRSA.class.getName());

        put("Alg.Alias.Signature.RAWDSA", "NONEWITHDSA");

        put("Alg.Alias.Signature.SHA1withRSAandMGF1", "SHA1withRSA/PSS");
        put("Alg.Alias.Signature.SHA224withRSAandMGF1", "SHA224withRSA/PSS");
        put("Alg.Alias.Signature.SHA256withRSAandMGF1", "SHA256withRSA/PSS");
        put("Alg.Alias.Signature.SHA384withRSAandMGF1", "SHA384withRSA/PSS");
        put("Alg.Alias.Signature.SHA512withRSAandMGF1", "SHA512withRSA/PSS");

        put("Alg.Alias.Signature.MD2withRSAEncryption", "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD4withRSAEncryption", "MD4WithRSAEncryption");
        put("Alg.Alias.Signature.MD5withRSAEncryption", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1withRSAEncryption", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.SHA224withRSAEncryption", "SHA224WithRSAEncryption");

        put("Alg.Alias.Signature.SHA256withRSAEncryption", "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature.SHA384withRSAEncryption", "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature.SHA512withRSAEncryption", "SHA512WithRSAEncryption");

        put("Alg.Alias.Signature.SHA256WithRSAEncryption", "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature.SHA384WithRSAEncryption", "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature.SHA512WithRSAEncryption", "SHA512WithRSAEncryption");

        put("Alg.Alias.Signature.SHA256WITHRSAENCRYPTION", "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature.SHA384WITHRSAENCRYPTION", "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature.SHA512WITHRSAENCRYPTION", "SHA512WithRSAEncryption");

        put("Alg.Alias.Signature.RIPEMD160withRSAEncryption", "RIPEMD160WithRSAEncryption");

        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD2WithRSA", "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD2withRSA", "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD2/RSA", "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD5WithRSA", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.MD5withRSA", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.MD5/RSA", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.MD4WithRSA", "MD4WithRSAEncryption");
        put("Alg.Alias.Signature.MD4withRSA", "MD4WithRSAEncryption");
        put("Alg.Alias.Signature.MD4/RSA", "MD4WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.md4WithRSAEncryption, "MD4WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1WithRSA", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1withRSA", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.SHA224WithRSA", "SHA224WithRSAEncryption");
        put("Alg.Alias.Signature.SHA224withRSA", "SHA224WithRSAEncryption");
        put("Alg.Alias.Signature.SHA256WithRSA", "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature.SHA256withRSA", "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature.SHA384WithRSA", "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature.SHA384withRSA", "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature.SHA512WithRSA", "SHA512WithRSAEncryption");
        put("Alg.Alias.Signature.SHA512withRSA", "SHA512WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1/RSA", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.SHA-1/RSA", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WithRSAEncryption");
        put("Alg.Alias.Signature." + PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WithRSAEncryption");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.1", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.5", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.1.2.840.113549.2.5with1.2.840.113549.1.1.1", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD160WithRSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD160withRSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD128WithRSA", "RIPEMD128WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD128withRSA", "RIPEMD128WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD256WithRSA", "RIPEMD256WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD256withRSA", "RIPEMD256WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD-160/RSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.RMD160withRSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.RMD160/RSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.1.3.36.3.3.1.2", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.1.3.36.3.3.1.3", "RIPEMD128WithRSAEncryption");
        put("Alg.Alias.Signature.1.3.36.3.3.1.4", "RIPEMD256WithRSAEncryption");

        put("Alg.Alias.Signature.MD2WITHRSAENCRYPTION", "MD2WithRSAEncryption");
        put("Alg.Alias.Signature.MD5WITHRSAENCRYPTION", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1WITHRSAENCRYPTION", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD160WITHRSAENCRYPTION", "RIPEMD160WithRSAEncryption");

        put("Alg.Alias.Signature.MD5WITHRSA", "MD5WithRSAEncryption");
        put("Alg.Alias.Signature.SHA1WITHRSA", "SHA1WithRSAEncryption");
        put("Alg.Alias.Signature.RIPEMD160WITHRSA", "RIPEMD160WithRSAEncryption");
        put("Alg.Alias.Signature.RMD160WITHRSA", "RIPEMD160WithRSAEncryption");
        //put("Alg.Alias.Signature.RIPEMD160WITHRSA", "RIPEMD160WithRSAEncryption");

        put("Alg.Alias.Signature.SHA1withECDSA", "ECDSA");
        put("Alg.Alias.Signature.ECDSAwithSHA1", "ECDSA");
        put("Alg.Alias.Signature.SHA1WITHECDSA", "ECDSA");
        put("Alg.Alias.Signature.ECDSAWITHSHA1", "ECDSA");
        put("Alg.Alias.Signature.SHA1WithECDSA", "ECDSA");
        put("Alg.Alias.Signature.ECDSAWithSHA1", "ECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.1", "ECDSA");
        put("Alg.Alias.Signature.SHA/DSA", "DSA");
        put("Alg.Alias.Signature.SHA1withDSA", "DSA");
        put("Alg.Alias.Signature.SHA1WITHDSA", "DSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.1", "DSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.3", "DSA");
        put("Alg.Alias.Signature.DSAwithSHA1", "DSA");
        put("Alg.Alias.Signature.DSAWITHSHA1", "DSA");
        put("Alg.Alias.Signature.SHA1WithDSA", "DSA");
        put("Alg.Alias.Signature.DSAWithSHA1", "DSA");
        put("Alg.Alias.Signature.1.2.840.10040.4.3", "DSA");
        put("Alg.Alias.Signature.MD5WithRSA/ISO9796-2", "MD5withRSA/ISO9796-2");
        put("Alg.Alias.Signature.SHA1WithRSA/ISO9796-2", "SHA1withRSA/ISO9796-2");
        put("Alg.Alias.Signature.RIPEMD160WithRSA/ISO9796-2", "RIPEMD160withRSA/ISO9796-2");

        put("Signature.ECGOST3410", JDKGOST3410Signer.ecgost3410.class.getName());
        put("Alg.Alias.Signature.ECGOST-3410", "ECGOST3410");
        put("Alg.Alias.Signature.GOST-3410-2001", "ECGOST3410");
        put("Alg.Alias.Signature.GOST3411withECGOST3410", "ECGOST3410");
        put("Alg.Alias.Signature.GOST3411WITHECGOST3410", "ECGOST3410");
        put("Alg.Alias.Signature.GOST3411WithECGOST3410", "ECGOST3410");
        put("Alg.Alias.Signature." + CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "ECGOST3410");

        put("Signature.GOST3410", JDKGOST3410Signer.gost3410.class.getName());
        put("Alg.Alias.Signature.GOST-3410", "GOST3410");
        put("Alg.Alias.Signature.GOST-3410-94", "GOST3410");
        put("Alg.Alias.Signature.GOST3411withGOST3410", "GOST3410");
        put("Alg.Alias.Signature.GOST3411WITHGOST3410", "GOST3410");
        put("Alg.Alias.Signature.GOST3411WithGOST3410", "GOST3410");
        put("Alg.Alias.Signature." + CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3410");
    }
}
