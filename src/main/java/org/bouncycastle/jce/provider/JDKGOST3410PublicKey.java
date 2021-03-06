package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.GOST3410NamedParameters;
import org.bouncycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import org.bouncycastle.jce.interfaces.GOST3410Params;
import org.bouncycastle.jce.interfaces.GOST3410PublicKey;
import org.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.bouncycastle.jce.spec.GOST3410PublicKeySpec;

public class JDKGOST3410PublicKey
    implements GOST3410PublicKey
{
    private BigInteger      y;
    private GOST3410Params  gost3410Spec;

    JDKGOST3410PublicKey(
        GOST3410PublicKeySpec    spec)
    {
        this.y = spec.getY();
        this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(spec.getP(), spec.getQ(), spec.getA()));
    }

    JDKGOST3410PublicKey(
        GOST3410PublicKey    key)
    {
        this.y = key.getY();
        this.gost3410Spec = key.getParameters();
    }

    JDKGOST3410PublicKey(
        GOST3410PublicKeyParameters  params,
        GOST3410ParameterSpec        spec)
    {
        this.y = params.getY();
        this.gost3410Spec = spec;
    }

    JDKGOST3410PublicKey(
        BigInteger        y,
        GOST3410ParameterSpec  gost3410Spec)
    {
        this.y = y;
        this.gost3410Spec = gost3410Spec;
    }

    JDKGOST3410PublicKey(
        SubjectPublicKeyInfo    info)
    {
        GOST3410PublicKeyAlgParameters       params = new GOST3410PublicKeyAlgParameters((ASN1Sequence)info.getAlgorithmId().getParameters());
        DEROctetString                    derY = null;

        try
        {
            derY = (DEROctetString)info.getPublicKey();
            
            byte[]                  keyEnc = derY.getOctets();
            byte[]                  keyBytes = new byte[keyEnc.length];
            
            for (int i = 0; i != keyEnc.length; i++)
            {
                keyBytes[i] = keyEnc[keyEnc.length - 1 - i]; // was little endian
            }

            this.y = new BigInteger(1, keyBytes);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("invalid info structure in GOST3410 public key");
        }

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

    public String getAlgorithm()
    {
        return "GOST3410";
    }

    public String getFormat()
    {
        return "X.509";
    }

    public byte[] getEncoded()
    {
        SubjectPublicKeyInfo    info;
        byte[]                  keyEnc = this.getY().toByteArray();
        byte[]                  keyBytes;
        
        if (keyEnc[0] == 0 && (keyEnc.length != 64 || keyEnc.length != 128))
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
            if (gost3410Spec.getEncryptionParamSetOID() != null)
            {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new DERObjectIdentifier(gost3410Spec.getPublicKeyParamSetOID()), new DERObjectIdentifier(gost3410Spec.getDigestParamSetOID()), new DERObjectIdentifier(gost3410Spec.getEncryptionParamSetOID())).getDERObject()), new DEROctetString(keyBytes));
            }
            else
            {
                info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new DERObjectIdentifier(gost3410Spec.getPublicKeyParamSetOID()), new DERObjectIdentifier(gost3410Spec.getDigestParamSetOID())).getDERObject()), new DEROctetString(keyBytes));
            }
        }
        else
        {
            info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94), new DEROctetString(keyBytes));
        }

        return info.getDEREncoded();
    }

    public GOST3410Params getParameters()
    {
        return gost3410Spec;
    }

    public BigInteger getY()
    {
        return y;
    }

    public String toString()
    {
        StringBuffer    buf = new StringBuffer();
        String          nl = System.getProperty("line.separator");

        buf.append("GOST3410 Public Key" + nl);
        buf.append("            y: " + this.getY().toString(16) + nl);

        return buf.toString();
    }
}
