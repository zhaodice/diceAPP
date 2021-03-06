package org.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.GOST3410NamedParameters;
import org.bouncycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.interfaces.GOST3410Params;
import org.bouncycastle.jce.interfaces.GOST3410PrivateKey;
import org.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.bouncycastle.jce.spec.GOST3410PrivateKeySpec;

public class JDKGOST3410PrivateKey
    implements GOST3410PrivateKey, PKCS12BagAttributeCarrier
{
    BigInteger          x;
    GOST3410Params      gost3410Spec;

    private Hashtable   pkcs12Attributes = new Hashtable();
    private Vector      pkcs12Ordering = new Vector();

    protected JDKGOST3410PrivateKey()
    {
    }

    JDKGOST3410PrivateKey(
        GOST3410PrivateKey    key)
    {
        this.x = key.getX();
        this.gost3410Spec = key.getParameters();
    }

    JDKGOST3410PrivateKey(
        GOST3410PrivateKeySpec    spec)
    {
        this.x = spec.getX();
        this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(spec.getP(), spec.getQ(), spec.getA()));
    }

    JDKGOST3410PrivateKey(
        PrivateKeyInfo  info)
    {
        GOST3410PublicKeyAlgParameters    params = new GOST3410PublicKeyAlgParameters((ASN1Sequence)info.getAlgorithmId().getParameters());
        DEROctetString      derX = (DEROctetString)info.getPrivateKey();
        byte[]              keyEnc = derX.getOctets();
        byte[]              keyBytes = new byte[keyEnc.length];
        
        for (int i = 0; i != keyEnc.length; i++)
        {
            keyBytes[i] = keyEnc[keyEnc.length - 1 - i]; // was little endian
        }
        
        this.x = new BigInteger(1, keyBytes);

        GOST3410ParamSetParameters p = GOST3410NamedParameters.getByOID(params.getPublicKeyParamSet());

        if (params.getEncryptionParamSet() != null)
        {
            this.gost3410Spec = new GOST3410ParameterSpec(params.getPublicKeyParamSet().getId(), params.getDigestParamSet().getId(), params.getEncryptionParamSet().getId());
        }
        else
        {
            this.gost3410Spec = new GOST3410ParameterSpec(params.getPublicKeyParamSet().getId(), params.getDigestParamSet().getId());
        }
    }

    JDKGOST3410PrivateKey(
        GOST3410PrivateKeyParameters  params,
        GOST3410ParameterSpec         spec)
    {
        this.x = params.getX();
        this.gost3410Spec = spec;

        if (spec == null) 
        {
            throw new IllegalArgumentException("spec is null");
        }
    }

    public String getAlgorithm()
    {
        return "GOST3410";
    }

    /**
     * return the encoding format we produce in getEncoded().
     *
     * @return the string "PKCS#8"
     */
    public String getFormat()
    {
        return "PKCS#8";
    }

    /**
     * Return a PKCS8 representation of the key. The sequence returned
     * represents a full PrivateKeyInfo object.
     *
     * @return a PKCS8 representation of the key.
     */
    public byte[] getEncoded()
    {
        ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
        DEROutputStream         dOut = new DEROutputStream(bOut);
        PrivateKeyInfo          info;
        byte[]                  keyEnc = this.getX().toByteArray();
        byte[]                  keyBytes = new byte[keyEnc.length];
        
        if (keyEnc[0] == 0)
        {
            keyBytes = new byte[keyEnc.length - 1];
        }
        else
        {
            keyBytes = new byte[keyEnc.length];
        }
        
        for (int i = 0; i != keyBytes.length; i++)
        {
            keyBytes[i] = keyEnc[keyEnc.length - 1 - i]; // must be little endian
        }
        
        if (gost3410Spec instanceof GOST3410ParameterSpec)
        {
            info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new DERObjectIdentifier(gost3410Spec.getPublicKeyParamSetOID()), new DERObjectIdentifier(gost3410Spec.getDigestParamSetOID())).getDERObject()), new DEROctetString(keyBytes));
        }
        else
        {
            info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94), new DEROctetString(keyBytes));
        }
        
        return info.getDEREncoded();
    }

    public GOST3410Params getParameters()
    {
        return gost3410Spec;
    }

    public BigInteger getX()
    {
        return x;
    }

    public void setBagAttribute(
        DERObjectIdentifier oid,
        DEREncodable        attribute)
    {
        pkcs12Attributes.put(oid, attribute);
        pkcs12Ordering.addElement(oid);
    }

    public DEREncodable getBagAttribute(
        DERObjectIdentifier oid)
    {
        return (DEREncodable)pkcs12Attributes.get(oid);
    }

    public Enumeration getBagAttributeKeys()
    {
        return pkcs12Ordering.elements();
    }
}
