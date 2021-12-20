package org.bouncycastle.ocsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import org.bouncycastle.asn1.ocsp.CertStatus;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.bouncycastle.asn1.ocsp.RevokedInfo;
import org.bouncycastle.asn1.ocsp.SingleResponse;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.CRLReason;

/**
 * Generator for basic OCSP response objects.
 */
public class BasicOCSPRespGenerator
{
    private List            list = new ArrayList();
    private GeneralName     responderName = null;
    private X509Extensions  responseExtensions = null;
    private RespID          responderID;

    private class ResponseObject
    {
        CertificateID         certId;
        CertStatus            certStatus;
        DERGeneralizedTime    thisUpdate;
        DERGeneralizedTime    nextUpdate;
        X509Extensions        extensions;

        public ResponseObject(
            CertificateID     certId,
            CertificateStatus certStatus,
            Date              thisUpdate,
            Date              nextUpdate,
            X509Extensions    extensions)
        {
            this.certId = certId;

            if (certStatus == null)
            {
                this.certStatus = new CertStatus();
            }
            else if (certStatus instanceof UnknownStatus)
            {
                this.certStatus = new CertStatus(2, new DERNull());
            }
            else 
            {
                RevokedStatus rs = (RevokedStatus)certStatus;
                
                if (rs.hasRevocationReason())
                {
                    this.certStatus = new CertStatus(
                                            new RevokedInfo(new DERGeneralizedTime(rs.getRevocationTime()), new CRLReason(rs.getRevocationReason())));
                }
                else
                {
                    this.certStatus = new CertStatus(
                                            new RevokedInfo(new DERGeneralizedTime(rs.getRevocationTime()), null));
                }
            }

            this.thisUpdate = new DERGeneralizedTime(thisUpdate);
            
            if (nextUpdate != null)
            {
                this.nextUpdate = new DERGeneralizedTime(nextUpdate);
            }
            else
            {
                this.nextUpdate = null;
            }

            this.extensions = extensions;
        }

        public SingleResponse toResponse()
            throws Exception
        {
            return new SingleResponse(certId.toASN1Object(), certStatus, thisUpdate, nextUpdate, extensions);
        }
    }

    private DERObject makeObj(
        byte[]  encoding)
        throws IOException
    {
        if (encoding == null)
        {
            return null;
        }

        ByteArrayInputStream    bIn = new ByteArrayInputStream(encoding);
        ASN1InputStream         aIn = new ASN1InputStream(bIn);

        return aIn.readObject();
    }

    /**
     * basic constructor
     */
    public BasicOCSPRespGenerator(
        RespID  responderID)
    {
        this.responderID = responderID;
    }

    /**
     * construct with the responderID to be the SHA-1 keyHash of the passed in public key.
     */
    public BasicOCSPRespGenerator(
        PublicKey       key)
        throws OCSPException
    {
        this.responderID = new RespID(key);
    }

    /**
     * Add a response for a particular Certificate ID.
     * 
     * @param certID certificate ID details
     * @param certStatus status of the certificate - null if okay
     */
    public void addResponse(
        CertificateID       certID,
        CertificateStatus   certStatus)
    {
        list.add(new ResponseObject(certID, certStatus, new Date(), null, null));
    }

    /**
     * Add a response for a particular Certificate ID.
     * 
     * @param certID certificate ID details
     * @param certStatus status of the certificate - null if okay
     * @param singleExtensions optional extensions
     */
    public void addResponse(
        CertificateID       certID,
        CertificateStatus   certStatus,
        X509Extensions      singleExtensions)
    {
        list.add(new ResponseObject(certID, certStatus, new Date(), null, singleExtensions));
    }
    
    /**
     * Add a response for a particular Certificate ID.
     * 
     * @param certID certificate ID details
     * @param nextUpdate date when next update should be requested
     * @param certStatus status of the certificate - null if okay
     * @param singleExtensions optional extensions
     */
    public void addResponse(
        CertificateID       certID,
        CertificateStatus   certStatus,
        Date                nextUpdate,
        X509Extensions      singleExtensions)
    {
        list.add(new ResponseObject(certID, certStatus, new Date(), nextUpdate, singleExtensions));
    }
    
    /**
     * Add a response for a particular Certificate ID.
     * 
     * @param certID certificate ID details
     * @param thisUpdate date this response was valid on
     * @param nextUpdate date when next update should be requested
     * @param certStatus status of the certificate - null if okay
     * @param singleExtensions optional extensions
     */
    public void addResponse(
        CertificateID       certID,
        CertificateStatus   certStatus,
        Date                thisUpdate,
        Date                nextUpdate,
        X509Extensions      singleExtensions)
    {
        list.add(new ResponseObject(certID, certStatus, thisUpdate, nextUpdate, singleExtensions));
    }
    
    /**
     * Set the extensions for the response.
     * 
     * @param responseExtensions the extension object to carry.
     */
    public void setResponseExtensions(
        X509Extensions  responseExtensions)
    {
        this.responseExtensions = responseExtensions;
    }

    private BasicOCSPResp generateResponse(
        DERObjectIdentifier signingAlgorithm,
        PrivateKey          key,
        X509Certificate[]   chain,
        Date                producedAt,
        String              provider,
        SecureRandom        random)
        throws OCSPException, NoSuchProviderException
    {
        Iterator    it = list.iterator();

        ASN1EncodableVector responses = new ASN1EncodableVector();

        while (it.hasNext())
        {
            try
            {
                responses.add(((ResponseObject)it.next()).toResponse());
            }
            catch (Exception e)
            {
                throw new OCSPException("exception creating Request", e);
            }
        }

        ResponseData  tbsResp = new ResponseData(new DERInteger(0), responderID.toASN1Object(), new DERGeneralizedTime(producedAt), new DERSequence(responses), responseExtensions);

        java.security.Signature sig = null;

        try
        {
            sig = java.security.Signature.getInstance(signingAlgorithm.getId(), provider);
            if (random != null)
            {
                sig.initSign(key, random);
            }
            else
            {
                sig.initSign(key);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new OCSPException("exception creating signature: " + e, e);
        }
        catch (InvalidKeyException e)
        {
            throw new OCSPException("exception creating signature: " + e, e);
        }

        DERBitString    bitSig = null;

        try
        {
            ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
            DEROutputStream         dOut = new DEROutputStream(bOut);

            dOut.writeObject(tbsResp);

            sig.update(bOut.toByteArray());

            bitSig = new DERBitString(sig.sign());
        }
        catch (Exception e)
        {
            throw new OCSPException("exception processing TBSRequest: " + e, e);
        }

        AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(signingAlgorithm, new DERNull());

        if (chain != null && chain.length > 0)
        {
            ASN1EncodableVector v = new ASN1EncodableVector();
            try
            {
                for (int i = 0; i != chain.length; i++)
                {
                    v.add(new X509CertificateStructure(
                            (ASN1Sequence)makeObj(chain[i].getEncoded())));
                }
            }
            catch (IOException e)
            {
                throw new OCSPException("error processing certs", e);
            }
            catch (CertificateEncodingException e)
            {
                throw new OCSPException("error encoding certs", e);
            }

            return new BasicOCSPResp(new BasicOCSPResponse(tbsResp, sigAlgId, bitSig, new DERSequence(v)));
        }
        else
        {
            return new BasicOCSPResp(new BasicOCSPResponse(tbsResp, sigAlgId, bitSig, null));
        }
    }
    
    public BasicOCSPResp generate(
        String             signingAlgorithm,
        PrivateKey         key,
        X509Certificate[]  chain,
        Date               thisUpdate,
        String             provider)
        throws OCSPException, NoSuchProviderException, IllegalArgumentException
    {
        return generate(signingAlgorithm, key, chain, thisUpdate, provider, null);
    }

    public BasicOCSPResp generate(
        String             signingAlgorithm,
        PrivateKey         key,
        X509Certificate[]  chain,
        Date               producedAt,
        String             provider,
        SecureRandom       random)
        throws OCSPException, NoSuchProviderException, IllegalArgumentException
    {
        if (signingAlgorithm == null)
        {
            throw new IllegalArgumentException("no signing algorithm specified");
        }

        try
        {
            DERObjectIdentifier oid = OCSPUtil.getAlgorithmOID(signingAlgorithm);
    
            return generateResponse(oid, key, chain, producedAt, provider, random);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("unknown signing algorithm specified");
        }
    }
    
    /**
     * Return an iterator of the signature names supported by the generator.
     * 
     * @return an iterator containing recognised names.
     */
    public Iterator getSignatureAlgNames()
    {
        return OCSPUtil.getAlgNames();
    }
}
