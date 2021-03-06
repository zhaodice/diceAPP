package org.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.util.encoders.Base64;

/**
 * class for dealing with X509 certificates.
 * <p>
 * At the moment this will deal with "-----BEGIN CERTIFICATE-----" to "-----END CERTIFICATE-----"
 * base 64 encoded certs, as well as the BER binaries of certificates and some classes of PKCS#7
 * objects.
 */
public class JDKX509CertificateFactory
    extends CertificateFactorySpi
{
    private SignedData         sData = null;
    private int                sDataObjectCount = 0;
    private InputStream        currentStream = null;
    
    private String readLine(
        InputStream in)
        throws IOException
    {
        int             c;
        StringBuffer    l = new StringBuffer();

        while (((c = in.read()) != '\n') && (c >= 0))
        {
            if (c == '\r')
            {
                continue;
            }

            l.append((char)c);
        }

        if (c < 0)
        {
            return null;
        }

        return l.toString();
    }

    private Certificate readDERCertificate(
        InputStream in)
        throws IOException
    {
        ASN1InputStream dIn = new ASN1InputStream(in);
        ASN1Sequence    seq = (ASN1Sequence)dIn.readObject();

        if (seq.size() > 1
                && seq.getObjectAt(0) instanceof DERObjectIdentifier)
        {
            if (seq.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData))
            {
                sData = new SignedData(ASN1Sequence.getInstance(
                                (ASN1TaggedObject)seq.getObjectAt(1), true));

                return new X509CertificateObject(
                            X509CertificateStructure.getInstance(
                                    sData.getCertificates().getObjectAt(sDataObjectCount++)));
            }
        }

        return new X509CertificateObject(
                            X509CertificateStructure.getInstance(seq));
    }

    /**
     * read in a BER encoded PKCS7 certificate.
     */
    private Certificate readPKCS7Certificate(
        InputStream  in)
        throws IOException
    {
        ASN1InputStream  dIn = new ASN1InputStream(in);
        ASN1Sequence     seq = (ASN1Sequence)dIn.readObject();

        if (seq.size() > 1
                && seq.getObjectAt(0) instanceof DERObjectIdentifier)
        {
            if (seq.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData))
            {
                sData = new SignedData(ASN1Sequence.getInstance(
                                (ASN1TaggedObject)seq.getObjectAt(1), true));
    
                return new X509CertificateObject(
                            X509CertificateStructure.getInstance(
                                    sData.getCertificates().getObjectAt(sDataObjectCount++)));
            }
        }

        return new X509CertificateObject(
                     X509CertificateStructure.getInstance(seq));
    }

    private Certificate readPEMCertificate(
        InputStream  in)
        throws IOException
    {
        String          line;
        StringBuffer    pemBuf = new StringBuffer();

        while ((line = readLine(in)) != null)
        {
            if (line.equals("-----BEGIN CERTIFICATE-----")
                || line.equals("-----BEGIN X509 CERTIFICATE-----"))
            {
                break;
            }
        }

        while ((line = readLine(in)) != null)
        {
            if (line.equals("-----END CERTIFICATE-----")
                || line.equals("-----END X509 CERTIFICATE-----"))
            {
                break;
            }

            pemBuf.append(line);
        }

        if (pemBuf.length() != 0)
        {
            ByteArrayInputStream bIn = new ByteArrayInputStream(Base64.decode(pemBuf.toString()));
            return readDERCertificate(bIn);
        }

        return null;
    }

    private CRL readDERCRL(
        InputStream in)
        throws IOException
    {
        ASN1InputStream  dIn = new ASN1InputStream(in);

        return new X509CRLObject(new CertificateList((ASN1Sequence)dIn.readObject()));
    }

    private CRL readPEMCRL(
        InputStream  in)
        throws IOException
    {
        String          line;
        StringBuffer    pemBuf = new StringBuffer();

        while ((line = readLine(in)) != null)
        {
            if (line.equals("-----BEGIN CRL-----")
                || line.equals("-----BEGIN X509 CRL-----"))
            {
                break;
            }
        }

        while ((line = readLine(in)) != null)
        {
            if (line.equals("-----END CRL-----")
                || line.equals("-----END X509 CRL-----"))
            {
                break;
            }

            pemBuf.append(line);
        }

        if (pemBuf.length() != 0)
        {
            ByteArrayInputStream bIn = new ByteArrayInputStream(Base64.decode(pemBuf.toString()));
            return readDERCRL(bIn);
        }

        return null;
    }

    /**
     * Generates a certificate object and initializes it with the data
     * read from the input stream inStream.
     */
    public Certificate engineGenerateCertificate(
        InputStream in) 
        throws CertificateException
    {
        if (currentStream == null)
        {
            currentStream = in;
            sData = null;
            sDataObjectCount = 0;
        }
        else if (currentStream != in) // reset if input stream has changed
        {
            currentStream = in;
            sData = null;
            sDataObjectCount = 0;
        }

        try
        {
            if (sData != null)
            {
                if (sDataObjectCount != sData.getCertificates().size())
                {
                    return new X509CertificateObject(
                                X509CertificateStructure.getInstance(
                                        sData.getCertificates().getObjectAt(sDataObjectCount++)));
                }
                else
                {
                    sData = null;
                    sDataObjectCount = 0;
                    return null;
                }
            }
            
            if (!in.markSupported())
            {
                in = new BufferedInputStream(in);
            }
            
            in.mark(10);
            int    tag = in.read();
            
            if (tag == -1)
            {
                return null;
            }
            
            if (tag != 0x30)  // assume ascii PEM encoded.
            {
                in.reset();
                return readPEMCertificate(in);
            }
            else if (in.read() == 0x80)    // assume BER encoded.
            {
                in.reset();
                return readPKCS7Certificate(in);
            }
            else
            {
                in.reset();
                return readDERCertificate(in);
            }
        }
        catch (Exception e)
        {
            throw new CertificateException(e.toString());
        }
    }

    /**
     * Returns a (possibly empty) collection view of the certificates
     * read from the given input stream inStream.
     */
    public Collection engineGenerateCertificates(
        InputStream inStream) 
        throws CertificateException
    {
        Certificate     cert;
        List            certs = new ArrayList();

        while ((cert = engineGenerateCertificate(inStream)) != null)
        {
            certs.add(cert);
        }

        return certs;
    }

    /**
     * Generates a certificate revocation list (CRL) object and initializes
     * it with the data read from the input stream inStream.
     */
    public CRL engineGenerateCRL(
        InputStream inStream) 
        throws CRLException
    {
        if (!inStream.markSupported())
        {
            inStream = new BufferedInputStream(inStream);
        }

        try
        {
            inStream.mark(10);
            if (inStream.read() != 0x30)  // assume ascii PEM encoded.
            {
                inStream.reset();
                return readPEMCRL(inStream);
            }
            else
            {
                inStream.reset();
                return readDERCRL(inStream);
            }
        }
        catch (Exception e)
        {
            throw new CRLException(e.toString());
        }
    }

    /**
     * Returns a (possibly empty) collection view of the CRLs read from
     * the given input stream inStream.
     *
     * The inStream may contain a sequence of DER-encoded CRLs, or
     * a PKCS#7 CRL set.  This is a PKCS#7 SignedData object, with the
     * only signficant field being crls.  In particular the signature
     * and the contents are ignored.
     */
    public Collection engineGenerateCRLs(
        InputStream inStream) 
        throws CRLException
    {
        return null;
    }

    public Iterator engineGetCertPathEncodings()
    {
    return PKIXCertPath.certPathEncodings.iterator();
    }

    public CertPath engineGenerateCertPath(
        InputStream inStream)
    throws CertificateException
    {
        return engineGenerateCertPath(inStream, "PkiPath");
    }

    public CertPath engineGenerateCertPath(
        InputStream inStream,
        String encoding)
    throws CertificateException
    {
        return new PKIXCertPath(inStream, encoding);
    }

    public CertPath engineGenerateCertPath(
        List certificates)
    throws CertificateException
    {
        Iterator iter = certificates.iterator();
        Object obj;
        while (iter.hasNext())
        {
            obj = iter.next();
            if (obj != null)
            {
                if (!(obj instanceof X509Certificate))
                {
                    throw new CertificateException("list contains none X509Certificate object while creating CertPath\n" + obj.toString());
                }
            }
        }
        return new PKIXCertPath(certificates);
    }
}
